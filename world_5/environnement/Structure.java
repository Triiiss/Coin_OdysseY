/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.environnement;

import world_5.exceptions.InvalidStructureException;

/**
 * Structure class to make walls, doors, trap, and items in the level
 */
public class Structure{
    /**The type of the structure (item, wall, etc..) */
    private int type;
    /**The x coordinate of the structure bottom left */
    private int x;
    /**The y coordinate of the structure bottom left */
    private int y;
    /**The width of the structure (horizontal) */
    private int width;
    /**The height of the structure (vertical) */
    private int height;

    /**
     * Constructor method
     * @param type Whether it's a wall(0), a trap(1), a door(2), a coin(100), a weapon(101) or an hourglass(102)
     * @param x The horizontal coordonate within the level of the bottom left corner
     * @param y The vertical coordonate within the level of the bottom left corner
     * @param width The size of the x coordonate
     * @param height The size of the y coordonate
     * @throws InvalidStructureException if the arguments of a structure are invalid
     */
    public Structure(int type, int width, int height, int x, int y) throws InvalidStructureException{
        if (width >= 0 && height >= 0 && x >= 0 && y >= 0){
            this.type = type;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        else{
            throw new InvalidStructureException("Structure arguments invalid");
        }
    }
    
    /**
     * @return the width
    */
    public int getWidth(){
        return this.width;
    }

    /**
     * @return the height
    */
    public int getHeight(){
        return this.height;
    }

    /**
     * @return the x coordinate
    */
    public int getX(){
        return this.x;
    }

    /**
     * @return the y coordinate
    */
    public int getY(){
        return this.y;
    }

    /**
     * @return the type
    */
    public int getType(){
        return this.type;
    }
}