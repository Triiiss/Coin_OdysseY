/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 * @TODO
 * Comportemental
 * Position dans le player      check
 * Position en objet            check
 * enum type CellType           check
 * bool collision               check
 */

package world_3;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

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
            System.out.println("What is your name?");       // Gets player name and creates player
            String playerName = Rule.getInput();
            Player player = new Player(playerName);

            Level[] game = new Level[args.length];          // All levels

            try{
                Direction action = null;
                int iLevel = 0;
                
                while (action != Direction.EXIT){
                    if (iLevel >= args.length){          // Winning game
                        System.out.println(game[iLevel-1].displayWin());
                        break;
                    }
                    else if (game[iLevel] == null){                  // Creation of the level
                        game[iLevel] = Level.getLevelFromFile(args[iLevel], player);
                    }
                    else if (Rule.gameOver(game[iLevel])){        // Lost game
                        System.out.println(game[iLevel].displayGameOver());
                        
                        action = Rule.getDirection();
                        switch(action){
                            case Direction.EXIT:
                                System.out.println("Exiting...");
                                break;
                            case Direction.RESTART:
                                game[iLevel] = null;
                                player.reset();         // Resets the player's info
                                game[iLevel] = Level.getLevelFromFile(args[iLevel],player);
                                break;
                            default:
                                System.out.println("Input invalid");
                                break;
                        }
                    }
                    else if (Rule.levelComplete(game[iLevel])){       // Winning level
                        System.out.println(game[iLevel].displayLevelComplete());
                        iLevel += 1;
                        Rule.getInput();
                    }
                    else{               // The actual game
                        System.out.println(game[iLevel].toString());
                        
                        action = Rule.getDirection();
                        game[iLevel].movePlayer(action);
                    }
                }
            } catch (FileNotFoundException e){
                System.err.println(e.getMessage());
            } catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        else{
            System.out.println("\nTo play a level, use the command : java -jar exec.jar [FILE_NAME]\n[FILE_NAME] is a file placed in the directory files (no need to do files/[FILE_NAME], just [FILE_NAME]) of a level, like pacMan.txt or mario.txt\n");
        }
    }

}