/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.inventory;

import world_5.types.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Inventory{
    private final int maxInventory;
    private int index;
    private List<Element> bag;

    private int weapon;
    private boolean lockpick;
    private boolean teleportation;

    public Inventory(int maxInventory){
        this.maxInventory = maxInventory;
        this.index = 0;
        this.bag = new ArrayList<Element>();

        this.weapon = 0;
        this.lockpick = false;
        this.teleportation = false;
    }

    public int getMaxInventory(){
        return this.maxInventory;
    }

    public int getIndex(){
        return this.index;
    }

    public List<Element> getBag(){
        return this.bag;
    }

    public int getWeapon(){
        return this.weapon;
    }

    public boolean getLockpick(){
        return this.lockpick;
    }

    public boolean getTeleportation(){
        return this.teleportation;
    }

    /**
     * Augment the inventoryIndex by one (used the key DOWN while in inventory)
     */
    public void addIndex(){
        if (this.index < this.bag.size()){
            this.index++;
        }
    }

    /**
     * Decrease the inventoryIndex by one (used the key UP while in inventory)
     */
    public void decreaseIndex(){
        if (this.index>0){
            this.index--;
        }
    }

    /**
     * Resets the inventoryIndex (used while quitting the inventory)
     */
    public void resetIndex(){
        this.index = 0;
    }


    /**
     * Add an element to the inventory (item from cell or competence from gains)
     * @param element the element to add to the inventory
     * @return if the element was added to the inventory (true) or not (false)
     */
    public boolean addInventory(Element element){
        if (this.bag.size() < this.maxInventory && this.bag != null){
            this.bag.add(element);
            Collections.sort(this.bag);        // Sorts inventory everytime we add something
            System.out.println("\u001B[94mYou have obtained " + element.getName() + "\u001B[0m");

            if (element instanceof Item){
                Item item = (Item) element;
                if (item.getType() == ItemType.WEAPON){
                    this.weapon++;
                }
            }
            else if (element instanceof Competence){
                Competence competence = (Competence) element;
                if (competence.getType() == CompetenceType.LOCKPICK){
                    this.lockpick = true;
                }
                else if (competence.getType() == CompetenceType.TELEPORTATION){
                    this.teleportation = true;
                }
            }
            return true;
        }

        return false;
    }

    /**
     * Removes the element in the this.inventoryIndex slot
     * Pushes all the elements to the begining of the list
     * @return the Element we remove (in case)
     */
    public Element removeInventory(){
        if (this.index >= 0 && this.index < this.bag.size() && this.bag != null){
            Element e = this.bag.remove(this.index);
            this.index = 0;

            if (e instanceof Item){
                Item item = (Item) e;
                if (item.getType() == ItemType.WEAPON){
                    this.weapon--;
                }
            }

            return e;
        }
        return null;
    }

    /**
     * Removes the element in the index slot
     * Pushes all the elements to the begining of the list
     * @param index the index we want to remove the element
     * @return the Element we remove (in case)
     */
    public Element removeInventory(int index){
        if (index >= 0 && index < this.bag.size() && this.bag != null){
            Element e = this.bag.remove(index);
            this.index = 0;

            if (e instanceof Item){
                Item item = (Item) e;
                if (item.getType() == ItemType.WEAPON){
                    this.weapon--;
                }
            }

            return e;
        }
        return null;
    }

    /**
     * Checks if the player has a weapon in case of an enemy collision
     * @return -1 if there is no weapon or the index of the weapon in the inventory
     */
    public int getWeaponIndex(){
        Iterator<Element> iterator = this.bag.iterator();
        int i = 0;

        while(iterator.hasNext()){
            Element element = iterator.next();
            if (element instanceof Item){
                Item item = (Item) element;
                if (item.getType() == ItemType.WEAPON) {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

    public void resetInventory(){
        this.bag.clear();
        this.index = 0;
        this.weapon = 0;
        this.lockpick = false;
        this.teleportation = false;
    }

}