import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombinationsTest {

    @Test
    void combinations2() {

        Squad s = Squad.createDefaultSquad();
        Object[] p = s.getPlayers().toArray();
        int k = 2;
        Combinations.combinations2(p, k, 0, new Object[k]);

    }
}