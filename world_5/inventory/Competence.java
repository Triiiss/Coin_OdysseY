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
     * Use the competence (does not disapear from the inventory)
     * @param level level with the player using the element (to erase the item from inventory)
     */
    public void use(Level level){
        switch(this.type){
            case CompetenceType.LOCKPICK:
                System.out.println("\u001B[35mYou can walk through closed doors\u001B[0m");
                level.getPlayer().getInventory().resetIndex();
                break;
            case CompetenceType.TELEPORTATION:
                if (level.teleportationPlayer()){
                    System.out.println("\u001B[35mYou got teleported in a random space\u001B[0m");
                }
                else{
                    System.out.println("\u001B[31mYou tried to teleport but failed\u001B[0m");
                }
                level.getPlayer().getInventory().resetIndex();
                break;
        }
    }
}