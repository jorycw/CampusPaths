package hw8.test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import hw5.Edge;
import hw8.Building;
import hw8.CampusPaths;
import hw8.Coord;


/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Building class.
 */

public class CampusPathsTest {
    @Test
    public void testCreate() {
        CampusPaths p = new CampusPaths("src/hw8/data/campus_buildings.dat", 
                "src/hw8/data/campus_paths.dat");
        assertTrue(p != null);
    }
    
    @Test
    public void testFindPath() {
        CampusPaths p = new CampusPaths("src/hw8/data/campus_buildings.dat", 
                "src/hw8/data/campus_paths.dat");
        List<Edge<Double, Coord>> m = p.findPath("KNE", "MGH");
        assertTrue(m != null);
    }
    
    
    @Test
    public void testGetBuildings() {
        CampusPaths p = new CampusPaths("src/hw8/data/campus_buildings.dat", 
                "src/hw8/data/campus_paths.dat");
        Map<String, Building> m = p.getBuildings();
        assertTrue(m != null);
    }
}