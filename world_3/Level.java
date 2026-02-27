/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_3;

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
    private Cell[][] level;
    private Player player;
    private int playerX;
    private int playerY;
    private int nbCoins;
    private int startPlayerX;
    private int startPlayerY;

    private static String CUR = System.getProperty("user.dir");     // no need

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
            this.nbCoins = 0;

            this.level = new Cell[this.height][this.width];

            for (int i=0;i<this.height;i++){        // Creates all cells as empty ones
                for (int j=0;j<this.width;j++){
                    this.level[i][j] = new Cell(j,i,false,1);
                }
            }

            for (int i=0;i<structs.length; i++){            // Fill the structures
                if (this.isInLevel(structs[i])){
                    switch(structs[i].getType()){
                        case 0:             // If it's a wall
                            for (int j=0;j<structs[i].getheight();j++){
                                for (int k=0;k<structs[i].getWidth();k++){
                                    if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() == 1 && !this.level[j+structs[i].getY()][k+structs[i].getX()].getCoin()){
                                        this.level[j+structs[i].getY()][k+structs[i].getX()].setType(0);
                                    }
                                }
                            }
                            break;
                        // Note : structure.type 1 is coins while cell.type 1 is empty that way all other type are the same and structure 1 and cell 1 is different
                        case 1:             // If it's coins
                            for (int j=0;j<structs[i].getheight();j++){
                                for (int k=0;k<structs[i].getWidth();k++){
                                    if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() != 0 && !this.level[j+structs[i].getY()][k+structs[i].getX()].getCoin()){       // Coins can be anywhere except walls and can't count a coin twice
                                        this.nbCoins += 1;
                                        this.level[j+structs[i].getY()][k+structs[i].getX()].addCoin();
                                    }
                                }
                            }
                            break;
                        case 2:         // If it's a trap
                            for (int j=0;j<structs[i].getheight();j++){
                                for (int k=0;k<structs[i].getWidth();k++){
                                    if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() == 1){
                                        this.level[j+structs[i].getY()][k+structs[i].getX()].setType(2);
                                    }
                                }
                            }
                            break;
                        case 3:         // If it's a locked door
                            for (int j=0;j<structs[i].getheight();j++){
                                for (int k=0;k<structs[i].getWidth();k++){
                                    if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() == 1){
                                        this.level[j+structs[i].getY()][k+structs[i].getX()].setType(3);
                                    }
                                }
                            }
                            break;
                    }
                }
            }
            if (!isAvailable(playerX,playerY)){      // Fills the player
                System.out.println(playerX + " " + playerY + " " + this.level[playerY][playerX].getType());
                throw new PlayerOutOfBoundsException("Creation of the level impossible : player out of the map or in a wall");
            }
            else if (player == null){
                throw new PlayerOutOfBoundsException("The player not given (null)");
            }
            else{
                this.playerX = playerX;
                this.playerY = playerY;
                this.startPlayerX = playerX;
                this.startPlayerY = playerY;
                this.player = player;
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
     */
    public static Level getLevelFromFile(String file, Player p1) throws FileNotFoundException{
        Path p = Paths.get(CUR+"/files/"+file);
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
                            case 0:     // nbStructures
                                structLevel = new Structure[Integer.parseInt(ligne)];
                                break;
                            case 1:     // Structures info
                                String[] structInfo = ligne.split(" ");
                                if (structLevel != null){
                                    structLevel[subSection] = new Structure(Integer.parseInt(structInfo[0]),Integer.parseInt(structInfo[1]),Integer.parseInt(structInfo[2]),Integer.parseInt(structInfo[3]),Integer.parseInt(structInfo[4]));
                                    subSection += 1;
                                }
                                break;
                            case 2:     // Level info
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
    public boolean isInLevel(Structure struct){
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
        if (x >= 0 && x < this.width && y >= 0 && y < this.height && !this.level[y][x].hasCollision()){
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
     * Returns the nbCoins of the structure
     * @return the nbCoins
    */
    public int getNbCoins(){
        return this.nbCoins;
    }

    /**
     * Displays the map and the structures within
     * @return The string to print out of the map, its edges and the UI
     */
    @Override
    public String toString(){
        StringBuilder level = new StringBuilder();

        for (int j = 0; j < this.width+2; j++) {
            level.append('#');
        }
        level.append('\n');

        for (int i = 0; i < this.height; i++) {
            level.append('#');
            for (int j = 0; j < this.width; j++) {
                level.append(this.level[i][j].getTypeChar(this.playerX,this.playerY));
            }
            level.append("#\n");
        }

        for (int j = 0; j < this.width + 2; j++) {
            level.append('#');
        }
        level.append("\n");

        level.append(this.player.toString() + " | ");
        for (int h=1; h<=this.player.getMaxHealth();h++){
            if (h > this.player.getHealthPoint()){
                level.append(" ♡ ");
            }
            else{
                level.append(" ❤︎⁠ ");
            }
        }
        level.append('\n');
        level.append("x: " + this.getPlayerX() + " y: " + this.getPlayerY() + " | coins left : "+ this.getNbCoins() + "\n");
        level.append("Z: Up | Q: Right | S: Down | D: Left | N: exit");

        return level.toString();
    }

    /**
     * Prints out the outer layer of the level and "LEVEL COMPLETE"
     * Usually printed out when all the coins a gathered
     * @return the winning screen as a string to print out
     */
    public String displayLevelComplete(){
        StringBuilder winScreen = new StringBuilder();

        for (int j=0;j<this.width+2;j++){
            winScreen.append('#');
        }
        winScreen.append('\n');

        for (int i=0;i<this.height/2;i++){
            winScreen.append('#');
            for (int j=0;j<this.width;j++){
                winScreen.append(' ');
            }
            winScreen.append('#');
            winScreen.append('\n');
        }

        winScreen.append('#');
        for (int j=0;j<(this.width/2)-7;j++){
            winScreen.append(' ');
        }
        winScreen.append("LEVEL COMPLETE");
        for (int j=0;j<(this.width/2)-7;j++){
            winScreen.append(' ');
        }
        if (this.width % 2 == 1){
            winScreen.append(' ');
        }
        winScreen.append('#');
        winScreen.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            winScreen.append('#');
            for (int j=0;j<this.width;j++){
                winScreen.append(' ');
            }
            winScreen.append('#');
            winScreen.append('\n');
        }
        for (int j=0;j<this.width+2;j++){
            winScreen.append('#');
        }
        winScreen.append('\n');
        winScreen.append(this.player.toString() + " | ");
        for (int h=1; h<=this.player.getMaxHealth();h++){
            if (h > this.player.getHealthPoint()){
                winScreen.append(" ♡ ");
            }
            else{
                winScreen.append(" ❤︎⁠ ");
            }
        }

        winScreen.append("\nPress any key to continue");

        return winScreen.toString();
    }

    /**
     * Prints out the outer layer of the level and "THANK YOU FOR PLAYING"
     * Usually printed out when all the levels are completed
     * @return the winning game screen as a string to print out
     */
    public String displayWin(){
        StringBuilder winScreen = new StringBuilder();

        for (int j=0;j<this.width+2;j++){
            winScreen.append('#');
        }
        winScreen.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            winScreen.append('#');
            for (int j=0;j<this.width;j++){
                winScreen.append(' ');
            }
            winScreen.append('#');
            winScreen.append('\n');
        }

        winScreen.append('#');
        for (int j=0;j<(this.width/2)-5;j++){
            winScreen.append(' ');
        }
        winScreen.append("THANK YOU");
        for (int j=0;j<(this.width/2)-4;j++){
            winScreen.append(' ');
        }
        if (this.width % 2 == 1){
            winScreen.append(' ');
        }
        winScreen.append('#');
        winScreen.append('\n');


        winScreen.append('#');
        for (int j=0;j<(this.width/2)-6;j++){
            winScreen.append(' ');
        }
        winScreen.append("FOR PLAYING");
        for (int j=0;j<(this.width/2)-5;j++){
            winScreen.append(' ');
        }
        if (this.width % 2 == 1){
            winScreen.append(' ');
        }
        winScreen.append('#');
        winScreen.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            winScreen.append('#');
            for (int j=0;j<this.width;j++){
                winScreen.append(' ');
            }
            winScreen.append('#');
            winScreen.append('\n');
        }
        for (int j=0;j<this.width+2;j++){
            winScreen.append('#');
        }
        winScreen.append('\n');
        winScreen.append(this.player.toString() + " | ");
        for (int h=1; h<=this.player.getMaxHealth();h++){
            if (h > this.player.getHealthPoint()){
                winScreen.append(" ♡ ");
            }
            else{
                winScreen.append(" ❤︎⁠ ");
            }
        }

        return winScreen.toString();
    }

    /**
     * Prints out the outer layer of the level and "GAME OVER"
     * Usually printed out when the health is negative or equal to 0
     * @return the losing screen as a string to print out
     */
    public String displayGameOver(){
        StringBuilder gameOverScreen = new StringBuilder();

        for (int j=0;j<this.width+2;j++){
            gameOverScreen.append('#');
        }
        gameOverScreen.append('\n');

        for (int i=0;i<this.height/2;i++){
            gameOverScreen.append('#');
            for (int j=0;j<this.width;j++){
                gameOverScreen.append(' ');
            }
            gameOverScreen.append('#');
            gameOverScreen.append('\n');
        }

        gameOverScreen.append('#');
        for (int j=0;j<(this.width/2)-4;j++){
            gameOverScreen.append(' ');
        }
        gameOverScreen.append("GAME OVER");
        for (int j=0;j<(this.width/2)-5;j++){
            gameOverScreen.append(' ');
        }
        if (this.width % 2 == 1){
            gameOverScreen.append(' ');
        }
        gameOverScreen.append('#');
        gameOverScreen.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            gameOverScreen.append('#');
            for (int j=0;j<this.width;j++){
                gameOverScreen.append(' ');
            }
            gameOverScreen.append('#');
            gameOverScreen.append('\n');
        }
        for (int j=0;j<this.width+2;j++){
            gameOverScreen.append('#');
        }
        gameOverScreen.append("\nR: restart the game | N: exit");

        return gameOverScreen.toString();
    }

    /**
     * Moves the player up, down, left, or rigth based on the direction
     * Checks if it can move there, updates the map, and update the score if it went on a coin
     * @param direction the direction from the enum class Direction
     */
    public void movePlayer(Direction direction){
        int newPlayerX = 0;
        int newPlayerY = 0;
        boolean validInput = true;
        switch (direction){
            case Direction.LEFT_KEY:
                newPlayerX = this.playerX - 1;
                newPlayerY = this.playerY;
                break;
            case Direction.UP_KEY:
                newPlayerX = this.playerX;
                newPlayerY = this.playerY - 1;
                break;
            case Direction.RIGHT_KEY:
                newPlayerX = this.playerX + 1;
                newPlayerY = this.playerY;
                break;
            case Direction.DOWN_KEY:
                newPlayerX = this.playerX;
                newPlayerY = this.playerY + 1;
                break;
            case Direction.EXIT_KEY:
                System.out.println("Exiting...");
                validInput = false;
                break;
            case null:
                System.out.println("Input invalid");
                validInput = false;
                break;
            default:
                System.out.println("Input invalid");
                validInput = false;
                break;
        }
        if (validInput){
            while (newPlayerX < 0){         // The tore system => adds a cycle to the coordinates
                newPlayerX += this.width;
            }
        
            while (newPlayerX >= this.width){
                newPlayerX -= this.width;
            }
            while (newPlayerY < 0){
                newPlayerY += this.height;
            }
            while (newPlayerY >= this.height){
                newPlayerY -= this.height;
            }

            if (isAvailable(newPlayerX,newPlayerY)){
                if (this.level[newPlayerY][newPlayerX].getCoin() && this.nbCoins > 0){
                    nbCoins -= 1;
                    this.player.addScore(10);
                    this.level[newPlayerY][newPlayerX].removeCoin();
                }
                if (this.level[newPlayerY][newPlayerX].getType() == 2 && this.player.getHealthPoint() > 0){
                    this.player.removeHealth(2);
                    this.level[newPlayerY][newPlayerX].setType(1);       // Delete the trap
                    newPlayerX = this.startPlayerX;
                    newPlayerY = this.startPlayerY;
                }

                this.playerX = newPlayerX;
                this.playerY = newPlayerY;
            }
        }
    }
}