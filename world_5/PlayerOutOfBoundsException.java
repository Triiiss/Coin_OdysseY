/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.exceptions;

/**
 * The exception if the player wants to be generated out of bounds or in a wall
 */
public class PlayerOutOfBoundsException extends RuntimeException{
    /**
     * Prints out the error message
     * @param msg The error message
     */
    public PlayerOutOfBoundsException(String msg){
        super(msg);
    }
}