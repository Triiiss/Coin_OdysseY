/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_3;

/**
 * The position class
 */
public class Position{
    private int x;
    private int y;

    /**
     * Constructor method
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x coordinate
     * @return the x coordinate
     */
    public int getX(){
        return this.x;
    }

    /**
     * Get the y coordinate
     * @return the y coordinate
     */
    public int getY(){
        return this.y;
    }

    /**
     * Set the x coordinate
     * @param x the new x coordinate
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Sets the y coordinate
     * @param y the new y coordinate
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Adds a value to x
     * @param x the added x value
     */
    public void addX(int x){
        this.x += x;
    }


    /**
     * Adds a value to y
     * @param y the added y value
     */
    public void addY(int y){
        this.y += y;
    }
}