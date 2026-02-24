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
            Scanner sc = new Scanner(System.in);

            System.out.println("What is your name?");       // Gets player name and creates player
            String playerName = sc.next();
            Player player = new Player(playerName);

            Level[] game = new Level[args.length];          // All levels

            try{
                String label;
                Direction action = null;
                int iLevel = 0;
                
                for (int i=0;i<args.length;i++){            // Creates all level
                    game[i] = Level.getLevelFromFile(args[i], player);
                }
                
                while (action != Direction.EXIT_KEY){
                    if (iLevel >= args.length){          // Winning game
                        System.out.println(game[iLevel-1].displayWin());
                        break;
                    }
                    else if (game[iLevel].getPlayer().getHealthPoint() <= 0){        // Lost game
                        System.out.println(game[iLevel].displayGameOver());
                        
                        label = sc.next();
                        action = Direction.stringToDirection(label);
                        switch(action){
                            case Direction.EXIT_KEY:
                                System.out.println("Exiting...");
                                break;
                            case Direction.RESTART_KEY:
                                game[iLevel] = null;
                                player.reset();         // Resets the player's info
                                game[iLevel] = Level.getLevelFromFile(args[iLevel],player);
                                break;
                            case null:
                                System.out.println("Input invalid");
                                break;
                            default:
                                System.out.println("Input invalid");
                                break;
                        }
                    }
                    else if (game[iLevel].getNbCoins() <= 0){       // Winning level
                        System.out.println(game[iLevel].displayLevelComplete());
                        iLevel += 1;
                        sc.next();
                    }
                    else{               // The actual game
                        System.out.println(game[iLevel].toString());
                        
                        label = sc.next();
                        action = Direction.stringToDirection(label);
                        game[iLevel].movePlayer(action);
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