/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_5.inventory;

import world_5.inventory.interfaces.*;
import world_5.inventory.item.*;
import world_5.inventory.competence.*;
import world_5.environnement.Level;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * The class that is in charge of the inventory
 */
public class Inventory{
    /**The maximum amount of elements stored in the bag */
    private final int maxInventory;
    /**The current index the player is looking at */
    private int index;
    /**The bag storing every storable */
    private List<Element> bag;

    /**The number of weapons in the player's bag */
    private int weapon;
    /**If the player has a lockpick competence in their bag */
    private boolean lockpick;
    /**If the player has a teleportation competence in their bag */
    private boolean teleportation;

    /**
     * Constructor method of the inventory
     * @param maxInventory the maximum amount of elements in the element
     */
    public Inventory(int maxInventory){
        this.maxInventory = maxInventory;
        this.index = 0;
        this.bag = new ArrayList<Element>();

        this.weapon = 0;
        this.lockpick = false;
        this.teleportation = false;
    }

    /**
     * @return the maximum amount of elements in the inventory
     */
    public int getMaxInventory(){
        return this.maxInventory;
    }

    /**
     * @return the current index
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * @return the bag where the elements are stored
     */
    public List<Element> getBag(){
        return this.bag;
    }

    /**
     * @return the amount of weapon stored in the inventory
     */
    public int getWeapon(){
        return this.weapon;
    }

    /**
     * @return if the player have a lockpick or not
     */
    public boolean getLockpick(){
        return this.lockpick;
    }

    /**
     * @return if the player have the teleportation ability or not
     */
    public boolean getTeleportation(){
        return this.teleportation;
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
            if (element instanceof Weapon){
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Increase the inventoryIndex by one (used the key DOWN while in inventory)
     */
    public void increaseIndex(){
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
     * Add an element to the inventory
     * @param element the element to add to the inventory
     * @return if the element was added to the inventory or not
     */
    private boolean addInventory(Element element){
        if (this.bag.size() < this.maxInventory && this.bag != null){
            this.bag.add(element);
            //Collections.sort(this.bag);        // Sorts inventory everytime we add something
            System.out.println("\u001B[94mYou have obtained " + element.getName() + "\u001B[0m");

            if (element instanceof Weapon){
                this.weapon++;
            }
            else if (element instanceof Lockpick){
                this.lockpick = true;
            }
            else if (element instanceof Teleportation){
                this.teleportation = true;
            }
            return true;
        }

        return false;
    }

    /**
     * Removes the element in the inventoryIndex slot
     * Pushes all the elements to the begining of the list
     * @return the Element we remove (in case)
     */
    public Element removeInventory(){
        if (this.index >= 0 && this.index < this.bag.size() && this.bag != null){
            Element e = this.bag.remove(this.index);
            this.index = 0;
            if (e instanceof Weapon){
                this.weapon--;
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
            if (e instanceof Weapon){
                this.weapon--;
            }

            return e;
        }
        return null;
    }

    /**
     * Reset the inventory by clearing it and reset all variables
     */
    public void resetInventory(){
        this.bag.clear();
        this.index = 0;
        this.weapon = 0;
        this.lockpick = false;
        this.teleportation = false;
    }

    /**
     * Picks up an element if it can be stored of pickup without checking inventory
     * @param element the element we want to pick up
     * @param level the level the element is in
     * @return if the element has been picked up to remove it from the grid
     */
    public boolean pickUp(Element element, Level level){
        if (element != null && element instanceof IPickable pickable){
            if (pickable.pickUp(level)){
                return stock(element);
            }
            return true;
        }
        return false;
    }

    /**
     * Stores an element in the inventory if it is stockable and if there's enough space in the inventory
     * @param element the element we want to store
     * @return if the element has been stored in the inventory
     */
    public boolean stock(Element element){
        if (element instanceof IStockable stockable && stockable.stock()){
            return this.addInventory(element);
        }
        return false;
    }

    /**
     * Uses the element
     * @param level the level to use event
     */
    public void use(Level level){
        if (this.bag.get(this.index) instanceof IUsable usable){
            if (usable.use(level)){
                this.removeInventory();
            }
        }
    }
}