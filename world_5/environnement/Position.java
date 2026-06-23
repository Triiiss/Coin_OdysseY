/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.environnement;

/**
 * The position x and y
 */
public class Position{
    /**The x coordinate */
    private int x;
    /**The y coordinate */
    private int y;

    /**
     * The constructor of a Position
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
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
     * @param x the new coordinate of the position
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * @param y the new coordinate of the position
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * If the position object is not corrupted
     * @return true if all values are valid
     */
    public boolean validPosition(){
        return (this.x >= 0 && this.y >= 0);
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
    
    /**
     * Clone a position by creating a new object
     * @return the new Position object
     */
    public Position clone(){
        Position clone = new Position(this.x, this.y);

        return clone;
    }

    /**
     * Checks if a position is equal to x and y
     * @param x the x coordinate we want to check
     * @param y the y coordinate we want to check
     * @return if the two coordinate sets are equal or not
     */
    public boolean equals(int x, int y){
        return this.equals(new Position(x, y));
    }

    /**
     * Checks if two positions are equal (same coord)
     * @param object the other object to check
     * @return if the two position are equal or not
     */
    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Position coord = (Position) object;
        return this.x == coord.getX() && this.y == coord.getY();
    }

    /**
     * Redefine the hashCode based on x and y
     * @return The hash of an object based on equals
     */
    @Override
    public int hashCode(){
        int result = 11;     // My favorite prime number
        result = 19*result + this.x;
        result = 31*result + this.y;

        return result;
    }
}