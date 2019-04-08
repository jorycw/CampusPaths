package hw8;

import java.io.*;
import java.util.*;

import hw5.Edge;

/**
 * Parser utility to load the Campus Paths dataset.
 */
public class CampusParser {
    /**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }

  /**
   * Reads the Campus Buildings dataset.
   * Each line of the input file contains an abbreviation of a building
   * the buildings name and the buildings x and y coordinates,
   * separated by a tab character
   * 
   * @requires filename is a valid file path
   * @param filename the file that will be read
   * @param buildings maps each buildings abbreviation to an object
   *        Building that holds information pertaining to that building
   * @modifies buildings
   * @effects fills buildings with abbreviation keys to building objects
   * @throws MalformedDataException if the file is not well-formed:
   *          each line contains exactly four tokens separated by a tab,
   *          or else starting with a # symbol to indicate a comment line.
   */
  public static void parseBuildings(String filename, Map<String, Building> buildings) throws MalformedDataException {
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));

        String inputLine;
        while ((inputLine = reader.readLine()) != null) {

            String[] tokens = inputLine.split("\t");
            if (tokens.length != 4) {
                throw new MalformedDataException("Line should contain exactly three tabs: "
                                                 + inputLine);
            }

            String abrev = tokens[0];
            String name = tokens[1];
            double x = Double.parseDouble(tokens[2]);
            double y = Double.parseDouble(tokens[3]);
            
            Building building = new Building(abrev, name, x, y);
            buildings.put(abrev, building);
        }
    } catch (IOException e) {
        System.err.println(e.toString());
        e.printStackTrace(System.err);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println(e.toString());
                e.printStackTrace(System.err);
            }
        }
    }
  }
  
  /**
   * Reads the Campus Paths dataset.
   * Each line of the input file contains x and y coordinates with
   * the following indented lines being connected paths to those
   * x and y coordinates along the the distance
   * 
   * parent path = "x,y"
   * child path = "\t x,t: distance"
   * 
   * @requires filename is a valid file path
   * @param filename the file that will be read
   * @param paths maps each coordinate to a list of coordinates it is connected
   * to in the form of edges with the distance as the label
   * @modifies paths
   * @effects fills paths with coordinate keys to lists of paths
   * @throws MalformedDataException if the file is not well-formed:
   *          each parent line contain two doubles seperate by a comma
   *          each child line contains three doubles, with the first two 
   *          coordinates seperated by a comma and the third (distance)
   *          seperate by a ":" colon
   */
  public static void parsePaths(String filename,
          Map<Coord, Set<Edge<Double, Coord>>> paths) throws MalformedDataException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String inputLine = reader.readLine();
            while (inputLine != null) {
                String[] tokens = inputLine.split(",");
                if (tokens.length != 2) {
                    throw new MalformedDataException("Line should contain exactly one tab: "
                                                     + inputLine);
                }
                double parentX = Double.parseDouble(tokens[0]);
                double parentY = Double.parseDouble(tokens[1]);
                Coord parentCoord = new Coord(parentX, parentY);
                
                if (!paths.containsKey(parentCoord)) {
                    paths.put(parentCoord, new HashSet<Edge<Double, Coord>>());
                }
                
                inputLine = reader.readLine();
                
                while (inputLine != null && inputLine.startsWith("\t")) {
                    String[] tokens2 = inputLine.split(",|: ");
                    if (tokens2.length != 3) {
                        throw new MalformedDataException("Line should contain exactly two tabs: "
                                                         + inputLine);
                    }
                    // 0 newX, 1 newY, 2 distance
                    double childX = Double.parseDouble(tokens2[0]);
                    double childY = Double.parseDouble(tokens2[1]);
                    double distance = Double.parseDouble(tokens2[2]);
                    Coord childCoord = new Coord(childX, childY);

                    paths.get(parentCoord).add(new Edge<Double, Coord>(distance, childCoord));
                    
                    inputLine = reader.readLine();
                    
                }
                
                
            }
        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.toString());
                    e.printStackTrace(System.err);
                }
            }
        }
      }

}

