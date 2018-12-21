import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ChemistryEngineTest {

    @Test
    void calculateChemistryRandom11_v1() {
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        long seed = new Long(10);
        ArrayList<Player> randos = pl.get11RandomGoldPlayers(seed);
        Squad s = new Squad(randos);

        //this could change as new player dataset is imported
        assertEquals(6, ChemistryEngine.calculateChemistry(s));
    }

    @Test
    void calculateChemistryRandom11_v2() {
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        long seed = new Long(11);
        ArrayList<Player> randos = pl.get11RandomGoldPlayers(seed);
        Squad s = new Squad(randos);

        //this could change as new player dataset is imported
        assertEquals(10, ChemistryEngine.calculateChemistry(s));
    }

    @Test
    void calculateChemistryRandomNation11_v1() {
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        long seed = new Long(13);
        ArrayList<Player> randos = pl.get11RandomPlayersFromNation(seed, "Germany");
        Squad s = new Squad(randos);

        assertEquals(53, ChemistryEngine.calculateChemistry(s));

    }

    @Test
    void calculateChemistryRandom11_v4() {
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        long seed = new Long(10);
        ArrayList<Player> randos = pl.get11RandomGoldPlayers(seed);
        Squad s = new Squad(randos);

        assertEquals(6, ChemistryEngine.calculateChemistry(s));
    }

    @Test
    void positionChem1() {
        // place a LB at LWB -> should be on 2 chem
        Position p1 = new Position("LWB");
        Position p2 = new Position("LB");
        int c1 = ChemistryEngine.positionChem(p1, p2);
        assertEquals(c1, 2);
    }

    @Test
    void positionChem2() {
        // place a LM at CM -> should be on 1 chem
        Position p1 = new Position("CM");
        Position p2 = new Position("LM");
        int c1 = ChemistryEngine.positionChem(p1, p2);
        assertEquals(c1, 1);
    }

    @Test
    void positionChem3() {
        // place a CAM at CAM -> should be on 3 chem
        Position p1 = new Position("CAM");
        Position p2 = new Position("CAM");
        int c1 = ChemistryEngine.positionChem(p1, p2);
        assertEquals(c1, 3);
    }
}