/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4;

/**
 * Cell class
 */
public class Cell{
    private Position coord;
    private boolean coin;
    private CellType type;
    private boolean collision;

    /**
     * The cell is a space that can either be a wall, empty, or a trap
     * The player can be on it, and it can have a coin or not
     * @param coord the coords
     * @param coin if it has a coin on it or not
     * @param type the type of the cell (0: a wall; 1: empty; 2: a trap)
     */
    public Cell(Position coord, boolean coin, CellType type){
        this.coord = coord;
        this.coin = coin;
        if (coin && type == CellType.WALL){      // Checks the type and no coins inside a wall
            this.type = CellType.EMPTY;
        }
        else{
            this.type = type;
        }
        this.collision = this.type.defaultCollision();
    }

    /**
     * The cell is a space that can either be a wall, empty, or a trap
     * The player can be on it, and it can have a coin or not
     * @param coord the coordinates
     * @param coin if it has a coin on it or not
     * @param type the type of the cell (0: a wall; 1: empty; 2: a trap)
     * @param collision the collision with the player
     */
    public Cell(Position coord,boolean coin, CellType type, boolean collision){
        this(coord,coin,type);
        this.collision = collision;
    }

    /**
     * Get the coordinate of the cell
     * @return The coordinate
     */
    public Position getCoord(){
        return this.coord;
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
     * @param collision if the cell collides with the player
     */
    public void setType(CellType type, boolean collision){
        if (!(coin && type == CellType.WALL && collision)){
            this.type = type;
            this.collision = collision;
        }
    }

    /**
     * Sets the type during the creation or for changing levels
     * @param type the new type
     */
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

    /**
     * Checks if two cells are equal (coordinate)
     * @param cell The cell we want to check
     * @return If two cells are equal or not
     */
    public boolean equals(Cell cell){
        return ((this.coord.equals(cell.getCoord())) && (this.type == cell.getType())) ? true : false;
    }

    /**
     * Redefine the hashCode
     * @return The hash of an object based on equals
     */
    @Override
    public int hashCode(){
        int result = 11;     // My favorite prime number
        result = 31*result + this.coord.hashCode();
        result = 31*result + this.type.hashCode();

        return result;
    }
}