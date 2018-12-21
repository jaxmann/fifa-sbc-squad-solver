import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SBChallengeTest {

    @Test
    void solver() {

        Squad s = Squad.createDefaultSquad();

        //initialize best cost as infinity
        double minCost = 1000000;

        Integer[] idxs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k = 2;
        Combinations c = new Combinations();
        c.combinations(idxs, k, 0, new Integer[k]);
        ArrayList<Object[]> list = c.getCombos();

        System.out.println(list.size());


        for (Object[] o: list) {
            System.out.println(Arrays.toString(o));
        }

//        Combinations.combinations(idx, k, 0, new Integer[k]);
        //get first n choose 2 indices
            //get every combination of 2 players, check chem/rating and and update min price if valid chem/rating and better min
        //get next n choose 2 indices
            //...


        System.out.println(s.getSquadPrice());
        System.out.println(ChemistryEngine.calculateChemistry(s));
        System.out.println(s.getSquadRating());





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