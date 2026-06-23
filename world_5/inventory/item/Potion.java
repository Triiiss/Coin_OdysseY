/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory.item;

import world_5.inventory.Element;
import world_5.inventory.interfaces.*;
import world_5.environnement.Level;
import world_5.characters.Player;
import world_5.characters.Character;

/**
 * The potion class that, we used, heals 2 or x amount of health points
 */ 
public class Potion extends Element implements IStockable, IPickable, IUsable{
    private int heal;
    /**
     * Constructor method for the Potion
     * @param name the name (mostly "potion")
     */
    public Potion(String name){
        super(name);
        this.heal = 2;
    }

    /**
     * Constructor method for the Potion with custom heal
     * @param name the name (mostly "potion")
     * @param heal the custom amount of heal
     */
    public Potion(String name, int heal){
        this(name);
        if (heal > 0){
            this.heal = heal;
        }
    }

    /**
     * Adds a freezing time of 10 for all enemies
     * @param level the levels the frozen enemies are going to be
     * @return if the elements goes away after use (here yes)
     */
    public boolean use(Level level){
        level.getPlayer().addHealth(this.heal);
        return true;
    }
}