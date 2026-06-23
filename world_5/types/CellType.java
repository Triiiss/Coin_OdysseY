/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.types;

/**
 * All the different types of Cells
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
     * Some cells have collision, and some are manually changed. With no manual change, we take the default one
     * @return if the cell has collision
     */
    public boolean defaultCollision(){
        return (this == WALL || this == DOOR);
    }
}