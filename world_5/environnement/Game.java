/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_5.environnement;

import world_5.types.*;
import world_5.exceptions.*;
import world_5.characters.*;
import world_5.inventory.*;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The game class
 */
public class Game{
    Level[] levels;
    Player player;

    /**
     * Constructor method
     * @param args the list of arguments given in main (for the file management)
     */
    public Game(String[] args){
        if (args.length >= 1){       // If no arguments, error message
            System.out.println("What is your name?");       // Gets player name and creates player
            String playerName = Rule.getInput();
            this.player = new Player(playerName);

            this.levels = new Level[args.length];          // All levels

            try{
                boolean exit = false;
                int iLevel = 0;
                
                while (!exit){
                    if (iLevel >= args.length){          // Winning game
                        System.out.println(this.levels[iLevel-1].displayWin());
                        break;
                    }
                    else if (this.levels[iLevel] == null){                  // Creation of the level
                        this.levels[iLevel] = Level.getLevelFromFile(args[iLevel], player);
                        System.out.println(this.levels[iLevel].displayObjective());
                        Rule.getDirection();
                    }
                    else if (Rule.gameOver(this.levels[iLevel])){        // Lost game
                        System.out.println(this.levels[iLevel].displayGameOver());
                        
                        Direction action = Rule.getDirection();
                        switch(action){
                            case Direction.EXIT:
                                System.out.println("Exiting...");
                                exit = true;
                                break;
                            case Direction.RESTART:
                                this.levels[iLevel] = null;
                                player.reset();         // Resets the player's info
                                this.levels[iLevel] = Level.getLevelFromFile(args[iLevel],player);
                                break;
                            default:
                                System.out.println("Input invalid");
                                break;
                        }
                    }
                    else if (Rule.levelComplete(this.levels[iLevel])){       // Winning level
                        System.out.println(this.levels[iLevel].displayLevelComplete());
                        iLevel += 1;
                        Rule.getInput();
                    }
                    else if (this.levels[iLevel].getOpenInventory()){       // If the inventory is open
                        System.out.println(this.levels[iLevel].displayInventory());

                        if(this.levels[iLevel].handleInventory()){      // Use an element
                            this.levels[iLevel].getPlayer().getInventory().get(this.levels[iLevel].getPlayer().getInventoryIndex()).use(this.levels[iLevel]);
                        }
                    }
                    else{               // The actual this.levels
                        System.out.println(this.levels[iLevel].toString());     // Display of the game
                        
                        Position oldPlayer = this.levels[iLevel].handleInput();    // Move the player
                        if (oldPlayer == null){     // If the oldPlayer is null, the Player pressed the escape key or something is wrong. We get out
                            exit = true;
                            break;
                        }
                        if (this.levels[iLevel].getOpenInventory()){
                            continue;
                        }
                        this.levels[iLevel].updateMap(oldPlayer);        //Updates enemies, events (coins, trap)
                    }
                }
            } catch (FileNotFoundException e){
                System.err.println(e.getMessage());
            } catch (IOException e){
                System.err.println(e.getMessage());
            }
            catch (InvalidLevelException e){
                System.err.println(e.getMessage());
            }
        }
        else{
            System.out.println("\nTo play a level, use the command : java -jar exec.jar [FILE_NAME]\n[FILE_NAME] is a file placed in the directory files (no need to do files/[FILE_NAME], just [FILE_NAME]) of a level, like pacMan.txt or mario.txt\n");
        }
    }
}