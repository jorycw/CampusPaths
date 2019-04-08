package hw9;
import java.util.List;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import hw5.Edge;
import hw8.Coord;
/**
 * 
 * @author Jory Williams
 * View component for campus paths GUI
 *
 */
public class CampusPathsView extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private CampusPathsModel model;
    private BufferedImage map;
    /**
     * @param m CampusPathsModel Campus Path object to be using for this view
     * 
     */
    public CampusPathsView(CampusPathsModel m) {
        model = m;
        try {
            map = ImageIO.read(new File("src/hw8/data/campus_map.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension((int)(model.getWidth() * .8),(int) (model.getHeight() * .8)));
    }
    /**
     * Set the view for the GUI whenever called
     * @param graphics graphic object to use
     * @modifies GUI currently seen by user
     * @effects displays route between to locations or 
     * resets GUI depending on user input of buttons
     */
    
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        double scaleX =  (819 + 0.0) / map.getWidth();
        double scaleY = (560 + 0.0) / map.getHeight();
        g.drawImage(map, 
                0, 0, 819, 560,
                null);
        List<Edge<Double, Coord>> path = model.getPath();
        graphics.setColor(Color.RED);
        if (path != null) {
            Coord last = path.get(0).getChild();
            g.setStroke(new BasicStroke(3));
            for (int i = 0; i < path.size() - 1; i++) {
                Coord curr = path.get(i).getChild();
                g.drawLine((int)((last.getX()) * scaleX), 
                        (int)((last.getY())* scaleY),
                        (int)((curr.getX())* scaleX), 
                        (int)((curr.getY())* scaleY));
                last = curr;
            }
            Point point = model.getStart();
            g.setColor(Color.GREEN);
            g.fillOval((int)((point.getX() - 5) * scaleX), (int)((point.getY() - 5) * scaleY), 10, 10);
            point = model.getEnd();
            g.setColor(Color.RED);
            g.fillOval((int)((point.getX() - 5) * scaleX), (int)((point.getY() - 5) * scaleY), 10, 10);
        }
    }
}
