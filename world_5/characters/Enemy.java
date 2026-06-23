/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.3
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.environnement.Level;
import world_5.environnement.Cell;

/**
 * The enemy class (the mean ones >:c)
 */
public abstract class Enemy extends Character{
    /**The starting coordinates of the enemy */
    private Position startCoord;

    /**
     * Constructor of the enemy parent to Zombie, Hunter and Ghost
     * @param name the unique name of an enemy
     * @param coord its current coordinates
     * @param maxhealth The max health of the enemy
     */
    public Enemy(String name, Position coord,int maxhealth){
        super(name, coord, maxhealth);
        this.startCoord = coord.clone();
    }

    /**
     * Checks if an enemy collides with a cell or not
     * @param cell the cell it collides
     * @return if the enemy can go on that space or not
     */
    public abstract boolean canMove(Cell cell);

    /**
     * movement depends on the enemy type
     * @param level The level where the enemy is moving
     */
    public abstract void move(Level level);

    /**
     * Resets the position of an enemy (get back to startCoord)
     */
    public void resetPosition(){
        this.coord.setX(this.startCoord.getX());
        this.coord.setY(this.startCoord.getY());
    }

    /**
     * The abstract function where the enemy takes life of a player because of contact
     * @param player the player that suffers
     */
    public abstract void attackPlayer(Player player);
}