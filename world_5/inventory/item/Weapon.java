/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.item;

import world_5.inventory.Element;
import world_5.inventory.interfaces.IStockable;
import world_5.inventory.interfaces.IPickable;

/**
 * The weapon class that allows to hit enemies
 */
public class Weapon extends Element implements IStockable, IPickable{
    /**
     * Constructor method for the Weapon
     * @param name the name (mostly "Weapon")
     */
    public Weapon(String name){
        super(name);
    }
}