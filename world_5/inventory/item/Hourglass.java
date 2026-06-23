/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.item;

import world_5.inventory.Element;
import world_5.inventory.interfaces.*;
import world_5.environnement.Level;

/**
 * The hourglass class that, we used, freezes enemies for 10 movements
 */
public class Hourglass extends Element implements IStockable, IPickable, IUsable{
    /**
     * Constructor method for the Hourglass
     * @param name the name (mostly "Hourglass")
     */
    public Hourglass(String name){
        super(name);
    }

    /**
     * Adds a freezing time of 10 for all enemies
     * @param level the levels the frozen enemies are going to be
     * @return if the elements goes away after use (here yes)
     */
    public boolean use(Level level){
        level.freezeEnemies(10);
        return true;
    }
}