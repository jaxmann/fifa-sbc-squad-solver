package player;

import java.io.IOException;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PlayerLoaderUtilTest {

    private PlayerLoaderUtil pl;

    @Before
   public void setUp() throws IOException {
        pl = PlayerLoaderUtil.getInstance();
    }

    @Test
   public void loadPlayers() {
        assertNotNull(pl);
    }

    @Test
   public void test_getAll82Plus() {
        ArrayList<Player> above82 = pl.getAll82Plus();
        assertTrue(above82.stream().allMatch(player -> player.getRating() >= 82));
    }

    @Test
   public void test_getAll83Plus() {
        ArrayList<Player> above83 = pl.getAll83Plus();
        assertTrue(above83.stream().allMatch(player -> player.getRating() >= 83));
    }

    @Test
   public void test_getAll84Plus() {
        ArrayList<Player> above84 = pl.getAll84Plus();
        assertTrue(above84.stream().allMatch(player -> player.getRating() >= 84));
    }

    @Test
   public void test_getAll85Plus() {
        ArrayList<Player> above85 = pl.getAll85Plus();
        assertTrue(above85.stream().allMatch(player -> player.getRating() >= 85));
    }

    @Test
   public void test_getAll86Plus() {
        ArrayList<Player> above86 = pl.getAll86Plus();
        assertTrue(above86.stream().allMatch(player -> player.getRating() >= 86));
    }

    @Test
   public void test_getAll87PlusTest() {
        ArrayList<Player> above87 = pl.getAll87Plus();
        assertTrue(above87.stream().allMatch(player -> player.getRating() >= 87));
    }

    @Test
   public void test_get11FrenchPlayers() {
        ArrayList<Player> frenchies = pl.get11FrenchPlayers();
        assertTrue(frenchies.stream().allMatch(player -> player.getNation().equals("France")));
    }

    @Test
   public void test_getByTeam() {
        HashMap<String, PriorityQueue<Player>> byTeam = pl.getByTeam();

        assertTrue(byTeam.entrySet().stream().anyMatch(team -> team.getKey().toLowerCase().contains("dortmund")));
        assertTrue(byTeam.entrySet().stream().anyMatch(team -> team.getKey().toLowerCase().contains("fc barcelona")));
        assertTrue(byTeam.entrySet().stream().anyMatch(team -> team.getKey().toLowerCase().contains("paris")));
        assertTrue(byTeam.entrySet().stream().anyMatch(team -> team.getKey().toLowerCase().contains("manchester")));
    }

    @Test
   public void test_getByLeague() {
        HashMap<String, PriorityQueue<Player>> byLeague = pl.getByLeague();

        assertTrue(byLeague.containsKey("Serie A TIM"));
        assertTrue(byLeague.containsKey("LaLiga Santander"));
        assertTrue(byLeague.containsKey("Premier League"));
        assertTrue(byLeague.containsKey("Bundesliga"));
        assertTrue(byLeague.containsKey("Ligue 1 Conforama"));
    }

    @Test
   public void test_getByNation() {
        HashMap<String, PriorityQueue<Player>> byNation = pl.getByNation();

        assertTrue(byNation.containsKey("France"));
        assertTrue(byNation.containsKey("Uruguay"));
        assertTrue(byNation.containsKey("Germany"));
        assertTrue(byNation.containsKey("Spain"));
        assertTrue(byNation.containsKey("Australia"));
    }

    @Test
   public void test_getByPos() {
        HashMap<String, PriorityQueue<Player>> byPos = pl.getByPos();

        // validate default positions exist
        assertTrue(byPos.containsKey(BasePosition.RB.toString()));
        assertTrue(byPos.containsKey("GK"));
        assertTrue(byPos.containsKey("CB"));
        assertTrue(byPos.containsKey("CF"));
        assertTrue(byPos.containsKey("LM"));

        // validate custom positions do not exist
        assertFalse(byPos.containsKey(ActualPosition.MCAM.toString()));
        assertFalse(byPos.containsKey(ActualPosition.RCDM.toString()));
    }

    @Test
   public void test_getByRating() {
        HashMap<Integer, PriorityQueue<Player>> byRating = pl.getByRating();

        assertTrue(byRating.containsKey(85));
        assertTrue(byRating.containsKey(76));
        assertTrue(byRating.containsKey(90));
        assertTrue(byRating.containsKey(88));
    }

    @Test
   public void test_mapVersionToCardType_gold_IF() {
        String revision = "IF";
        int rating = 75;
        assertEquals(PlayerLoaderUtil.mapVersionToCardType(revision, rating), CardType.GOLD_IF);
    }

    @Test
   public void test_mapVersionToCardType_silver_IF() {
        String revision = "SIF";
        int rating = 65;
        assertEquals(PlayerLoaderUtil.mapVersionToCardType(revision, rating), CardType.SILVER_IF);
    }

    @Test
   public void test_mapVersionToCardType_bronze_IF() {
        String revision = "TIF";
        int rating = 55;
        assertEquals(PlayerLoaderUtil.mapVersionToCardType(revision, rating), CardType.BRONZE_IF);
    }

    @Test
   public void test_convertStringPriceToDouble_hundred() {
        String price = "800";
        assertEquals(800.0, PlayerLoaderUtil.convertStringPriceToDouble(price));
    }

    @Test
   public void test_convertStringPriceToDouble_thousand() {
        // lower case as well
        String price = "30.7k";
        assertEquals(30700.0, PlayerLoaderUtil.convertStringPriceToDouble(price));
    }

    @Test
   public void test_convertStringPriceToDouble_million() {
        String price = "1.4M";
        assertEquals(1400000.0, PlayerLoaderUtil.convertStringPriceToDouble(price));
    }

    @Test
   public void test_convertStringPriceToDouble_invalid() {
        // due to parsing error or w/e - not a big deal since these are untradeable anyway
        String price = "POTM";
        assertEquals(15000000.0, PlayerLoaderUtil.convertStringPriceToDouble(price));
    }

    @Test
   public void test_getAnyPlayerAtPositionAndRating_happy() {
        // Arrange
        BasePosition expectedPos = BasePosition.GK;
        int expectedRating = 85;

        // Act
        Player player = pl.getAnyPlayerAtPositionAndRating(expectedPos, expectedRating);

        // Assert
        assertEquals(expectedPos, player.getPosBase());
        assertEquals(expectedRating, player.getRating());
    }

    @Test
   public void test_getAnyPlayerAtPositionAndRating_invalid() {
        // Arrange
        // impossible possible - no players should exist
        BasePosition expectedPos = BasePosition.INVALID;
        int expectedRating = 85;

        // Act
        Player player = pl.getAnyPlayerAtPositionAndRating(expectedPos, expectedRating);

        // Assert
        assertNull(player);
    }

    @Test
   public void test_getAnyPlayerAtExactRating() {
        // Arrange
        int expectedRating = 85;

        // Act
        Player player = pl.getAnyPlayerAtExactRating(expectedRating);

        // Assert
        assertEquals(expectedRating, player.getRating());
    }

    @Test
   public void test_getAnyPlayerAtExactRating_invalid() {
        // Arrange
        int expectedRating = 100;

        // Act
        Player player = pl.getAnyPlayerAtExactRating(expectedRating);

        // Assert
        assertNull(player);
    }

    @Test
   public void test_getNCheapestAtExactRating_happy() {
        // Arrange
        int expectedRating = 85;
        int expectedNumPlayers = 5;

        // Act
        ArrayList<Player> players = pl.getNCheapestAtExactRating(expectedNumPlayers, expectedRating);

        PriorityQueue<Player> cache = new PriorityQueue<>(pl.getByRating().get(expectedRating));

        // Assert
        assertTrue(players.stream().allMatch(player -> player.getRating() == expectedRating));
        assertEquals(players.size(), expectedNumPlayers);
        for (int i=0; i<expectedNumPlayers; i++) {
            // dump off players until N players deleted, next player should be N+1, which should be more expensive than all N returned by above func
            cache.remove();
        }
        Player nPlus1CheapestPlayer = cache.remove();
        assertTrue(players.stream().allMatch(player -> player.getPrice() < nPlus1CheapestPlayer.getPrice()));
    }

    @Test
   public void test_getNCheapestAtExactRating_impossibleRating() {
        // Arrange
        int expectedRating = 100;
        int expectedNumPlayers = 5;

        // Act
        ArrayList<Player> players = pl.getNCheapestAtExactRating(expectedNumPlayers, expectedRating);

        // Assert
        assertTrue(players.isEmpty());
    }

    @Test
   public void test_getNCheapestAtMinRating_happy() {
        // Arrange
        int expectedMinRating = 85;
        int expectedNumPlayers = 500;

        // Act
        ArrayList<Player> players = pl.getNCheapestAtMinRating(expectedNumPlayers, expectedMinRating);

        // Assert
        assertTrue(players.stream().allMatch(player -> player.getRating() >= expectedMinRating));
        assertTrue(players.stream().anyMatch(player -> player.getRating() > expectedMinRating));
        assertEquals(players.size(), expectedNumPlayers);
    }

    @Test
   public void test_getNCheapestAtMinRating_impossibleRating() {
        // Arrange
        int expectedMinRating = 99;
        int expectedNumPlayers = 500;

        // Act
        ArrayList<Player> players = pl.getNCheapestAtMinRating(expectedNumPlayers, expectedMinRating);

        // Assert
        assertTrue(players.isEmpty());
    }

    @Test
   public void test_getNCheapestAtExactRatingAndPosition_happy() {
        // Arrange
        int expectedRating = 83;
        int expectedNumPlayers = 2;
        BasePosition expectedPos = BasePosition.GK;

        // Act
        ArrayList<Player> players = pl.getNCheapestAtExactRatingAndPosition(expectedNumPlayers, expectedPos, expectedRating);
        PriorityQueue<Player> cache = new PriorityQueue<>(pl.getByRating().get(expectedRating));

        // Assert
        assertTrue(players.stream().allMatch(player -> player.getRating() == expectedRating));
        assertTrue(players.stream().allMatch(player -> player.getPosBase() == expectedPos));
        assertEquals(players.size(), expectedNumPlayers);
        while (expectedNumPlayers > 0) {
            // dump off players until N players deleted, next player should be N+1, which should be more expensive than all N returned by above func
            Player player = cache.remove();
            if (player.getPosBase() == expectedPos) {
                cache.remove();
                expectedNumPlayers--;
            } else {
                cache.remove();
            }
        }
        Player nPlus1CheapestPlayer = cache.remove();
        assertTrue(players.stream().allMatch(player -> player.getPrice() <= nPlus1CheapestPlayer.getPrice()));
    }

    @Test
   public void test_getNCheapestAtExactRatingAndPosition_impossibleRating() {
        // Arrange
        int expectedRating = 99;
        int expectedNumPlayers = 2;
        BasePosition expectedPos = BasePosition.GK;

        // Act
        ArrayList<Player> players = pl.getNCheapestAtExactRatingAndPosition(expectedNumPlayers, expectedPos, expectedRating);

        // Assert
        assertTrue(players.isEmpty());
    }

    @Test
   public void test_getNCheapestAtExactRatingAndPosition_notEnoughPlayers() {
        // Arrange
        int expectedRating = 85;
        int expectedNumPlayers = 200;
        BasePosition expectedPos = BasePosition.GK;

        // Act
        ArrayList<Player> players = assertDoesNotThrow(() -> pl.getNCheapestAtExactRatingAndPosition(expectedNumPlayers, expectedPos, expectedRating));

        // Assert
        assertTrue(players.stream().allMatch(player -> player.getRating() == expectedRating));
        assertTrue(players.stream().allMatch(player -> player.getPosBase() == expectedPos));
        assertTrue(players.size() < expectedNumPlayers);
    }

    @Test
   public void test_getNCheapestAtMinRatingAndPosition_happy() {
        // Arrange
        int expectedMinRating = 83;
        int expectedNumPlayers = 20;
        BasePosition expectedPos = BasePosition.GK;

        // Act
        ArrayList<Player> players = assertDoesNotThrow(() -> pl.getNCheapestAtMinRatingAndPosition(expectedNumPlayers, expectedPos, expectedMinRating));

        // Assert
        assertTrue(players.stream().allMatch(player -> player.getRating() >= expectedMinRating));
        assertTrue(players.stream().anyMatch(player -> player.getRating() > expectedMinRating));
        assertTrue(players.stream().allMatch(player -> player.getPosBase() == expectedPos));
        assertEquals(players.size(), expectedNumPlayers);
    }
}