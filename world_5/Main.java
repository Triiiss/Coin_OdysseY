/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2

 */

package world_5;

import world_5.environnement.*;
import world_5.characters.*;
import world_5.types.Direction;


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