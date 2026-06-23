/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.environnement.Cell;
import world_5.environnement.Level;
import world_5.environnement.Rule;
import world_5.types.CellType;

import java.util.List;

/**
 * The hunter (chases the player)
 */
public class Hunter extends Enemy{
    /**
     * The hunter enemy : chases the player with the most optimal path
     * @param name the unique name of the hunter
     * @param coord its current coordinates
     * @param maxhealth The max health of the hunter
     */
    public Hunter(String name, Position coord,int maxhealth){
        super(name, coord, maxhealth);
    }

    /**
     * Checks if an enemy collides with a cell or not
     * Hunter don't go on traps and cannot phase through collision paths
     * @param cell the cell it collides
     * @return if the enemy can go on (true) that space or not
     */
    public boolean canMove(Cell cell){
        return !cell.getCollision()&& cell.getType() != CellType.TRAP;
    }

    /**
     * Moves the enemy with the type hunter
     * @param level the level where the enemy moves
     */
    public void move(Level level){
        Position newEnemy = Rule.shortestPath(level, this.coord, level.getPlayer().getCoord(), this);
            
        if (level.isAccessible(newEnemy,this)){
            this.moveTo(newEnemy.getX(),newEnemy.getY());
        }
    }

    /**
     * The function where the enemy takes life of a player
     * The hunter takes 2 life from the player
     * @param player the player that suffers
     */
    public void attackPlayer(Player player){
        player.removeHealth(2);

        System.out.println("\u001B[31mYou've been hit by " + this.name + " (hunter) \u001B[0m");
    }
}