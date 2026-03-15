/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4;

public class Position{
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public boolean equals(int x, int y){
        if (this.x == x && this.y == y){
            return true;
        }
        return false;
    }

    public boolean equals(Position coord){
        if (this.x == coord.getX() && this.y == coord.getY()){
            return true;
        }
        return false;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void addX(int x){
        this.x += x;
    }

    public void addY(int y){
        this.y += y;
    }
}