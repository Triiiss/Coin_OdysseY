/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4;

/**
 * Enumeration of the keys zqsd and exit n (because it's far from the zqsd to avoid missclicks)
 */
public enum CellType{
    WALL,
    TRAP,
    EMPTY,
    DOOR;

    public static boolean isValidType(int type){
        return type >= 0 && type < CellType.values().length;
    }

    public boolean defaultCollision(){
        return (this == WALL || this == DOOR);
    }
}