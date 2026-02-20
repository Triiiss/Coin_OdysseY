/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_3;

/**
 * Structure class to make walls in the level
 */
public class Structure{
    private int type;
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * Constructor method
     * @param type Whether it's a wall(0), a coin(1) or else(...)
     * @param x The horizontal coordonate within the level
     * @param y The vertical coordonate within the level
     * @param width The size of the x coordonate
     * @param height The size of the y coordonate
     */
    public Structure(int type, int width, int height, int x, int y){
        if (type >= 0 && type < 3 && width >= 0 && height >= 0 && x >= 0 && y >= 0){
            this.type = type;
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

    /**
     * Returns the type of the structure
     * @return the type
    */
    public int getType(){
        return this.type;
    }
}