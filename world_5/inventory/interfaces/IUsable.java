/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.interfaces;

import world_5.environnement.Level;

/**
 * The interface for usable elements
 */
public interface IUsable{
    /**
     * Use the element. Different for each one
     * @param level the level the player is in
     * @return if the element is removed from the inventory afterwards
     */
    public abstract boolean use(Level level);
}