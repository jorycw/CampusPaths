package hw5.test;
import static org.junit.Assert.*;

import java.util.*;

import hw5.*;
import org.junit.Test;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Graph class.
 */

public final class GraphTest {
    @Test
    public void testGraph() {
        Graph<String, String> g1 = new Graph<String, String>();
        assertTrue(g1 != null);
        assertTrue(Graph.class.equals(g1.getClass()));
    }
    
    @Test
    public void testAddNode() {
        Graph<String, String> g1 = new Graph<String, String>();
        g1.addNode("n1");
        Set<String> l1 = g1.getNodes();
        assertTrue(l1.contains("n1"));
        assertTrue(l1.size() == 1);
    }
    
    @Test
    public void testAddEdges() {
        Graph<String, String> g1 = new Graph<String, String>();
        g1.addNode("p");
        g1.addNode("c");
        g1.addEdge("p", "c", "edge");
        Edge<String, String> e1 = new Edge<String, String>("edge", "c");
        List<Edge<String, String>> l1 = g1.getEdges("p");
        assertTrue(l1.size() == 1);
        assertTrue(l1.contains(e1));
        
        g1.addEdge("p", "notIn", "edge");
        l1 = g1.getEdges("p");
        assertTrue(l1.size() == 2);
        assertTrue(l1.contains(new Edge<String, String>("edge", "notIn")));
        
        g1.addEdge("p", "p", "edge");
        assertTrue(l1.size() == 2);
    }
    
    @Test
    public void testGetNodes() {
        Graph<String, String> g1 = new Graph<String, String>();
        g1.addNode("n1");
        Set<String> l1 = g1.getNodes();
        assertTrue(l1.contains("n1"));
        assertTrue(l1.size() == 1); 
        g1.addNode("n1");
        g1.addNode("n1");
        l1 = g1.getNodes();
        assertTrue(l1.size() == 1); 
        g1.addNode("n2");
        g1.addNode("n2");
        l1 = g1.getNodes();
        assertTrue(l1.contains("n2"));
        assertTrue(l1.size() == 2); 
    }
    
    @Test
    public void testGetEdges() {
        Graph<String, String> g1 = new Graph<String, String>();
        //Node n1 = new Node("n1");
        //Node n2 = new Node("n2");
        List<Edge<String, String>> list1 = new ArrayList<Edge<String, String>>();
        List<Edge<String, String>> list2;
        g1.addNode("n1");
        g1.addNode("n2");
        g1.addEdge("n1",  "n2", "oneToTwo");
        list1.add(new Edge<String, String>("oneToTwo", "n2"));
        
        list2 = g1.getEdges("n1");
        assertTrue(list1.containsAll(list2));
        assertTrue(list2.containsAll(list1));
        
        g1.addEdge("n1",  "n2", "oneToTwo"); // duplicate, dont add
        
        list2 = g1.getEdges("n1");
        assertTrue(list1.containsAll(list2));
        assertTrue(list2.containsAll(list1));
        
        g1.addEdge("n1",  "n2", "oneToTwoSecond");
        list1.add(new Edge<String, String>("oneToTwoSecond", "n2")); // same place, diff data
        
        list2 = g1.getEdges("n1");
        assertTrue(list1.containsAll(list2));
        assertTrue(list2.containsAll(list1));
        
        g1.addEdge("n1", "n1", "self");
        list1.add(new Edge<String, String>("self", "n1"));
        list2 = g1.getEdges("n1");
        assertTrue(list1.size() == list2.size());
        assertTrue(list1.containsAll(list2));
        assertTrue(list2.containsAll(list1));
        
        
    }
    
    
//    public Graph()
//    public void addNode(String data)
//    public void addEdge(Node parent, Node child, String label)
//    public List<Node> getNodes()
//    public List<Edge> getEdges(Node parent)
}
