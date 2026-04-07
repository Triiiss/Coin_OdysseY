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
public abstract class Element implements Comparable<Element>{
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
     * @param level level with the player using the element (to erase the item from inventory)
     */
    public abstract void use(Level level);

    /**
     * Two characters are considered equals if their name match up (case sensitivity ignored)
     * @return true if it's equal or false if not HERE
     */
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        if (object == null || this.getClass() != object.getClass()){
            return false;
        }

        Element element = (Element) object;
        return this.name.equalsIgnoreCase(element.getName());
    }

    /**
     * Redefine the hashCode
     * @return The hash of an object based on equals
     */
    @Override
    public int hashCode(){
        int result = 11;     // My favorite prime number

        result = result*19 + this.name.toLowerCase().hashCode();
        result = result*31 + this.getClass().hashCode();

        return result;
    }

    /**
     * Compare two elements. Item before Competence, then alphabetical order
     * @param e The other element we want to compare it to
     * @return negative if this goes first, positive if e goes first and 0 if they are equal
     */
    @Override
    public int compareTo(Element e){
        if (this instanceof Item && e instanceof Competence){
            return -1;
        }
        else if(this instanceof Competence && e instanceof Item){
            return 1;
        }
        else{
            return this.name.compareToIgnoreCase(e.getName());
        }
    }
}