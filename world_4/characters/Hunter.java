/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4.characters;

import world_4.environnement.Position;
import world_4.environnement.Cell;
import world_4.environnement.Level;
import world_4.types.CellType;

import java.util.List;

/**
 * The hunter (phases through walls enemy)
 */


public class Hunter extends Enemy{
    /**
     * The hunter enemy
     * @param name The name of the zombie
     * @param coord The starting position of the enemy
     * @param MAXHEALTH The maximum health
     */
    public Hunter(String name, Position coord,int MAXHEALTH){
        super(name, coord, 3);
    }

    /**
     * Moves the enemy with the type HUNTER
     * @param level the level where the enemy moves
     */
    public void move(Level level){
        Position newEnemy = level.shortestPath(this, level.getPlayer().getCoord());
            
        if (level.isAvailable(newEnemy,this)){
            this.move(newEnemy.getX(),newEnemy.getY());
        }
    }

    /**
     * The function where the enemy takes life of a player
     * @param player the player that suffers
     */
    public void enemyHit(Player player){
        player.removeHealth(2);

        System.out.println("\u001B[31mYou've been hit by " + this.name + " (hunter) \u001B[0m");
    }
    
    /**
     * Checks if an enemy collides with a cell or not
     * @param cell the cell it collides
     * @return if the enemy can go on (true) that space or not
     */
    public boolean enemyCollision(Cell cell){
        return !cell.getCollision()&& cell.getType() != CellType.TRAP;
    }

    /**
     * Gets the char to represent the enemy
     * @return the char C
     */
    public char getChar(){
        return 'C';
    }
}