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

    public Element(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public abstract boolean pickUp(Level level);

    public abstract void use();
}
