import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class Squad implements Serializable {

    private static final String BRICKED_PLAYER_NAME = "brickedPlayer";

    private HashMap<Position, Player> lineup;
    private ArrayList<Position> positions;
    private ArrayList<Player> players;
    private Graph graph;

    public Squad(ArrayList<Position> positions, ArrayList<Player> players, Formation formation) {
        this.positions = positions;
        this.players = players;
        this.lineup = new HashMap<>();
        this.graph = formation.getGraph();

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
            if (entry.getKey().getActual().equals(brick.getPos().getActual())) {
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

    //this seems super inefficient... why does it need to make a copy
    //maybe also don't pass in int as "pos", kind of confusing
    public static Squad newAtPos(Squad currentSquad, int i, Player p) {

        //exit early if trying to change bricked player - prob better way to do this than checking name but w/e
        if (currentSquad.getPlayers().get(i).getName().equals("brickedPlayer")) {
            return currentSquad;
        }
        Squad copy = currentSquad.deepClone();
//        copy.printSquad();
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



    public void updateLineup() {
        for (int i = 0; i < this.positions.size(); i++) {
            this.lineup.put(this.positions.get(i), this.players.get(i));
        }
    }


    public ArrayList<Integer> getRatings() {
        ArrayList<Integer> ratings = new ArrayList<>();
        for(HashMap.Entry<Position, Player> entry : this.lineup.entrySet()) {
            Player player = entry.getValue();

            ratings.add(player.getRating());

        }

        return ratings;
    }

    public double getSquadPrice() {

        double totalPrice = 0.0;
        for(HashMap.Entry<Position, Player> entry : this.lineup.entrySet()) {
            Player player = entry.getValue();

            totalPrice += player.getPrice();

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

            System.out.println("pos: " + pos.getActual() + "| player: " + player.getName() + "| rating: " + player.getRating() + "| price: " + player.getPrice());

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

    public Player getPlayerAtPosition(BasePosition basePosition) {
        for (Map.Entry<Position, Player> entry : this.getLineup().entrySet()) {
            if (entry.getKey().getBasePos() == basePosition) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean doesSquadSatisfyConstraint(Constraint constraint) {
        switch(constraint.getConstraintType()) {
            case MINCHEM:
                return ChemistryEngine.calculateChemistry(this) >= constraint.getMinChem();
            case MINRATING:
                return this.getSquadRating() >= constraint.getMinRating();
            case BRICKS:
                for (int i=0; i<constraint.getBricks().size(); i++) {
                    if (!this.getPlayerAtPosition(constraint.getBricks().get(i).getPos().getBasePos()).getName().equals(BRICKED_PLAYER_NAME)) {
                        return false;
                    }
                }
                return true;
            case EXACT_OF_CARDTYPE:
                return true;
            case EXACT_OF_CLUB:
                return true;
            case EXACT_OF_LEAGUE:
                return true;
            case EXACT_OF_NATION:
                return true;
            case MIN_OF_CARDTYPE:
                return true;
            case MIN_OF_CLUB:
                return true;
            case MIN_OF_LEAGUE:
                return true;
            case MIN_OF_NATION:
                return true;
            case NUMPLAYERS:
                return true;
        }
        return true;
    }

    public Graph getGraph() {
        return graph;
    }

}
