package hw6.test;

import java.io.*;
import java.util.*;

import hw5.*; //imported
import hw6.MarvelParser.MalformedDataException;
import hw6.MarvelPaths;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph, the Marvel parser, and your BFS
 * algorithm.
 **/
public class HW6TestDriver {

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW6TestDriver td;

            if (args.length == 0) {
                td = new HW6TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW6TestDriver(new FileReader(tests),
                                           new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw6.test.HW6TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw6.test.HW6TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    private final Map<String, Graph<String, String>> graphs = new HashMap<String, Graph<String, String>>();
    
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW6TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW6TestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests()
        throws IOException
    {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("LoadGraph")) {
                loadGraph(arguments);
            } else if (command.equals("FindPath")) {
                findPath(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph<String, String>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String, String> graph = graphs.get(graphName);
        graph.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
            String edgeLabel) {
        
        Graph<String, String> graph = graphs.get(graphName);
        graph.addEdge(parentName, childName, edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph<String, String> graph = graphs.get(graphName);
        Set<String> setNodes = graph.getNodes();
        List<String> nodes = new ArrayList<String>(setNodes);
        output.print(graphName + " contains:");
        int nodeSize = nodes.size();
        if (nodeSize > 0) {
            Collections.sort(nodes);
            //nodes.sort((String s1, String n2) -> (s1.compareTo(s2)));
            for (int i = 0; i < nodeSize; i++) {
                output.print(" " + nodes.get(i));
            }
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Graph<String, String> graph = graphs.get(graphName);
        List<Edge<String, String>> edges = graph.getEdges(parentName);
        output.print("the children of " + parentName + " in " + graphName + " are:");
        int edgeSize = edges.size();
        if (edgeSize > 0) {
            edges.sort((Edge<String, String> e1, Edge<String, String> e2) -> {
                if (e1.getChild().equals(e2.getChild())) {
                    return e1.getLabel().compareTo(e2.getLabel());
                } else {
                    return e1.getChild().compareTo(e2.getChild());
                }
            });
            for (int i = 0; i < edgeSize; i++) {
                Edge<String, String> edge = edges.get(i);
                output.print(" " + edge.getChild() + "(" + edge.getLabel() + ")");
            }
        } 
        output.println();
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
    
    private void loadGraph(List<String> arguments) throws MalformedDataException {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graph = arguments.get(0);
        String file = arguments.get(1);

        loadGraph(graph, file);
    }

    private void loadGraph(String graph, String file) throws MalformedDataException {
        Graph<String, String> marvelGraph = MarvelPaths.loadGraph("src/hw6/data/" + file);
        graphs.put(graph, marvelGraph);
        output.println("loaded graph " + graph);
    }
    
    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }
        String graph = arguments.get(0);
        String start = arguments.get(1);
        String end = arguments.get(2);
        
        findPath(graph, start.replace("_", " "), end.replace("_", " "));
    }

    private void findPath(String graphName, String start, String end) {
        Graph<String, String> graph = graphs.get(graphName);
        Set<String> nodes = graph.getNodes();
        if (!nodes.contains(start) || !nodes.contains(end)) {
            if (!nodes.contains(start)) {
                output.println("unknown character " + start);
            }
            if (!nodes.contains(end)) {
                output.println("unknown character " + end);
            }
        } else {
            List<Edge<String, String>> path = MarvelPaths.findPath(graph, start, end);
            output.println("path from " + start + " to " + end + ":");
            String last = start;
            if (path == null) {
                output.println("no path found");
            } else {
                for (int i = 0; i < path.size(); i++) {
                    Edge<String, String> edge = path.get(i);
                    String child = edge.getChild();
                    output.println(last + " to " + child + " via " + edge.getLabel());
                    last = child;
                }
            }
        }
    }
}
