package squad;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import player.Player;
import player.PlayerLoader;
import squad.Squad;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SquadTest {

    PlayerLoader pl;

    @BeforeEach
    public void setUp() throws IOException {
        pl = new PlayerLoader();
        pl.loadPlayers(true);
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