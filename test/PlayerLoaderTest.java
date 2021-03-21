import org.junit.jupiter.api.BeforeEach;
import player.Player;
import org.junit.jupiter.api.Test;
import player.PlayerLoader;
import player.position.ActualPosition;
import player.position.BasePosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerLoaderTest {

    private static boolean setUpIsDone = false;
    private PlayerLoader pl;

    @BeforeEach
    public void setUp() throws IOException {
        pl = new PlayerLoader();
        pl.loadPlayers(true);
    }

    @Test
    void loadPlayers() {
        assertNotNull(pl);
    }

    @Test
    void test_getAll82Plus() {
        ArrayList<Player> above82 = pl.getAll82Plus();
        assertTrue(above82.stream().allMatch(player -> player.getRating() >= 82));
    }

    @Test
    void test_getAll83Plus() {
        ArrayList<Player> above83 = pl.getAll83Plus();
        assertTrue(above83.stream().allMatch(player -> player.getRating() >= 83));
    }

    @Test
    void test_getAll84Plus() {
        ArrayList<Player> above84 = pl.getAll84Plus();
        assertTrue(above84.stream().allMatch(player -> player.getRating() >= 84));
    }

    @Test
    void test_getAll85Plus() {
        ArrayList<Player> above85 = pl.getAll85Plus();
        assertTrue(above85.stream().allMatch(player -> player.getRating() >= 85));
    }

    @Test
    void test_getAll86Plus() {

        ArrayList<Player> above86 = pl.getAll86Plus();
        assertTrue(above86.stream().allMatch(player -> player.getRating() >= 86));
    }

    @Test
    void test_getAll87PlusTest() {
        ArrayList<Player> above87 = pl.getAll87Plus();
        assertTrue(above87.stream().allMatch(player -> player.getRating() >= 87));
    }

    @Test
    void test_get11FrenchPlayers() {
        ArrayList<Player> frenchies = pl.get11FrenchPlayers();
        assertTrue(frenchies.stream().allMatch(player -> player.getNation().equals("France")));
    }

    @Test
    void test_getByTeam() {
        HashMap<String, ArrayList<Player>> byTeam = pl.getByTeam();

        assertTrue(byTeam.containsKey("Borussia Dortmund"));
        assertTrue(byTeam.containsKey("Juventus"));
        assertTrue(byTeam.containsKey("FC Barcelona"));
        assertTrue(byTeam.containsKey("Paris Saint-Germain"));
        assertTrue(byTeam.containsKey("Manchester United"));
    }

    @Test
    void test_getByLeague() {
        HashMap<String, ArrayList<Player>> byLeague = pl.getByLeague();

        assertTrue(byLeague.containsKey("Serie A TIM"));
        assertTrue(byLeague.containsKey("LaLiga Santander"));
        assertTrue(byLeague.containsKey("Premier League"));
        assertTrue(byLeague.containsKey("Bundesliga"));
        assertTrue(byLeague.containsKey("Ligue 1 Conforama"));
    }

    @Test
    void test_getByNation() {
        HashMap<String, ArrayList<Player>> byNation = pl.getByNation();

        assertTrue(byNation.containsKey("France"));
        assertTrue(byNation.containsKey("Uruguay"));
        assertTrue(byNation.containsKey("Germany"));
        assertTrue(byNation.containsKey("Spain"));
        assertTrue(byNation.containsKey("Australia"));
    }

    @Test
    void test_getByPos() {
        HashMap<String, ArrayList<Player>> byPos = pl.getByPos();

        // validate default positions exist
        assertTrue(byPos.containsKey(BasePosition.RB.toString()));
        assertTrue(byPos.containsKey("GK"));
        assertTrue(byPos.containsKey("CB"));
        assertTrue(byPos.containsKey("CF"));
        assertTrue(byPos.containsKey("LM"));

        // validate custom positions do not exist
        assertFalse(byPos.containsKey(ActualPosition.MCAM));
        assertFalse(byPos.containsKey(ActualPosition.RCDM));
    }

    @Test
    void test_getByRating() {
        HashMap<Integer, ArrayList<Player>> byRating = pl.getByRating();

        assertTrue(byRating.containsKey(85));
        assertTrue(byRating.containsKey(76));
        assertTrue(byRating.containsKey(90));
        assertTrue(byRating.containsKey(88));
        assertTrue(byRating.containsKey(54));

    }
}