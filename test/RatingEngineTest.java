import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RatingEngineTest {

    @Test
    void test83RatingV1() {

        ArrayList<Player> r1 = new ArrayList<>();

        Player p1 = new Player(84);
        Player p2 = new Player(84);
        Player p3 = new Player(84);
        Player p4 = new Player(84);
        Player p5 = new Player(84);
        Player p6 = new Player(84);
        Player p7 = new Player(84);
        Player p8 = new Player(84);

        Player p9 = new Player(83);
        Player p10 = new Player(83);
        Player p11 = new Player(83);

        r1.add(p1);
        r1.add(p2);
        r1.add(p3);
        r1.add(p4);
        r1.add(p5);
        r1.add(p6);
        r1.add(p7);
        r1.add(p8);
        r1.add(p9);
        r1.add(p10);
        r1.add(p11);

        Squad s = new Squad(r1);

        assertEquals(83.0, RatingEngine.getRating(s));

    }

    @Test
    void test84RatingV1() {

        ArrayList<Player> r2 = new ArrayList<>();

        Player p1 = new Player(87);

        Player p2 = new Player(84);
        Player p3 = new Player(84);
        Player p4 = new Player(84);
        Player p5 = new Player(84);
        Player p6 = new Player(84);

        Player p7 = new Player(82);
        Player p8 = new Player(82);
        Player p9 = new Player(82);
        Player p10 = new Player(82);
        Player p11 = new Player(82);

        r2.add(p1);
        r2.add(p2);
        r2.add(p3);
        r2.add(p4);
        r2.add(p5);
        r2.add(p6);
        r2.add(p7);
        r2.add(p8);
        r2.add(p9);
        r2.add(p10);
        r2.add(p11);

        Squad s = new Squad(r2);

        assertEquals(84.0, RatingEngine.getRating(s));

    }

}