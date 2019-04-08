package hw8.test;
import static org.junit.Assert.*;
import org.junit.Test;

import hw8.Building;
/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Building class.
 */

public class BuildingTest {
    @Test
    public void testCreate() {
        Building b = new Building("ABC", "asdadsaf", 1.1, 2.2);
        assertTrue(b != null);
    }
    
    @Test
    public void testGet() {
        Building b = new Building("ABC", "asdadsaf", 1.1, 2.2);
        assertTrue(b.getAbrev().equals("ABC"));
        assertTrue(b.getName().equals("asdadsaf"));
        assertTrue(b.getCoord().getX() == 1.1);
        assertTrue(b.getCoord().getY() == 2.2);
    }
    
    @Test
    public void testEquals() {
        Building b = new Building("ABC", "asdadsaf", 1.1, 2.2);
        Building c = new Building("ABC", "asdadsaf", 1.1, 2.2);
        Building d = new Building("ABCD", "asdadsaf", 1.1, 2.2);
        Building e = new Building("ABC", "asdadsafD", 1.1, 2.2);
        Building f = new Building("ABC", "asdadsaf", 2.2, 1.1);
        assertTrue(b.equals(c));
        assertTrue(!b.equals(d));
        assertTrue(!b.equals(e));
        assertTrue(!b.equals(f));
    }
    
    @Test
    public void testHashCode() {
        Building b = new Building("ABC", "asdadsaf", 1.1, 2.2);
        Building c = new Building("ABC", "asdadsaf", 1.1, 2.2);
        assertTrue(c.hashCode() == b.hashCode());
    }
}
