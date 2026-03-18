/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_4;

/**
 * The enemy class >:c
 */
public class Enemy extends Character{
    private Position startCoord;
    private EnemyType type;

    /**
     * Constructor method
     * @param name its name
     * @param coord its current coordinates
     * @param MAXHEALTH The max health of the enemy
     * @param collide if the enemy collides with the walls
     * @param type the enemy type
     */
    public Enemy(String name, Position coord,int MAXHEALTH, boolean collide, EnemyType type){
        super(name, coord, MAXHEALTH, collide);
        this.startCoord = new Position(coord.getX(),coord.getY());
        this.type = type;
    }

    /**
     * Get the starting coordinate of an enemy
     * @return The start coordinate of the enemy
     */
    public Position getStartCoord(){
        return this.startCoord;
    }

    /**
     * Get the type of the enemy
     * @return the enemy type
     */
    public EnemyType getType(){
        return this.type;
    }

    /**
     * Checks if an enemy collides with a cell or not
     * @param cell the cell it collides
     * @return if the enemy can go on that space or not
     */
    public boolean enemyCollision(Cell cell){
        switch (this.type){
            case EnemyType.RANDOM:
                return (!cell.getCollision() && cell.getType() != CellType.TRAP) ? true : false;
        }
        return false;
    }

    /**
     * Restes the position of an enemy (get back to startCoord)
     */
    public void resetPosition(){
        this.coord.setX(this.startCoord.getX());
        this.coord.setY(this.startCoord.getY());
    }
}