package squad;

import chemistry.ChemistryEngine;
import constraint.Brick;
import player.*;
import squad.formation.Formation;
import squad.formation.FormationFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SquadTest {

    private PlayerLoaderUtil pl;

    @Before
    public void setUp() throws IOException {
        pl = PlayerLoaderUtil.getInstance();
    }

    @Test
    public void test_SquadConstructor_valid() {

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
    public void test_SquadConstructor_invalidPosition() {

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
    public void test_SquadConstructor_invalidNumOfPositions() {

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
    public void test_getRatings() throws Exception {
        int expectedRating = 75;
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, expectedRating, false);
        assertTrue(s.getRatings().stream().allMatch(rating -> rating == expectedRating));
    }

    @Test
    public void test_getSquadPrice() throws Exception {
        double pricePerPlayer = 800;
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, pricePerPlayer, 75, false);

        assertEquals(11*800.0, s.getSquadPrice());
        assertTrue(s.getPlayers().stream().allMatch(player -> player.getPrice() == pricePerPlayer));
    }

    @Test
    public void newAtPos_cannotAddToBrick() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        s.setBrick(new Brick(new Position(BasePosition.GK)));

        Player p = new Player(85);
        Squad newSquad = Squad.newAtPos(s, 10, p);

        assertTrue(newSquad.getPlayers().stream().allMatch(player -> player != p));
    }

    @Test
    public void newAtPos_addNewPlayer() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        Player p = new Player(85);
        Squad newSquad = Squad.newAtPos(s, 10, p);

        assertTrue(newSquad.getPlayers().stream().anyMatch(player -> player == p));
    }

    @Test
    public void test_replacePlayer_bricked() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        s.setBrick(new Brick(new Position(BasePosition.GK)));

        Player p = Squad.getPlayerAtActualPos(s, ActualPosition.GK);
        Player newPlayer = new Player(85);
        Squad newSquad = Squad.replacePlayer(s, p, newPlayer);

        assertEquals(s, newSquad);
    }

    @Test
    public void test_replacePlayer_notfound() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        Player newPlayer = Squad.getPlayerAtActualPos(s, ActualPosition.GK);
        Player oldPlayer = new Player(85);
        Squad newSquad = Squad.replacePlayer(s, oldPlayer, newPlayer);

        assertEquals(s, newSquad);
    }

    @Test
    public void test_replacePlayer_happy() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        Player newPlayer = new Player(85);
        Player oldPlayer = Squad.getPlayerAtActualPos(s, ActualPosition.GK);
        Squad newSquad = Squad.replacePlayer(s, oldPlayer, newPlayer);

        assertTrue(newSquad.getPlayers().stream().anyMatch(player -> player.getRating() == 85));
    }

    @Test
    public void test_getPlayerAtActualPos() throws Exception {
        // 41212
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        Player playerRetrieved = Squad.getPlayerAtActualPos(s, ActualPosition.GK);

        assertSame(s.getLineup().get(new Position(BasePosition.GK)), playerRetrieved);
    }

    @Test
    public void test_getPlayerAtActualPos_multiPartPosition() throws Exception {
        // 41212
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        Player playerRetrieved = Squad.getPlayerAtActualPos(s, ActualPosition.LCB);

        assertEquals(s.getLineup().get(new Position(BasePosition.CB, ActualPosition.RCB)), playerRetrieved);
    }

    @Test
    public void test_optimizeRatingWithoutReducingChem_fixed() throws Exception {
        PlayerLoaderUtil pl = PlayerLoaderUtil.getInstance();
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player p1 = pl.lookupPlayer("alisson", 90);
            Player p2 = pl.lookupPlayer("laporte", 87);
            Player p3 = pl.lookupPlayer("ronaldo", 98);
            Player p4 = pl.lookupPlayer("vela", 83);
            Player p5 = pl.lookupPlayer("matuidi", 83);
            Player p6 = pl.lookupPlayer("casemiro", 89);
            Player p7 = pl.lookupPlayer("ter stegen", 90);
            Player p8 = pl.lookupPlayer("grimaldo", 87);
            Player p9 = pl.lookupPlayer("rafa", 83);
            Player p10 = pl.lookupPlayer("strakosha", 83);
            Player p11 = pl.lookupPlayer("gonzalo", 83);

            players.add(p1); //rb
            players.add(p2); //lb
            players.add(p3); //gk
            players.add(p4); //lcb
            players.add(p5); //rcb
            players.add(p6); //rm
            players.add(p7); //cdm
            players.add(p8); //lm
            players.add(p9); //lst
            players.add(p10); //rst
            players.add(p11); //cam

        } catch (PlayerNotFoundException e) {
            System.out.println(e.getMessage());
        }

        Squad oldSquad = new Squad(positions, players, f);

        Squad betterSquad = Squad.optimizeRatingWithoutReducingChem(oldSquad, 2);

        assertTrue(betterSquad.getFractionalSquadRating() >= oldSquad.getFractionalSquadRating());
        assertTrue(betterSquad.getSquadPrice() <= oldSquad.getSquadPrice());
        assertTrue(ChemistryEngine.calculateChemistry(betterSquad) >= ChemistryEngine.calculateChemistry(oldSquad));

        // more iterations should be as least as good
        Squad bestSquad = Squad.optimizeRatingWithoutReducingChem(betterSquad, 10);

        assertTrue(bestSquad.getFractionalSquadRating() >= betterSquad.getFractionalSquadRating());
        assertTrue(bestSquad.getSquadPrice() <= betterSquad.getSquadPrice());
        assertTrue(ChemistryEngine.calculateChemistry(bestSquad) >= ChemistryEngine.calculateChemistry(betterSquad));
    }

    @Test
    public void test_optimizeRatingWithoutReducingChem_random() throws Exception {
        PlayerLoaderUtil pl = PlayerLoaderUtil.getInstance();
        ArrayList<Player> players = pl.get11RandomGoldPlayers(new Random().nextLong());
        FormationFactory ff = new FormationFactory();
        Formation formation = ff.getFormation("41212");
        ArrayList<Position> positions = formation.getPositions();

        Squad oldSquad = new Squad(positions, players, formation);

        Squad betterSquad = Squad.optimizeRatingWithoutReducingChem(oldSquad, 2);

        assertTrue(betterSquad.getFractionalSquadRating() >= oldSquad.getFractionalSquadRating());
        assertTrue(betterSquad.getSquadPrice() <= oldSquad.getSquadPrice());
        assertTrue(ChemistryEngine.calculateChemistry(betterSquad) >= ChemistryEngine.calculateChemistry(oldSquad));

        // more iterations should be as least as good
        Squad bestSquad = Squad.optimizeRatingWithoutReducingChem(betterSquad, 10);

        assertTrue(bestSquad.getFractionalSquadRating() >= betterSquad.getFractionalSquadRating());
        assertTrue(bestSquad.getSquadPrice() <= betterSquad.getSquadPrice());
        assertTrue(ChemistryEngine.calculateChemistry(bestSquad) >= ChemistryEngine.calculateChemistry(betterSquad));
    }

    @Test
    public void swapNRandomPlayers_cannotAddToBrick() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        s.setBrick(new Brick(new Position(BasePosition.GK)));
        // index of brickedPlayer in createTestSquad is last index

        // should end early because not possible to swap out 11 players without having to swap the 1 brick
        Squad newSquad = Squad.swapNRandomPlayers(11, s, pl.getAll86Plus());

        assertEquals(s, newSquad);
    }

    @Test
    public void swapNRandomPlayers_swap11Players() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        // should end early because not possible to swap out 11 players without having to swap the 1 brick
        Squad newSquad = Squad.swapNRandomPlayers(11, s, pl.getAll86Plus());

        assertTrue(newSquad.getPlayers().stream().allMatch(player -> !s.getPlayers().contains(player)));
    }

    @Test
    public void swapNRandomPlayers_swap0Players() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        // should end early because not possible to swap out 11 players without having to swap the 1 brick
        Squad newSquad = Squad.swapNRandomPlayers(0, s, pl.getAll86Plus());

        assertTrue(newSquad.getPlayers().stream().allMatch(player -> s.getPlayers().contains(player)));
    }

    @Test
    public void swapNRandomPlayers_swapSomePlayers() throws Exception {
        Squad s = SquadHelper.createTestSquad("bayern", "germany", "bundesliga", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false);

        // should end early because not possible to swap out 11 players without having to swap the 1 brick
        Squad newSquad = Squad.swapNRandomPlayers(6, s, pl.getAll86Plus());

        assertTrue(newSquad.getPlayers().stream().anyMatch(player -> player.getRating() >= 86));
        assertTrue(newSquad.getPlayers().stream().anyMatch(player -> player.getRating() < 86));
    }

    @Test
    public void updateAtPos() {
    }

    @Test
    public void testUpdateAtPos() {
    }

    @Test
    public void updateLineup() {
    }

    @Test
    public void getSquadPrice() {
    }

    @Test
    public void getSquadRating() {
    }

    @Test
    public void getFractionalSquadRating() {
    }

    @Test
    public void getPlayersAsString() {
    }

    @Test
    public void getPlayerAtPosition() {
    }

    @Test
    public void getNumOfCardType() {
    }

    @Test
    public void getNumOfNation() {
    }

    @Test
    public void getNumOfLeague() {
    }

    @Test
    public void getNumOfTeam() {
    }

    @Test
    public void getNumPlayers() {
    }

    @Test
    public void getNumOfClub() {
    }

    @Test
    public void getGraph() {
    }

    @Test
    public void test_getPossibleCombinationsForSquadRating() throws Exception {
        for (int rating=81; rating<=90; rating++) {
            ArrayList<ArrayList<Integer>> combinations = Squad.getPossibleCombinationsForSquadRating(rating);
            for (ArrayList<Integer> list : combinations) {ArrayList<Player> players = new ArrayList<>();
                FormationFactory ff = new FormationFactory();
                Formation f = ff.getFormation("41212");
                ArrayList<Position> positions = f.getPositions();
                for (int value : list) {
                    Player p = new Player(value);
                    players.add(p);
                }
                Squad s = new Squad(positions, players, f);
                assertEquals((double)rating, s.getSquadRating());
                System.out.println("Matched expected rating of: [" + rating + "] ratings: " + list.toString());
            }
        }
    }

    @Test
    public void test_shuffleSquadForBetterChemistry() {
    }

    @Test
    public void optimizeChemWithoutReducingRating() throws Exception {
        PlayerLoaderUtil pl = PlayerLoaderUtil.getInstance();
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player p1 = pl.lookupPlayer("", 87, "uruguay", "", "", "");
            Player p2 = pl.lookupPlayer("shaw", 86);
            Player p3 = pl.lookupPlayer("alisson", 90);
            Player p4 = pl.lookupPlayer("gomez", 85);
            Player p5 = pl.lookupPlayer("walker", 86);
            Player p6 = pl.lookupPlayer("ilkay", 83);
            Player p7 = pl.lookupPlayer("kroos", 88);
            Player p8 = pl.lookupPlayer("reus", 87);
            Player p9 = pl.lookupPlayer("niklas", 84);
            Player p10 = pl.lookupPlayer("n'golo", 88);
            Player p11 = pl.lookupPlayer("sterling", 88);

            players.add(p1); //rb
            players.add(p2); //lb
            players.add(p3); //gk
            players.add(p4); //lcb
            players.add(p5); //rcb
            players.add(p6); //rm
            players.add(p7); //cdm
            players.add(p8); //lm
            players.add(p9); //lst
            players.add(p10); //rst
            players.add(p11); //cam

        } catch (PlayerNotFoundException e) {
            System.out.println(e.getMessage());
        }

        Squad currentSquad = new Squad(positions, players, f);
        Squad optimizedSquad = Squad.optimizeChemWithoutReducingRating(currentSquad, 30);
        Squad doubleOptimized = Squad.optimizeRatingWithoutReducingChem(optimizedSquad, 10);

        assertEquals(currentSquad, Squad.optimizeRatingWithoutReducingChem(optimizedSquad, 10));
    }
}