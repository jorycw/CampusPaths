package hw8.test;
import static org.junit.Assert.*;
import org.junit.Test;

import hw8.Coord;
/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Coord class.
 */

public class CoordTest {
    @Test
    public void testCreate() {
        Coord c = new Coord(1.1, 2.2);
        assertTrue(c != null);
    }
    
    @Test
    public void testGet() {
        Coord c = new Coord(1.1, 2.2);
        assertTrue(c.getX() == 1.1);
        assertTrue(c.getY() == 2.2);
    }
    
    @Test
    public void testEquals() {
        Coord c = new Coord(1.1, 2.2);
        Coord d = new Coord(1.1, 2.2);
        Coord e = new Coord(2.2, 1.1);
        assertTrue(c.equals(d));
        assertTrue(!c.equals(e));
    }
    
    @Test
    public void testHashCode() {
        Coord c = new Coord(1.1, 2.2);
        Coord d = new Coord(1.1, 2.2);
        assertTrue(c.hashCode() == d.hashCode());
    }
}
