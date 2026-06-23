/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.item;

import world_5.inventory.*;
import world_5.interfaces.*;
import world_5.environnement.Level;

public class Hourglass extends Element implements IStockable, IPickable, IUsable{
    public Hourglass(String name){
        super(name);
    }

    public boolean use(Level level){

        return false;
    }
}