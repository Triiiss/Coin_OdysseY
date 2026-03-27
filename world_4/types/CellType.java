/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4;

/**
 * Enumeration of the keys zqsd and exit n (because it's far from the zqsd to avoid missclicks)
 */
public enum CellType{
    /**A wall # */
    WALL,
    /**A trap * */
    TRAP,
    /**Empty space  */
    EMPTY,
    /**A door D */
    DOOR;

    /**
     * Gets an int and checks if it corresponds to a type (used during creation, and file reading)
     * @param type The type of the structure/cell
     * @return If the type is valid or not
     */
    public static boolean isValidType(int type){
        return type >= 0 && type < CellType.values().length;
    }

    /**
     * Some cells have collision, and some are manually changed. With no manual change, we take the default one
     * @return if the cell has collision
     */
    public boolean defaultCollision(){
        return (this == WALL || this == DOOR);
    }
}
