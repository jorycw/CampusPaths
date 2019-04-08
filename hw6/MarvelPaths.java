package hw6;
import hw5.*;
import java.util.*;
import hw6.MarvelParser.MalformedDataException;
/** <b>MarvelPaths</b> is a class specifically created to represent 
 * the network between Marvel characters and the comic books they are in 
 * as a directed graph and use a breadth of first search to quickly find
 * the shortest path between two characters. 
*/
public class MarvelPaths {
    /*
     * This class is purely static and doesn't represent a certain abstract
     * data type.  
     */
    
    /**
     * @throws MalformedDataException when file given to loadGraph is not
     * correctly formated - (WILL NOT BE THE CASE)
     * @effects Using graph created from a file of marvel characters allows
     * client to look up the shortest paths between characters through the
     * command-line
     */
    public static void main(String[] args) {
        System.out.println("Please wait while Marvel Paths loads...");
        Graph<String, String> superHeroes = loadGraph("src/hw6/data/marvel.tsv");
        Scanner sc = new Scanner(System.in);
        String char1 = "";
        while (!char1.equalsIgnoreCase("EXIT")) {
                System.out.println("Between which two characters do you wish to find a path (CHAR_1 CHAR_2), to exit (EXIT), (Use underscores to represent spaces in a characters name)");
                Set<String> chars = superHeroes.getNodes();
                char1 = sc.next().toUpperCase().replace("_"," ");
                if (!char1.equalsIgnoreCase("EXIT")) {
                    if (!chars.contains(char1)) {
                        System.out.println("Marvel Paths does not contain first character inputted");
                    }
                    String char2 = sc.next().toUpperCase().replace("_"," ");
                    if (!chars.contains(char2)) {
                        System.out.println("Marvel Paths does not contain second character inputted");
                    }
                    if (chars.contains(char1) && chars.contains(char2)) {
                        List<Edge<String, String>> path = findPath(superHeroes, char1, char2);
                        if (path == null) {
                            System.out.print("No path found");
                        } else {
                            System.out.println(char1 + path.get(0));
                            for (int i = 1; i < path.size(); i++) {
                                System.out.println("" + path.get(i - 1).getChild() + path.get(i));
                            }
                        }
                    }
                }
        }
        System.out.println("Exiting marvel paths...");
        sc.close();
    }
    
    /**
     * @param filename name of file contained correctly formatted 
     * nodes/edges that the MarvelParser is able to parse
     * @return Graph object with all the given nodes containing 
     * edges directed towards all those that they share the same 
     * edge label with
     * @throws MalformedDataException if file given is not correctly
     * formatted for MarvelParser
     * @effects creates a directed graph of the parsed given file
     */
    public static Graph<String, String> loadGraph(String filename) {
        Set<String> characters = new HashSet<String>();
        Map<String, List<String>> books = new HashMap<String, List<String>>();
        try {
            MarvelParser.parseData(filename, characters, books);
        } catch (MalformedDataException e) {
            System.out.println("Caught IOException: "
                    + e.getMessage());
        }
        Graph<String, String> graph = new Graph<String, String>();
        for (String charact : characters) {
            graph.addNode(charact);
        }
        for (String book : books.keySet()) {
            List<String> listOfChars = books.get(book);
            for (int i = 0; i < listOfChars.size(); i++) {
                for (int j = i + 1; j < listOfChars.size(); j++) {
                    graph.addEdge(listOfChars.get(i), listOfChars.get(j), book);
                    graph.addEdge(listOfChars.get(j), listOfChars.get(i), book);
                }
            }
        }
        return graph;
    }
    
    /**
     * @param graph Graph to find paths in
     * @param start The node that the path is to start from
     * @param end The node that the path is to end on
     * @return the quickest path from start to end in the form of
     * a List of Edges in the order that the directed path goes
     * @effects Using Breadth First Search algorithm returns
     * the quickest path from node start to node end, breaking ties
     * lexicographically
     */
    public static List<Edge<String, String>> findPath(Graph<String, String> graph, String start, String end) {
        Queue<String> toVisit = new LinkedList<String>();
        Map<String, List<Edge<String, String>>> visited = new HashMap<String, List<Edge<String, String>>>();
        toVisit.add(start);
        visited.put(start, new ArrayList<Edge<String, String>>());
        while (!toVisit.isEmpty()) {  // checking all to visit
            String node = toVisit.remove();
            List<Edge<String, String>> nodeEdges = visited.get(node);
            if (!node.equals(end)) {
                List<Edge<String, String>> edges = graph.getEdges(node);
                edges.sort((Edge<String, String> e1, Edge<String, String> e2) -> 
                        (e1.getChild().compareTo(e2.getChild()))); //sorting lexigraphical
                for (Edge<String, String> path : edges) {
                    String child = path.getChild();
                    if (!visited.containsKey(child)) {
                        toVisit.add(child);
                        List<Edge<String, String>> childList = new ArrayList<Edge<String, String>>(nodeEdges);
                        childList.add(path);
                        visited.put(child, childList);
                    }
                }   
            } else {
                return new ArrayList<Edge<String, String>>(nodeEdges);
            }
        }
        return null;
    }
}
