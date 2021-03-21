import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import player.Player;
import player.PlayerLoader;
import squad.Squad;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SquadTest {

    PlayerLoader pl;

    @BeforeEach
    public void setUp() {
        pl = new PlayerLoader();
        pl.loadPlayers(true);
    }

    @Test
    void getRatings() {
    }

    @Test
    void getSquadPrice() {

        long seed = new Long(13);
        ArrayList<Player> randos = pl.get11RandomPlayersFromNation(seed, "Germany");
        Squad s = new Squad(randos);

        assertEquals(189200.0, s.getSquadPrice());
    }

    @Test
    void printSquad() {
    }

    @Test
    void getLineup() {
    }

    @Test
    void getPositions() {
    }

    @Test
    void getPlayers() {
    }

    @Test
    void getGraph() {
    }
}