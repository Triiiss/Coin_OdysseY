/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.inventory.*;
import world_5.types.*;

/**
 * The player class to play a video game
 */
public class Player extends Character{
    private int score;
    private static int nbPlayers = 0;

    private int inventorySpace;
    private final int maxInventory;
    private Element[] inventory;
    private int inventoryIndex;

    private boolean weapon;

    /**
     * Consctuctor of the Player object (score is automatically at 0)
     * @param name name of the player
     */
    public Player(String name){
        super(name,new Position(-1,-1),5);
        this.score = 0;
        this.inventorySpace = 0;
        this.inventoryIndex = 0;
        this.maxInventory = 5;
        this.inventory = new Element[this.maxInventory];

        for (int i=0;i<this.maxInventory;i++){
            this.inventory[i] = null;
        }
        this.weapon = false;

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
    public int getInventorySpace(){
        return this.inventorySpace;
    }

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
    public Element[] getInventory(){
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
     * Add an element to the inventory (item from cell or competence from gains)
     * @param element the element to add to the inventory
     * @return if the element was added to the inventory (true) or not (false)
     */
    public boolean addInventory(Element element){
        if (this.inventorySpace < this.maxInventory && this.inventory[this.inventorySpace] == null){
            this.inventory[this.inventorySpace] = element;
            this.inventorySpace++;
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
        if (this.inventorySpace > 0 && this.inventoryIndex >= 0 && this.inventoryIndex < this.inventorySpace && this.inventory[this.inventoryIndex] != null){
            Element e = this.inventory[this.inventoryIndex];
            this.inventory[this.inventoryIndex] = null;
            this.inventorySpace--;

            int last = -1;
            int i= this.maxInventory-1;
            while (i>=0){               // Pushes all real elements to the begining of the list
                if (this.inventory[i] != null){
                    last = i;
                }
                else if (last != -1){
                    this.inventory[i] = this.inventory[last];
                    this.inventory[last] = null;
                    last = -1;
                    i = this.maxInventory-1;
                    continue;
                }
                i--;
            }
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
        if (this.inventorySpace > 0 && index >= 0 && index < this.inventorySpace && this.inventory[index] != null){
            Element e = this.inventory[index];
            this.inventory[index] = null;
            this.inventorySpace--;

            int last = -1;
            int i= this.maxInventory-1;
            while (i>=0){               // Pushes all real elements to the begining of the list
                if (this.inventory[i] != null){
                    last = i;
                }
                else if (last != -1){
                    this.inventory[i] = this.inventory[last];
                    this.inventory[last] = null;
                    last = -1;
                    i = this.maxInventory-1;
                    continue;
                }
                i--;
            }
            this.resetInventoryIndex();

            return e;
        }
        return null;
    }

    /**
     * Augment the inventoryIndex by one (used the key DOWN while in inventory)
     */
    public void addInventoryIndex(){
        if (this.inventoryIndex < this.inventorySpace){
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
        for (int i=0;i<this.inventorySpace;i++){
            if (this.inventory[i] instanceof Item){
                Item item = (Item) this.inventory[i];
                if (item.getType() == ItemType.WEAPON) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean hasLockpick(){
        for (int i=0;i<this.inventorySpace;i++){
            if (this.inventory[i] instanceof Competence){
                Competence competence = (Competence) this.inventory[i];
                if (competence.getType() == CompetenceType.LOCKPICK) {
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
     * Resets the character in case of game over (no need to create it again, just reset the score and the health)
     */
    public void reset(){
        this.score = 0;
        this.healthPoint = this.maxHealth;

        for (int i=0;i<this.maxInventory;i++){
            this.inventory[i] = null;
        }
    }
}