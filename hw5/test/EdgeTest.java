package hw5.test;
import static org.junit.Assert.*;
import hw5.*;
import org.junit.Test;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Edge class.
 */

public final class EdgeTest{
    
    @Test
    public void testConstructorWLabelNode() {
        Edge<String, String> e1 = new Edge<String, String>("e1", "n1");
        assertTrue(e1 != null);
        assertEquals(Edge.class, e1.getClass());
    }
    
    @Test
    public void testGetLabel() {
        String label = "e1";
        Edge<String, String> e1 = new Edge<String, String>(label, "n1");
        assertTrue(e1.getLabel().equals(label));
        Edge<String, String> e2 = new Edge<String, String>(e1);
        assertTrue(e2.getLabel().equals(label));
    }
    
    @Test
    public void testGetChild() {
        //Node child = new Node("child");
        Edge<String, String> e1 = new Edge<String, String>("e1", "child");
        assertTrue(e1.getChild().equals("child"));
        Edge<String, String> e2 = new Edge<String, String>("e2", "child");
        assertTrue(e1.getChild().equals(e2.getChild()));
    }
    
    @Test
    public void testEquals() {
        Edge<String, String> e1 = new Edge<String, String>("e1+2+4", "n1");
        Edge<String, String> e2 = new Edge<String, String>("e1+2+4", "n1");
        Edge<String, String> e3 = new Edge<String, String>("e3", "n1");
        Edge<String, String> e4 = new Edge<String, String>("e1+2+4", "n2");
        assertTrue(e1.equals(e2));
        assertTrue(e1.getChild().equals(e2.getChild()));
        assertTrue(e1.getLabel().equals(e2.getLabel()));
        assertFalse(e1.equals(e3));
        assertFalse(e1.equals(e4));
    }
    
    @Test
    public void testConstructorWEdge() {
        Edge<String, String> e1 = new Edge<String, String>("e1", "n1");
        Edge<String, String> e2 = new Edge<String, String>(e1);
        assertTrue(e1.equals(e2));
        assertTrue(e1.getChild().equals(e2.getChild()));
        assertTrue(e1.getLabel().equals(e2.getLabel()));
    }
    
    @Test
    public void testHashCode() {
        Edge<String, String> e1 = new Edge<String, String>("e1", "n1");
        Edge<String, String> e2 = new Edge<String, String>("e1", "n1");
        Edge<String, String> e3 = new Edge<String, String>(e1);
        Edge<String, String> e4 = new Edge<String, String>("e1", "n1");
        assertTrue(e1.hashCode() == e2.hashCode());
        assertTrue(e1.hashCode() == e3.hashCode());
        assertTrue(e1.hashCode() == e4.hashCode());
    }
}
