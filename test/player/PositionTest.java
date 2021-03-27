package player;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PositionTest {

    @Before
    public void setUp() {
    }

    @Test
    public void test_equals() {
        Position pos1 = new Position(BasePosition.CB, ActualPosition.RCB);
        Position pos2 = new Position(BasePosition.CB, ActualPosition.RCB);
        assertEquals(pos1, pos2);
    }

    @Test
    public void test_equals_identity() {
        Position pos1 = new Position(BasePosition.CB, ActualPosition.RCB);
        assertEquals(pos1, pos1);
    }

    @Test
    public void test_equals_invalid() {
        Position pos1 = new Position(BasePosition.CB, ActualPosition.RCB);
        Position pos2 = new Position(BasePosition.CB, ActualPosition.LCB);
        assertNotEquals(pos1, pos2);
    }

    @Test
    public void test_equals_invalidObject() {
        Position pos1 = new Position(BasePosition.CB, ActualPosition.RCB);
        Player p1 = new Player(85);
        assertDoesNotThrow(() -> assertNotEquals(pos1, p1));
    }

    @Test
    public void test_hashCode() {
        Position pos1 = new Position(BasePosition.CB, ActualPosition.RCB);
        Position pos2 = new Position(BasePosition.CB, ActualPosition.RCB);
        assertTrue(pos1.hashCode() == pos2.hashCode());
    }

}