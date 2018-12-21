import java.util.ArrayList;
import java.util.HashMap;

public class Squad {

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

            System.out.println("pos: " + pos.getActual() + " player: " + player.getName());

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

    public Graph getGraph() {
        return graph;
    }

}
