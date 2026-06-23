/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.environnement;

import world_5.types.CellType;
import world_5.inventory.Element;

/**
 * Cell class that constitutes a level
 */
public class Cell{
    /**The coordinates of the Cell */
    private Position coord;
    /**The type of cell (wall, empty, trap, door) */
    private CellType type;
    /**If this specific cell has collision or not */
    private boolean collision;
    /**If the cell has an item */
    private boolean hasItem;
    /**The possible item on the cell */
    private Element item;

    /**
     * The default constructor
     * The player and other characters can be on a Cell, and it can have an item or not
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
    public Cell(Position coord, CellType type, Element item){
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
    public Cell(Position coord, CellType type, boolean collision, Element item){
        this(coord,type,collision);

        if (item != null && !collision){        // The item can be on a Cell where the player can walk on
            this.hasItem = true;
            this.item = item;
        }
    }

    /**
     * @return The coordinate of the cell
     */
    public Position getCoord(){
        return this.coord;
    }

    /**
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
     * @return if the cell has an item or not
     */
    public boolean hasItem(){
        return this.hasItem;
    }

    /**
     * @return the item of the cell (null if there is none)
     */
    public Element getItem(){
        if (this.hasItem){
            return this.item;
        }
        return null;
    }

    /**
     * Sets the type during the creation or for changing levels
     * @param type the new type
     * @param collision if the cell collides with the player
     */
    public void setType(CellType type, boolean collision){
        if (!(this.hasItem && collision)){
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
     * Adds an item to a cell
     * @param item the item we want to add
     * @return if the item was successfully added or not
     */
    public boolean addItem(Element item){
        if (item != null && !this.hasItem && !this.collision){     // No writting over an already existing item
            this.hasItem = true;
            this.item = item;
            return true;
        }
        return false;
    }

    /**
     * Removes the item from a cell
     * @return the item that was previously on the cell
     */
    public Element removeItem(){
        Element item = this.item;
        this.item = null;
        this.hasItem = false;

        return item;
    }

    /**
     * Checks if two cells are equal (coordinate, type and collision not item)
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
        return this.coord.equals(cell.getCoord()) && this.type.equals(cell.getType()) && this.collision == cell.getCollision();
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

        return result;
    }
}