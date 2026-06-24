/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_5.environnement;

import world_5.characters.*;
import world_5.types.*;
import world_5.inventory.item.*;

import java.util.Scanner;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * The rules of the game, comportemental things
 */
public class Rule{
    /**
     * Empty constructor
     */
    public Rule(){
        // Empty constructor
    }

    /**
     * Each cell has a type, and shows something based on what's on it
     * The player is priority, then the other enemies then coins, then walls & doors, then items and finaly traps or empty space
     * So a coin can be on a trap and just show the coin (it's a hidden trap hehe)
     * Walls with no collision can also hide items
     * @param cell The cell we want to give the corresponding char of
     * @param level The level (to access the player and enemies)
     * @return the character of the cell ( ,.,#,*,1,,W,H,G,R,C, etc)
     */
    public static String cellChar(Cell cell, Level level){
        String RESET = "\u001B[0m";
        String YELLOW = "\u001B[33m";  // Coin
        String RED = "\u001B[31m";      // Enemy
        String CYAN = "\u001B[36m";     // Enemies frozen
        String MAGENTA = "\u001B[35m";  // Traps
        String BLUE = "\u001B[94m";     // Items
        String GREEN = "\u001B[92m";    // Player

        if (level.getPlayer().getCoord().equals(cell.getCoord())){      // The player
            return GREEN + "1" + RESET;
        }

        if(level.getEnemyCells().contains(cell)){                       // The enemies
            Iterator<Enemy> iterator = level.getEnemies().iterator();
            while (iterator.hasNext()){
                Enemy enemy = iterator.next();
                if (enemy.getCoord().equals(cell.getCoord())){
                    if (enemy instanceof Zombie){
                        if (level.getFreeze() == 0){
                            return RED + "R" + RESET;
                        }
                        return CYAN + "R" + RESET;
                    }
                    if (enemy instanceof Ghost){
                        if (level.getFreeze() == 0){
                            return RED + "G" + RESET;
                        }
                        return CYAN + "G" + RESET;
                    }
                    if (enemy instanceof Hunter){
                        if (level.getFreeze() == 0){
                            return RED + "C" + RESET;
                        }
                        return CYAN + "C" + RESET;
                    }
                }
            }
        }

        if (cell.hasItem() && cell.getItem() instanceof Coin){       // Coin (that can hid traps and walls)
            return YELLOW + "." + RESET;
        }

        switch(cell.getType()){                         // Walls and doors
            case CellType.WALL:
                return "#" + RESET;
            case CellType.DOOR:
                return "D" + RESET;
            case CellType.WATER:
                return BLUE + "≋" + RESET;
        }

        if (cell.hasItem()){                         // Items (that can be hidden behind walls or hide traps)
            if (cell.getItem() instanceof Weapon){
                return BLUE + "W" + RESET;
            }
            if (cell.getItem() instanceof Hourglass){
                return BLUE + "H" + RESET;
            }
            if (cell.getItem() instanceof Potion){
                return BLUE + "P" + RESET;
            }
        }

        switch(cell.getType()){                         // Traps and empty space
            case CellType.EMPTY:
                return " " + RESET;
            case CellType.TRAP:
                return MAGENTA + "*" + RESET;
        }
        return " ";
    }

    /**
     * Checks if the game is over/lost (player has no more health)
     * @param level the level with the player in it
     * @return if the game is over or not
     */
    public static boolean gameOver(Player player){
        return player.getHealthPoint() <= 0;
    }

    /**
     * Checks if a level is done depending on the objective
     * @param level the level we're checking
     * @return if the level objective is completed or not
     */
    public static boolean levelComplete(Level level){
        if (level.getType() == ObjectiveType.ENEMIES){
            return level.getEnemies().isEmpty();
        }
        if (level.getType() == ObjectiveType.COINS){
            return level.getNbCoins() <= 0;
        }
        if (level.getType() == ObjectiveType.SURVIVAL){
            return (level.getTime() >= level.getGoalTime() && level.getGoalTime() > 0);
        }
        return level.getNbCoins() <= 0;     // Default type
    }

    /**
     * Asks an input to the user
     * @return the input with the keyboard as a letter
     */
    public static String getInput(){
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    /**
     * Bind the letters of the input to the direction of the game
     * @param input the input of the user as a letter
     * @return the direction or key the user inputed
     */
    private static Direction keyBinding(String input){
        char leftKey = 'q';
        char upKey = 'z';
        char rightKey = 'd';
        char downKey = 's';
        char inventoryKey = 'i';
        char useKey = 'u';
        char exitKey = 'n';
        char restartKey = 'r';


        if (java.lang.Character.toLowerCase(input.charAt(0)) == leftKey) {
            return Direction.LEFT;
        }
        if (java.lang.Character.toLowerCase(input.charAt(0)) == upKey) {
            return Direction.UP;
        }
        if (java.lang.Character.toLowerCase(input.charAt(0)) == rightKey) {
            return Direction.RIGHT;
        }
        if (java.lang.Character.toLowerCase(input.charAt(0)) == inventoryKey){
            return Direction.INVENTORY;
        }
        if (java.lang.Character.toLowerCase(input.charAt(0)) == useKey){
            return Direction.USE;
        }
        if (java.lang.Character.toLowerCase(input.charAt(0)) == downKey) {
            return Direction.DOWN;
        }
        if (java.lang.Character.toLowerCase(input.charAt(0)) == exitKey) {
            return Direction.EXIT;
        }
        if (java.lang.Character.toLowerCase(input.charAt(0)) == restartKey) {
            return Direction.RESTART;
        }

        return Direction.UNKNOWN;
    }

    /**
     * Ask an input from the user and return the direction
     * @return the direction given by the user
     */
    public static Direction getDirection(){
        return Rule.keyBinding(Rule.getInput());
    }

    /**
     * The tore mecanic (donut shaped map)
     * @param level the level we want to use the tore in
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
        if (level.getLevel()[newPlayer.getY()][newPlayer.getX()].getType() == CellType.TRAP){
            System.out.println("\u001B[31mYou fell into a trap !\u001B[0m");
            level.getPlayer().removeHealth(2);

            level.getLevel()[newPlayer.getY()][newPlayer.getX()].setType(CellType.EMPTY);       // Delete the trap

            level.resetEnemies();        // Resets the entities' positions
            newPlayer.setX(level.getStartPlayer().getX());
            newPlayer.setY(level.getStartPlayer().getY());
        }
    }

    /**
     * Return the next path to take to get, from source to the target
     * @param level The level we move ins
     * @param source The source probably the hunter's position
     * @param target The target usually the player
     * @param character The character to check the collision
     * @return the next step as a position
     */
    public static Position shortestPath(Level level, Position source, Position target, world_5.characters.Character character){
        boolean[][] visited = new boolean[level.getHeight()][level.getWidth()];
        HashMap<Position, Position> path = new HashMap<>();
        ArrayList<Position> queue = new ArrayList<Position>();

        visited[source.getY()][source.getX()] = true;     // Initialisation
        queue.add(source);
        path.put(source, null);

        int[][] directions = {      // directions of the directions available for the source (neighbour)
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

                if (level.isAccessible(next, character) && !visited[next.getY()][next.getX()]){       // Adds a new step
                    visited[next.getY()][next.getX()] = true;
                    path.put(next,current);
                    queue.add(next);
                }
            }
        }
        if (!path.containsKey(target)){       // The target and source aren't connected => no movements
            return source;
        }

        Position step = target;
        while (path.get(step) != null && !path.get(step).equals(source)){       // From target to "just before source"
            step = path.get(step);
        }

        return step;
    }
}