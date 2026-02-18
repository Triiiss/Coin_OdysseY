/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_2;

/**
 * Structure class to make walls in the level
 */
public class Structure{
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * Constructor method
     * @param x The horizontal coordonate within the level
     * @param y The vertical coordonate within the level
     * @param width The size of the x coordonate
     * @param height The size of the y coordonate
     */
    public Structure(int width, int height, int x, int y){
        if (width >= 0 && height >= 0 && x >= 0 && y >= 0){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
    /**
     * Returns the width of the structure
     * @return the width
    */
    public int getWidth(){
        return this.width;
    }

    /**
     * Returns the height of the structure
     * @return the height
    */
    public int getheight(){
        return this.height;
    }

    /**
     * Returns the x coordinate of the structure
     * @return the x coordinate
    */
    public int getX(){
        return this.x;
    }

    /**
     * Returns the y coordinate of the structure
     * @return the y coordinate
    */
    public int getY(){
        return this.y;
    }
    
/*
    /**
     * Check if the structure can be fitted inside the level
     * @param level The level to check
     * @return true if it can be in the level, false if not
     * /
    public isinLevel(Level level){
        condition = (this.x >= 0) && (this.x <= level.length) && (this.y >= 0) && (this.y <= level.width) &&(this.x + this.length >= 0) && (this.x + this.length <= level.length) && (this.y + this.width >= 0) && (this.y + this.width <= level.width);        // x, x+length, y et y+width sont dans les bornes
        return condition ? true : false;
    }*/
}