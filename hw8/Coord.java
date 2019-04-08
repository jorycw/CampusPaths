package hw8;

/** <b>Coord</b> represents an <b>immutable</b> object containing a two
 * double fields representing the x and y coordinates on a 2-d grid
 * system
*/

public class Coord {

    private final double x;
    private final double y;
    /**
     * @param xCoord x coordinate of this coordinate
     * @param yCoord y coordinate of this coordinate
     * @effects creates a building containing x and y positions
     */
    public Coord(double xCoord, double yCoord) {
        x = xCoord;
        y = yCoord;
    }

    /**
     * @return x coordinate as a double
     */
    public double getX() {
        return x;
    }
    
    /**
     * @return y coordinate as a double
     */
    public double getY() {
        return y;
    }
    
    /**
     * Standard equality operation.
     *
     * @param obj The object to be compared for equality.
     * @return true if and only if 'obj' and 'this'  are both Coord objects
     *  and contain the same x and y fields
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coord) {
            Coord building = (Coord) obj;
            return (x == building.x && 
                    y == building.y);        
        } else {
            return false;
        }
    }
    
    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also.
     */
    @Override
    public int hashCode() {
        return 7 * (int) x + (int) y;
    }
}


