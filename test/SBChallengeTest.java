import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SBChallengeTest {

    @Test
    void solver() {

        Squad s = Squad.createDefaultSquad();


        System.out.println(s.getSquadPrice());
        System.out.println(ChemistryEngine.calculateChemistry(s));
        System.out.println(RatingEngine.getRating(s));





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