package hw8;

/** <b>Building</b> represents an <b>immutable</b> object containing a field
 *  for an abbreviation of the building, a field for the buildings full name,
 *  and a reference to the buildings coordinates.
*/

public class Building {

    private final String abrev;
    private final String name;
    private final Coord coord;
    /**
     * @param edgeLabel Label of directed edge
     * @param nodeChild Node that directed edge points to
     * @effects created a edge containing edgeLabel and a K of the
     * child node pointing to
     */
    public Building(String shortName, String longName, double xCoord, double yCoord) {
        abrev = shortName;
        name = longName;
        coord = new Coord(xCoord, yCoord);
    }
    
    /**
     * @return String abbreviation of this building
     */
    public String getAbrev() {
        return abrev;
    }

    /**
     * @return String name of this building
     */
    public String getName() {
        return name;
    }

    /**
     * @return Coord coordinates of this building
     */
    public Coord getCoord() {
        return coord;
    }
    /**
     * Standard equality operation.
     *
     * @param obj The object to be compared for equality.
     * @return true if and only if 'obj' and 'this'  are both Buidling objects
     *  and contain the same abrev, name and coord fields
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Building) {
            Building building = (Building) obj;
            return (abrev.equals(building.abrev) && 
                    name.equals(building.name) && 
                    coord.equals(building.coord));        
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
        return abrev.hashCode() + name.hashCode() + coord.hashCode();
    }
}

