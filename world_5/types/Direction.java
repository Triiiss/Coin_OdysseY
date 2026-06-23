/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.types;

/**
 * Enumeration of the keys zqsd, exit n (it's far from the zqsd to avoid missclicks), 
 */
public enum Direction{
    /**Key to go left (letter Q) */
    LEFT,
    /**Key to go up (letter Z) */
    UP,
    /**Key to go rigth (letter D) */
    RIGHT,
    /**Key to go down (letter S) */
    DOWN,
    /**Key to open or close the inventory (letter I) */
    INVENTORY,
    /**Key to use an element in the inventory (letter U) */
    USE,
    /**Key to exit (letter N) */
    EXIT,
    /**Key to go restart after a game over (letter R) */
    RESTART,
    /**An unknown key  */
    UNKNOWN;
}