package hw8;
import java.util.*;

import hw5.*;
import hw7.MarvelPaths2;
/** <b>CampusPaths</b> represents an <b>immutable</b> collection of nodes with
 * x and y coordinates that paths between those nodes with certain distances. 
 * <p>
 * CampusPaths can be used to find the shortest path (as far as distance) between
 * to coordinate points.
*/
//("immutable" is a common term for which "Effective Java" (p. 63)
//provides the following definition: "An immutatable class is simply
//a class whose instances cannot be modified.  All of the information
//contained in each instance is provided when it is created and is
//fixed for the lifetime of the object.")
public class CampusPaths {

    private final Map<String, Building> buildings;
    private final Graph<Coord, Double> graph;
    private static final boolean IS_DEBUG = false;
    
    // Abstraction Function:
    // AF(CampusPaths) a CampusPaths, c collection of 
    // points, n as coords, with paths, p between them as edges
    // with a label of distance d
    //
    //
    // Representation invariant for every CampusPaths c
    //   c != null
    //   n != null
    //   d != null
    //   every key in graph and its value != null
    //   every key in buildings and its value != null
    //   
    
    /** 
     * @requires buildingFile, pathfile to be a valid path name, to be 
     *           formatted correctly
     * @param buildingFile String of file to be parsed for all buildings
     * @param pathFile String of file to be parsed for all paths between all 
     *        coordinates
     * @modifies this
     * @effects fills buildings with each buildings abbreviation to a building
     *           object containing all information about the building
 *     @effects fills graph with coordinate nodes and edges between them 
 *              representing paths with labels being distances
     */
    public CampusPaths(String buildingFile, String pathFile) {
        buildings = new HashMap<String, Building>();
        Map<Coord, Set<Edge<Double, Coord>>> paths = 
                new HashMap<Coord, Set<Edge<Double, Coord>>>();
        try {
            CampusParser.parseBuildings(buildingFile, buildings);
            CampusParser.parsePaths(pathFile, paths);
        } catch (hw8.CampusParser.MalformedDataException e) {
            System.out.println("Caught IOException: "
                    + e.getMessage());
        }
        
        graph = new Graph<Coord, Double>();
        
        for (Coord coord : paths.keySet()) {
            graph.addNode(coord);
        }
        
        for (Coord coord : paths.keySet()) {
            Set<Edge<Double, Coord>> listOfPaths = paths.get(coord);
            for (Edge<Double, Coord> path : listOfPaths) {
                graph.addEdge(coord, path.getChild(), path.getLabel());
            }
        }
        checkRep();        
    }
    
    /**
     * @requires both buildings to already be in CampusPaths
     * @param abrevStart String of starting building in path, abbreviation
     * @param abrevEnd String of ending building in path, abbreviation
     * @return List of Edges representing least distance path with each
     *      edge being a single path from on node to the other in order
     *      last node is a placeholder for total distance traveled
     *      Null if no path between the two
     */
    public List<Edge<Double, Coord>> findPath(String abrevStart, String abrevEnd) {
        checkRep();
        Coord start = buildings.get(abrevStart).getCoord();
        Coord end = buildings.get(abrevEnd).getCoord();
        checkRep();
        return MarvelPaths2.findPath(graph, start, end);
    }
    /**
     * 
     * @return Map of all building abbreviations to the building object
     * contain abbreviation, name and coordinates of building
     */
    public Map<String, Building> getBuildings() {
        checkRep();
        Map<String, Building> buildRet = new HashMap<String, Building>(buildings);
        checkRep();
        return buildRet;
    }
    
    /**
     * Checks that the representation invariant holds (if any).
     */
    private void checkRep() {
        if (IS_DEBUG) {
            assert (this != null) : "CampusPaths is null";
            for (String s : buildings.keySet()) {
                assert (s != null) : "null buildings key contained"; 
                assert (buildings.get(s) != null) : "null buildings value contained"; 
            }
            for (Coord c : graph.getNodes()) {
                assert (c != null) : "null graph node contained"; 
                for (Edge<Double, Coord> e : graph.getEdges(c)) {
                    assert (e != null) : "null graph Edge contained"; 
                    assert (e.getLabel() != null) : "null distance contained";
                }
                
            }
        }
    }
 }
