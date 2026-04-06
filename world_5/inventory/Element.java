/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory;
import world_5.environnement.Level;
import world_5.environnement.Cell;
import world_5.characters.Player;

/**
 * Element class
 */
public abstract class Element{
    private String name;

    /**
     * Constructor of an element
     * @param name the name of the element
     */
    public Element(String name){
        this.name = name;
    }

    /**
     * Get the name of the element
     * @return the name given to the element
     */
    public String getName(){
        return this.name;
    }

    /**
     * Use the element
     * @param player the player using the element (to erase the item from inventory)
     */
    public abstract void use(Level level);
}