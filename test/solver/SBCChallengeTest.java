package solver;

import chemistry.ChemistryEngine;
import constraint.Constraint;
import constraint.ConstraintType;
import constraint.Constraints;
import player.BasePosition;
import player.CardType;
import player.Player;
import player.PlayerLoaderUtil;
import squad.Squad;
import squad.SquadHelper;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.Ignore;

public class SBCChallengeTest {

    private PlayerLoaderUtil pl;

    @Before
    public void setUp() throws Exception {
        pl = PlayerLoaderUtil.getInstance();
    }

    @Ignore
    @Test
    public void test_simulatedAnnealing() throws IOException {

        Squad current = null;
        ArrayList<Player> availablePlayers = null;
        try {
            current = SquadHelper.create83DefaultSquad();
            pl.loadPlayers(false);
            availablePlayers = pl.getAll83Plus();
//            availablePlayers = pl.getNCheapestAtMinRating(800, 84);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        //fit {(750000, 100), (500000, 105), (200000, 110), (100000, 120), (50000, 140), (25000, 160), (15000, 180), (10000, 190), (5000, 200)}
        //CONSTRAINTS
        Constraint c1 = new Constraint(ConstraintType.MINCHEM);
        c1.setMinChem(50);
        Constraint c2 = new Constraint(ConstraintType.MINRATING);
        c2.setMinRating(87);
        Constraint c3 = new Constraint(ConstraintType.MIN_OF_CARDTYPE);
        c3.setCardType(CardType.GOLD_IF);
        c3.setMin_of_cardtype(1);

        Constraints constraints = new Constraints();
//        constraints.addConstraint(c1);
        constraints.addConstraint(c2);
//        constraints.addConstraint(c3);

        //END CONSTRAINTS

        SBCChallenge sbc = new SBCChallenge(constraints);
        sbc.runSimulatedAnnealing(current, availablePlayers, true, false);
    }

    @Ignore
    @Test
    public void solver() throws IOException {

        Squad s = null;
        ArrayList<Player> allPlayers = null;
        PlayerLoaderUtil pl = PlayerLoaderUtil.getInstance();

        try {
            s = SquadHelper.create87DefaultSquad();
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