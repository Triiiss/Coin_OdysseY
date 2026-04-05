/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.types;

/**
 * Enumeration of the keys zqsd and exit n (because it's far from the zqsd to avoid missclicks)
 */
public enum ItemType{
    /**A coin . */
    COIN,
    /**A weapon W */
    WEAPON,
    /**An hourglass */
    HOURGLASS;

    /**
     * Gets an int and checks if it corresponds to a type (used during creation, and file reading)
     * @param type The type of the structure/cell
     * @return If the type is valid or not
     */
    public static boolean isValidType(int type){
        return type >= 0 && type < CellType.values().length;
    }

    /**
     * Gets the default consumability of an item
     * @return the value of consumable for a specific type
     */
    public boolean defaultConsumable(){
        return (this == COIN);
    }
}