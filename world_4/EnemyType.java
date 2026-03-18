/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4;

/**
 * Enumeration of the keys zqsd and exit n (because it's far from the zqsd to avoid missclicks)
 */
public enum EnemyType{
    /**An enemy that moves randomly */
    RANDOM,
    /**A ghost that goes through walls */
    GHOST,
    /**A hunter the follows the player */
    HUNTER,
    /**Unknow type */
    UNKNOWN;

    /**
     * Checks if an int of a type is valid or not
     * @param type the type as an int
     * @return if the type is valid or not
     */
    public static boolean isValidType(int type){
        return type >= 0 && type < CellType.values().length;
    }
}