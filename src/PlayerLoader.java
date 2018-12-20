import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PlayerLoader {

    //do union of lists instead of sql queries
    private HashMap<String, ArrayList<Player>> byTeam;
    private HashMap<String, ArrayList<Player>> byLeague;
    private HashMap<String, ArrayList<Player>> byNation;
    private HashMap<String, ArrayList<Player>> byPos;
    private HashMap<Integer, ArrayList<Player>> byRating;
    private ArrayList<Player> allPlayers;

    public void loadPlayers() {
        String csvFile = "./resources/FutBinCards19.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        byTeam = new HashMap<>();
        byLeague = new HashMap<>();
        byNation = new HashMap<>();
        byPos = new HashMap<>();
        byRating = new HashMap<>();
        allPlayers = new ArrayList<>();


        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                String[] player = line.split(cvsSplitBy);

                if (player.length == 8) {

                    String pos = player[5];
                    String name = player[1];
                    String team = player[0];
                    String league = player[2];
                    String nation = player[3];
                    String price = player[7];
                    String version = player[6];
                    int rating = Integer.parseInt(player[4]);

                    if (!team.equals("Icons")) {

                        Player p = new Player(name, team, nation, league, pos, version, price, rating, false);

                        allPlayers.add(p);

                        if (!byTeam.containsKey(team)) {
                            byTeam.put(team, new ArrayList<>());
                        }
                        byTeam.get(team).add(p);

                        if (!byLeague.containsKey(league)) {
                            byLeague.put(league, new ArrayList<>());
                        }
                        byLeague.get(league).add(p);

                        if (!byNation.containsKey(nation)) {
                            byNation.put(nation, new ArrayList<>());
                        }
                        byNation.get(nation).add(p);

                        if (!byPos.containsKey(pos)) {
                            byPos.put(pos, new ArrayList<>());
                        }
                        byPos.get(pos).add(p);

                        if (!byRating.containsKey(rating)) {
                            byRating.put(rating, new ArrayList<>());
                        }
                        byRating.get(rating).add(p);
                    }

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public ArrayList<Player> get11FrenchPlayers() {
        ArrayList<Player> frenchPlayers = new ArrayList<>();

        for (int i=0; i<11; i++) {
            frenchPlayers.add(byNation.get("France").get(i));
        }

        return frenchPlayers;
    }

    public ArrayList<Player> get11RandomGoldPlayers() {
        ArrayList<Player> randomGolds = new ArrayList<>();
        Random randomGenerator = new Random();
        randomGenerator.setSeed(10);

        while (randomGolds.size() != 11) {
            int index = randomGenerator.nextInt(allPlayers.size());
            Player candidate = allPlayers.get(index);
            if (!randomGolds.contains(candidate) && candidate.getRating() >= 75) {
                randomGolds.add(candidate);
            }
        }

        return randomGolds;
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public HashMap<Integer, ArrayList<Player>> getByRating() {
        return byRating;
    }

    public HashMap<String, ArrayList<Player>> getByTeam() {
        return byTeam;
    }

    public HashMap<String, ArrayList<Player>> getByLeague() {
        return byLeague;
    }

    public HashMap<String, ArrayList<Player>> getByNation() {
        return byNation;
    }

    public HashMap<String, ArrayList<Player>> getByPos() {
        return byPos;
    }
}

class Player {

    private String name;
    private String team;
    private String nation;
    private String league;
    private String pos;
    private String price;
    private int rating;
    private String version;
    private boolean loyalty;

    public Player(String name, String team, String nation, String league, String pos, String version, String price, int rating, boolean loyalty) {
        this.name = name;
        this.team = team;
        this.nation = nation;
        this.league = league;
        this.pos = pos;
        this.rating = rating;
        this.version = version;
        this.price = price;
        this.loyalty = loyalty;
    }

    public Player(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isLoyalty() {
        return loyalty;
    }

    public void setLoyalty(boolean loyalty) {
        this.loyalty = loyalty;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}



