import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RatingEngineTest {

    @Test
    void test83RatingV1() {

        ArrayList<Integer> r1 = new ArrayList<>();
        r1.add(84);
        r1.add(84);
        r1.add(84);
        r1.add(84);
        r1.add(84);
        r1.add(84);
        r1.add(84);
        r1.add(84);
        r1.add(83);
        r1.add(83);
        r1.add(83);

        assertEquals(83.0, RatingEngine.getRating(r1));

    }

    @Test
    void test84RatingV1() {

        ArrayList<Integer> r2 = new ArrayList<>();
        r2.add(87);
        r2.add(84);
        r2.add(84);
        r2.add(84);
        r2.add(84);
        r2.add(84);
        r2.add(82);
        r2.add(82);
        r2.add(82);
        r2.add(82);
        r2.add(82);

        assertEquals(84.0, RatingEngine.getRating(r2));

    }

}