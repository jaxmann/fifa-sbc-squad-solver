package player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import squad.SquadHelper;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private PlayerComparator playerComparator;

    @BeforeEach
    void setUp() {
        playerComparator = new PlayerComparator();
    }

    @Test
    void test_compare_negative() {
        Player p1 = new Player("p1", "team", "nation", "league", BasePosition.GK, CardType.GOLD_IF, 500, 85, false);
        Player p2 = new Player("p2", "team", "nation", "league", BasePosition.GK, CardType.GOLD_IF, 600, 85, false);
        assertEquals(-1, playerComparator.compare(p1, p2));
    }

    @Test
    void test_compare_positive() {
        Player p1 = new Player("p1", "team", "nation", "league", BasePosition.GK, CardType.GOLD_IF, 500, 85, false);
        Player p2 = new Player("p2", "team", "nation", "league", BasePosition.GK, CardType.GOLD_IF, 600, 85, false);
        assertEquals(1, playerComparator.compare(p2, p1));
    }

    @Test
    void test_compare_null() {
        Player p1 = new Player("p1", "team", "nation", "league", BasePosition.GK, CardType.GOLD_IF, 500, 85, false);
        try {
            assertEquals(0, playerComparator.compare(null, p1));
            fail("NPE was not thrown");
        } catch (NullPointerException npe) {
            assertTrue(true);
        }
    }
}