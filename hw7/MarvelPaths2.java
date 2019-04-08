package hw7;
import java.util.*;


import hw5.*;
import hw6.*;
/** <b>MarvelPaths2</b> is a class specifically created to represent 
 * the network between Marvel characters and the comic books they are in 
 * as a directed graph and use a Dijkstra's Algorithm to quickly find
 * the shortest path between two characters (where the length of each
 * path is the inverse of how many different comic books two characters
 * appear in together). 
*/
public class MarvelPaths2 {
    /*
     * This class is purely static and doesn't represent a certain abstract
     * data type.  
     */
    
    /**
     * @param filename name of file containing 
     * nodes/edges for MarvelParser to parse
     * @return Graph object with all the given nodes containing 
     * edges directed towards all those that they share a comic 
     * book, Graph<String, Double> object
     * where the edge labels are the inverse of how many books they
     * share
     * @effects creates a directed graph of the parsed given file
     * @requires fileName to be correctly formatted for MarvelParser
     */
    public static Graph<String, Double> loadGraph(String filename) {
        Graph<String, String> graphBase = MarvelPaths.loadGraph(filename);
        Graph<String, Double> graphRet = new Graph<String, Double>();
        
        for (String node : graphBase.getNodes()) {
            graphRet.addNode(node); // transfer nodes from graph<S, S> to graph<S, D>
        }
        for (String parent : graphBase.getNodes()) {
            // for each parent in the graph
            Map<String, Double> weight = new HashMap<String, Double>();
            // weight calculates the weight between the parent and all of its children
            for (Edge<String, String> book : graphBase.getEdges(parent)) {
                // for each edge/child
                String child = book.getChild();
                if (!weight.keySet().contains(child)) {
                    // either add the child with a weight of 1 if hasnt been added
                    weight.put(child, 1.0);
                } else {
                    // otherwise increase its weight by one
                    weight.put(child, weight.get(child) + 1);
                }
            }
            
            for (String child : weight.keySet()) {
                graphRet.addEdge(parent, child, 1 / weight.get(child));
                // adds all edges into new graph, taking weight inverses
            }
        }
        return graphRet;
    }
    
    /**
     * @param graph Graph to find paths in, Graph<String, Double>
     * @param start The node that the path is to start from
     * @param end The node that the path is to end on
     * @return the shortest path from start to end in the form of
     * a List of Edges in the order that the directed path goes
     * (shorter meaning more comic books shared), the last Edge
     * is an edge holding the total cost
     * Returns null if no path exists
     * If two paths are equiv, returns path whose first edges
     * contain lower costs
     * @effects Using Dijkstra's Algorithm, returns
     * the shortest path from node start to node end, breaking ties
     * with the lowest path
     */
    public static <T> List<Edge<Double, T>> findPath(Graph<T, Double> graph, T start, T end) {
        Queue<Edge<Double, T>> toVisit =
                new PriorityQueue<Edge<Double, T>>((e1, e2) ->
                Double.compare(e1.getLabel(), e2.getLabel()));
        // queue sorts by each edges length, edges are place holders for what nodes we are at, and 
        // total costs so far, actual paths are store under "visited"
        Map<T, List<Edge<Double, T>>> visited = new HashMap<T, List<Edge<Double, T>>>();
        // stores shortest paths to each key, in order inserted into list
        List<Edge<Double, T>> startList = new ArrayList<Edge<Double, T>>();
        Edge<Double, T> baseEdge = new Edge<Double, T>(0.0, start);
        startList.add(baseEdge);
        toVisit.add(baseEdge);
        visited.put(start, startList);
        // initialize for first node
        while (!toVisit.isEmpty()) { 
            Edge<Double, T> node = toVisit.remove();
            T nodeName = node.getChild();
            List<Edge<Double, T>> pastPath = visited.get(nodeName);
            if (!nodeName.equals(end)) {
                // if not at end node, address paths to all this nodes children
                // this works because we go back to shortest path in queue after
                // and override any single paths if shorter one is found
                List<Edge<Double, T>> edges = graph.getEdges(nodeName);
                edges.sort((Edge<Double, T> e1, Edge<Double, T> e2) -> 
                        (Double.compare(e1.getLabel(), (e2.getLabel())))); 
                // sorting by shortest paths
                for (Edge<Double, T> nextPath : edges) {
                    T child = nextPath.getChild();
                    List<Edge<Double, T>> childList = 
                            new ArrayList<Edge<Double, T>>(pastPath);
                    double totalCost = childList.get(childList.size() - 1).getLabel();
                    Edge<Double, T> costHolder = 
                            new Edge<Double, T>(nextPath.getLabel() + totalCost, child);
                    // new placeholder for overall cost
                    childList.set(childList.size() - 1, nextPath);
                    // replace the last costHolder with this edge
                    childList.add(costHolder);
                    // append new costHolder to end
                    if (!visited.containsKey(child) || 
                            visited.get(child).get(visited.get(child).size() - 1).getLabel()
                            > costHolder.getLabel()) {
                    // first check : haven't visited node yet,
                    // second check : have visited node but this path is shorter
                        visited.put(child, childList);
                        // either puts path in visited or overrides larger path
                        toVisit.add(costHolder);
                        // adds node priority queue
                    }
                } 
            } else {
                List<Edge<Double, T>> list = new ArrayList<Edge<Double, T>>(pastPath);
                list.add(node);
                // return current path for the node we are on + a edge holding total cost
                return list;
            }
        }
        // all nodes have been visited and end not found
        return null;
    }
}

