/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4.types;

/**
 * Enumeration of the keys zqsd and exit n (because it's far from the zqsd to avoid missclicks)
 */
public enum Direction{
    /**
     * Key to go left made with the letter Q
     */
    LEFT,
    /**
     * Key to go up made with the letter Z
     */
    UP,
    /**
     * Key to go rigth made with the letter D
     */
    RIGHT,
    /**
     * Key to go down made with the letter S
     */
    DOWN,
    /**
     * Key to go exit made with the letter N
     */
    EXIT,
    /**
     * Key to go restart after a game over made with the letter N
     */
    RESTART,
    
    /**An unknown key */
    UNKNOWN;
}