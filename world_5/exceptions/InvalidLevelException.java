/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.exceptions;

/**
 * The exception if the level is corrupted or invalid (width and height invalid)
 */
public class InvalidLevelException extends Exception{
    /**
     * Prints out the error message
     * @param msg The error message
     */
    public InvalidLevelException(String msg){
        super(msg);
    }
}