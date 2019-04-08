package hw5;
import java.util.*;
/** <b>Graph</b> represents an <b>immutable</b> collection of nodes 
 * and directed edges connecting nodes. 
 * <p>
 * A graph contains no duplicate nodes (a node is equal to another node
 * if the data field of both nodes are equal) and no node can contain a
 * duplicate edge (duplicate meaning equal parent, child, and label).
 * Nodes are of type K, and edge labels are of type V
*/
//("immutable" is a common term for which "Effective Java" (p. 63)
//provides the following definition: "An immutatable class is simply
//a class whose instances cannot be modified.  All of the information
//contained in each instance is provided when it is created and is
//fixed for the lifetime of the object.")

public class Graph<K, V> {

    private final Map<K, Set<Edge<V, K>>> graph;
    private static final boolean IS_DEBUG = false;
    
    // Abstraction Function:
    // AF(Graph) a graph, g of 
    // nodes, n and their data d, mapped to a list of 
    // directed edges and their labels, e between nodes
    // where {n1=[n3(e1), n4(e2)], n2,... } represents the graph of nodes 
    // with n1, n2 being nodes in g, n3, n4 being nodes that directed edges
    // from n1 point to with e1, e2 being the edges labels respectively
    //
    //
    // Representation invariant for every Graph g:
    //   0 <= i < graph.size()
    //   i < j < graph.size()
    //   0 < k < g{i}.size()
    //   0 < m < g{j}.size()
    //   where equals (and consequently contains) for edges 
    //   are defined in @overrides in the edge class
    //   nodes are represented directly as Strings as keys
    //
    //          !g.contains(null) &&
    //          !g{i}.equals(g{j}) &&
    //          !g{i[k]}.equals(g{j[m]}) &&
    //          !g{i}.contains(null);
    //
    //   In other words,
    //     * Graph does not contain null Nodes
    //     * Graph does not contain duplicate Nodes
    //     * There are no duplicate edges between Nodes
    //         (duplicate meaning same parent, child, and label)
    //     * There are no null edges between Nodes
    // (A representation invariant tells us something that is true for all
    // instances of a Graph)
    
    /**
     * @effects constructs a new Graph
     */
    public Graph() {
        graph = new HashMap<K, Set<Edge<V, K>>>();
        checkRep();
    }
    
    /**
     * @param data The data for the new node being constructed
     * @modifies this
     * @effects adds a node with the given data field to the graph
     */
    public void addNode(K node) {
        if (!graph.containsKey(node)) {
            graph.put(node, new HashSet<Edge<V, K>>());
        }
        checkRep();
    }
    
    /**
     *  
     * @param parent Parent node the directed edge will being going from
     * @param child Child node the directed edge will point to
     * @param label Label of the new edge
     * @modifies this
     * @effects creates a new edge with from parent to given child with label,
     * does nothing if duplicate edge already added
     * @requires parent node must already be added to graph
     */
    public void addEdge(K parent, K child, V label) {
        checkRep();
        Edge<V, K> edge = new Edge<V, K>(label, child);
        graph.get(parent).add(edge);
        checkRep();
    }
    
    /**
     * @return set of K type nodes currently in graph
     */
    public Set<K> getNodes() {
        checkRep();
        return graph.keySet();
    }
    
    /**
     * @param parent Node that the edges will come from
     * @return list of Edge's that are directed away from the parent Node
     * to its children, Edges of type V label and type K child node
     */
    public List<Edge<V, K>> getEdges(K parent) {
        checkRep();
        Set<Edge<V, K>> edgeSet = graph.get(parent);
        return new ArrayList<Edge<V, K>>(edgeSet);
    }
    
    /**
     * Checks that the representation invariant holds (if any).
     */
    private void checkRep() {
        if (IS_DEBUG) {
            assert (!graph.keySet().contains(null)) : "null node contained";
            for (K node : graph.keySet()) {
                assert (node!= null) : "null edge contained"; 
            }
        }
    }
    
}
