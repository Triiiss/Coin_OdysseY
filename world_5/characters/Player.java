/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.environnement.Cell;
import world_5.inventory.*;
import world_5.types.*;



/**
 * The player class to play a video game
 */
public class Player extends Character{
    private int score;
    private static int nbPlayers = 0;

    private Inventory inventory;
    private int kills;

    /**
     * Consctuctor of the Player object (score is automatically at 0)
     * @param name name of the player
     */
    public Player(String name){
        super(name,new Position(-1,-1),5);
        this.score = 0;
        this.kills = 0;

        this.inventory = new Inventory(5);

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
     * Get the list of Elements used as inventory
     * @return the inventory
     */
    public Inventory getInventory(){
        return this.inventory;
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
     * Checks if the player can move to a cell
     * @param cell The cell the player wants to go to
     * @return if the player is able to walk to the cell
     */
    public boolean canMove(Cell cell){
        if (this.inventory.getLockpick()){
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

        this.inventory.resetInventory();
    }
}