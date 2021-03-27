package squad;

import chemistry.ChemistryEngine;
import player.BasePosition;
import player.CardType;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SquadHelperTest {

    @Before
    public void setUp() {
    }

    @Test
    public void create87DefaultSquad() throws Exception {
        Squad s = SquadHelper.create87DefaultSquad();
        assertEquals(87.0, s.getSquadRating());
    }

    @Test
    public void create39ChemSquad() throws Exception {
        Squad s = SquadHelper.create43ChemSquad();
        assertEquals(43.0, ChemistryEngine.calculateChemistry(s));
    }

    @Test
    public void create85DefaultSquad() throws Exception {
        Squad s = SquadHelper.create85DefaultSquad();
        assertEquals(85.0, s.getSquadRating());
    }

    @Test
    public void create83DefaultSquad() throws Exception {
        Squad s = SquadHelper.create83DefaultSquad();
        assertEquals(83.0, s.getSquadRating());
    }

    @Test
    public void create75DefaultSquad() throws Exception {
        Squad s = SquadHelper.create75DefaultSquad();
        assertEquals(75.0, s.getSquadRating());
    }

    @Test
    public void createTestSquad() throws Exception {
        Squad s = SquadHelper.createTestSquad("team", "germany", "bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 89, false);
        assertEquals(89.0, s.getSquadRating());
        assertTrue(s.getPlayers().stream().allMatch(player -> player.getTeam().equals("team")));
        assertTrue(s.getPlayers().stream().allMatch(player -> player.getNation().equals("germany")));
        assertTrue(s.getPlayers().stream().allMatch(player -> player.getLeague().equals("bundesliga")));
        assertTrue(s.getPlayers().stream().allMatch(player -> player.getPosBase().equals(BasePosition.GK)));
        assertTrue(s.getPlayers().stream().allMatch(player -> player.getVersion().equals(CardType.GOLD_IF)));
        assertTrue(s.getPlayers().stream().allMatch(player -> player.getPrice() == 800));
        assertTrue(s.getPlayers().stream().allMatch(player -> player.getRating() == 89));
        assertTrue(s.getPlayers().stream().allMatch(player -> !player.getLoyalty()));
    }

}