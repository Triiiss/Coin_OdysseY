/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.competence;

import world_5.inventory.Element;
import world_5.inventory.interfaces.IStockable;

/**
 * The Lockpick class that allows to walk through locked doors
 */
public class Lockpick extends Element implements IStockable{
    /**
     * Constructor method for the Lockpick
     * @param name the name (mostly "Lockpicking")
     */
    public Lockpick(String name){
        super(name);
    }
}