/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4;

/**
 * The position x and y
 */
public class Position{
    private int x;
    private int y;

    /**
     * The constructor of a position
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
     * Checks if a position is equal to x and y
     * @param x the x coordinate we want to check
     * @param y the y coordinate we want to check
     * @return if the two coordinate set are equal or not
     */
    public boolean equals(int x, int y){
        if (this.x == x && this.y == y){
            return true;
        }
        return false;
    }

    /**
     * Checks if two positions are equal (same coord)
     * @param coord the other position to check
     * @return if the two position are equal or not
     */
    public boolean equals(Position coord){
        if (this.x == coord.getX() && this.y == coord.getY()){
            return true;
        }
        return false;
    }

    /**
     * Redefine the hashCode
     * @return The hash of an object based on equals
     */
    @Override
    public int hashCode(){
        int result = 11;     // My favorite prime number
        result = 31*result + this.x;
        result = 31*result + this.y;

        return result;
    }

    /**
     * Change the x coordinate
     * @param x the new coordinate of the position
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Change the y coordinate
     * @param y the new coordinate of the position
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Add to the x position
     * @param x the new x to add to the position
     */
    public void addX(int x){
        this.x += x;
    }

    /**
     * Add to the y position
     * @param y the new y to add to the position
     */
    public void addY(int y){
        this.y += y;
    }
}