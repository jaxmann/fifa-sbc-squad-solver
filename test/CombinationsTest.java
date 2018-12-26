import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombinationsTest {

    @Test
    void combinations2() {

        Squad s = Squad.create87DefaultSquad();
        Object[] p = s.getPlayers().toArray();
        int k = 2;
        Combinations c = new Combinations();
//        c.combinations(p, k, 0, new Object[k]);


    }
}