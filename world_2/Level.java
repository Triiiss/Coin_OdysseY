/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.0
 */

package world_2;

import java.nio.file.*;
import java.io.FileNotFoundException;
import java.util.List;
import java.io.IOException;

/**
 * Level class
 * The coordonates are (0,0) from the top rigth corner, and (x,y) x is the horizontal, and y the vertical
 */
public class Level{
    private int width;
    private int height;
    private char[][] level;
    private Player player;
    private int playerX;
    private int playerY;

    private static String CUR = System.getProperty("user.dir");

    /**
     * Constructor method
     * @param width size of the x coordonate
     * @param height size of the y coordonate
     * @param structs a list (can be empty) of structures to add to the level
     * @param player The player with the name and the score
     * @param playerX the x coordinate of the player
     * @param playerY the y coordinate of the player
     */
    public Level(int width, int height, Structure[] structs, Player player, int playerX, int playerY){
        if (width > 0 && height > 0){
            this.width = width;
            this.height = height;

            this.level = new char[this.height][this.width];

            for (int i=0;i<this.height;i++){        // Fill spaces
                for (int j=0;j<this.width;j++){
                    this.level[i][j] = ' ';
                }
            }

            for (int i=0;i<structs.length; i++){            // Fill the structures
                if (this.isinLevel(structs[i])){
                    for (int j=0;j<structs[i].getheight();j++){
                        for (int k=0;k<structs[i].getWidth();k++){
                            this.level[j+structs[i].getY()][k+structs[i].getX()] = '#';
                        }
                    }
                }
            }
            if (!isAvailable(playerX,playerY)){      // Fills the player
                throw new PlayerOutOfBoundsException("Creation of the level impossible : player out of the map or in a wall, or not given");
            }
            else if (player == null){
                throw new PlayerOutOfBoundsException("The player cannot be null");
            }
            else{
                this.playerX = playerX;
                this.playerY = playerY;
                this.player = player;

                this.level[playerY][playerX] = '1';
            }
        }
    }

    /**
     * Consctructor from the text file
     * Format of the file :
     * NAME
     * NB_STRUCT n
     * n STRUCT INFO (width height x y)
     * LEVEL INFO (width height playerX playerY)
     * 
     * @param file The file path in the directory files
     * @return a level object based on the info of the file
     * @throws FileNotFoundException If the file given isn't there
     */
    public static Level getLevelFromFile(String file) throws FileNotFoundException{
        Path p = Paths.get(CUR+"/files/"+file);

        Player p1 = null;
        Structure[] structLevel = null;

        int width = -1;
        int height = -1;
        int playerX = -1;
        int playerY = -1;

        int section = 0;
        int subSection = 0;

        if (Files.exists(p) && Files.isRegularFile(p) && Files.isReadable(p)){
            try {
                List<String> lignes = Files.readAllLines(p);
                for (String ligne : lignes) {
                    if (ligne.isEmpty()){
                        section += 1;
                    }
                    else{
                        switch (section){
                            case 0:     // Player
                                p1 = new Player(ligne);
                                break;
                            case 1:     // nbStructures
                                structLevel = new Structure[Integer.parseInt(ligne)];
                                break;
                            case 2:     // Structures info
                                String[] structInfo = ligne.split(" ");
                                if (structLevel != null){
                                    structLevel[subSection] = new Structure(Integer.parseInt(structInfo[0]),Integer.parseInt(structInfo[1]),Integer.parseInt(structInfo[2]),Integer.parseInt(structInfo[3]));
                                    subSection += 1;
                                }
                                break;
                            case 3:     // Level info
                                String[] levelInfo = ligne.split(" ");
                                width = Integer.parseInt(levelInfo[0]);
                                height = Integer.parseInt(levelInfo[1]);
                                playerX = Integer.parseInt(levelInfo[2]);
                                playerY = Integer.parseInt(levelInfo[3]);
                                break;
                        }
                    }
                }
                if (structLevel != null || p1 != null){
                    return new Level(width, height, structLevel, p1, playerX, playerY);
                }
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
        else{
            throw new FileNotFoundException("File not found");
        }
        return null;
    }

    /**
     * Check if the structure can be fitted inside the level
     * @param struct The structure to check
     * @return true if it can be in the level, false if not
     */
    public boolean isinLevel(Structure struct){
        boolean condition = ((struct.getX() >= 0) && (struct.getX() <= this.width) && (struct.getY() >= 0) && (struct.getY() <= this.height) &&(struct.getX() + struct.getWidth() >= 0) && (struct.getX() + struct.getWidth() <= this.width) && (struct.getY() + struct.getheight() >= 0) && (struct.getY() + struct.getheight() <= this.height));        // x, x+width, y et y+height sont dans les bornes
        return condition ? true : false;
    }

    /**
     * Checks the space for movePlayer functions
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the player can move to the space (x,y)
     */
    public boolean isAvailable(int x, int y){
        if (x >= 0 && x < this.width && y >= 0 && y < this.height && this.level[y][x] == ' '){
            return true;
        }
        return false;
    }

    /**
     * Returns the height of the structure
     * @return the height
    */
    public int getheight(){
        return this.height;
    }

    /**
     * Returns the width of the structure
     * @return the width
    */
    public int getWidth(){
        return this.width;
    }

    /**
     * Returns the player of the structure
     * @return the player
    */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Returns the playerX of the structure
     * @return the playerX
    */
    public int getPlayerX(){
        return this.playerX;
    }

    /**
     * Returns the playerY of the structure
     * @return the playerY
    */
    public int getPlayerY(){
        return this.playerY;
    }

    /**
     * Displays the map and the structures within
     */
    @Override
    public String toString(){
        StringBuilder level = new StringBuilder();

        for (int j = 0; j < this.width+2; j++) {
            level.append('#');
        }
        level.append("\n");

        for (int i = 0; i < this.height; i++) {
            level.append('#');
            for (int j = 0; j < this.width; j++) {
                level.append(this.level[i][j]);
            }
            level.append("#\n");
        }

        for (int j = 0; j < this.width + 2; j++) {
            level.append('#');
        }
        level.append("\n");

        level.append(this.player.toString() + '\n');
        level.append("x: " + this.getPlayerX() + " y: " + this.getPlayerY() + " | score : " + this.player.getScore() +"\n");
        level.append("Z: Up | Q: Right | S: Down | D: Left | N: exit");

        return level.toString();
    }

    /**
     * Moves the player left
     */
    public void movePlayerLeft(){
        if (this.isAvailable(this.playerX - 1, this.playerY)){
            this.level[playerY][playerX] = ' ';
            this.playerX -= 1;
            this.level[playerY][playerX] = '1';
        }
    }

    /**
     * Moves the player up
     */
    public void movePlayerUp(){
        if (this.isAvailable(this.playerX, this.playerY - 1)){
            this.level[playerY][playerX] = ' ';
            this.playerY -= 1;
            this.level[playerY][playerX] = '1';
        }
    }

    /**
     * Moves the player rigth
     */
    public void movePlayerRigth(){
        if (this.isAvailable(this.playerX + 1, this.playerY)){
            this.level[playerY][playerX] = ' ';
            this.playerX += 1;
            this.level[playerY][playerX] = '1';
        }
    }

    /**
     * Moves the player down
     */
    public void movePlayerDown(){
        if (this.isAvailable(this.playerX, this.playerY + 1)){
            this.level[playerY][playerX] = ' ';
            this.playerY += 1;
            this.level[playerY][playerX] = '1';
        }
    }
}