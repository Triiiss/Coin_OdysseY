/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_3;

import java.util.Scanner;

/**
 * The rule class
 */
public class Rule{

    /**
     * Empty constructor method
     * @param level the level we do nothing about
     */
    public Rule(Level level){
    }

    /**
     * Each cell has a type, and shows something based on what's on it
     * The player is priority, then coins then the actual type
     * So a coin can be on a trap and just show the coin (it's a hidden trap hehe)
     * @param cell The cell we get the char from
     * @param playerX The X coordinate of the player
     * @param playerY The coordinates of the player to know if the player is in the cell or not
     * @return the character of the cell ( ,.,#,*,1,etc)
     */
    public static char cellChar(Cell cell,int playerX, int playerY){
        if (playerX == cell.getX() && playerY == cell.getY()){
            return '1';
        }
        else if (cell.getCoin()){
            return '.';
        }
        switch(cell.getType()){
            case CellType.WALL:
                return '#';
            case CellType.EMPTY:
                return ' ';
            case CellType.TRAP:
                return '*';
            case CellType.DOOR:
                return 'D';
        }
        return ' ';
    }

    /**
     * If the game is lost or not
     * @param level the level to get the player from
     * @return if the player lost or not
     */
    public static boolean gameOver(Level level){
        return level.getPlayer().getHealthPoint() <= 0;
    }

    /**
     * If the level is complete or not
     * @param level the level
     * @return if the level is complete or not
     */
    public static boolean levelComplete(Level level){
        return level.getNbCoins() <= 0;
    }

    /**
     * Gets the input of the yser
     * @return the input as a string
     */
    public static String getInput(){
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    /**
     * Links a string input to a Direction
     * @param input the input
     * @return the input as a direction
     */
    public static Direction keyBinding(String input){
        char leftKey = 'q';
        char upKey = 'z';
        char rightKey = 'd';
        char downKey = 's';
        char exitKey = 'n';
        char restartKey = 'r';


        if (input.charAt(0) == leftKey) {
            return Direction.LEFT;
        }
        if (input.charAt(0) == upKey) {
            return Direction.UP;
        }
        if (input.charAt(0) == rightKey) {
            return Direction.RIGHT;
        }
        if (input.charAt(0) == downKey) {
            return Direction.DOWN;
        }
        if (input.charAt(0) == exitKey) {
            return Direction.EXIT;
        }
        if (input.charAt(0) == restartKey) {
            return Direction.RESTART;
        }

        return Direction.UNKNOWN;
    }

    /**
     * Get the direction as an input
     * @return the direction given by the user
     */
    public static Direction getDirection(){
        return Rule.keyBinding(getInput());
    }

    /**
     * The tore mechanic
     * @param level the level with the user
     * @param coord the coordinates the player wants to go to
     */
    public static void tore(Level level, Position coord){
        while (coord.getX() < 0){         // The tore system => adds a cycle to the coordinates
            coord.addX(level.getWidth());
        }
        while (coord.getX() >= level.getWidth()){
            coord.addX(-level.getWidth());
        }
        while (coord.getY() < 0){
            coord.addY(level.getHeight());
        }
        while (coord.getY() >= level.getHeight()){
            coord.addY(-level.getHeight());
        }
    }

    /**
     * Uses an event to activate trap
     * @param level the level the trap is activated in
     * @param newPlayer the position of the player
     */
    public static void activateTrap(Level level,Position newPlayer){
        level.getPlayer().removeHealth(2);
        level.getLevel()[newPlayer.getY()][newPlayer.getX()].setType(CellType.EMPTY);       // Delete the trap
        newPlayer.setX(level.getStartPlayer().getX());
        newPlayer.setY(level.getStartPlayer().getY());
    }

    /**
     * Uses an event to collect a coin
     * @param level the level the coin is taken from
     * @param newPlayer the position of the player
     */
    public static void collectCoin(Level level, Position newPlayer){
        level.getPlayer().addScore(10);
        level.getLevel()[newPlayer.getY()][newPlayer.getX()].removeCoin();
    }
}