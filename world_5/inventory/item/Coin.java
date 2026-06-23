/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.item;

import world_5.inventory.Element;
import world_5.inventory.interfaces.IPickable;
import world_5.environnement.Level;

/**
 * The coin can be pickup to add score and complete level objective
 */
public class Coin extends Element implements IPickable{
    /**
     * Constructor method for the coin
     * @param name the name of the object (mostly Coin)
     */
    public Coin(String name){
        super(name);
    }

    /**
     * Add score to player
     * @param level the level so we can decrease NbCoin and add score to player
     * @return if the usable can be stored (not here)
     */
    @Override
    public boolean pickUp(Level level){
        level.decreaseNbCoin();
        level.getPlayer().addScore(10);
        return false;
    }
}