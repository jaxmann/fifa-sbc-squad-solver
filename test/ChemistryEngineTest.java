import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChemistryEngineTest {

    @Test
    void calculateChemistry() {
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers();
        ArrayList<Player> frenchies = pl.get11RandomGoldPlayers();
        Squad s = new Squad(frenchies);

        ChemistryEngine.calculateChemistry(s);

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