/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_3;

/**
 * Cell class
 */
public class Cell{
    private int x;
    private int y;
    private boolean coin;
    private CellType type;
    private boolean collision;

    /**
     * The cell is a space that can either be a wall, empty, or a trap
     * The player can be on it, and it can have a coin or not
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @param coin if it has a coin on it or not
     * @param type the type of the cell (0: a wall; 1: empty; 2: a trap)
     */
    public Cell(int x, int y, boolean coin, CellType type){
        this.x = x;
        this.y = y;
        this.coin = coin;
        if (coin && type == CellType.WALL){      // Checks the type and no coins inside a wall
            this.type = CellType.EMPTY;
        }
        else{
            this.type = type;
        }
        this.collision = this.type.defaultCollision();
    }

    public Cell(int x,int y,boolean coin, CellType type, boolean collision){
        this(x,y,coin,type);
        this.collision = collision;
    }

    /**
     * Get the X coordinate of the cell
     * @return The x coordinate
     */
    public int getX(){
        return this.x;
    }

    /**
     * Get the Y coordinate of the cell
     * @return The y coordinate
     */
    public int getY(){
        return this.y;
    }

    /**
     * Get wether the cell has a coin or not
     * @return The coin boolean
     */
    public boolean getCoin(){
        return this.coin;
    }

    /**
     * Get the type of the cell
     * @return The type of the cell
     */
    public CellType getType(){
        return this.type;
    }

    /**
     * Some tiles have collitions with the player (locked doors, or walls), some not (empty, or traps)
     * By adding a type of cell, adding it here will make it collide or not
     * @return true if it can collide with the player false if not
     */
    public boolean getCollision(){
        return this.collision;
    }

    /**
     * Sets the type during the creation or for changing levels
     * @param type the new type
     */
    public void setType(CellType type, boolean collision){
        if (!(coin && type == CellType.WALL && collision)){
            this.type = type;
            this.collision = collision;
        }
    }

    public void setType(CellType type){
        setType(type,type.defaultCollision());
    }

    /**
     * Adds a coin during the creation of the level
     */
    public void addCoin(){
        if (this.type != CellType.WALL){        // Not in a wall
            this.coin = true;
        }
        else{
            this.coin = false;      // Automatically sets it to 0 if it's in a wall
        }
    }

    /**
     * Removes a coin, for instance when the player takes it
     */
    public void removeCoin(){
        this.coin = false;
    }
}