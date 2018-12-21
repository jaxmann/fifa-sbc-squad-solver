import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SquadTest {

    @Test
    void getRatings() {
    }

    @Test
    void getSquadPrice() {

        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        long seed = new Long(13);
        ArrayList<Player> randos = pl.get11RandomPlayersFromNation(seed, "Germany");
        Squad s = new Squad(randos);

        assertEquals(96550.0, s.getSquadPrice());

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