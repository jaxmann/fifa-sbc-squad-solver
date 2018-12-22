import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Squad implements Serializable {

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

    public static Squad newAtPos(Squad currentSquad, int i, Player p) {

        Squad copy = currentSquad.deepClone();
//        copy.printSquad();
        copy.players.set(i, p);

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

    public static Squad createDefaultSquad() {

        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();


        try {
            Player neto = pl.getPlayer("neto", 84);
            Player kagawa = pl.getPlayer("kagawa", 83);
            Player navas = pl.getPlayer("navas", 87);
            Player gimenez = pl.getPlayer("", 84, "uruguay", "CB", "madrid", "");
            Player savic = pl.getPlayer("", 83, "montenegro", "CB", "madrid", "laliga");
            Player lucas = pl.getPlayer("lucas", 83, "spain", "", "madrid", "laliga");
            Player kroos = pl.getPlayer("kroos", 90);
            Player david = pl.getPlayer("david", 89);
            Player iniesta = pl.getPlayer("iniesta", 87);
            Player diego = pl.getPlayer("costa", 85);
            Player isco = pl.getPlayer("isco", 89);


            players.add(neto); //rb
            players.add(kagawa); //lb
            players.add(navas); //gk
            players.add(gimenez); //lcb
            players.add(savic); //rcb
            players.add(lucas); //rm
            players.add(kroos); //cdm
            players.add(david); //lm
            players.add(iniesta); //lst
            players.add(diego); //rst
            players.add(isco); //cam

            for (Player p : players) {
                System.out.println(p.toString());
            }
        } catch (PlayerNotFoundException e) {
            e.printName();
        }

        Squad s = new Squad(positions, players, f);

        return s;

    }


    public void printSquad() {
        System.out.println("----------SQUAD-------------");
        for(HashMap.Entry<Position, Player> entry : this.lineup.entrySet()) {
            Position pos = entry.getKey();
            Player player = entry.getValue();

            System.out.println("pos: " + pos.getActual() + "| player: " + player.getName() + "| rating: " + player.getRating() + "| price:" + player.getPrice());

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

    public Graph getGraph() {
        return graph;
    }

}
