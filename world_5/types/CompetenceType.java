/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.types;

/**
 * Enumeration of the keys zqsd and exit n (because it's far from the zqsd to avoid missclicks)
 */
public enum CompetenceType{
    /**After 100 points */
    LOCKPICK,
    /**After killing 3 enemies */
    TELEPORTATION;

    /**
     * Gets an int and checks if it corresponds to a type (used during creation, and file reading)
     * @param type The type of the structure/cell
     * @return If the type is valid or not
     */
    public static boolean isValidType(int type){
        return type >= 0 && type < CellType.values().length;
    }
}