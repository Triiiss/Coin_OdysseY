/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.environnement;

import world_5.types.*;
import world_5.inventory.*;

/**
 * Cell class
 */
public class Cell{
    private Position coord;
    private CellType type;
    private boolean collision;
    private boolean hasItem;
    private Item item;

    /**
     * The cell is a space that can either be a wall, empty, or a trap
     * The player can be on it, and it can have a coin or not
     * @param coord the coords
     * @param coin if it has a coin on it or not
     * @param type the type of the cell (0: a wall; 1: empty; 2: a trap)
     */
    public Cell(Position coord, CellType type){
        this.coord = coord;
        this.type = type;
        this.collision = this.type.defaultCollision();
        this.hasItem = false;
        this.item = null;
    }

    public Cell(Position coord, CellType type, Item item){
        this(coord,type);
        if (item != null && type != CellType.WALL){     // No items within walls
            this.hasItem = true;
            this.item = item;
        }
    }
    
    /**
     * The cell is a space that can either be a wall, empty, or a trap
     * The player can be on it, and it can have a coin or not
     * @param coord the coordinates
     * @param coin if it has a coin on it or not
     * @param type the type of the cell (0: a wall; 1: empty; 2: a trap)
     * @param collision the collision with the player
     */
    public Cell(Position coord, CellType type, boolean collision){
        this(coord,type);
        this.collision = collision;
    }

    public Cell(Position coord, CellType type, boolean collision, Item item){
        this(coord,type,collision);

        if (item != null && !collision){
            this.hasItem = true;
            this.item = item;
        }
    }

    /**
     * Get the coordinate of the cell
     * @return The coordinate
     */
    public Position getCoord(){
        return this.coord;
    }

    public boolean hasCoin(){
        if (this.hasItem && this.item.getType() == ItemType.COIN){
            return true;
        }
        return false;
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

    public boolean getHasItem(){
        return this.hasItem;
    }

    public Item getItem(){
        return this.item;
    }

    /**
     * Sets the type during the creation or for changing levels
     * @param type the new type
     * @param collision if the cell collides with the player
     */
    public void setType(CellType type, boolean collision){
        if (!(this.hasItem && this.item.getType() == ItemType.COIN && type == CellType.WALL && collision)){
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

    public void addItem(Item item){
        if (item != null && !this.hasItem){     // No writting over an already existing item
            this.hasItem = true;
            this.item = item;
        }
    }

    public Item removeItem(){
        Item item = this.item;
        this.item = null;
        this.hasItem = false;

        return item;
    }

    /**
     * Checks if two cells are equal (coordinate)
     * @param cell The cell we want to check
     * @return If two cells are equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        Cell cell = (Cell) obj;

        return this.coord.equals(cell.getCoord()) &&
            this.type.equals(cell.getType());
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