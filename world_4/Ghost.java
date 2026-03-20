/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4;

/**
 * The ghost (phases through walls enemy)
 */


public class Ghost extends Enemy{
    /**
     * The ghost enemy
     * @param name The name of the zombie
     * @param coord The starting position of the enemy
     * @param MAXHEALTH The maximum health
     */
    public Ghost(String name, Position coord,int MAXHEALTH){
        super(name, coord, MAXHEALTH, false);
    }

    /**
     * Moves the enemy with the type GHOST
     * @param level the level where the enemy moves
     */
    public void move(Level level){
        Position newEnemy = new Position(this.getCoord().getX(),this.getCoord().getY());
        
        //FQJIGhoqsdklvcnxfmgisd

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
        return true;
    }

    /**
     * Gets the char to represent the enemy
     * @return the char G
     */
    public char getChar(){
        return 'G';
    }
}