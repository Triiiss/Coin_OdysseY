/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.competence;

import world_5.inventory.Element;
import world_5.inventory.interfaces.IStockable;
import world_5.inventory.interfaces.IUsable;
import world_5.environnement.Level;

/**
 * The Teleportation class that when used teleports you to a random space
 */
public class Teleportation extends Element implements IStockable, IUsable{
    /**
     * Constructor method for the Teleportation
     * @param name the name (mostly "Teleportation")
     */
    public Teleportation(String name){
        super(name);
    }

    /**
     * Teleports to a random space
     * @param level the level the player teleports in
     * @return if the element goes away after use (here no)
     */
    public boolean use(Level level){
        level.teleportationPlayer();
        return false;
    }
}