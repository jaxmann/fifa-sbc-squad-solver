package squad;

import constraint.Brick;
import org.junit.jupiter.api.BeforeEach;
import player.*;
import org.junit.jupiter.api.Test;
import squad.formation.Formation;
import squad.formation.FormationFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SquadTest {

    PlayerLoaderUtil pl;

    @BeforeEach
    public void setUp() throws IOException {
        pl = new PlayerLoaderUtil();
        pl.loadPlayers(false);
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
    void test_getRatings() throws Exception {
        int expectedRating = 75;
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, expectedRating, false);
        assertTrue(s.getRatings().stream().allMatch(rating -> rating == expectedRating));
    }

    @Test
    void test_getSquadPrice() throws Exception {
        double pricePerPlayer = 800;
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, pricePerPlayer, 75, false);

        assertEquals(11*800, s.getSquadPrice());
        assertTrue(s.getPlayers().stream().allMatch(player -> player.getPrice() == pricePerPlayer));
    }

    @Test
    void newAtPos_cannotAddToBrick() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        s.setBrick(new Brick(new Position(BasePosition.GK)));
        // index of brickedPlayer in createTestSquad is last index
        Player p = new Player(85);
        Squad newSquad = Squad.newAtPos(s, 10, p);

        assertTrue(newSquad.getPlayers().stream().allMatch(player -> player != p));
    }

    @Test
    void newAtPos_addNewPlayer() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        Player p = new Player(85);
        Squad newSquad = Squad.newAtPos(s, 10, p);

        assertTrue(newSquad.getPlayers().stream().anyMatch(player -> player == p));
    }

    @Test
    void updateAtPos() {
    }

    @Test
    void testUpdateAtPos() {
    }

    @Test
    void updateLineup() {
    }

    @Test
    void getSquadPrice() {
    }

    @Test
    void getSquadRating() {
    }

    @Test
    void getFractionalSquadRating() {
    }

    @Test
    void getPlayersAsString() {
    }

    @Test
    void getPlayerAtPosition() {
    }

    @Test
    void getNumOfCardType() {
    }

    @Test
    void getNumOfNation() {
    }

    @Test
    void getNumOfLeague() {
    }

    @Test
    void getNumOfTeam() {
    }

    @Test
    void getNumPlayers() {
    }

    @Test
    void getNumOfClub() {
    }

    @Test
    void getGraph() {
    }
}