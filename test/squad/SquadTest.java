package squad;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;
import player.Player;
import player.PlayerLoaderUtil;
import org.junit.jupiter.api.Test;
import player.Position;
import squad.formation.Formation;
import squad.formation.FormationFactory;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SquadTest {

    PlayerLoaderUtil pl;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeEach
    public void setUp() throws IOException {
        pl = new PlayerLoaderUtil();
        pl.loadPlayers(true);
    }

    @Test
    void test_SquadConstructor_valid() {

        ArrayList<Position> positions;

        Position rb = new Position("RB");
        Position lb = new Position("LB");
        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position rm = new Position("RM");
        Position lm = new Position("LM");
        Position cam = new Position("CAM");
        Position lst = new Position("ST", "LST");
        Position rst = new Position("ST", "RST");
        Position cdm = new Position("CDM");

        positions = new ArrayList<>();
        positions.add(rb);
        positions.add(lb);
        positions.add(gk);
        positions.add(lcb);
        positions.add(rcb);
        positions.add(rm);
        positions.add(cdm);
        positions.add(lm);
        positions.add(lst);
        positions.add(rst);
        positions.add(cam);

        FormationFactory ff = new FormationFactory();
        Formation formation = ff.getFormation("41212");
        formation.getPositions();

        ArrayList<Player> players = pl.get11FrenchPlayers();

        assertDoesNotThrow(() -> new Squad(positions, players, formation));
    }

    @Test
    void test_SquadConstructor_invalidPosition() {

        ArrayList<Position> positions;

        Position rb = new Position("RB");
        Position lb = new Position("LB");
        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position rm = new Position("RM");
        Position lm = new Position("LM");
        Position cam = new Position("CAM");
        Position lst = new Position("CB", "MCB"); // not a valid position in this formation
        Position rst = new Position("ST", "RST");
        Position cdm = new Position("CDM");

        positions = new ArrayList<>();
        positions.add(rb);
        positions.add(lb);
        positions.add(gk);
        positions.add(lcb);
        positions.add(rcb);
        positions.add(rm);
        positions.add(cdm);
        positions.add(lm);
        positions.add(lst);
        positions.add(rst);
        positions.add(cam);

        FormationFactory ff = new FormationFactory();
        Formation formation = ff.getFormation("41212");
        formation.getPositions();

        ArrayList<Player> players = pl.get11FrenchPlayers();

        try {
            Squad s = new Squad(positions, players, formation);
            fail("Test should have thrown exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid squad position"));
        }
    }

    @Test
    void test_SquadConstructor_invalidNumOfPositions() {

        ArrayList<Position> positions;

        Position rb = new Position("RB");
        Position lb = new Position("LB");
        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position rm = new Position("RM");
        Position lm = new Position("LM");
        Position cam = new Position("CAM");
        Position lst = new Position("ST", "RST");
        Position rst = new Position("ST", "RST"); // invalid number of RST for this formation
        Position cdm = new Position("CDM");

        positions = new ArrayList<>();
        positions.add(rb);
        positions.add(lb);
        positions.add(gk);
        positions.add(lcb);
        positions.add(rcb);
        positions.add(rm);
        positions.add(cdm);
        positions.add(lm);
        positions.add(lst);
        positions.add(rst);
        positions.add(cam);

        FormationFactory ff = new FormationFactory();
        Formation formation = ff.getFormation("41212");
        formation.getPositions();

        ArrayList<Player> players = pl.get11FrenchPlayers();

        try {
            Squad s = new Squad(positions, players, formation);
            fail("Test should have thrown exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("11 unique"));
        }
    }

    @Test
    void test_getRatings() {
    }

    @Test
    void test_getSquadPrice() {
        long seed = new Long(13);
        ArrayList<Player> randos = pl.get11RandomPlayersFromNation(seed, "Germany");
        Squad s = new Squad(randos);

        assertEquals(189200.0, s.getSquadPrice());
    }

    @Test
    void test_printSquad() {
    }

    @Test
    void test_getLineup() {
    }

    @Test
    void test_getPositions() {
    }

    @Test
    void test_getPlayers() {
    }

    @Test
    void test_getGraph() {
    }
}