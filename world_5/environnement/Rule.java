/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.environnement;

import world_5.characters.*;
import world_5.types.*;

import java.util.Scanner;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

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
    public static String cellChar(Cell cell, Level level){
        String RESET = "\u001B[0m";
        String YELLOW = "\u001B[33m";  // Coin
        String RED = "\u001B[31m";      // Enemy
        String CYAN = "\u001B[36m";     // Player
        String MAGENTA = "\u001B[35m";  // Traps
        String BLUE = "\u001B[94m";     // Items

        if (level.getPlayer().getCoord().equals(cell.getCoord())){
            return CYAN + "1" + RESET;
        }

        if(level.getEnemyCells().contains(cell)){
            Iterator<Enemy> iterator = level.getEnemies().iterator();
            while (iterator.hasNext()){
                Enemy enemy = iterator.next();
                if (enemy.getCoord().equals(cell.getCoord())){
                    if (enemy instanceof Zombie){
                        return RED + "R" + RESET;
                    }
                    if (enemy instanceof Ghost){
                        return RED + "G" + RESET;
                    }
                    if (enemy instanceof Hunter){
                        return RED + "C" + RESET;
                    }
                }
            }
        }

        if (cell.getHasItem()){
            switch (cell.getItem().getType()){
                case ItemType.COIN:
                    return YELLOW + "." + RESET;
                case ItemType.WEAPON:
                    return BLUE + "W" + RESET;
                case ItemType.HOURGLASS:
                    return BLUE + "H" + RESET;
                    
            }
            
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
        System.out.println("\u001B[31mYou fell into a trap !\u001B[0m");
        level.getPlayer().removeHealth(2);

        level.resetEnemies();        // Resets the entities

        level.getLevel()[newPlayer.getY()][newPlayer.getX()].setType(CellType.EMPTY);       // Delete the trap
        newPlayer.setX(level.getStartPlayer().getX());
        newPlayer.setY(level.getStartPlayer().getY());
    }

    /**
     * Collect the coin from the cell
     * @param level The level we take the coin from
     * @param newPlayer the player's future position
     */
    public static void collectItem(Level level, Position newPlayer){
        if (level.getLevel()[newPlayer.getY()][newPlayer.getX()].getItem().pickUp(level)){      // If it got picked up
            level.getLevel()[newPlayer.getY()][newPlayer.getX()].removeItem();                  // Delete the item from the cell
        }
    }

     /**
     * Return the next path to take to get from enemy.coord to the target
     * @param source The enemy mostly the hunter
     * @param target The target usually the player
     * @return the next step
     */
    public static Position shortestPath(Level level, Position source, Position target, Enemy enemy){
        boolean[][] visited = new boolean[level.getHeight()][level.getWidth()];
        HashMap<Position, Position> path = new HashMap<>();
        ArrayList<Position> queue = new ArrayList<Position>();

        visited[source.getY()][source.getX()] = true;     // Initialisation
        queue.add(source);
        path.put(source, null);

        int[][] directions = {      // directions of x an dy
            {0, -1}, // up
            {0, 1},  // down
            {-1, 0}, // left
            {1, 0}   // right
        };

        while (!queue.isEmpty()){
            Position current = queue.remove(0);

            if (current.equals(target)){
                break;
            }

            for (int[] dir : directions){       // Check all "children" (all four directions)
                Position next = new Position(current.getX() + dir[0],current.getY() + dir[1]);

                if (level.isAvailable(next, enemy) && !visited[next.getY()][next.getX()]){       // Adds a new step
                    visited[next.getY()][next.getX()] = true;
                    path.put(next,current);
                    queue.add(next);
                }
            }
        }
        if (!path.containsKey(target)){       // The target and source aren't connected
            return source;
        }

        Position step = target;
        while (path.get(step) != null && !path.get(step).equals(source)){       // From target to "just before source"
            step = path.get(step);
        }

        return step;
    }
}