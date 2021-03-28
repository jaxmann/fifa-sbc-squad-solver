package squad;

import chemistry.ChemistryEngine;
import constraint.Brick;
import constraint.Constraint;
import constraint.Constraints;
import player.*;
import squad.formation.Formation;
import squad.formation.FormationFactory;
import squad.formation.Graph;

import java.io.Serializable;
import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Squad implements Serializable {

    public static final String BRICKED_PLAYER_NAME = "brickedPlayer";

    private HashMap<Position, Player> lineup;
    private ArrayList<Position> positions;
    private ArrayList<Player> players;
    private Graph graph;

    public Squad(ArrayList<Position> positions, ArrayList<Player> players, Formation formation) throws Exception {
        // to make sure LB = Mendy, for instance, make sure the index of LB = index of Mendy in each respective arraylist
        this.positions = positions;
        this.players = players;
        this.lineup = new HashMap<>();
        this.graph = formation.getGraph();

        // validate each requested position exists in formation
        if (!positions.stream().allMatch(position -> formation.getPositions().contains(position))) {
            throw new Exception("Invalid squad positions or formation generated");
        }

        if (!(new HashSet<>(positions).size() == positions.size())) {
            throw new Exception("11 unique positions required - at least one position was repeated");
        }

        for (int i = 0; i < this.positions.size(); i++) {
            this.lineup.put(this.positions.get(i), this.players.get(i));
        }

    }

    // make squad with random assortment of positions in 41212
    public Squad(ArrayList<Player> players) {
        this.players = players;
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        this.positions = f.getPositions();
        this.lineup = new HashMap<>();
        this.graph = f.getGraph();

        for (int i = 0; i < this.positions.size(); i++) {
            this.lineup.put(this.positions.get(i), this.players.get(i));
        }
    }

    // return true if match found
    public boolean setBrick(Brick brick) {
        Player brickPlayer = new Player(BRICKED_PLAYER_NAME, brick.getClub(), brick.getNation(), brick.getLeague(), brick.getPos().getBasePos());
        for (Map.Entry<Position, Player> entry : this.getLineup().entrySet()) {
            if (entry.getKey().getActualPosition().equals(brick.getPos().getActualPosition())) {
                this.getPlayers().remove(entry.getValue());
                this.getPlayers().add(brickPlayer);

                this.getLineup().remove(entry.getKey());
                this.getLineup().put(brick.getPos(), brickPlayer);

                return true;
            }
        }
        return false;
    }

    public Squad deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Squad) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //maybe also don't pass in int as "pos", kind of confusing
    public static Squad newAtPos(Squad currentSquad, int i, Player p) {

        //exit early if trying to change bricked player - prob better way to do this than checking name but w/e
        if (currentSquad.getPlayers().get(i).getName().equals("brickedPlayer")) {
            return currentSquad;
        }

        Squad copy = currentSquad.deepClone();
        copy.getPlayers().set(i, p);

        for (int j = 0; j < copy.getPositions().size(); j++) {
            copy.getLineup().put(copy.getPositions().get(j), copy.getPlayers().get(j));
        }

        return copy;
    }

    public static Squad replacePlayer(Squad currentSquad, Player oldPlayer, Player newPlayer) {

        //exit early if player not found OR trying to change bricked player OR player already exists once
        if (currentSquad.getPlayers().indexOf(oldPlayer) < 0 || oldPlayer.getName().equals(Squad.BRICKED_PLAYER_NAME) || currentSquad.getPlayers().indexOf(newPlayer) >= 0) {
            return currentSquad;
        }

        Squad copy = currentSquad.deepClone();

        copy.getPlayers().set(copy.getPlayers().indexOf(oldPlayer), newPlayer);

        for (Map.Entry<Position, Player> entry : copy.getLineup().entrySet()) {
            if (entry.getValue().equals(oldPlayer)) {
                entry.setValue(newPlayer);
            }
        }

        return copy;
    }

    public static Player getPlayerAtActualPos(Squad currentSquad, ActualPosition actualPosition) {

        for (Map.Entry<Position, Player> entry : currentSquad.getLineup().entrySet()) {
            if (entry.getKey().getActualPosition() == actualPosition) {
                return entry.getValue();
            }
        }

        // player not found
        return null;
    }

    public static Squad swapNRandomPlayers(int numPlayersToSwap, Squad currentSquad, ArrayList<Player> availablePlayers) {

        LinkedList<Integer> indicesToReplace = getNRandomDistinctIndicesInRange(numPlayersToSwap, 11);
        LinkedList<Integer> playerIndicesToSwapIn = getNRandomDistinctIndicesInRange(numPlayersToSwap, availablePlayers.size());

        Squad copy = currentSquad.deepClone();

        int indToReplace;
        int indToAdd;

        while (!indicesToReplace.isEmpty()) {
            indToReplace = indicesToReplace.pop();
            indToAdd = playerIndicesToSwapIn.pop();
            //exit early if trying to change bricked player - prob better way to do this than checking name but w/e
            if (currentSquad.getPlayers().get(indToReplace).getName().equals(Squad.BRICKED_PLAYER_NAME)) {
                return currentSquad;
            }

            copy.getPlayers().remove(copy.getLineup().get(copy.getPositions().get(indToReplace)));
            copy.getPlayers().add(availablePlayers.get(indToAdd));
            copy.getLineup().put(copy.getPositions().get(indToReplace), availablePlayers.get(indToAdd));

        }

        return copy;
    }

    // try to find cheaper players at same rating without affecting overall chemistry
    public static Squad optimizeRatingWithoutReducingChem(Squad currentSquad, int numPlayersToTry) throws IOException {

        double currentChemistry = ChemistryEngine.calculateChemistry(currentSquad);
        PlayerLoaderUtil pl = PlayerLoaderUtil.getInstance();

        for (Map.Entry<Position, Player> entry : currentSquad.getLineup().entrySet()) {
            Player currentPlayer = entry.getValue();
            // make a copy of the min heap so we don't affect shared object
            PriorityQueue<Player> playersAtRating = new PriorityQueue<>(pl.getByRating().get(currentPlayer.getRating()));
            // try n players at player's position
            for (int i=0; i<numPlayersToTry; i++) {
                if (!playersAtRating.isEmpty()) {
                    Player proposedPlayer = playersAtRating.remove();
                    if (proposedPlayer.getPrice() > currentPlayer.getPrice() || currentPlayer.equals(proposedPlayer)) {
                        // it's a min heap so any further players will be even more expensive, can break out of loop here
                        break;
                    } else {
                        Squad proposedSquad = Squad.replacePlayer(currentSquad, currentPlayer, proposedPlayer);
                        double newChemistry;
                        if ((newChemistry = ChemistryEngine.calculateChemistry(proposedSquad)) >= currentChemistry) {
                            // if found cheaper squad with player replaced (same rating -> same squad rating) but cheaper w/ same or better chem, update
                            currentPlayer = proposedPlayer;
                            currentSquad = proposedSquad;
                            currentChemistry = newChemistry;
                        }
                    }
                }
            }
        }

        return currentSquad;
    }


    // try to fix problem of not being able to swap out CB Joe Gomez, for instance, b/c he links to alisson and CB Walker, although way too expensive
    // 1. try to swap random players around in squad (maybe use shuffle function) - maybe should be higher level
    // 2. for any strong (double) links, get all players with same links and try to swap them in, and replace if rating/price is better
    public static Squad optimizeChemWithoutReducingRating(Squad currentSquad, int numPlayersToTry) {
        return null;
    }

    // return possible ways to generate N rated squad
    public static ArrayList<ArrayList<Integer>> getPossibleCombinationsForSquadRating(int rating) {
        // pattern is very simple if you have any list of combinations - rating for 89 is just 1 less than all ratings for 90 etc
        HashMap<Integer, ArrayList<ArrayList<Integer>>> mapping = new HashMap<>();
        ArrayList<ArrayList<Integer>> rating90 = new ArrayList<>();
        rating90.add(new ArrayList<>(Arrays.asList(88,89,89,89,90,90,90,90,90,90,91)));
        rating90.add(new ArrayList<>(Arrays.asList(89,89,90,90,90,90,90,90,90,90,90)));
        rating90.add(new ArrayList<>(Arrays.asList(89,89,89,89,89,89,90,90,90,91,91)));
        rating90.add(new ArrayList<>(Arrays.asList(85,90,90,90,90,90,90,90,90,90,90)));
        rating90.add(new ArrayList<>(Arrays.asList(88,89,89,89,89,89,89,90,91,91,91)));
        rating90.add(new ArrayList<>(Arrays.asList(88,88,88,89,89,89,90,90,91,91,91)));
        rating90.add(new ArrayList<>(Arrays.asList(88,89,89,89,89,89,89,90,90,91,92)));
        rating90.add(new ArrayList<>(Arrays.asList(89,89,89,89,89,89,90,90,90,90,92)));
        rating90.add(new ArrayList<>(Arrays.asList(89,89,89,89,89,89,89,89,89,92,92)));
        rating90.add(new ArrayList<>(Arrays.asList(88,88,88,88,88,88,90,91,91,91,91)));
        rating90.add(new ArrayList<>(Arrays.asList(87,88,88,88,88,89,90,91,91,91,91)));
        rating90.add(new ArrayList<>(Arrays.asList(88,89,89,89,89,89,89,90,90,90,93)));
        rating90.add(new ArrayList<>(Arrays.asList(88,89,89,89,89,89,89,90,90,90,93)));
        rating90.add(new ArrayList<>(Arrays.asList(88,88,89,89,89,89,89,89,89,92,93)));
        rating90.add(new ArrayList<>(Arrays.asList(87,89,89,89,89,89,89,89,89,92,93)));
        rating90.add(new ArrayList<>(Arrays.asList(87,89,89,89,89,89,89,89,89,92,93)));
        rating90.add(new ArrayList<>(Arrays.asList(86,86,86,86,87,89,91,91,91,91,93)));
        mapping.put(90, rating90);
        ArrayList<ArrayList<Integer>> rating89 = new ArrayList<>();
        rating89.add(new ArrayList<>(Arrays.asList(87,88,88,88,89,89,89,89,89,89,90)));
        rating89.add(new ArrayList<>(Arrays.asList(88,88,89,89,89,89,89,89,89,89,89)));
        rating89.add(new ArrayList<>(Arrays.asList(88,88,88,88,88,88,89,89,89,90,90)));
        rating89.add(new ArrayList<>(Arrays.asList(84,89,89,89,89,89,89,89,89,89,89)));
        rating89.add(new ArrayList<>(Arrays.asList(87,88,88,88,88,88,88,89,90,90,90)));
        rating89.add(new ArrayList<>(Arrays.asList(87,87,87,88,88,88,89,89,90,90,90)));
        rating89.add(new ArrayList<>(Arrays.asList(87,88,88,88,88,88,88,89,89,90,91)));
        rating89.add(new ArrayList<>(Arrays.asList(88,88,88,88,88,88,89,89,89,89,91)));
        rating89.add(new ArrayList<>(Arrays.asList(88,88,88,88,88,88,88,88,88,91,91)));
        rating89.add(new ArrayList<>(Arrays.asList(87,87,87,87,87,87,89,90,90,90,90)));
        rating89.add(new ArrayList<>(Arrays.asList(86,87,87,87,87,88,89,90,90,90,90)));
        rating89.add(new ArrayList<>(Arrays.asList(87,88,88,88,88,88,88,89,89,89,92)));
        rating89.add(new ArrayList<>(Arrays.asList(87,87,87,88,88,88,89,89,89,89,92)));
        rating89.add(new ArrayList<>(Arrays.asList(86,88,88,88,88,88,88,88,88,91,92)));
        rating89.add(new ArrayList<>(Arrays.asList(85,85,85,85,86,88,90,90,90,90,92)));
        mapping.put(89, rating89);
        ArrayList<ArrayList<Integer>> rating88 = new ArrayList<>();
        rating88.add(new ArrayList<>(Arrays.asList(86,87,87,87,88,88,88,88,88,88,89)));
        rating88.add(new ArrayList<>(Arrays.asList(87,87,88,88,88,88,88,88,88,88,88)));
        rating88.add(new ArrayList<>(Arrays.asList(87,87,87,87,87,87,88,88,88,89,89)));
        rating88.add(new ArrayList<>(Arrays.asList(83,88,88,88,88,88,88,88,88,88,88)));
        rating88.add(new ArrayList<>(Arrays.asList(86,87,87,87,87,87,87,88,89,89,89)));
        rating88.add(new ArrayList<>(Arrays.asList(86,86,86,87,87,87,88,88,89,89,89)));
        rating88.add(new ArrayList<>(Arrays.asList(86,87,87,87,87,87,87,88,88,89,90)));
        rating88.add(new ArrayList<>(Arrays.asList(87,87,87,87,87,87,88,88,88,88,90)));
        rating88.add(new ArrayList<>(Arrays.asList(87,87,87,87,87,87,87,87,87,90,90)));
        rating88.add(new ArrayList<>(Arrays.asList(86,86,86,86,86,86,88,89,89,89,89)));
        rating88.add(new ArrayList<>(Arrays.asList(85,86,86,86,86,87,88,89,89,89,89)));
        rating88.add(new ArrayList<>(Arrays.asList(86,87,87,87,87,87,87,88,88,88,91)));
        rating88.add(new ArrayList<>(Arrays.asList(86,86,86,87,87,87,88,88,88,88,91)));
        rating88.add(new ArrayList<>(Arrays.asList(86,86,87,87,87,87,87,87,87,90,91)));
        rating88.add(new ArrayList<>(Arrays.asList(85,87,87,87,87,87,87,87,87,90,91)));
        rating88.add(new ArrayList<>(Arrays.asList(84,84,84,84,85,87,89,89,89,89,91)));
        mapping.put(88, rating88);
        ArrayList<ArrayList<Integer>> rating87 = new ArrayList<>();
        rating87.add(new ArrayList<>(Arrays.asList(85,86,86,86,87,87,87,87,87,87,88)));
        rating87.add(new ArrayList<>(Arrays.asList(86,86,87,87,87,87,87,87,87,87,87)));
        rating87.add(new ArrayList<>(Arrays.asList(86,86,86,86,86,86,87,87,87,88,88)));
        rating87.add(new ArrayList<>(Arrays.asList(82,87,87,87,87,87,87,87,87,87,87)));
        rating87.add(new ArrayList<>(Arrays.asList(85,86,86,86,86,86,86,87,88,88,88)));
        rating87.add(new ArrayList<>(Arrays.asList(85,85,85,86,86,86,87,87,88,88,88)));
        rating87.add(new ArrayList<>(Arrays.asList(85,86,86,86,86,86,86,87,87,88,89)));
        rating87.add(new ArrayList<>(Arrays.asList(86,86,86,86,86,86,87,87,87,87,89)));
        rating87.add(new ArrayList<>(Arrays.asList(86,86,86,86,86,86,86,86,86,89,89)));
        rating87.add(new ArrayList<>(Arrays.asList(85,85,85,85,85,85,87,88,88,88,88)));
        rating87.add(new ArrayList<>(Arrays.asList(84,85,85,85,85,86,87,88,88,88,88)));
        rating87.add(new ArrayList<>(Arrays.asList(85,86,86,86,86,86,86,87,87,87,90)));
        rating87.add(new ArrayList<>(Arrays.asList(85,85,85,86,86,86,87,87,87,87,90)));
        rating87.add(new ArrayList<>(Arrays.asList(85,85,86,86,86,86,86,86,86,89,90)));
        rating87.add(new ArrayList<>(Arrays.asList(84,86,86,86,86,86,86,86,86,89,90)));
        rating87.add(new ArrayList<>(Arrays.asList(83,83,83,83,84,86,88,88,88,88,90)));
        mapping.put(87, rating87);
        ArrayList<ArrayList<Integer>> rating86 = new ArrayList<>();
        rating86.add(new ArrayList<>(Arrays.asList(84,85,85,85,86,86,86,86,86,86,87)));
        rating86.add(new ArrayList<>(Arrays.asList(85,85,86,86,86,86,86,86,86,86,86)));
        rating86.add(new ArrayList<>(Arrays.asList(85,85,85,85,85,85,86,86,86,87,87)));
        rating86.add(new ArrayList<>(Arrays.asList(81,86,86,86,86,86,86,86,86,86,86)));
        rating86.add(new ArrayList<>(Arrays.asList(84,85,85,85,85,85,85,86,87,87,87)));
        rating86.add(new ArrayList<>(Arrays.asList(84,84,84,85,85,85,86,86,87,87,87)));
        rating86.add(new ArrayList<>(Arrays.asList(84,85,85,85,85,85,85,86,86,87,88)));
        rating86.add(new ArrayList<>(Arrays.asList(85,85,85,85,85,85,86,86,86,86,88)));
        rating86.add(new ArrayList<>(Arrays.asList(85,85,85,85,85,85,85,85,85,88,88)));
        rating86.add(new ArrayList<>(Arrays.asList(84,84,84,84,84,84,86,87,87,87,87)));
        rating86.add(new ArrayList<>(Arrays.asList(83,84,84,84,84,85,86,87,87,87,87)));
        rating86.add(new ArrayList<>(Arrays.asList(84,85,85,85,85,85,85,86,86,86,89)));
        rating86.add(new ArrayList<>(Arrays.asList(84,84,84,85,85,85,86,86,86,86,89)));
        rating86.add(new ArrayList<>(Arrays.asList(84,84,85,85,85,85,85,85,85,88,89)));
        rating86.add(new ArrayList<>(Arrays.asList(83,85,85,85,85,85,85,85,85,88,89)));
        rating86.add(new ArrayList<>(Arrays.asList(82,82,82,82,83,85,87,87,87,87,89)));
        mapping.put(86, rating86);
        ArrayList<ArrayList<Integer>> rating85 = new ArrayList<>();
        rating85.add(new ArrayList<>(Arrays.asList(83,84,84,84,85,85,85,85,85,85,86)));
        rating85.add(new ArrayList<>(Arrays.asList(84,84,85,85,85,85,85,85,85,85,85)));
        rating85.add(new ArrayList<>(Arrays.asList(84,84,84,84,84,84,85,85,85,86,86)));
        rating85.add(new ArrayList<>(Arrays.asList(80,85,85,85,85,85,85,85,85,85,85)));
        rating85.add(new ArrayList<>(Arrays.asList(83,84,84,84,84,84,84,85,86,86,86)));
        rating85.add(new ArrayList<>(Arrays.asList(83,83,83,84,84,84,85,85,86,86,86)));
        rating85.add(new ArrayList<>(Arrays.asList(83,84,84,84,84,84,84,85,85,86,87)));
        rating85.add(new ArrayList<>(Arrays.asList(84,84,84,84,84,84,85,85,85,85,87)));
        rating85.add(new ArrayList<>(Arrays.asList(84,84,84,84,84,84,84,84,84,87,87)));
        rating85.add(new ArrayList<>(Arrays.asList(83,83,83,83,83,83,85,86,86,86,86)));
        rating85.add(new ArrayList<>(Arrays.asList(82,83,83,83,83,84,85,86,86,86,86)));
        rating85.add(new ArrayList<>(Arrays.asList(83,84,84,84,84,84,84,85,85,85,88)));
        rating85.add(new ArrayList<>(Arrays.asList(83,83,83,84,84,84,85,85,85,85,88)));
        rating85.add(new ArrayList<>(Arrays.asList(83,83,84,84,84,84,84,84,84,87,88)));
        rating85.add(new ArrayList<>(Arrays.asList(82,84,84,84,84,84,84,84,84,87,88)));
        rating85.add(new ArrayList<>(Arrays.asList(81,81,81,81,82,84,86,86,86,86,88)));
        mapping.put(85, rating85);
        ArrayList<ArrayList<Integer>> rating84 = new ArrayList<>();
        rating84.add(new ArrayList<>(Arrays.asList(82,83,83,83,84,84,84,84,84,84,85)));
        rating84.add(new ArrayList<>(Arrays.asList(83,83,84,84,84,84,84,84,84,84,84)));
        rating84.add(new ArrayList<>(Arrays.asList(83,83,83,83,83,83,84,84,84,85,85)));
        rating84.add(new ArrayList<>(Arrays.asList(79,84,84,84,84,84,84,84,84,84,84)));
        rating84.add(new ArrayList<>(Arrays.asList(82,83,83,83,83,83,83,84,85,85,85)));
        rating84.add(new ArrayList<>(Arrays.asList(82,82,82,83,83,83,84,84,85,85,85)));
        rating84.add(new ArrayList<>(Arrays.asList(82,83,83,83,83,83,83,84,84,85,86)));
        rating84.add(new ArrayList<>(Arrays.asList(83,83,83,83,83,83,84,84,84,84,86)));
        rating84.add(new ArrayList<>(Arrays.asList(83,83,83,83,83,83,83,83,83,86,86)));
        rating84.add(new ArrayList<>(Arrays.asList(82,82,82,82,82,82,84,85,85,85,85)));
        rating84.add(new ArrayList<>(Arrays.asList(81,82,82,82,82,83,84,85,85,85,85)));
        rating84.add(new ArrayList<>(Arrays.asList(82,83,83,83,83,83,83,84,84,84,87)));
        rating84.add(new ArrayList<>(Arrays.asList(82,82,82,83,83,83,84,84,84,84,87)));
        rating84.add(new ArrayList<>(Arrays.asList(82,82,83,83,83,83,83,83,83,86,87)));
        rating84.add(new ArrayList<>(Arrays.asList(81,83,83,83,83,83,83,83,83,86,87)));
        rating84.add(new ArrayList<>(Arrays.asList(80,80,80,80,81,83,85,85,85,85,87)));
        mapping.put(84, rating84);
        ArrayList<ArrayList<Integer>> rating83 = new ArrayList<>();
        rating83.add(new ArrayList<>(Arrays.asList(81,82,82,82,83,83,83,83,83,83,84)));
        rating83.add(new ArrayList<>(Arrays.asList(82,82,83,83,83,83,83,83,83,83,83)));
        rating83.add(new ArrayList<>(Arrays.asList(82,82,82,82,82,82,83,83,83,84,84)));
        rating83.add(new ArrayList<>(Arrays.asList(78,83,83,83,83,83,83,83,83,83,83)));
        rating83.add(new ArrayList<>(Arrays.asList(81,82,82,82,82,82,82,83,84,84,84)));
        rating83.add(new ArrayList<>(Arrays.asList(81,81,81,82,82,82,83,83,84,84,84)));
        rating83.add(new ArrayList<>(Arrays.asList(81,82,82,82,82,82,82,83,83,84,85)));
        rating83.add(new ArrayList<>(Arrays.asList(82,82,82,82,82,82,83,83,83,83,85)));
        rating83.add(new ArrayList<>(Arrays.asList(82,82,82,82,82,82,82,82,82,85,85)));
        rating83.add(new ArrayList<>(Arrays.asList(81,81,81,81,81,81,83,84,84,84,84)));
        rating83.add(new ArrayList<>(Arrays.asList(80,81,81,81,81,82,83,84,84,84,84)));
        rating83.add(new ArrayList<>(Arrays.asList(81,82,82,82,82,82,82,83,83,83,86)));
        rating83.add(new ArrayList<>(Arrays.asList(81,81,81,82,82,82,83,83,83,83,86)));
        rating83.add(new ArrayList<>(Arrays.asList(81,81,82,82,82,82,82,82,82,85,86)));
        rating83.add(new ArrayList<>(Arrays.asList(80,82,82,82,82,82,82,82,82,85,86)));
        rating83.add(new ArrayList<>(Arrays.asList(80,80,80,80,80,82,84,84,84,84,86)));
        mapping.put(83, rating83);
        ArrayList<ArrayList<Integer>> rating82 = new ArrayList<>();
        rating82.add(new ArrayList<>(Arrays.asList(80,81,81,81,82,82,82,82,82,82,83)));
        rating82.add(new ArrayList<>(Arrays.asList(81,81,82,82,82,82,82,82,82,82,82)));
        rating82.add(new ArrayList<>(Arrays.asList(81,81,81,81,81,81,82,82,82,83,83)));
        rating82.add(new ArrayList<>(Arrays.asList(77,82,82,82,82,82,82,82,82,82,82)));
        rating82.add(new ArrayList<>(Arrays.asList(80,81,81,81,81,81,81,82,83,83,83)));
        rating82.add(new ArrayList<>(Arrays.asList(80,80,80,81,81,81,82,82,83,83,83)));
        rating82.add(new ArrayList<>(Arrays.asList(80,81,81,81,81,81,81,82,82,83,84)));
        rating82.add(new ArrayList<>(Arrays.asList(81,81,81,81,81,81,82,82,82,82,84)));
        rating82.add(new ArrayList<>(Arrays.asList(81,81,81,81,81,81,81,81,81,84,84)));
        rating82.add(new ArrayList<>(Arrays.asList(80,80,80,80,80,80,82,83,83,83,83)));
        rating82.add(new ArrayList<>(Arrays.asList(79,80,80,80,80,81,82,83,83,83,83)));
        rating82.add(new ArrayList<>(Arrays.asList(80,81,81,81,81,81,81,82,82,82,85)));
        rating82.add(new ArrayList<>(Arrays.asList(80,80,80,81,81,81,82,82,82,82,85)));
        rating82.add(new ArrayList<>(Arrays.asList(80,80,81,81,81,81,81,81,81,84,85)));
        rating82.add(new ArrayList<>(Arrays.asList(79,81,81,81,81,81,81,81,81,84,85)));
        rating82.add(new ArrayList<>(Arrays.asList(79,79,79,79,79,81,83,83,83,83,85)));
        mapping.put(82, rating82);
        ArrayList<ArrayList<Integer>> rating81 = new ArrayList<>();
        rating81.add(new ArrayList<>(Arrays.asList(79,80,80,80,81,81,81,81,81,81,82)));
        rating81.add(new ArrayList<>(Arrays.asList(80,80,81,81,81,81,81,81,81,81,81)));
        rating81.add(new ArrayList<>(Arrays.asList(80,80,80,80,80,80,81,81,81,82,82)));
        rating81.add(new ArrayList<>(Arrays.asList(76,81,81,81,81,81,81,81,81,81,81)));
        rating81.add(new ArrayList<>(Arrays.asList(79,80,80,80,80,80,80,81,82,82,82)));
        rating81.add(new ArrayList<>(Arrays.asList(79,79,79,80,80,80,81,81,82,82,82)));
        rating81.add(new ArrayList<>(Arrays.asList(79,80,80,80,80,80,80,81,81,82,83)));
        rating81.add(new ArrayList<>(Arrays.asList(80,80,80,80,80,80,81,81,81,81,83)));
        rating81.add(new ArrayList<>(Arrays.asList(80,80,80,80,80,80,80,80,80,83,83)));
        rating81.add(new ArrayList<>(Arrays.asList(79,79,79,79,79,79,81,82,82,82,82)));
        rating81.add(new ArrayList<>(Arrays.asList(78,79,79,79,79,80,81,82,82,82,82)));
        rating81.add(new ArrayList<>(Arrays.asList(79,80,80,80,80,80,80,81,81,81,84)));
        rating81.add(new ArrayList<>(Arrays.asList(79,79,79,80,80,80,81,81,81,81,84)));
        rating81.add(new ArrayList<>(Arrays.asList(79,79,80,80,80,80,80,80,80,83,84)));
        rating81.add(new ArrayList<>(Arrays.asList(78,80,80,80,80,80,80,80,80,83,84)));
        rating81.add(new ArrayList<>(Arrays.asList(78,78,78,78,78,80,82,82,82,82,84)));
        mapping.put(81, rating81);

        if (mapping.containsKey(rating)) {
            return mapping.get(rating);
        }
        return null;
    }

    public static Squad shuffleSquadForBetterChemistry(Squad squad) {
        return null;
    }


    public static LinkedList<Integer> getNRandomDistinctIndicesInRange(int numValues, int maxRange) {
        HashSet<Integer> randomIndices = new HashSet<>();
        Random random = new Random();
        while (randomIndices.size() < numValues) {
            int randNum = random.nextInt(maxRange);
            if (!randomIndices.contains(randNum)) {
                randomIndices.add(randNum);
            }
        }
        return new LinkedList<>(randomIndices);
    }

    public void updateAtPos(int i, Player p) {
        this.players.set(i, p);
        this.updateLineup();
    }

    public void updateAtPos(ActualPosition uniqueActualPos, Player p) {
        for (Map.Entry<Position, Player> entry : this.getLineup().entrySet()) {
            if (entry.getKey().getActualPosition().equals(uniqueActualPos)) {
                this.getLineup().put(entry.getKey(), p);
            }
        }
    }

    public void updateLineup() {
        for (int i = 0; i < this.getPositions().size(); i++) {
            this.getLineup().put(this.getPositions().get(i), this.getPlayers().get(i));
        }
    }

    // get list of rating of current lineup
    public ArrayList<Integer> getRatings() {
        ArrayList<Integer> ratings = new ArrayList<>();
        for(HashMap.Entry<Position, Player> entry : this.getLineup().entrySet()) {
            ratings.add(entry.getValue().getRating());
        }
        return ratings;
    }

    // get total price of squad
    public double getSquadPrice() {
        double totalPrice = 0.0;
        for(HashMap.Entry<Position, Player> entry : this.getLineup().entrySet()) {
            totalPrice += entry.getValue().getPrice();
        }
        return totalPrice;
    }

    public double getSquadRating() {
        ArrayList<Integer> ratings = this.getRatings();

        double playerSum = 0;
        for (int r : ratings) {
            playerSum += r;
        }

        double avg = playerSum / 11;

        double totExcess = 0.0;
        for (int r : ratings) {
            if (r >= avg) {
                totExcess += (r - avg);
            }
        }

        double teamTot = Math.round(playerSum + totExcess) / 11;
        double finalRating = Math.floor(teamTot);

        return finalRating;
    }

    public double getFractionalSquadRating() {
        ArrayList<Integer> ratings = this.getRatings();

        double playerSum = 0;
        for (int r : ratings) {
            playerSum += r;
        }

        double avg = playerSum / 11;

        double totExcess = 0.0;
        for (int r : ratings) {
            if (r >= avg) {
                totExcess += (r - avg);
            }
        }

        double teamTot = Math.round(playerSum + totExcess) / 11;

//        double finalRating = Math.floor(teamTot);

        return teamTot;
    }

    public void printSquad() {
        System.out.println("----------SQUAD-------------");
        for(HashMap.Entry<Position, Player> entry : this.lineup.entrySet()) {
            Position pos = entry.getKey();
            Player player = entry.getValue();

            System.out.println("pos: " + pos.getActualPosition().toString() + " | player: " + player.getName() + " | rating: " + player.getRating() + " | price: " + player.getPrice());
        }
    }

    public HashMap<Position, Player> getLineup() {
        return lineup;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<String> getPlayersAsString() {
        ArrayList<String> asString = new ArrayList<>();

        for (Player p: players) {
            asString.add(p.getName());
        }

        return asString;
    }

    public Player getPlayerAtPosition(String position) {
        for (Map.Entry<Position, Player> entry : this.getLineup().entrySet()) {
            if (entry.getKey().getActualPosition().toString().equals(position)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public int getNumOfCardType(CardType cardType) {
        int numOfCardType = 0;
        for (Player player : this.getLineup().values()) {
            if (player.getVersion().equals(cardType)) {
                numOfCardType++;
            }
        }
        return numOfCardType;
    }

    public int getNumOfNation(String nation) {
        int numOfNation = 0;
        for (Player player : this.getLineup().values()) {
            if (player.getNation().equals(nation)) {
                numOfNation++;
            }
        }
        return numOfNation;
    }

    public int getNumOfLeague(String league) {
        int numOfLeague = 0;
        for (Player player : this.getLineup().values()) {
            if (player.getLeague().equals(league)) {
                numOfLeague++;
            }
        }
        return numOfLeague;
    }

    public int getNumOfTeam(String team) {
        int numOfTeam = 0;
        for (Player player : this.getLineup().values()) {
            if (player.getTeam().equals(team)) {
                numOfTeam++;
            }
        }
        return numOfTeam;
    }

    // get number of non-bricked players
    public int getNumPlayers() {
        int numPlayers = 0;
        for (Player player : this.getLineup().values()) {
            // bricked players have reserved name
            if (!player.getName().equals(BRICKED_PLAYER_NAME)) {
                numPlayers++;
            }
        }
        return numPlayers;
    }

    // alias because i'll forget
    public int getNumOfClub(String club) {
        return getNumOfTeam(club);
    }

    public boolean doesSquadSatisfyAllConstaints(Constraints constraints) {
        return constraints.getConstraints().stream().allMatch(constraint -> this.doesSquadSatisfyConstraint(constraint));
    }

    public boolean doesSquadSatisfyConstraint(Constraint constraint) {
        switch(constraint.getConstraintType()) {
            case MINCHEM:
                return ChemistryEngine.calculateChemistry(this) >= constraint.getMinChem();
            case MINRATING:
                return this.getSquadRating() >= constraint.getMinRating();
            case BRICKS:
                for (int i=0; i<constraint.getBricks().size(); i++) {
                    Player playerAtPos = this.getPlayerAtPosition(constraint.getBricks().get(i).getPos().getActualPosition().toString());
                    if (!playerAtPos.getName().equals(BRICKED_PLAYER_NAME)) {
                        return false;
                    }
                    if (playerAtPos.getNation() != null && !playerAtPos.getNation().equals(constraint.getBricks().get(i).getNation())) {
                        return false;
                    }
                    if (playerAtPos.getLeague() != null && !playerAtPos.getLeague().equals(constraint.getBricks().get(i).getLeague())) {
                        return false;
                    }
                    if (playerAtPos.getTeam() != null && !playerAtPos.getTeam().equals(constraint.getBricks().get(i).getTeam())) {
                        return false;
                    }
                }
                return true;
            case EXACT_OF_CARDTYPE:
                return getNumOfCardType(constraint.getCardType()) == constraint.getExact_of_cardtype();
            case EXACT_OF_CLUB:
                return getNumOfClub(constraint.getClub()) == constraint.getExact_of_club();
            case EXACT_OF_LEAGUE:
                return getNumOfLeague(constraint.getLeague()) == constraint.getExact_of_league();
            case EXACT_OF_NATION:
                return getNumOfNation(constraint.getNation()) == constraint.getExact_of_nation();
            case MIN_OF_CARDTYPE:
                return getNumOfCardType(constraint.getCardType()) >= constraint.getMin_of_cardtype();
            case MIN_OF_CLUB:
                return getNumOfClub(constraint.getClub()) >= constraint.getMin_of_club();
            case MIN_OF_LEAGUE:
                return getNumOfLeague(constraint.getLeague()) >= constraint.getMin_of_league();
            case MIN_OF_NATION:
                return getNumOfNation(constraint.getNation()) >= constraint.getMin_of_nation();
            case NUMPLAYERS:
                // this constraint should really just be created by bricking any unavailable player slots
                return true;
            default:
                return true;
        }
    }

    public Graph getGraph() {
        return graph;
    }

}
