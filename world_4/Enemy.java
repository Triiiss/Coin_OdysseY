/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_4;

import java.util.Random;

/**
 * The enemy class >:c
 */
public class Enemy extends Character{
    private Position startCoord;
    private EnemyType type;

    public Enemy(String name, Position coord,int MAXHEALTH, boolean collide){
        super(name, coord, MAXHEALTH, collide);
        this.startCoord = new Position(coord.getX(),coord.getY());
        this.type = EnemyType.RANDOM;
    }

    /**
     * @return The start coordinate of the enemy
     */
    public Position getStartCoord(){
        return this.startCoord;
    }

    public void move(){
        Random rand = new Random();
        switch(this.type){
            case EnemyType.RANDOM:
                switch(rand.nextInt(4)){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
                break;
        }
    }
}
