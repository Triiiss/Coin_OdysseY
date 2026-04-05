/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory;

import world_5.types.*;
import world_5.environnement.Cell;
import world_5.environnement.Level;
import world_5.characters.Player;

/**
 * Item class
 */
public class Item extends Element{
    private boolean consumable;
    private ItemType type;

    public Item(String name, ItemType type){
        super(name);
        this.consumable = type.defaultConsumable();
        this.type = type;
    }

    public boolean getConsumable(){
        return this.consumable;
    }

    public ItemType getType(){
        return this.type;
    }
    
    public boolean pickUp(Level level){
        if (this.consumable){
            switch (this.type){
                case ItemType.COIN:
                    level.getPlayer().addScore(10);
                    level.removeOneNbCoin();
                    return true;
            }
        }
        else if (level.getPlayer().getInventorySpace() < level.getPlayer().getMaxInventory()){      // If there is space
            level.getPlayer().addInventory(this);
            return true;
        }
        return false;
    }

    public void use(Player player){
        switch(this.type){
            case ItemType.WEAPON:
                // stuff
                player.removeInventory();
                break;
            case ItemType.HOURGLASS:
                player.removeInventory();
                break;
        }
    }
}