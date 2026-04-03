/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2
 */

package world_5.environnement;

import world_5.characters.*;
import world_5.types.*;
import world_5.exceptions.*;

import java.nio.file.*;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

/**
 * Level class
 * The coordonates are (0,0) from the top rigth corner, and (x,y) x is the horizontal, and y the vertical
 */
public class Level{
    private int width;
    private int height;
    private Cell[][] level;
    private List<Enemy> enemies;
    private Set<Cell> enemyCells;
    private Player player;
    private int nbCoins;
    private Position startPlayer;

    private static String CUR = System.getProperty("user.dir");

    /**
     * Constructor method
     * @param width size of the x coordonate
     * @param height size of the y coordonate
     * @param structs a list (can be empty) of structures to add to the level
     * @param enemies the list of enemies in the level  
     * @param player The player with the name and the score
     * @param playerX the x coordinate of the player
     * @param playerY the y coordinate of the player
     */
    public Level(int width, int height, Structure[] structs, List<Enemy> enemies, Player player, int playerX, int playerY){
        if (width > 0 && height > 0){
            this.width = width;
            this.height = height;
            this.nbCoins = 0;
            this.enemies = enemies;
            this.enemyCells = new HashSet<Cell>();

            this.level = new Cell[this.height][this.width];

            for (int i=0;i<this.height;i++){        // Creates all cells as empty ones
                for (int j=0;j<this.width;j++){
                    this.level[i][j] = new Cell(new Position(j,i),false,CellType.EMPTY);
                }
            }

            for (int i=0;i<structs.length; i++){            // Fill the structures
                if (this.isInLevel(structs[i])){
                    for (int j=0;j<structs[i].getHeight();j++){
                        for (int k=0;k<structs[i].getWidth();k++){
                            switch(structs[i].getType()){
                                case 0:             // If it's a wall
                                    if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() == CellType.EMPTY && !this.level[j+structs[i].getY()][k+structs[i].getX()].getCoin()){
                                        this.level[j+structs[i].getY()][k+structs[i].getX()].setType(CellType.WALL);
                                    }
                                    break;
                                // Note : structure.type 1 is coins while cell.type 1 is empty that way all other type are the same and structure 1 and cell 1 is different
                                case 1:             // If it's coins
                                    if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() != CellType.WALL && !this.level[j+structs[i].getY()][k+structs[i].getX()].getCoin()){       // Coins can be anywhere except walls and can't count a coin twice
                                        this.nbCoins += 1;
                                        this.level[j+structs[i].getY()][k+structs[i].getX()].addCoin();
                                    }
                                    break;
                                case 2:         // If it's a trap
                                    if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() == CellType.EMPTY){
                                        this.level[j+structs[i].getY()][k+structs[i].getX()].setType(CellType.TRAP);
                                    }
                                    break;
                                case 3:         // If it's a locked door
                                    if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() == CellType.EMPTY){
                                        this.level[j+structs[i].getY()][k+structs[i].getX()].setType(CellType.DOOR);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
            Iterator<Enemy> iterator = this.enemies.iterator();
            while (iterator.hasNext()){
                Enemy foe = iterator.next();
                enemyCells.add(this.level[foe.getCoord().getY()][foe.getCoord().getX()]);
            }
            
            if (!isAvailable(new Position(playerX,playerY))){      // Player not in map
                System.out.println(playerX + " " + playerY + " " + this.level[playerY][playerX].getType().name());
                throw new PlayerOutOfBoundsException("Creation of the level impossible : player out of the map or in a wall");
            }
            else if (player == null){       // Player not given
                throw new PlayerOutOfBoundsException("The player not given (null)");
            }
            else{       // Fill the player
                this.startPlayer = new Position(playerX,playerY);
                this.player = player;
                this.player.moveTo(playerX,playerY);
            }
        }
    }

    /**
     * Consctructor from the text file
     * Format of the file :
     * NB_STRUCT n
     * n STRUCT INFO (width height x y)
     * LEVEL INFO (width height playerX playerY)
     * 
     * @param file The file path in the directory files
     * @param p1 The player. It doesn't have to be created with the level, it is given so the player can stay the same in different levels
     * @return a level object based on the info of the file
     * @throws IOException If the format read is not valid
     * @throws FileNotFoundException If the file given isn't there
     */
    public static Level getLevelFromFile(String file, Player p1) throws FileNotFoundException, IOException{
        Path p = Paths.get(CUR+"/files/"+file);
        Structure[] structLevel = null;
        List<Enemy> enemies = new ArrayList<Enemy>();

        int width = -1;
        int height = -1;
        int playerX = -1;
        int playerY = -1;

        int section = 0;
        int subSection = 0;

        if (Files.exists(p) && Files.isRegularFile(p) && Files.isReadable(p)){
            try{
                List<String> lignes = Files.readAllLines(p);
                for (String ligne : lignes) {
                    if (ligne.isEmpty()){
                        section += 1;
                        subSection = 0;
                    }
                    else{
                        switch (section){
                            case 0:     // nbStructures
                                structLevel = new Structure[Integer.parseInt(ligne)];
                                if (Integer.parseInt(ligne) == 0){
                                    section += 1;
                                }
                                break;
                            case 1:     // Structures info
                                String[] structInfo = ligne.split(" ");
                                if (structInfo.length == 5){
                                    structLevel[subSection] = new Structure(Integer.parseInt(structInfo[0]),Integer.parseInt(structInfo[1]),Integer.parseInt(structInfo[2]),Integer.parseInt(structInfo[3]),Integer.parseInt(structInfo[4]));
                                    subSection += 1;
                                }
                                break;
                            case 2:
                                String[] enemiesInfo = ligne.split(" ");
                                if (enemiesInfo.length == 5){
                                    switch (Integer.parseInt(enemiesInfo[4])){
                                        case 0:
                                            enemies.add(new Zombie(enemiesInfo[0], new Position(Integer.parseInt(enemiesInfo[1]),Integer.parseInt(enemiesInfo[2])),Integer.parseInt(enemiesInfo[3])));
                                            break;
                                        case 1:
                                            enemies.add(new Ghost(enemiesInfo[0], new Position(Integer.parseInt(enemiesInfo[1]),Integer.parseInt(enemiesInfo[2])),Integer.parseInt(enemiesInfo[3])));
                                            break;
                                        case 2:
                                            enemies.add(new Hunter(enemiesInfo[0], new Position(Integer.parseInt(enemiesInfo[1]),Integer.parseInt(enemiesInfo[2])),Integer.parseInt(enemiesInfo[3])));
                                            break;
                                            
                                        default:            // The default type
                                            enemies.add(new Zombie(enemiesInfo[0], new Position(Integer.parseInt(enemiesInfo[1]),Integer.parseInt(enemiesInfo[2])),Integer.parseInt(enemiesInfo[3])));
                                            break;
                                    }
                                }
                                break;
                            case 3:     // Level info
                                String[] levelInfo = ligne.split(" ");
                                if (levelInfo.length == 4){
                                width = Integer.parseInt(levelInfo[0]);
                                height = Integer.parseInt(levelInfo[1]);
                                playerX = Integer.parseInt(levelInfo[2]);
                                playerY = Integer.parseInt(levelInfo[3]);
                                }
                                break;
                        }
                    }
                }
                if (structLevel != null && p1 != null){
                    return new Level(width, height, structLevel, enemies, p1, playerX, playerY);
                }
            } catch(IOException e){
                System.out.println(e.getMessage());
                throw new IOException(e.getMessage());
            }
        }
        else{
            throw new FileNotFoundException("File not found");
        }
        return null;
    }

    /**
     * Returns the height of the structure
     * @return the height
    */
    public int getHeight(){
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
     * Returns the nbCoins of the structure
     * @return the nbCoins
    */
    public int getNbCoins(){
        return this.nbCoins;
    }

    /**
     * Get the level as cells
     * @return the level
     */
    public Cell[][] getLevel(){
        return this.level;
    }

    public List<Enemy> getEnemies(){
        return this.enemies;
    }

    public Set<Cell> getEnemyCells(){
        return this.enemyCells;
    }

    /**
     * Get the position of the start coordinate of the player
     * @return The starting position of the player
     */
    public Position getStartPlayer(){
        return this.startPlayer;
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
                level.append(Rule.cellChar(this.level[i][j], this));
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
                level.append(" \u001B[31m❤︎⁠\u001B[0m ");
            }
        }
        level.append('\n');
        level.append("x: " + this.player.getCoord().getX() + " y: " + this.player.getCoord().getY() + " | coins left : \u001B[33m"+ this.getNbCoins() + "⁠\u001B[0m\n");
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
                winScreen.append(" \u001B[31m❤︎⁠\u001B[0m ");
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
                winScreen.append(" \u001B[31m❤︎\u001B[0m⁠ ");
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
     * Check if the structure can be fitted inside the level
     * @param struct The structure to check
     * @return true if it can be in the level, false if not
     */
    public boolean isInLevel(Structure struct){
        boolean condition = ((struct.getX() >= 0) && (struct.getX() <= this.width) && (struct.getY() >= 0) && (struct.getY() <= this.height) &&(struct.getX() + struct.getWidth() >= 0) && (struct.getX() + struct.getWidth() <= this.width) && (struct.getY() + struct.getHeight() >= 0) && (struct.getY() + struct.getHeight() <= this.height));        // x, x+width, y et y+height sont dans les bornes
        return condition ? true : false;
    }

    /**
     * Checks the space for movePlayer functions
     * @param coord The coordinate of the cell
     * @return true if the player can move to the space (x,y)
     */
    public boolean isAvailable(Position coord){
        return (coord.getX() >= 0 && coord.getX() < this.width && coord.getY() >= 0 && coord.getY() < this.height && !this.level[coord.getY()][coord.getX()].getCollision()) ? true : false;
    }

    /**
     * Checks the space for moveEnemy functions
     * @param coord The coordinate of the cell
     * @param enemy checks if the ennemy collids with the walls
     * @return true if the player can move to the space (x,y)
     */
    public boolean isAvailable(Position coord, Enemy enemy){
        return (coord.getX() >= 0 && coord.getX() < this.width && coord.getY() >= 0 && coord.getY() < this.height && enemy.canMove(this.level[coord.getY()][coord.getX()])) ? true : false;
    }

    /**
     * Resets all the enemies when the player is hurt
     */
    public void resetEnemies(){
        Iterator<Enemy> iterator = this.enemies.iterator();
        this.enemyCells.clear();
        while (iterator.hasNext()){
            Enemy enemy = iterator.next();
            enemy.resetPosition();
            this.enemyCells.add(this.level[enemy.getCoord().getY()][enemy.getCoord().getX()]);
        }
    }

    /**
     * Moves the player up, down, left, or rigth based on the direction
     * Checks if it can move there, updates the map, and update the score if it went on a coin
     * @param direction the direction from the enum class Direction
     */
    public void move(Direction direction){
    
    }

    public Position handleInput(){
        Position newPlayer = this.player.getCoord().clone();
        Position oldPlayer = this.player.getCoord().clone();
        Direction direction = Rule.getDirection();
        boolean validInput = false;

        switch (direction){
            case Direction.LEFT:
                newPlayer.addX(-1);
                validInput = true;
                break;
            case Direction.UP:
                newPlayer.addY(-1);
                validInput = true;
                break;
            case Direction.RIGHT:
                newPlayer.addX(1);
                validInput = true;
                break;
            case Direction.DOWN:
                newPlayer.addY(1);
                validInput = true;
                break;
            case Direction.EXIT:
                System.out.println("Exiting...");
                return null;
            default:
                System.out.println("Input invalid");
                break;
        }

        Rule.tore(this,newPlayer);

        if (validInput && isAvailable(newPlayer)){
            this.player.moveTo(newPlayer.getX(),newPlayer.getY());
        }

        return oldPlayer;
    }

    public void update(Position oldPlayer){
        boolean trap = false;
        boolean validInput = !this.player.getCoord().equals(oldPlayer);

        if (this.level[this.player.getCoord().getY()][this.player.getCoord().getX()].getCoin() && this.nbCoins > 0){      // Get coin
            nbCoins -= 1;
            Rule.collectCoin(this,this.player.getCoord());
        }
        if (this.level[this.player.getCoord().getY()][this.player.getCoord().getX()].getType() == CellType.TRAP && this.player.getHealthPoint() > 0){         // Get on a trap
            trap = true;
            Rule.activateTrap(this,this.player.getCoord());
        }


        if (!trap){     // If not a trap
            this.enemyCells.clear();
            Iterator<Enemy> iterator = this.enemies.iterator();
            while (iterator.hasNext()){         // Check if enemy hits whether the player moved or not
                Enemy enemy = iterator.next();
                Position old = enemy.getCoord().clone();
                enemy.move(this);
                enemyCells.add(this.level[enemy.getCoord().getY()][enemy.getCoord().getX()]);

                if ((player.getCoord().equals(enemy.getCoord())) || (old.equals(player.getCoord()) && oldPlayer.equals(enemy.getCoord()) && validInput && isAvailable(this.player.getCoord()))){
                    enemy.enemyHit(this.player);
                    this.resetEnemies();
                    break;
                }
            }
        }
    }
}