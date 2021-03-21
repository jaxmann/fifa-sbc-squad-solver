import solver.Combinations;
import squad.Squad;
import squad.SquadHelper;
import org.junit.jupiter.api.Test;

class CombinationsTest {

    @Test
    void combinations2() {

        Squad s = SquadHelper.create87DefaultSquad();
        Object[] p = s.getPlayers().toArray();
        int k = 2;
        Combinations c = new Combinations();
//        c.combinations(p, k, 0, new Object[k]);


    }
}