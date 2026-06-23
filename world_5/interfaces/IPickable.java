/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.interfaces;

import world_5.environnement.Level;

public interface IPickable{
    public default boolean pickUp(Level level){
        return true;
    }
}