import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerLoaderTest {

    private static boolean setUpIsDone = false;
    private PlayerLoader pl;

    @Test
    void loadPlayers() {
        pl = new PlayerLoader();
        pl.loadPlayers();
        assertNotNull(pl);
    }

    @Test
    void get11FrenchPlayers() {
        pl = new PlayerLoader();
        pl.loadPlayers();

        ArrayList<Player> frenchies = pl.get11FrenchPlayers();
        for (Player p: frenchies) {
            assertEquals(p.getNation(), "France");
        }
    }

    @Test
    void getByTeam() {
        pl = new PlayerLoader();
        pl.loadPlayers();

        HashMap<String, ArrayList<Player>> byTeam = pl.getByTeam();

        assertTrue(byTeam.containsKey("Icons"));

        assertTrue(byTeam.containsKey("Borussia Dortmund"));
        assertTrue(byTeam.containsKey("Juventus"));
        assertTrue(byTeam.containsKey("FC Barcelona"));
        assertTrue(byTeam.containsKey("Paris Saint-Germain"));
        assertTrue(byTeam.containsKey("Manchester United"));
    }

    @Test
    void getByLeague() {
        pl = new PlayerLoader();
        pl.loadPlayers();

        HashMap<String, ArrayList<Player>> byLeague = pl.getByLeague();

        assertTrue(byLeague.containsKey("Serie A TIM"));
        assertTrue(byLeague.containsKey("LaLiga Santander"));
        assertTrue(byLeague.containsKey("Premier League"));
        assertTrue(byLeague.containsKey("Bundesliga"));
        assertTrue(byLeague.containsKey("Ligue 1 Conforama"));
    }

    @Test
    void getByNation() {
        pl = new PlayerLoader();
        pl.loadPlayers();

        HashMap<String, ArrayList<Player>> byNation = pl.getByNation();

        assertTrue(byNation.containsKey("France"));
        assertTrue(byNation.containsKey("Uruguay"));
        assertTrue(byNation.containsKey("Germany"));
        assertTrue(byNation.containsKey("Spain"));
        assertTrue(byNation.containsKey("Australia"));
    }

    @Test
    void getByPos() {
        pl = new PlayerLoader();
        pl.loadPlayers();

        HashMap<String, ArrayList<Player>> byPos = pl.getByPos();

        assertTrue(byPos.containsKey("RB"));
        assertTrue(byPos.containsKey("GK"));
        assertTrue(byPos.containsKey("CB"));
        assertTrue(byPos.containsKey("CF"));
        assertTrue(byPos.containsKey("LM"));

    }

    @Test
    void getByRating() {
        pl = new PlayerLoader();
        pl.loadPlayers();

        HashMap<Integer, ArrayList<Player>> byRating = pl.getByRating();

        assertTrue(byRating.containsKey(85));
        assertTrue(byRating.containsKey(76));
        assertTrue(byRating.containsKey(90));
        assertTrue(byRating.containsKey(88));
        assertTrue(byRating.containsKey(54));

    }


}