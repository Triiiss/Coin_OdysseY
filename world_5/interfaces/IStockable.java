/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.interfaces;

public interface IStockable{
    public default boolean stock(){
        return true;
    }
}