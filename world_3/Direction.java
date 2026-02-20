/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_3;

/**
 * Enumeration of the keys zqsd and exit n (because it's far from the zqsd to avoid missclicks)
 */
public enum Direction{
    /**
     * Key to go left made with the letter Q
     */
    LEFT_KEY("q"),
    /**
     * Key to go up made with the letter Z
     */
    UP_KEY("z"),
    /**
     * Key to go rigth made with the letter D
     */
    RIGHT_KEY("d"),
    /**
     * Key to go down made with the letter S
     */
    DOWN_KEY("s"),
    /**
     * Key to go exit made with the letter N
     */
    EXIT_KEY("n"),
    /**
     * Key to go restart after a game over made with the letter N
     */
    RESTART_KEY("r");

    private final String label;

    /**
     * Allows to give values to the constants
     * @param label The label
     */
    private Direction(String label) {
        this.label = label;
    }
    
    /**
     * Takes a string (input from the user) and translates it into a value from the enum
     * @param label the string of letter input from the user
     * @return The letter if the constant if the key is linked to a letter
     */
    public static Direction stringToDirection(String label) {
        for (Direction action : Direction.values()) {
            if (action.label.equalsIgnoreCase(label)) {
                return action;
            }
        }
        return null;
    }
}