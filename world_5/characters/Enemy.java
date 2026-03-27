/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.environnement.Level;
import world_5.environnement.Cell;

/**
 * The enemy class >:c
 */
public abstract class Enemy extends Character{
    private Position startCoord;

    /**
     * Constructor method
     * @param name its name
     * @param coord its current coordinates
     * @param MAXHEALTH The max health of the enemy
     * @param collide if the enemy collides with the walls
     */
    public Enemy(String name, Position coord,int MAXHEALTH){
        super(name, coord, MAXHEALTH);
        this.startCoord = new Position(coord.getX(),coord.getY());
    }

    /**
     * Get the starting coordinate of an enemy
     * @return The start coordinate of the enemy
     */
    public Position getStartCoord(){
        return this.startCoord;
    }

    /**
     * Restes the position of an enemy (get back to startCoord)
     */
    public void resetPosition(){
        this.coord.setX(this.startCoord.getX());
        this.coord.setY(this.startCoord.getY());
    }

    /**
     * move prototype 
     * @param level The level where the enemy moves
     */
    public abstract void move(Level level);

    /**
     * Checks if an enemy collides with a cell or not
     * @param cell the cell it collides
     * @return if the enemy can go on that space or not
     */
    public abstract boolean enemyCollision(Cell cell);

    /**
     * The abstract function where the enemy takes life of a player
     * @param player the player that suffers
     */
    public abstract void enemyHit(Player player);

    /**
     * Gets the char to represent the enemy
     * @return the char R, H or G
     */
    public abstract char getChar();
}