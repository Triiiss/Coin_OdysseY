/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_4;

import java.util.Scanner;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;

/**
 * The rules of the game, comportemental things
 */
public class Rule{
    /**
     * Empty constructor
     */
    public Rule(){
    }

    /**
     * Each cell has a type, and shows something based on what's on it
     * The player is priority, then coins then the actual type
     * So a coin can be on a trap and just show the coin (it's a hidden trap hehe)
     * @param cell The cell we want to give the char of
     * @param enemies The list of enemies
     * @param playerCoord the player's coordinate
     * @return the character of the cell ( ,.,#,*,1,etc)
     */
    public static String cellChar(Cell cell,HashMap<Cell,Enemy> enemyCells, Position playerCoord){
        String RESET = "\u001B[0m";
        String YELLOW = "\u001B[33m";  // Coin
        String RED = "\u001B[31m";      // Enemy
        String CYAN = "\u001B[36m";     // Player
        String MAGENTA = "\u001B[35m";  // Traps

        if (playerCoord.equals(cell.getCoord())){
            return CYAN + "1" + RESET;
        }
        for (Cell c : enemyCells.keySet()){
            if (c.equals(cell)){
                return RED + enemyCells.get(c).getChar() + RESET;
            }
        }

        if (cell.getCoin()){
            return YELLOW + "." + RESET;
        }
        switch(cell.getType()){
            case CellType.WALL:
                return "#" + RESET;
            case CellType.EMPTY:
                return " " + RESET;
            case CellType.TRAP:
                return MAGENTA + "*" + RESET;
            case CellType.DOOR:
                return "D" + RESET;
        }
        return " ";
    }

    /**
     * Checks if the game is over (player no health)
     * @param level the level with the player in it
     * @return if the game is over or not
     */
    public static boolean gameOver(Level level){
        return level.getPlayer().getHealthPoint() <= 0;
    }

    /**
     * Checks if a level is done (no coins left)
     * @param level the level
     * @return if the level is completed or not
     */
    public static boolean levelComplete(Level level){
        return level.getNbCoins() <= 0;
    }

    /**
     * Asks an input to the user
     * @return the input as a letter
     */
    public static String getInput(){
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    /**
     * Link the letters of the input to the direction
     * @param input the input of the user
     * @return the direction
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
     * Ask input and return the direction
     * @return the direction given by the user
     */
    public static Direction getDirection(){
        return Rule.keyBinding(getInput());
    }

    /**
     * The tore mecanic (loop)
     * @param level the level it is in
     * @param coord the coords that needs changing if it is out of bound
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
     * Activate a trap a hurt the player
     * @param level The level we activate the trap from
     * @param newPlayer the player's future position
     */
    public static void activateTrap(Level level,Position newPlayer){
        level.getPlayer().removeHealth(2);
        level.resetEnemies();
        level.getLevel()[newPlayer.getY()][newPlayer.getX()].setType(CellType.EMPTY);       // Delete the trap
        System.out.println("\u001B[31mYou fell into a trap !\u001B[0m");
        newPlayer.setX(level.getStartPlayer().getX());
        newPlayer.setY(level.getStartPlayer().getY());
    }

    /**
     * Collect the coin from the cell
     * @param level The level we take the coin from
     * @param newPlayer the player's future position
     */
    public static void collectCoin(Level level, Position newPlayer){
        level.getPlayer().addScore(10);
        level.getLevel()[newPlayer.getY()][newPlayer.getX()].removeCoin();
    }
}