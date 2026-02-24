/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_3;

/**
 * Cell class
 */
public class Cell{
    private int x;
    private int y;
    private boolean coin;
    private int type;

    /**
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @param coin if it has a coin on it or not
     * @param type the type of the cell (0: a wall; 1: empty; 2: a trap)
     */
    public Cell(int x, int y, boolean coin, int type){
        this.x = x;
        this.y = y;
        this.coin = coin;
        if (Cell.isValidType(type) && ((coin == true && type != 0) || (coin == false))){      // Checks the type and no coins inside a wall
            this.type = type;
        }
    }

    /**
     * @return The x coordinate
     */
    public int getX(){
        return this.x;
    }

    /**
     * @return The y coordinate
     */
    public int getY(){
        return this.y;
    }

    /**
     * @return The coin boolean
     */
    public boolean getCoin(){
        return this.coin;
    }

    /**
     * @return The type of the cell
     */
    public int getType(){
        return this.type;
    }

    public static boolean isValidType(int type){
        return (type == 0 || type == 1 || type == 2 || type == 3) ? true : false;
    }

    public boolean hasCollision(){
        return (this.type == 0 || this.type == 3) ? true : false;
    }

    /**
     * Sets the type during the creation or for changing levels
     * @param type the new type
     */
    public void setType(int type){
        if (Cell.isValidType(type) && ((coin == true && type != 0) || (coin == false))){
            this.type = type;
        }
    }

    /**
     * Adds a coin during the creation of the level
     */
    public void addCoin(){
        if (this.type != 0){        // Not in a wall
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

    public char getTypeChar(int playerX, int playerY){
        if (playerX == this.x && playerY == this.y){
            return '1';
        }
        else if (this.coin){
            return '.';
        }
        switch(this.type){
            case 0:
                return '#';
            case 1:
                return ' ';
            case 2:
                return '*';
            case 3:
                return 'D';
        }
        return ' ';
    }
}
