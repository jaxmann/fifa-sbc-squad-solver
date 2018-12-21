import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SBChallengeTest {

    @Test
    void solver() {

        Squad s = Squad.createDefaultSquad();

        //initialize best cost as infinity
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