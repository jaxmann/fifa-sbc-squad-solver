package solver;

import chemistry.ChemistryEngine;
import constraint.Constraint;
import constraint.ConstraintType;
import constraint.Constraints;
import org.junit.Ignore;
import player.Player;
import player.PlayerLoaderUtil;
import squad.Squad;
import squad.SquadHelper;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.fail;

public class SBCChallengeTest {

    @Ignore
    @Test
    public void test_simulatedAnnealing() {

        Squad current = null;
        ArrayList<Player> availablePlayers = null;
        try {
            current = SquadHelper.create75GoldNonRareTestSquad();
            PlayerLoaderUtil pl = new PlayerLoaderUtil();
            pl.loadPlayers(true);
            availablePlayers = pl.getAll86Plus();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        //fit {(750000, 100), (500000, 105), (200000, 110), (100000, 120), (50000, 140), (25000, 160), (15000, 180), (10000, 190), (5000, 200)}
        //CONSTRAINTS
//        Constraint c1 = new Constraint(ConstraintType.MINCHEM);
//        c1.setMinChem(60);
        Constraint c2 = new Constraint(ConstraintType.MINRATING);
        c2.setMinRating(87);

        Constraints constraints = new Constraints();
//        constraints.addConstraint(c1);
        constraints.addConstraint(c2);

        //END CONSTRAINTS

        SBCChallenge sbc = new SBCChallenge(constraints);
        sbc.runSimulatedAnnealing(current, availablePlayers, true, false);
    }

    @Test
    public void solver() {

        Squad s = null;
        ArrayList<Player> allPlayers = null;
        PlayerLoaderUtil pl = null;

        try {
            s = SquadHelper.create87DefaultSquad();
            pl = new PlayerLoaderUtil();
            pl.loadPlayers(true);
            allPlayers = pl.getAllPlayers();
        } catch (Exception e) {
            fail(e.getMessage());
        }

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



//        Solver.Combinations.combinations(idx, k, 0, new Integer[k]);
        //get first n choose 2 indices
            //get every combination of 2 players, check chem/rating and and update min price if valid chem/rating and better min
        //get next n choose 2 indices
            //...


//        System.out.println(s.getSquadPrice());
//        System.out.println(Chemistry.ChemistryEngine.calculateChemistry(s));
//        System.out.println(s.getSquadRating());

    }

    @Test
    public void test_get87defaultSquad() {
        Squad s = null;
        try {
            s = SquadHelper.create87DefaultSquad();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        for (Player p: s.getPlayers()) {
            System.out.println(p.toString());
        }
    }

    @Test
    public void test_get85defaultSquad() {
        Squad s = null;
        try {
            s = SquadHelper.create85DefaultSquad();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        for (Player p: s.getPlayers()) {
            System.out.println(p.toString());
        }
    }

    @Test
    public void test_get83defaultSquad() {
        Squad s = null;
        try {
            s = SquadHelper.create83DefaultSquad();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        for (Player p: s.getPlayers()) {
            System.out.println(p.toString());
        }
    }

    @Test
    public void test_get75defaultSquad() {
        Squad s = null;
        try {
            s = SquadHelper.create75DefaultSquad();
        } catch (Exception e) {
            fail (e.getMessage());
        }

        for (Player p: s.getPlayers()) {
            System.out.println(p.toString());
        }
    }

    @Test
    public void test_getFitnessScore() {
    }

    @Test
    public void test_getSquad() {
    }

    @Test
    public void test_setSquad() {
    }

    @Test
    public void test_getMinRating() {
    }

    @Test
    public void test_setMinRating() {
    }

    @Test
    public void test_getMinChem() {
    }

    @Test
    public void test_setMinChem() {
    }
}