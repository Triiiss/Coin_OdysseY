/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.environnement.Cell;
import world_5.inventory.*;
import world_5.types.*;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * The player class to play a video game
 */
public class Player extends Character{
    private int score;
    private static int nbPlayers = 0;

    private final int maxInventory;
    private List<Element> inventory;
    private int inventoryIndex;

    private boolean weapon;
    private int kills;

    /**
     * Consctuctor of the Player object (score is automatically at 0)
     * @param name name of the player
     */
    public Player(String name){
        super(name,new Position(-1,-1),5);
        this.score = 0;
        this.inventoryIndex = 0;
        this.maxInventory = 5;
        this.inventory = new ArrayList<Element>();

        this.weapon = false;
        this.kills = 0;

        System.out.println("[Creation] Number of total players : " + Player.nbPlayers);
        Player.nbPlayers++;
    }

    /**
     * Constructor with no need of a name argument. The default name will be PlayerN with N the number of total players
     */
    public Player(){
        this("Player" + (Player.nbPlayers + 1));
    }

    /**
     * Count the players that were created using the constructor method
     * @return the number of player total
     */
    public static int getNbPlayer() {
        return Player.nbPlayers;
    }

    /**
     * Get the score of the player
     * @return The score
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Get the space used in the inventory
     * @return the index of the last element in inventory
     */
    /*public int getInventorySpace(){
        return this.inventorySpace;
    }*/

    /**
     * Get the maximum space in the inventory
     * @return max inventory
     */
    public int getMaxInventory(){
        return this.maxInventory;
    }

    /**
     * Get the list of Elements used as inventory
     * @return the inventory
     */
    public List<Element> getInventory(){
        return this.inventory;
    }

    /**
     * Get what index the cursor in the inventory is (to know which element to use)
     * @return the inventory index
     */
    public int getInventoryIndex(){
        return this.inventoryIndex;
    }

    /**
     * Get if a weapon is drawn are not
     * @return the weapon
     */
    public boolean getWeapon(){
        return this.weapon;
    }

    /**
     * Get the number of enemy killed by the player
     * @return the number of kills
     */
    public int getKills(){
        return this.kills;
    }

    /**
     * Displays the information of your Player object
     */
    public void display(){
        System.out.println("Player : " + this.name + " | score : " + this.score);
    }

    /**
     * Puts the character object in the form of a string
     * @return the string in the form of [name] : [score] pts
     */
    public String toString(){
        String s = this.score > 1 ? "s" : "";
        return this.name + " : " + this.score + " pt" + s;
    }

    /**
     * Adds points to the score of the player
     * @param points The number of points to add (positive)
     */
    public void addScore(int points){
        if (points >= 0){
            this.score += points;
        }
    }

    /**
     * Remove points to the score of the player
     * If the update would result in a score below zero, the score is reset to zero
     * @param points The number of points to remove (positive)
     */
    public void removeScore(int points){
        if (points >= 0){
            this.score -= this.score - points <= 0 ? this.score : points;
        }
    }

    /**
     * Adds a kill to the player's counter and adds score
     */
    public void addKill(){
        this.addScore(20);
        this.kills++;
    }

    /**
     * Add an element to the inventory (item from cell or competence from gains)
     * @param element the element to add to the inventory
     * @return if the element was added to the inventory (true) or not (false)
     */
    public boolean addInventory(Element element){
        if (this.inventory.size() < this.maxInventory && this.inventory != null){
            this.inventory.add(element);
            Collections.sort(this.inventory);        // Sorts inventory everytime we add something
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
        if (this.inventoryIndex >= 0 && this.inventoryIndex < this.inventory.size() && this.inventory != null){
            Element e = this.inventory.remove(this.inventoryIndex);
            this.resetInventoryIndex();

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
        if (this.inventoryIndex >= 0 && index < this.inventory.size() && this.inventory != null){
            Element e = this.inventory.remove(index);
            this.resetInventoryIndex();

            return e;
        }
        return null;
    }

    /**
     * Augment the inventoryIndex by one (used the key DOWN while in inventory)
     */
    public void addInventoryIndex(){
        if (this.inventoryIndex < this.inventory.size()){
            this.inventoryIndex++;
        }
    }

    /**
     * Decrease the inventoryIndex by one (used the key UP while in inventory)
     */
    public void decreaseInventoryIndex(){
        if (this.inventoryIndex>0){
            this.inventoryIndex--;
        }
    }

    /**
     * Checks if the player has a weapon in case of an enemy collision
     * @return -1 if there is no weapon or the index of the weapon in the inventory
     */
    public int hasWeapon(){
        Iterator<Element> iterator = this.inventory.iterator();
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

    /**
     * Checks if the player has a lockpick competence in case of a closed door
     * @return true if lockpick false if not
     */
    public boolean hasLockpick(){
        Iterator<Element> iterator = this.inventory.iterator();

        while(iterator.hasNext()){
            Element element = iterator.next();
            if (element instanceof Competence){
                Competence competence = (Competence) element;
                if (competence.getType() == CompetenceType.LOCKPICK) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the player has a teleportation competence to not add it multiple times
     * @return true if lockpick false if not
     */
    public boolean hasTeleportation(){
        Iterator<Element> iterator = this.inventory.iterator();

        while(iterator.hasNext()){
            Element element = iterator.next();
            if (element instanceof Competence){
                Competence competence = (Competence) element;
                if (competence.getType() == CompetenceType.TELEPORTATION) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Attacks an enemy
     * @param enemy the enemy that gets hit
     */
    public void attackEnemy(Enemy enemy){
        enemy.removeHealth(1);
        if (enemy.getHealthPoint() > 0){
            System.out.println("\u001B[31mYou've hit " + enemy.getName() + " " + enemy.getHealthPoint() + " HP left \u001B[0m");
        }
        else{
            System.out.println("\u001B[31mEnemy " + enemy.getName() + " defeated \u001B[0m");
        }
    }

    /**
     * Resets the inventoryIndex (used while quitting the inventory)
     */
    public void resetInventoryIndex(){
        this.inventoryIndex = 0;
    }

    /**
     * Checks if the player can move to a cell
     * @param cell The cell the player wants to go to
     * @return if the player is able to walk to the cell
     */
    public boolean canMove(Cell cell){
        if (this.hasLockpick()){
            return !cell.getCollision() || cell.getType() == CellType.DOOR;
        }
        return !cell.getCollision();
    }
    
    /**
     * Resets the character in case of game over (no need to create it again, just reset the score and the health)
     */
    public void reset(){
        this.score = 0;
        this.healthPoint = this.maxHealth;

        this.inventory.clear();
    }
}