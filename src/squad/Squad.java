package squad;

import chemistry.ChemistryEngine;
import constraint.Brick;
import constraint.Constraint;
import constraint.Constraints;
import player.CardType;
import player.ActualPosition;
import squad.formation.Formation;
import squad.formation.FormationFactory;
import squad.formation.Graph;
import player.Player;
import player.Position;

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
