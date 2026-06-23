/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.competence;

import world_5.inventory.*;
import world_5.interfaces.*;
import world_5.environnement.Level;


public class Teleportation extends Element implements IStockable, IUsable{
    public Teleportation(String name){
        super(name);
    }

    public boolean use(Level level){
        level.teleportationPlayer();
        return false;
    }
}