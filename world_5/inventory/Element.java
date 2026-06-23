/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_5.inventory;

import world_5.characters.Player;
import world_5.inventory.interfaces.IPickable;

/**
 * Element class for all objects
 */
public abstract class Element implements Comparable<Element>{
    /**The name of the Element. Used to distinguish multiple Elements */
    private String name;

    /**
     * Constructor of an element
     * @param name the name of the element
     */
    public Element(String name){
        this.name = name;
    }

    /**
     * @return the name of the element
     */
    public String getName(){
        return this.name;
    }

    /**
     * Two elements are considered equals if their name and their class match up (case sensitivity ignored)
     * @param object the object we want to compare
     * @return true if the elements are equal or false if not
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
     * Redefine the hashCode based on name and class
     * @return The hash of an object based on equals
     */
    @Override
    public int hashCode(){
        int result = 11;

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
        if (this instanceof IPickable && !(e instanceof IPickable)){
            return -1;
        }
        else if(!(this instanceof IPickable) && e instanceof IPickable){
            return 1;
        }
        else{
            return this.name.compareToIgnoreCase(e.getName());
        }
    }
}