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

    public Competence(String name, CompetenceType type){
        super(name);
        this.type = type;
    }

    public CompetenceType getType(){
        return this.type;
    }
    
    public boolean pickUp(Level level){
        if (level.getPlayer().getInventorySpace() < level.getPlayer().getMaxInventory()){
            level.getPlayer().addInventory(this);
            return true;
        }
        return false;
    }

    public void use(Player player){
        
    }
}