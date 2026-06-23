/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.interfaces;

/**
 * The interface for stockable elements
 */
public interface IStockable{
    /**
     * @return if the pickable object can be stored after beeing picked up
     */
    public default boolean stock(){
        return true;
    }
}