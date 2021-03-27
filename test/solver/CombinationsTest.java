package solver;

import player.BasePosition;
import player.CardType;
import squad.Squad;
import squad.SquadHelper;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CombinationsTest {

    @Test
    public void test_combinations2() {

        Squad s = null;
        try {
            s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Object[] p = s.getPlayers().toArray();
        int k = 2;
        Combinations c = new Combinations();
//        c.combinations(p, k, 0, new Object[k]);

    }
}