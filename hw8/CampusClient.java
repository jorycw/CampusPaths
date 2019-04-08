package hw8;

import java.util.*;

import hw5.Edge;

/** <b>Campus Client</b> is a class specifically created to represent 
 * the network of paths between different coordinates the the distance
 * between them and use a Dijkstra's Algorithm to quickly find
 * the shortest path between two coordinates
*/

public class CampusClient {
    /*
     * This class is purely static and doesn't represent a certain abstract
     * data type.  
     */
    
    /*
     * @effects using a CampusPaths runs a simple text interface, prompting
     * the user for commands to either list the buildings, find the shortest
     * route with Dijkstra's algorithm and print the possible menu options
     */
    public static void main(String[] args) {
        CampusPaths paths = new CampusPaths("src/hw8/data/campus_buildings.dat", 
                "src/hw8/data/campus_paths.dat");
        
        Scanner sc = new Scanner(System.in);
        String input = "";
        menu();
        while (!input.equals("q")) {
            System.out.println();
            System.out.print("Enter an option ('m' to see the menu): ");
            input = checkLine(sc);
            
            if (input.equals("b")) {
                Map<String, Building> buildingMap = paths.getBuildings();
                
                List<Building> buildings = new ArrayList<Building>(buildingMap.keySet().size());
                for (String abrev : buildingMap.keySet()) {
                    buildings.add(buildingMap.get(abrev));
                }
                
                buildings.sort((Building b1, Building b2) -> 
                        b1.getAbrev().compareTo(b2.getAbrev()));
                
                for (int i = 0; i < buildings.size(); i++) {
                    Building temp = buildings.get(i);
                    System.out.println(temp.getAbrev() + ": " + temp.getName());
                }
            } else if (input.equals("r")) {
                Map<String, Building> buildings = paths.getBuildings();
                
                System.out.print("Abbreviated name of starting building: ");
                String start = checkLine(sc);
                System.out.print("Abbreviated name of ending building: ");
                String end = checkLine(sc);
                
                boolean hasStart = buildings.keySet().contains(start);
                if (!hasStart) {
                    System.out.println("Unknown building: " + input);
                }
                boolean hasEnd = buildings.keySet().contains(end);
                if (!hasEnd) {
                    System.out.println("Unknown building: " + input);
                }
                
                if (hasStart && hasEnd) {
                    List<Edge<Double, Coord>> path = paths.findPath(start, end);
                    Coord lastCoord = buildings.get(start).getCoord();
                    System.out.println("Path from " + 
                            buildings.get(start).getName() + 
                            " to " + 
                            buildings.get(end).getName() + 
                            ":");
                    for (int i = 0; i < path.size() - 2; i++) {
                        Edge<Double, Coord> currPath = path.get(i);
                        Coord thisCoord = currPath.getChild();
                        String dir = getDir(thisCoord.getX() - lastCoord.getX(), lastCoord.getY() - thisCoord.getY());
                        System.out.println("\tWalk " + 
                                String.format("%.0f", currPath.getLabel()) + 
                                " feet " + 
                                dir + 
                                " to (" + 
                                String.format("%.0f", currPath.getChild().getX()) + 
                                ", " + 
                                String.format("%.0f", currPath.getChild().getY()) + 
                                ")");
                        lastCoord = thisCoord;
                    }
                    System.out.println("Total distance: " + 
                            String.format("%.0f", path.get(path.size() - 1).getLabel()) + 
                            " feet");
                }
            } else if (input.equals("m")) {
                menu();
            } else if (!input.equals("q")) {
                System.out.println("Unknown Option");
            }
        }

        sc.close();
    }
    
    /**
     * 
     * @param x coordinate from (0,0) to get the angle of theta from
     * @param y coordinate from (0,0) to get the angle of theta from
     * @return String direction as a capitol letter/s using the angle of 
     * given coordinates, tie breaker between two directions is given to
     * direction with on letter (i.e N S E W)
     */
    private static String getDir(double x, double y) {
        Double dir = Math.atan2(y,  x);
        dir = dir / Math.PI;
        dir = dir % 2;
        if (dir <= -.875) {
            return "W";
        } else if (dir < -.625) {
            return "SW";
        } else if (dir <= -.375) {
            return "S";
        } else if (dir < -.125) {
            return "SE";
        } else if (dir <= .125) {
            return "E";
        } else if (dir < .375) {
            return "NE";
        } else if (dir <= .625) {
            return "N";
        } else if (dir < .875) {
            return "NW";
        } else  {
            return "W";
        }
    }
    
    /**
     * @effects prints out menu options
     */
    private static void menu() {
        System.out.println("Menu:");
        System.out.println("\t" + "r to find a route");
        System.out.println("\tb to see a list of all buildings");
        System.out.println("\tq to quit");
    }
    
    /**
     * 
     * @param sc input from client as a scanner object
     * @return the nextLine that isn't empty and isn't commented
     * (starts with #)
     * @effects checks lines until it finds on that isn't empty and 
     * isn't a comment, and returns that line. Reprints all empty and
     * commented lines to console
     */
    private static String checkLine(Scanner sc) {
        String input = sc.nextLine();
        while (input.isEmpty() || input.startsWith("#")) {
            System.out.println(input);
            input = sc.nextLine();
        }
        return input;
    }
}
