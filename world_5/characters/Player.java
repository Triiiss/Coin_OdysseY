/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.environnement.Cell;
import world_5.inventory.Inventory;
import world_5.types.CellType;

/**
 * The player class to play Coin Odyssey
 */
public class Player extends Character{
    /**The score of the player */
    private int score;
    /**The inventory where elements are stored */
    private Inventory inventory;
    /**The number of enemies killed by the player */
    private int kills;
    /**The number of players created in this class */
    private static int nbPlayers = 0;

    /**
     * Consctuctor of the Player object (score is automatically at 0, and position is set to (-1;-1))
     * @param name The unique name of the player
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
     * @return the number of player total created with the Player class
     */
    public static int getNbPlayer(){
        return Player.nbPlayers;
    }

    /**
     * @return The score of the player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * @return the inventory
     */
    public Inventory getInventory(){
        return this.inventory;
    }

    /**
     * @return the number of enemy killed by the player
     */
    public int getKills(){
        return this.kills;
    }

    /**
     * Adds points to the score of the player
     * @param points The number of points to add (positive)
     */
    public void addScore(int points){
        if (points > 0){
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
     * Puts the character object in the form of a string
     * @return the string in the form of [name] : [score] pts
     */
    @Override
    public String toString(){
        String s = this.score > 1 ? "s" : "";       // Checks if points are plural
        return this.name + " : " + this.score + " pt" + s;
    }

    /**
     * Attacks an enemy (because the player has a weapon)
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
     * Resets the character in case of game over
     */
    public void reset(){
        this.score = 0;
        this.healthPoint = this.maxHealth;

        this.inventory.resetInventory();
    }
}