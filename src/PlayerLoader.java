import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

public class PlayerLoader {

    //do union of lists instead of sql queries
    private HashMap<String, ArrayList<Player>> byTeam;
    private HashMap<String, ArrayList<Player>> byLeague;
    private HashMap<String, ArrayList<Player>> byNation;
    private HashMap<String, ArrayList<Player>> byPos;
    private HashMap<Integer, ArrayList<Player>> byRating;
    private ArrayList<Player> allPlayers;

    public void loadPlayers(boolean exclude100kPlus) {
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
                    double price = convertStringPriceToDouble(player[7]);
                    String version = player[6];
                    int rating = Integer.parseInt(player[4]);

                    if (!team.equals("Icons") && ((price < 100000 && exclude100kPlus) || !exclude100kPlus)) {

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

    public static double convertStringPriceToDouble(String price) {

        double price_stripped = 0.0;
        if (price.contains("M")) {
            String price_temp = price.replace("M","");
            price_stripped = 1000000*Double.parseDouble(price_temp);
        } else if (price.contains("K")) {
            String price_temp = price.replace("K", "");
            price_stripped = 1000*Double.parseDouble(price_temp);
        } else if (price.matches(".*[a-zA-Z].*")) {
            price_stripped = 1000000;
        } else {
            price_stripped = Double.parseDouble(price);
        }

        if (price_stripped < 50) {
            price_stripped = 1000000;
        }

        return price_stripped;

    }

    public Player getPlayer(String name, int rating, String nation, String position, String team, String league) throws PlayerNotFoundException {

        boolean namePresent = false;
        boolean ratingPresent = false;
        boolean nationPresent = false;
        boolean positionPresent = false;
        boolean teamPresent = false;
        boolean leaguePresent = false;

        if (!name.equals("")) {
            namePresent = true;
        }
        if (rating != 0) {
            ratingPresent = true;
        }
        if (!nation.equals("")) {
            nationPresent = true;
        }
        if (!position.equals("")) {
            positionPresent = true;
        }
        if (!team.equals("")) {
            teamPresent = true;
        }
        if (!league.equals("")) {
            leaguePresent = true;
        }

        for (Player player: this.allPlayers) {
            if ((!namePresent || player.getName().toLowerCase().contains(name.toLowerCase())) &&
                    (!ratingPresent || player.getRating() == rating) &&
                    (!nationPresent || player.getNation().toLowerCase().contains(nation.toLowerCase()) &&
                            (!positionPresent || player.getPos().toLowerCase().equals(position.toLowerCase())) &&
                            (!teamPresent || player.getTeam().toLowerCase().contains(team.toLowerCase())) &&
                            (!leaguePresent || player.getLeague().toLowerCase().contains(league.toLowerCase())
            ))) {
                return player;
            }
        }

        throw new PlayerNotFoundException(name);

    }

    public Player getPlayer(String name, int rating) throws PlayerNotFoundException {

        for (Player player: this.allPlayers) {
            if (player.getName().toLowerCase().contains(name.toLowerCase()) && player.getRating() == rating) {
                return player;
            }
        }

        throw new PlayerNotFoundException(name);
    }

    public ArrayList<Player> get11FrenchPlayers() {
        ArrayList<Player> frenchPlayers = new ArrayList<>();

        for (int i=0; i<11; i++) {
            frenchPlayers.add(byNation.get("France").get(i));
        }

        return frenchPlayers;
    }

    public ArrayList<Player> get11RandomGoldPlayers(long seed) {
        ArrayList<Player> randomGolds = new ArrayList<>();
        Random randomGenerator = new Random();
        randomGenerator.setSeed(seed);

        while (randomGolds.size() != 11) {
            int index = randomGenerator.nextInt(allPlayers.size());
            Player candidate = allPlayers.get(index);
            if (!randomGolds.contains(candidate) && candidate.getRating() >= 75) {
                randomGolds.add(candidate);
            }
        }

        return randomGolds;
    }

    public ArrayList<Player> get11RandomPlayersFromNation(long seed, String nation) {
        ArrayList<Player> randomGolds = new ArrayList<>();
        Random randomGenerator = new Random();
        randomGenerator.setSeed(seed);

        while (randomGolds.size() != 11) {
            int index = randomGenerator.nextInt(allPlayers.size());
            Player candidate = allPlayers.get(index);
            if (!randomGolds.contains(candidate) && candidate.getRating() >= 75 && candidate.getNation().equals(nation)) {
                randomGolds.add(candidate);
            }
        }

        return randomGolds;
    }

    public ArrayList<Player> getAll82Plus() {
        ArrayList<Player> all82Plus = new ArrayList<>();

        for (Player p: this.allPlayers) {
            if (p.getRating() >= 82) {
                all82Plus.add(p);
            }
        }

        return all82Plus;

    }

    public ArrayList<Player> getAll83Plus() {
        ArrayList<Player> all83Plus = new ArrayList<>();

        for (Player p: this.allPlayers) {
            if (p.getRating() >= 83) {
                all83Plus.add(p);
            }
        }

        return all83Plus;

    }

    public ArrayList<Player> getAll84Plus() {
        ArrayList<Player> all84Plus = new ArrayList<>();

        for (Player p: this.allPlayers) {
            if (p.getRating() >= 84) {
                all84Plus.add(p);
            }
        }

        return all84Plus;

    }

    public ArrayList<Player> getAll85Plus() {
        ArrayList<Player> all85Plus = new ArrayList<>();

        for (Player p: this.allPlayers) {
            if (p.getRating() >= 85) {
                all85Plus.add(p);
            }
        }

        return all85Plus;

    }

    public ArrayList<Player> getAll86Plus() {
        ArrayList<Player> all86Plus = new ArrayList<>();

        for (Player p: this.allPlayers) {
            if (p.getRating() >= 86) {
                all86Plus.add(p);
            }
        }

        return all86Plus;

    }

    public ArrayList<Player> getAll87Plus() {
        ArrayList<Player> all87Plus = new ArrayList<>();

        for (Player p: this.allPlayers) {
            if (p.getRating() >= 87) {
                all87Plus.add(p);
            }
        }

        return all87Plus;

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

class PlayerNotFoundException extends Exception {

    private String name;

    public PlayerNotFoundException(String name) {
        this.name = name;
    }

    public void printName() {
        System.out.println("not able to find player: " + this.name);
    }
}

class Player implements Serializable {

    private String name;
    private String team;
    private String nation;
    private String league;
    private String pos;
    private double price;
    private int rating;
    private String version;
    private boolean loyalty;

    public Player(String name, String team, String nation, String league, String pos, String version, double price, int rating, boolean loyalty) {
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

    public Player deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Player) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return this.name + " ~ " + this.rating;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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



