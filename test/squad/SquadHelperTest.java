package squad;

import chemistry.ChemistryEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.BasePosition;
import player.CardType;

import static org.junit.jupiter.api.Assertions.*;

class SquadHelperTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void create87DefaultSquad() throws Exception {
        Squad s = SquadHelper.create87DefaultSquad();
        assertEquals(87, s.getSquadRating());
    }

    @Test
    void create39ChemSquad() throws Exception {
        Squad s = SquadHelper.create39ChemSquad();
        assertEquals(39, ChemistryEngine.calculateChemistry(s));
    }

    @Test
    void create85DefaultSquad() throws Exception {
        Squad s = SquadHelper.create85DefaultSquad();
        assertEquals(85, s.getSquadRating());
    }

    @Test
    void create83DefaultSquad() throws Exception {
        Squad s = SquadHelper.create83DefaultSquad();
        assertEquals(83, s.getSquadRating());
    }

    @Test
    void create75DefaultSquad() throws Exception {
        Squad s = SquadHelper.create75DefaultSquad();
        assertEquals(75, s.getSquadRating());
    }

    @Test
    void createTestSquad() throws Exception {
        Squad s = SquadHelper.createTestSquad("team", "germany", "bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 89, false);
        assertEquals(89, s.getSquadRating());
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