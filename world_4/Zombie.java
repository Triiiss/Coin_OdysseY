/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4;

import java.util.Random;

/**
 * The zombie (random enemy)
 */
public class Zombie extends Enemy{
    /**
     * The zombie enemy
     * @param name The name of the zombie
     * @param coord The starting position of the enemy
     * @param MAXHEALTH The maximum health
     */
    public Zombie(String name, Position coord,int MAXHEALTH){
        super(name, coord, MAXHEALTH/*, true*/);
    }

    /**
     * Moves the enemy with the type RANDOM
     * @param level the level where the enemy moves
     */
    public void move(Level level){
        Position newEnemy = new Position(this.getCoord().getX(),this.getCoord().getY());
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

        if (level.isAvailable(newEnemy,this)){
            this.move(newEnemy.getX(),newEnemy.getY());
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
    public boolean enemyCollision(Cell cell){
        return !cell.getCollision() && cell.getType() != CellType.TRAP;
    }
    
    /**
     * Gets the char to represent the enemy
     * @return the char R
     */
    public char getChar(){
        return 'R';
    }
}