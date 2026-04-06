/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_5.characters;

import world_5.environnement.Position;
import world_5.environnement.Cell;
import world_5.environnement.Level;
import world_5.types.CellType;

import java.util.Random;

/**
 * The zombie (random enemy)
 */
public class Zombie extends Enemy{
    /**
     * The zombie enemy
     * @param name The name of the zombie
     * @param coord The starting position of the enemy
     * @param maxhealth The maximum health
     */
    public Zombie(String name, Position coord,int maxhealth){
        super(name, coord, maxhealth/*, true*/);
    }
    
    /**
     * Checks if an enemy collides with a cell or not
     * @param cell the cell it collides
     * @return if the enemy can go on that space or not
     */
    public boolean canMove(Cell cell){
        return !cell.getCollision() && cell.getType() != CellType.TRAP;
    }

    /**
     * Moves the enemy with the type RANDOM
     * @param level the level where the enemy moves
     */
    public void move(Level level){
        Position newEnemy = this.getCoord().clone();
        Random rand = new Random();
        switch(rand.nextInt(4)){
            case 0:
                newEnemy.addX(1);
                break;
            case 1:
                newEnemy.addY(1);
                break;
            case 2:
                newEnemy.addX(-1);
                break;
            case 3:
                newEnemy.addY(-1);
                break;
        }

        if (level.isAccessible(newEnemy,this)){
            this.moveTo(newEnemy.getX(),newEnemy.getY());
        }
    }

    /**
     * The function where the enemy takes life of a player
     * @param player the player that suffers
     */
    public void attackPlayer(Player player){
        player.removeHealth(1);

        System.out.println("\u001B[31mYou've been hit by " + this.name + "\u001B[0m");
    }
}