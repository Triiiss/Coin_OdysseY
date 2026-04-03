/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.environnement.Cell;
import world_5.environnement.Level;
import world_5.types.CellType;

/**
 * The ghost (phases through walls enemy)
 */


public class Ghost extends Enemy{
    /**
     * The ghost enemy
     * @param name The name of the zombie
     * @param coord The starting position of the enemy
     * @param maxhealth The maximum health
     */
    public Ghost(String name, Position coord,int maxhealth){
        super(name, coord, maxhealth);
    }

    /**
     * Moves the enemy with the type GHOST
     * @param level the level where the enemy moves
     */
    public void move(Level level){
        Position newEnemy = this.getCoord().clone();
        
        if (this.coord.getX() < level.getPlayer().getCoord().getX()){
            newEnemy.addX(1);
        }
        else if (this.coord.getX() > level.getPlayer().getCoord().getX()){
            newEnemy.addX(-1);
        }
        else{   
            if (this.coord.getY() < level.getPlayer().getCoord().getY()){
                newEnemy.addY(1);
            }
            else if (this.coord.getY() > level.getPlayer().getCoord().getY()){
                newEnemy.addY(-1);
            } 
        }

        if (level.isAvailable(newEnemy,this)){
            this.moveTo(newEnemy.getX(),newEnemy.getY());
        }
    }

    /**
     * The function where the enemy takes life of a player
     * @param player the player that suffers
     */
    public void enemyHit(Player player){
        player.removeHealth(1);

        System.out.println("\u001B[31mYou've been hit by " + this.name + "\u001B[0m");
    }
    
    /**
     * Checks if an enemy collides with a cell or not
     * @param cell the cell it collides
     * @return if the enemy can go on that space or not
     */
    public boolean canMove(Cell cell){
        return true;
    }
}