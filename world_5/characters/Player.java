/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.inventory.*;

/**
 * The player class to play a video game
 */
public class Player extends Character{
    private int score;
    private static int nbPlayers = 0;
    private int inventorySpace;
    private final int maxInventory;
    private Element[] inventory;

    /**
     * Consctuctor of the Player object (score is automatically at 0)
     * @param name name of the player
     */
    public Player(String name){
        super(name,new Position(-1,-1),5);
        this.score = 0;
        this.inventorySpace = 0;

        this.maxInventory = 5;
        this.inventory = new Element[this.maxInventory];

        for (int i=0;i<this.maxInventory;i++){
            this.inventory[i] = null;
        }

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

    public int getInventorySpace(){
        return this.inventorySpace;
    }

    public int getMaxInventory(){
        return this.maxInventory;
    }

    public Element[] getInventory(){
        return this.inventory;
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

    public boolean addInventory(Element element){
        if (this.inventorySpace <= this.maxInventory && this.inventory[this.inventorySpace] == null){
            this.inventory[this.inventorySpace] = element;
            this.inventorySpace++;
            return true;
        }

        return false;
    }

    public Element removeInventory(int index){
        if (this.inventorySpace > 0 && index >= 0 && index < this.maxInventory && this.inventory[index] != null){
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
                    last = -1;
                    i = this.maxInventory-1;
                    continue;
                }
                i--;
            }

            return e;
        }
        return null;
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