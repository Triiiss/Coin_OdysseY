/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.types;

/**
 * All the types of level
 */
public enum ObjectiveType{
    /**To finish the level, you have to collect all the coins */
    COINS,
    /**To finish the level, you have to defeat all enemies */
    ENEMIES;

    /**
     * Gets an int and checks if it corresponds to a type (used during creation, and file reading)
     * @param type The type of the level
     * @return If the type is valid or not
     */
    public static boolean isValidType(int type){
        return type >= 0 && type < CellType.values().length;
    }
}