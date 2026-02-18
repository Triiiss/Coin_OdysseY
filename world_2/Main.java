/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_2;

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

                /*firstLevel.display();     // Bouger manuellement pour voir si ça marche
                System.out.println("x: " + firstLevel.getPlayerX() + " y: " + firstLevel.getPlayerY());
                firstLevel.movePlayerLeft();
                firstLevel.display();
                System.out.println("x: " + firstLevel.getPlayerX() + " y: " + firstLevel.getPlayerY());
                firstLevel.movePlayerUp();
                firstLevel.display();
                System.out.println("x: " + firstLevel.getPlayerX() + " y: " + firstLevel.getPlayerY());*/

                Scanner sc = new Scanner(System.in);
                String label;
                Direction action = null;

                while (action != Direction.EXIT_KEY){
                    firstLevel.display();
                    System.out.println("x: " + firstLevel.getPlayerX() + " y: " + firstLevel.getPlayerY());
                    System.out.println("Z: Up | Q: Right | S: Down | D: Left | N: exit");
                    
                    label = sc.next();
                    action = Direction.stringToDirection(label);
                    switch (action){
                        case Direction.LEFT_KEY:
                            firstLevel.movePlayerLeft();
                            break;
                        case Direction.UP_KEY:
                            firstLevel.movePlayerUp();
                            break;
                        case Direction.RIGHT_KEY:
                            firstLevel.movePlayerRigth();
                            break;
                        case Direction.DOWN_KEY:
                            firstLevel.movePlayerDown();
                            break;
                        case Direction.EXIT_KEY:
                            System.out.println("Exiting...");
                            continue;
                        case null:
                            System.out.println("Input invalid");
                            break;
                        default:
                            System.out.println("Input invalid");
                            break;
                    }
                }
            } catch (FileNotFoundException e){
                System.err.println(e.getMessage());
            } catch (PlayerOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
        }
        else{
            System.out.println("To play a level, use the command : java -jar exec.jar [FILE_NAME]\n[FILE_NAME] is a file placed in the directory files (no need to do files/[FILE_NAME], just [FILE_NAME]) of a level, like pacMan.txt or mario.txt");
        }
    }

}