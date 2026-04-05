/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_3;

/**
 * Enumeration of the keys zqsd and exit n (because it's far from the zqsd to avoid missclicks)
 */
public enum CellType{
    /**
     * A wall #
     */
    WALL,
    /**
     * A trap *
     */
    TRAP,
    /**
     * An empty space 
     */
    EMPTY,
    /**
     * A door D
     */
    DOOR;

    /**
     * If an int corresponding to a type is valid
     * @param type the int of the type
     * @return if the type is valid or not
     */
    public static boolean isValidType(int type){
        return type >= 0 && type < CellType.values().length;
    }

    /**
     * The default collision based on the type
     * @return the default collision
     */
    public boolean defaultCollision(){
        return (this == WALL || this == DOOR);
    }
}