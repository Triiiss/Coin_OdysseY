/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.item;

import world_5.inventory.*;
import world_5.interfaces.*;
import world_5.environnement.Level;

public class Coin extends Element implements IPickable{
    public Coin(String name){
        super(name);
    }

    @Override
    public boolean pickUp(Level level){
        level.decreaseNbCoin();
        level.getPlayer().addScore(10);
        return false;
    }
}