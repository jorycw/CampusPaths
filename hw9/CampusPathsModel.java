package hw9;

import java.awt.Point;
import java.util.*;
import javax.swing.JComponent;
import hw5.*;
import hw8.*;
/**
 * 
 * @author Jory Williams
 * Model component for campus paths GUI
 *
 */
public class CampusPathsModel extends JComponent {
    private static final long serialVersionUID = 1L;
    private int width, height;
    private Point start, end;
    private CampusPaths graph;
    private List<Edge<Double, Coord>> path;
    
    /**
     * Construct model component for campus paths GUI and initializes
     * fields
     * @param g CampusPaths object of paths on campus to be used
     * @param h height of GUI
     * @param w width of GUI
     */
    public CampusPathsModel(CampusPaths g, int h, int w) {
        width = w;
        height = h;
        graph = g;
        path = null;
        start = null;
        end = null; 
    }
    /**
     * 
     * @return Point object of starting point for last call of findPath
     */
    public Point getStart() {
        return new Point(start);
    }
   
    /**
     * 
     * @return Point object of ending point for last call of findPath
     */
        public Point getEnd() {
        return new Point(end);
    }
    /**
     * @return width of GUI
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * @return height of GUI
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Finds shortest path between two given buildings and stores it
     * @param startAbrev abreviation of starting building
     * @param endAbrev abreviation of ending building
     * @requires both buildings to be in current CampusPaths
     */
    public void findPath(String startAbrev, String endAbrev) {
        Map<String, Building> builds = graph.getBuildings();
        Building start = builds.get(startAbrev);
        Building end = builds.get(endAbrev);
        this.start = new Point((int)start.getCoord().getX(), (int)start.getCoord().getY());
        this.end = new Point((int)end.getCoord().getX(), (int)end.getCoord().getY());
        path = graph.findPath(startAbrev, endAbrev);
    }
    
    /**
     * 
     * @return Path between buildings what the findPath method was last called
     */
    public List<Edge<Double, Coord>> getPath() {
        return path;
    }
    /**
     * resets the state of the GUI back to when it was created
     */
    public void reset() {
        path = null;
    }
}
