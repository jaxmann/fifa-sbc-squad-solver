import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerLoader {

    //do union of lists instead of sql queries
    private HashMap<String, ArrayList<Player>> byTeam;
    private HashMap<String, ArrayList<Player>> byLeague;
    private HashMap<String, ArrayList<Player>> byNation;
    private HashMap<String, ArrayList<Player>> byPos;

    public void loadPlayers() {
        String csvFile = "./resources/FutBinCards19.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        byTeam = new HashMap<>();
        byLeague = new HashMap<>();
        byNation = new HashMap<>();
        byPos = new HashMap<>();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] player = line.split(cvsSplitBy);

                String pos = player[4];
                String name = player[1];
                String team = player[0];
                String league = player[2];
                String nation = player[3];
                String price = player[6];
                String version = player[5];

                Player p = new Player(name, team, nation, league, pos, version, price);

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
    private String version;

    public Player(String name, String team, String nation, String league, String pos, String version, String price) {
        this.name = name;
        this.team = team;
        this.nation = nation;
        this.league = league;
        this.pos = pos;
        this.version = version;
        this.price = price;
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



