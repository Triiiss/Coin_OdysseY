/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_5.characters;

import world_5.environnement.*;
import world_5.types.CellType;

import java.util.List;

/**
 * The hunter (phases through walls enemy)
 */


public class Hunter extends Enemy{
    /**
     * The hunter enemy
     * @param name The name of the zombie
     * @param coord The starting position of the enemy
     * @param maxhealth The maximum health
     */
    public Hunter(String name, Position coord,int maxhealth){
        super(name, coord, 3);
    }

    /**
     * Moves the enemy with the type HUNTER
     * @param level the level where the enemy moves
     */
    public void move(Level level){
        Position newEnemy = Rule.shortestPath(level, this.coord, level.getPlayer().getCoord(), this);
            
        if (level.isAvailable(newEnemy,this)){
            this.moveTo(newEnemy.getX(),newEnemy.getY());
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
    public boolean canMove(Cell cell){
        return !cell.getCollision()&& cell.getType() != CellType.TRAP;
    }
}