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

    /**
     * Constructor method
     * Consumable is given by default (no choosing)
     * @param name the name of the item
     * @param type the type of the item
     */
    public Item(String name, ItemType type){
        super(name);
        this.consumable = type.defaultConsumable();
        this.type = type;
    }

    /**
     * Get the consumability of the item
     * Used while picking up the item
     * @return if the item is consumable or not
     */
    public boolean getConsumable(){
        return this.consumable;
    }

    /**
     * Get the type of the item
     * @return the type of the item as ItemType 
     */
    public ItemType getType(){
        return this.type;
    }
    
    /**
     * Tries to pick up an item from a cell
     * @param level the level the player picking it up is in
     * @return if the item was successfully picked up or not
     */
    public boolean pickUp(Level level){
        if (this.consumable){
            switch (this.type){
                case ItemType.COIN:
                    level.getPlayer().addScore(10);
                    level.decreaseNbCoin();
            }
            return true;
        }
        else if (level.getPlayer().getInventorySpace() < level.getPlayer().getMaxInventory()){      // If there is space
            level.getPlayer().addInventory(this);
            return true;
        }
        return false;
    }

    /**
     * Use the item and deletes it from the inventory
     * @param player the player using the item
     */
    public void use(Level level){
        switch(this.type){
            case ItemType.WEAPON:       // Item deleted after hitting an enemy
                System.out.println("\u001B[94mYou will be able to hit enemies\u001B[0m");
                level.getPlayer().resetInventoryIndex();
                break;
            case ItemType.HOURGLASS:
                level.freezeEnemies(10);
                level.getPlayer().removeInventory();
                System.out.println("\u001B[36mEnemies are frozen for 10 movements\u001B[0m");
                break;
        }
    }
}