package hw5;

/** <b>Edge</b> represents an <b>immutable</b> object containing a field
 *  for data concerning the Edge and a reference to the child Node that this
 *  directed Edge points to.
 *  
 *  Edge with a label of type V and a child node of type K
*/
//See Graphs's documentation for a definition of "immutable".

public class Edge<V, K> {

    private final V label;
    private final K child;
    /**
     * @param edgeLabel Label of directed edge
     * @param nodeChild Node that directed edge points to
     * @effects created a edge containing edgeLabel and a K of the
     * child node pointing to
     */
    public Edge(V edgeLabel, K childName) {
        label = edgeLabel;
        child = childName;
    }
    
    /**
     * @param oldEdge An edge object to make a copy of
     * @effects makes a copy of old edge object using the same
     *     label field child fields
     */
    public Edge(Edge<V, K> oldEdge) {
        this(oldEdge.label, oldEdge.child);
    }
    
    /**
     * @return label of type V of this edge
     */
    public V getLabel() {
        return label;
    }

    /**
     * @return child of type K that this directed edge points to
     */
    public K getChild() {
        return child;
    }
    
    /**
     * Standard equality operation.
     *
     * @param obj The object to be compared for equality.
     * @return true if and only if 'obj' and 'this'  are both Edge objects
     *  and contain the same label and child field, case sensitive
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge<?, ?> edge = (Edge<?, ?>) obj;
            return label.equals(edge.label) && child.equals(edge.child);         
        } else {
            return false;
        }
    }
    
    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also.
     */
    @Override
    public int hashCode() {
        return label.hashCode() + child.hashCode();
    }
    
    /**
     * Standard toString function.
     * 
     * @return a String representing this edge in the format
     *  -("label")-> "child" for visualization purposes
     * @requires type V and K to  have adequate toString methods for
     * proper visualization
     */
    
    @Override
    public String toString() {
        return "-(" + label + ")->" + child;
    }
}
