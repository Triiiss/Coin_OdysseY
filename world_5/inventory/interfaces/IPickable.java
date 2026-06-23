/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.interfaces;

import world_5.environnement.Level;

/**
 * The interface of pickable elements
 */
public interface IPickable{
    /**
     * @param level the level we pick the object from
     * @return if the pickable object can be stored after beeing picked up
     */
    public default boolean pickUp(Level level){
        return true;
    }
}