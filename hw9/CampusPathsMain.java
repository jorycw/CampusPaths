package hw9;

import javax.swing.JFrame;
import hw8.*;
/**
 * 
 * @author Jory Williams
 *  Puts all the GUI campus path finder Swing components together
 *  to display a map with the fastest route shown, made for a screen
 *  with a 1024 x 768 resolution.
 */
public class CampusPathsMain {
    
    private static final String buildings = "src/hw8/data/campus_buildings.dat";
    private static final String paths = "src/hw8/data/campus_paths.dat";
    private static final int viewVert = 768; 
    private static final int viewHor = 1024; 
    
    
    /**
     * Puts the Controller, Model and View components together and 
     * begins the graphical interface
     */
    public static void main(String[] args) {
        CampusPaths graph = new CampusPaths(buildings, paths);
        CampusPathsModel model = new CampusPathsModel(graph, viewVert, viewHor);
        CampusPathsController controller = 
                new CampusPathsController(graph, model);
        
        JFrame frame = new JFrame("UW Campus Path Finder");
        frame.setSize(viewVert, viewHor);
        frame.add(controller);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
