import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SBChallengeTest {

    @Test
    void simulatedAnnealing() {

        Squad current = SquadHelper.create75DefaultSquad();
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        ArrayList<Player> availablePlayers = pl.getAll84Plus();

        //fit {(750000, 100), (500000, 105), (200000, 110), (100000, 120), (50000, 140), (25000, 160), (15000, 180), (10000, 190), (5000, 200)}
        //CONSTRAINTS
//        Constraint c1 = new Constraint(ConstraintType.MINCHEM);
//        c1.setMinChem(80);
        Constraint c2 = new Constraint(ConstraintType.MINRATING);
        c2.setMinRating(88);

        Constraints constraints = new Constraints();
//        constraints.addConstraint(c1);
        constraints.addConstraint(c2);

        //END CONSTRAINTS

        SBChallenge sbc = new SBChallenge(constraints);
        sbc.runSimulatedAnnealing(current, availablePlayers, true, false);

    }

    @Test
    void solver() {

        Squad s = SquadHelper.create87DefaultSquad();
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        ArrayList<Player> allPlayers = pl.getAllPlayers();

        //initialize best cost as infinity
        double minCost = 1000000;
        double minRating = 87;
        double minChem = 70;
        Squad bestSquad = s;

        ///////////////// SQUAD PICKS

        Integer[] squadInds = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k = 2;
        Combinations c = new Combinations();
        c.combinations(squadInds, k, 0, new Integer[k]);
        ArrayList<Object[]> squadCombos = c.getCombos();

        ///////////////// PLAYER PICKS

        int eSize = pl.getAll86Plus().size();
        Integer[] playerInds = new Integer[eSize];

        for (int i=0; i<eSize; i++) {
            playerInds[i] = i;
        }

        Combinations c2 = new Combinations();
        c2.combinations(playerInds, k, 0, new Integer[k]);
        ArrayList<Object[]> playerCombos = c2.getCombos();

        Squad temp;
        for (int i=0; i<squadCombos.size(); i++) {
            for (int j=0; j<playerCombos.size(); j++) {
                temp = s;
                temp.updateAtPos((Integer)squadCombos.get(i)[0], allPlayers.get((Integer)playerCombos.get(j)[0]));
                temp.updateAtPos((Integer)squadCombos.get(i)[1], allPlayers.get((Integer)playerCombos.get(j)[1]));
                if (temp.getSquadRating() >= minRating && ChemistryEngine.calculateChemistry(temp) >= 70) {
                    if (temp.getSquadPrice() < minCost) {
                        System.out.println("PRICE: " + temp.getSquadPrice() + " CHEM: " + ChemistryEngine.calculateChemistry(temp) + " RATING: " + temp.getSquadRating());
                        temp.printSquad();
                        minCost = temp.getSquadPrice();
                        bestSquad = temp;
                    }
                }
            }
        }



//        Combinations.combinations(idx, k, 0, new Integer[k]);
        //get first n choose 2 indices
            //get every combination of 2 players, check chem/rating and and update min price if valid chem/rating and better min
        //get next n choose 2 indices
            //...


//        System.out.println(s.getSquadPrice());
//        System.out.println(ChemistryEngine.calculateChemistry(s));
//        System.out.println(s.getSquadRating());





    }

    @Test
    void get87defaultSquadTest() {
        Squad s = SquadHelper.create87DefaultSquad();

        for (Player p: s.getPlayers()) {
            System.out.println(p.toString());
        }
    }

    @Test
    void get85defaultSquadTest() {
        Squad s = SquadHelper.create85DefaultSquad();

        for (Player p: s.getPlayers()) {
            System.out.println(p.toString());
        }
    }

    @Test
    void get83defaultSquadTest() {
        Squad s = SquadHelper.create83DefaultSquad();

        for (Player p: s.getPlayers()) {
            System.out.println(p.toString());
        }
    }

    @Test
    void get75defaultSquadTest() {
        Squad s = SquadHelper.create75DefaultSquad();

        for (Player p: s.getPlayers()) {
            System.out.println(p.toString());
        }
    }

    @Test
    void getFitnessScore() {
    }

    @Test
    void getSquad() {
    }

    @Test
    void setSquad() {
    }

    @Test
    void getMinRating() {
    }

    @Test
    void setMinRating() {
    }

    @Test
    void getMinChem() {
    }

    @Test
    void setMinChem() {
    }
}