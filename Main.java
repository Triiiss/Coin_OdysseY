/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_3;

import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Main class
 */
public class Main{
    /**
     * Empty constructor
     */
    private Main(){
        // Empty constructor
    }

    /**
     * Main method
     * @param args the string of arguments
     */
    public static void main(String[] args){
        if (args.length >= 1){       // If no arguments, error message
            String file = args[0];

            try{
                Level firstLevel = Level.getLevelFromFile(file);

                Scanner sc = new Scanner(System.in);
                String label;
                Direction action = null;

                while (action != Direction.EXIT_KEY){
                    if (firstLevel.getPlayer().getHealthPoint() <= 0){
                        System.out.println(firstLevel.displayGameOver());
                        
                        label = sc.next();
                        action = Direction.stringToDirection(label);
                        switch(action){
                            case Direction.EXIT_KEY:
                                System.out.println("Exiting...");
                                break;
                            case Direction.RESTART_KEY:
                                firstLevel = null;
                                firstLevel = Level.getLevelFromFile(file);
                                break;
                            case null:
                                System.out.println("Input invalid");
                                break;
                            default:
                                System.out.println("Input invalid");
                                break;
                        }
                    }
                    else if (firstLevel.getNbCoins() <= 0){
                        System.out.println(firstLevel.displayWin());
                        break;
                    }
                    else{
                        System.out.println(firstLevel.toString());
                        
                        label = sc.next();
                        action = Direction.stringToDirection(label);
                        firstLevel.movePlayer(action);
                    }
                }
            } catch (FileNotFoundException e){
                System.err.println(e.getMessage());
            }
        }
        else{
            System.out.println("\nTo play a level, use the command : java -jar exec.jar [FILE_NAME]\n[FILE_NAME] is a file placed in the directory files (no need to do files/[FILE_NAME], just [FILE_NAME]) of a level, like pacMan.txt or mario.txt\n");
        }
    }

}