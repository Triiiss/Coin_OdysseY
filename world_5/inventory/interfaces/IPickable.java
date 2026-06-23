/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.interfaces;

import world_5.environnement.Level;

public interface IPickable{
    public default boolean pickUp(Level level){
        return true;
    }
}