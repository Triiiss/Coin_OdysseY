/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.environnement.Cell;
import world_5.environnement.Level;

/**
 * The ghost (phases through walls enemy type)
 */
public class Ghost extends Enemy{
    /**
     * The ghost enemy
     * @param name the unique name of the ghost
     * @param coord its current coordinates
     * @param maxhealth The max health of the ghost
     */
    public Ghost(String name, Position coord,int maxhealth){
        super(name, coord, maxhealth);
    }
    
    /**
     * Checks if an enemy collides with a cell or not
     * The ghost can move no matter what
     * @param cell the cell it collides
     * @return if the enemy can go on that space or not (yes it can)
     */
    public boolean canMove(Cell cell){
        return true;
    }

    /**
     * Moves the enemy with the type ghost
     * Moves towards the player
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

        if (level.isAccessible(newEnemy,this)){
            this.moveTo(newEnemy.getX(),newEnemy.getY());
        }
    }

    /**
     * The function where the enemy takes life of a player
     * The ghost only takes one life from the player
     * @param player the player that suffers
     */
    public void attackPlayer(Player player){
        player.removeHealth(1);

        System.out.println("\u001B[31mYou've been hit by " + this.name + " (ghost) \u001B[0m");
    }
}