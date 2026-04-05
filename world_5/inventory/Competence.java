/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory;

import world_5.types.CompetenceType;
import world_5.environnement.Level;
import world_5.environnement.Cell;
import world_5.characters.Player;

/**
 * Competence class
 */
public class Competence extends Element{
    private CompetenceType type;

    /**
     * Constructor
     * @param name the name of the competence
     * @param type the type of the competence (type safe)
     */
    public Competence(String name, CompetenceType type){
        super(name);
        this.type = type;
    }

    /**
     * Get the type of the competence
     * @return the type of the competence as Competencetype
     */
    public CompetenceType getType(){
        return this.type;
    }
    
    /**
     * Pick up the competence
     * @param level the level the player picking up the competence is in
     * @return if the competence was picked up or not
     */
    public boolean pickUp(Level level){
        if (level.getPlayer().getInventorySpace() < level.getPlayer().getMaxInventory()){
            level.getPlayer().addInventory(this);
            return true;
        }
        return false;
    }

    /**
     * Use the competence (does not disapear from the inventory)
     * @param player the player using the competence
     */
    public void use(Player player){
        switch(this.type){
            case CompetenceType.LOCKPICK:
                break;
            case CompetenceType.TELEPORTATION:
                break;
        }
    }
}