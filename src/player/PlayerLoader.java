package player;

import java.io.*;
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

    public void loadPlayers(boolean exclude100kPlus) throws IOException {
        String csvFile = "./resources/fifa21/FutBinCards21.csv";
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
                    int rating = Integer.parseInt(player[4]);
                    CardType version = mapVersionToCardType(player[6], rating);


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
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
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

    public static CardType mapVersionToCardType(String version, int rating) {
        // TODO: will need to heavily modify this depending on incoming data
        switch(version) {
            case "Normal":
                if (rating >= 75) {
                    // TODO: differentiate between rare and non-rare
                    return CardType.GOLD_RARE;
                } else if (rating >= 65) {
                    return CardType.SILVER_RARE;
                } else {
                    return CardType.BRONZE_RARE;
                }
            case "IF":
            case "SIF":
            case "TIF":
                if (rating >= 75) {
                    return CardType.GOLD_IF;
                } else if (rating >= 65) {
                    return CardType.SILVER_IF;
                } else {
                    return CardType.BRONZE_IF;
                }
            case "CL":
                // TODO: differentiate between rare and non-rare
                return CardType.UCL_RARE;
            case "OTW":
                return CardType.OTW;
            case "UCL-LIVE":
                return CardType.UCL_LIVE;
            case "SCREAM":
                return CardType.SCREAM;
            default:
                return CardType.UNKNOWN;
        }
    }

    public static double convertStringPriceToDouble(String price) {

        double price_stripped = 0.0;
        if (price.matches("^[0-9]+\\.?[0-9]*?[Mm]$")) {
            String price_temp = price.replace("M","");
            price_stripped = 1000000*Double.parseDouble(price_temp);
        } else if (price.matches("^[0-9]+\\.?[0-9]*?[Kk]$")) {
            String price_temp = price.replace("K", "");
            price_stripped = 1000*Double.parseDouble(price_temp);
        } else if (price.matches(".*[a-zA-Z].*")) {
            // probably an CSV crawling error to make a string appear in this position, but likely means it's untradeable anyway
            price_stripped = 15000000;
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
                            (!positionPresent || player.getPos().toString().toLowerCase().equals(position.toLowerCase())) &&
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

    public ArrayList<Player> getAll81Plus() {
        ArrayList<Player> all87Plus = new ArrayList<>();

        for (Player p: this.allPlayers) {
            if (p.getRating() >= 81) {
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


