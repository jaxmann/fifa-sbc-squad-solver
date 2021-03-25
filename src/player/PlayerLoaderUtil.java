package player;

import java.io.*;
import java.util.*;


public class PlayerLoaderUtil {

    private static final int MAX_NUMBER_OF_PLAYERS = 15000;

    private HashMap<String, PriorityQueue<Player>> byTeam;
    private HashMap<String, PriorityQueue<Player>> byLeague;
    private HashMap<String, PriorityQueue<Player>> byNation;
    private HashMap<String, PriorityQueue<Player>> byPos;
    private HashMap<Integer, PriorityQueue<Player>> byRating;
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
                            byTeam.put(team, new PriorityQueue<>(MAX_NUMBER_OF_PLAYERS, new PlayerComparator()));
                        }
                        byTeam.get(team).add(p);

                        if (!byLeague.containsKey(league)) {
                            byLeague.put(league, new PriorityQueue<>(MAX_NUMBER_OF_PLAYERS, new PlayerComparator()));
                        }
                        byLeague.get(league).add(p);

                        if (!byNation.containsKey(nation)) {
                            byNation.put(nation, new PriorityQueue<>(MAX_NUMBER_OF_PLAYERS, new PlayerComparator()));
                        }
                        byNation.get(nation).add(p);

                        if (!byPos.containsKey(pos)) {
                            byPos.put(pos, new PriorityQueue<>(MAX_NUMBER_OF_PLAYERS, new PlayerComparator()));
                        }
                        byPos.get(pos).add(p);

                        if (!byRating.containsKey(rating)) {
                            byRating.put(rating, new PriorityQueue<>(MAX_NUMBER_OF_PLAYERS, new PlayerComparator()));
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

        double price_stripped;
        if (price.matches("^[0-9]+\\.?[0-9]*?[Mm]$")) {
            String price_temp = price.replaceAll("[Mm]","");
            price_stripped = 1000000*Double.parseDouble(price_temp);
        } else if (price.matches("^[0-9]+\\.?[0-9]*?[Kk]$")) {
            String price_temp = price.replaceAll("[Kk]", "");
            price_stripped = 1000*Double.parseDouble(price_temp);
        } else if (price.matches(".*[a-zA-Z].*")) {
            // probably an CSV crawling error to make a string appear in this position, but likely means it's untradeable anyway
            price_stripped = 15000000;
        } else {
            price_stripped = Double.parseDouble(price);
        }

        return price_stripped;
    }

    // for tests
    public Player getAnyPlayerAtPositionAndRating(BasePosition pos, int rating) {
        // all players for every rating int are pre-cached by loadPlayers into Min Heap
        PriorityQueue<Player> cachedPlayers = this.getByRating().get(rating);
        for (Player player: cachedPlayers) {
            // don't need to check rating
            if (player.getPosBase() == pos) {
                return player;
            }
        }
        return null;
    }

    public Player getAnyPlayerAtExactRating(int rating) {
        // all players for every rating int are pre-cached by loadPlayers into Min Heap
        if (this.getByRating().containsKey(rating)) {
            return this.getByRating().get(rating).peek();
        }
        return null;
    }

    public ArrayList<Player> getNCheapestAtExactRating(int numPlayers, int rating) {
        ArrayList<Player> cheapestPlayers = new ArrayList<>();
        // all players for every rating int are pre-cached by loadPlayers into Min Heap
        for (int i=0; i<numPlayers; i++) {
            if (this.getByRating().containsKey(rating)) {
                cheapestPlayers.add(this.getByRating().get(rating).remove());
            } else {
                continue;
            }
        }
        return cheapestPlayers;
    }

    public ArrayList<Player> getNCheapestAtMinRating(int numPlayers, int rating) {
        ArrayList<Player> cheapestPlayers = new ArrayList<>();
        // all players for every rating int are pre-cached by loadPlayers into Min Heap
        for (int i=0; i<numPlayers; i++) {
            double minPrice = Double.POSITIVE_INFINITY;
            int minIndex = -1;
            // each j is a priority queue of min rating "rating", peek each one, find best, remove that one and add
            for (int j=rating; j<100; j++) {
                if (this.getByRating().containsKey(j) && !this.getByRating().get(j).isEmpty()) {
                    if (this.getByRating().get(j).peek().getPrice() < minPrice) {
                        minPrice = this.getByRating().get(j).peek().getPrice();
                        minIndex = j;
                    }
                }
            }
            // if minIndex is set, we found a player
            if (minIndex > 0) {
                cheapestPlayers.add(this.getByRating().get(minIndex).remove());
            }
        }
        return cheapestPlayers;
    }

    public ArrayList<Player> getNCheapestAtExactRatingAndPosition(int numPlayers, BasePosition pos, int rating) {
        ArrayList<Player> cheapestPlayers = new ArrayList<>();
        // all players for every rating int are pre-cached by loadPlayers into Min Heap
        while (numPlayers > 0) {
            if (this.getByRating().containsKey(rating)) {
                Player currentPlayer = this.getByRating().get(rating).remove();
                if (currentPlayer.getPosBase() == pos) {
                    cheapestPlayers.add(currentPlayer);
                    numPlayers--;
                }
                if (this.getByRating().get(rating).isEmpty()) {
                    break;
                }
            } else {
                break;
            }
        }
        return cheapestPlayers;
    }

    public ArrayList<Player> getNCheapestAtMinRatingAndPosition(int numPlayers, BasePosition pos, int rating) {
        ArrayList<Player> cheapestPlayers = new ArrayList<>();
        // all players for every rating int are pre-cached by loadPlayers into Min Heap
        for (int i=0; i<numPlayers; i++) {
            double minPrice = Double.POSITIVE_INFINITY;
            int minIndex = -1;
            // each j is a priority queue of min rating "rating", peek each one, find best, remove that one and add
            for (int j=rating; j<100; j++) {
                if (this.getByRating().containsKey(j) && !this.getByRating().get(j).isEmpty()) {
                    while (!this.getByRating().get(j).isEmpty() && this.getByRating().get(j).peek().getPosBase() != pos) {
                        // pop off heap if pos doesn't match - we can't use these players anyway
                        this.getByRating().get(j).remove();
                    }
                    if (!this.getByRating().get(j).isEmpty() && this.getByRating().get(j).peek().getPrice() < minPrice) {
                        minPrice = this.getByRating().get(j).peek().getPrice();
                        minIndex = j;
                    }
                }
            }
            // if minIndex is set, we found a player
            if (minIndex > 0) {
                cheapestPlayers.add(this.getByRating().get(minIndex).remove());
            }
        }
        return cheapestPlayers;
    }

    public Player lookupPlayerByNameAndRating(String nameSubstring, int rating, String nation, String position, String team, String league) throws PlayerNotFoundException {
        boolean namePresent = false;
        boolean ratingPresent = false;
        boolean nationPresent = false;
        boolean positionPresent = false;
        boolean teamPresent = false;
        boolean leaguePresent = false;

        if (!nameSubstring.equals("")) {
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
            if ((!namePresent || player.getName().toLowerCase().contains(nameSubstring.toLowerCase())) &&
                    (!ratingPresent || player.getRating() == rating) &&
                    (!nationPresent || player.getNation().toLowerCase().contains(nation.toLowerCase()) &&
                            (!positionPresent || player.getPosBase().toString().toLowerCase().equals(position.toLowerCase())) &&
                            (!teamPresent || player.getTeam().toLowerCase().contains(team.toLowerCase())) &&
                            (!leaguePresent || player.getLeague().toLowerCase().contains(league.toLowerCase())
            ))) {
                return player;
            }
        }

        throw new PlayerNotFoundException(nameSubstring);
    }

    public Player lookupPlayerByNameAndRating(String nameSubstring, int rating) throws PlayerNotFoundException {

        for (Player player: this.allPlayers) {
            if (player.getName().toLowerCase().contains(nameSubstring.toLowerCase()) && player.getRating() == rating) {
                return player;
            }
        }

        throw new PlayerNotFoundException(nameSubstring);
    }


    public ArrayList<Player> get11FrenchPlayers() {
        ArrayList<Player> frenchPlayers = new ArrayList<>();

        for (int i=0; i<11; i++) {
            frenchPlayers.add(getByNation().get("France").remove());
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

    public HashMap<Integer, PriorityQueue<Player>> getByRating() {
        return byRating;
    }

    public HashMap<String, PriorityQueue<Player>> getByTeam() {
        return byTeam;
    }

    public HashMap<String, PriorityQueue<Player>> getByLeague() {
        return byLeague;
    }

    public HashMap<String, PriorityQueue<Player>> getByNation() {
        return byNation;
    }

    public HashMap<String, PriorityQueue<Player>> getByPos() {
        return byPos;
    }
}


