/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2

 */

package world_4;

import world_4.environnement.*;
import world_4.characters.*;
import world_4.types.Direction;


/**
 * Main class
 */
public class Main{
    /**
     * Empty constructor
     */
    private Main(){
        // Empty constructor
    }

    /**
     * Main method
     * @param args the string of arguments
     */
    public static void main(String[] args){
        new Game(args);
    }
}