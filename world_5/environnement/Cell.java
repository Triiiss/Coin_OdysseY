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
     * @param type the type of the cell
     */
    public Cell(Position coord, CellType type){
        this.coord = coord;
        this.type = type;
        this.collision = this.type.defaultCollision();
        this.hasItem = false;
        this.item = null;
    }

    /**
     * The cell with an item
     * @param coord the coords
     * @param type the type of the cell
     * @param item the item in the space
     */
    public Cell(Position coord, CellType type, Item item){
        this(coord,type);
        if (item != null && type != CellType.WALL){     // No items within walls
            this.hasItem = true;
            this.item = item;
        }
    }
    
    /**
     * The cell with a special collision
     * @param coord the coordinates
     * @param type the type of the cell
     * @param collision the collision with the player
     */
    public Cell(Position coord, CellType type, boolean collision){
        this(coord,type);
        this.collision = collision;
    }

    /**
     * The cell with the special collision and an item
     * @param coord the coordinates
     * @param type the type of the cell
     * @param collision the collision with the player
     * @param item the item in the space
     */
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
     * Get the hasItem (if there is an item here or not)
     * @return if the cell has an item or not
     */
    public boolean getHasItem(){
        return this.hasItem;
    }

    /**
     * Get the item of the cell
     * @return the item of the cell
     */
    public Item getItem(){
        return this.item;
    }

    /**
     * To know if a cell has an item which is a coin
     * @return true if the cell has a coin or false if not
     */
    public boolean hasCoin(){
        if (this.hasItem && this.item.getType() == ItemType.COIN){
            return true;
        }
        return false;
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

    /**
     * Adds an item in a cell
     * @param item the item we want to add
     * @return if the item was added or not
     */
    public boolean addItem(Item item){
        if (item != null && !this.hasItem && type != CellType.WALL){     // No writting over an already existing item
            this.hasItem = true;
            this.item = item;
            return true;
        }
        return false;
    }

    /**
     * Removes the item from a cell
     * @return the item that was on the cell
     */
    public Item removeItem(){
        Item item = this.item;
        this.item = null;
        this.hasItem = false;

        return item;
    }

    /**
     * Checks if two cells are equal (coordinate)
     * @param object The cell we want to check
     * @return If two cells are equal or not
     */
    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || this.getClass() != object.getClass()){
            return false;
        }

        Cell cell = (Cell) object;

        if (this.hasItem){
            return this.coord.equals(cell.getCoord()) && this.type.equals(cell.getType()) && this.collision == cell.getCollision() && this.hasItem == cell.getHasItem() && this.item.equals(cell.getItem());
        }
        return this.coord.equals(cell.getCoord()) && this.type.equals(cell.getType()) && this.collision == cell.getCollision() && this.hasItem == cell.getHasItem();
    }

    /**
     * Redefine the hashCode
     * @return The hash of an object based on equals
     */
    @Override
    public int hashCode(){
        int result = 11;     // My favorite prime number
        result = 17*result + this.coord.hashCode();
        result = 19*result + this.type.hashCode();
        result = 23*result + Boolean.valueOf(this.collision).hashCode();
        result = 29*result + Boolean.valueOf(this.hasItem).hashCode();
        if (this.hasItem){
            result = 31*result + this.item.hashCode();
        }

        return result;
    }
}