package solver;

import solver.Combinations;
import squad.Squad;
import squad.SquadHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class CombinationsTest {

    @Test
    void test_combinations2() {

        Squad s = null;
        try {
            s = SquadHelper.create75GoldNonRareTestSquad();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Object[] p = s.getPlayers().toArray();
        int k = 2;
        Combinations c = new Combinations();
//        c.combinations(p, k, 0, new Object[k]);

    }
}