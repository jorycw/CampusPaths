
package hw9;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import hw8.*;

/**
 * 
 * @author Jory Williams
 * Controller for campus paths GUI,
 *
 */
public class CampusPathsController extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    private JComboBox<String> startBuild;
    private JComboBox<String> endBuild;
    
    private CampusPathsView view;
    private CampusPathsModel model;
    /**
     * Constructs controller for campus paths GUI
     * @param graph CampusPaths object of the paths in campus
     * @param m Reference of model being used for GUI
     * @requires graph and m to not be null
     */
    public CampusPathsController(CampusPaths graph, CampusPathsModel m) {
        model = m;
        view = new CampusPathsView(model);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        Map<String, Building> buildMap = graph.getBuildings();
        String[] buildArray = new String[buildMap.keySet().size()];
        
        int i = 0;
        for (String abrev : buildMap.keySet()) {
            Building b = buildMap.get(abrev); 
            buildArray[i] = b.getAbrev() + " - " + b.getName();
            i++;
        }

        this.add(new JLabel("Choose Starting Building: "));
        startBuild = new JComboBox<String>(buildArray);
        this.add(startBuild);
        this.add(new JLabel("Choose Ending Building: "));
        endBuild = new JComboBox<String>(buildArray);
        this.add(endBuild);
        
        JButton findPath = new JButton("Find Path");
        JButton reset = new JButton("Reset");
        
        CampusButtonListener buttonListener = new CampusButtonListener();
        findPath.addActionListener(buttonListener);
        reset.addActionListener(buttonListener);
        
        this.add(reset);
        this.add(findPath);
        this.add(view);
    }
    
    /**
     * 
     * Action listener that either finds shortest path or resets GUI
     *
     */
    private class CampusButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
          switch(e.getActionCommand()) {
            case "Find Path":
                model.findPath(((String) startBuild.getSelectedItem()).split(" - ")[0], 
                        ((String) endBuild.getSelectedItem()).split(" - ")[0]);
                repaint();
                break;
            case "Reset":
                model.reset();
                repaint();
                break;
          }
        }
      }
}
