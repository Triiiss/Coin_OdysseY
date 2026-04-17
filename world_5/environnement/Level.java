/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2
 */

package world_5.environnement;

import world_5.characters.Character;
import world_5.characters.*;
import world_5.types.*;
import world_5.exceptions.*;
import world_5.inventory.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Random;
import java.util.List;
import java.util.HashSet;

/**
 * Level class
 * The coordonates are (0,0) from the top rigth corner, and (x,y) x is the horizontal, and y the vertical
 */
public class Level{
    private int width;
    private int height;

    private Cell[][] level;
    private int nbCoins;
    private ObjectiveType type;

    private List<Enemy> enemies;
    private Set<Cell> enemyCells;
    private Player player;
    private Position startPlayer;

    private boolean openInventory;
    private int freeze;

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
     * @param type the type of the objective
     * @throws InvalidLevelException if the width and height are invalid
     */
    public Level(int width, int height, Structure[] structs,List<Enemy> enemies, Player player, int playerX, int playerY, ObjectiveType type) throws InvalidLevelException{
        if (width > 0 && height > 0){
            this.width = width;
            this.height = height;
            this.nbCoins = 0;
            this.type = type;
            this.freeze = 0;
            this.openInventory = false;
            this.enemies = enemies;
            this.enemyCells = new HashSet<Cell>();

            this.level = new Cell[this.height][this.width];

            for (int i=0;i<this.height;i++){        // Creates all cells as empty ones
                for (int j=0;j<this.width;j++){
                    this.level[i][j] = new Cell(new Position(j,i),CellType.EMPTY);
                }
            }

            for (int i=0;i<structs.length; i++){            // Fill the structures
                if (structs[i] != null){
                    
                    if (this.isInLevel(structs[i])){
                        for (int j=0;j<structs[i].getHeight();j++){
                            for (int k=0;k<structs[i].getWidth();k++){
                                switch(structs[i].getType()){
                                    case 0:             // If it's a wall
                                        if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() == CellType.EMPTY && !this.level[j+structs[i].getY()][k+structs[i].getX()].hasCoin()){
                                            this.level[j+structs[i].getY()][k+structs[i].getX()].setType(CellType.WALL);
                                        }
                                        break;
                                    case 1:         // If it's a trap
                                        if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() == CellType.EMPTY){
                                            this.level[j+structs[i].getY()][k+structs[i].getX()].setType(CellType.TRAP);
                                        }
                                        break;
                                    case 2:         // If it's a locked door
                                        if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() == CellType.EMPTY){
                                            this.level[j+structs[i].getY()][k+structs[i].getX()].setType(CellType.DOOR);
                                        }
                                        break;
                                    case 100:             // If it's coins
                                        if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() != CellType.WALL && !this.level[j+structs[i].getY()][k+structs[i].getX()].getHasItem()){       // Items can be anywhere except walls or write over other items
                                            this.nbCoins += 1;
                                            this.level[j+structs[i].getY()][k+structs[i].getX()].addItem(new Item("coin", ItemType.COIN));
                                        }
                                        break;
                                    case 101:
                                        if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() != CellType.WALL && !this.level[j+structs[i].getY()][k+structs[i].getX()].getHasItem()){
                                            this.level[j+structs[i].getY()][k+structs[i].getX()].addItem(new Item("Weapon",ItemType.WEAPON));
                                        }
                                        break;
                                    case 102:
                                        if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() != CellType.WALL && !this.level[j+structs[i].getY()][k+structs[i].getX()].getHasItem()){
                                            this.level[j+structs[i].getY()][k+structs[i].getX()].addItem(new Item("Hourglass",ItemType.HOURGLASS));
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }
            }
            Iterator<Enemy> iterator = this.enemies.iterator();
            while (iterator.hasNext()){
                Enemy foe = iterator.next();
                if (foe.getCoord().validPosition() && foe.getCoord().getY() < this.height && foe.getCoord().getX() < this.width){
                    enemyCells.add(this.level[foe.getCoord().getY()][foe.getCoord().getX()]);
                }
            }

            if (!this.isAccessible(new Position(playerX,playerY), null)){      // Player not in map
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
        else{
            throw new InvalidLevelException("The level's arguments are invalid");
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
     * @throws InvalidLevelException if the width or height aren't valid
     */
    public static Level getLevelFromFile(String file, Player p1) throws FileNotFoundException, IOException, InvalidLevelException{
        Path p = Paths.get(CUR+"/files/"+file);
        Structure[] structLevel = null;
        List<Enemy> enemies = new ArrayList<Enemy>();

        int width = -1;
        int height = -1;
        int playerX = -1;
        int playerY = -1;
        ObjectiveType type = ObjectiveType.COINS;

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
                                    try{
                                        structLevel[subSection] = new Structure(Integer.parseInt(structInfo[0]),Integer.parseInt(structInfo[1]),Integer.parseInt(structInfo[2]),Integer.parseInt(structInfo[3]),Integer.parseInt(structInfo[4]));
                                        subSection += 1;
                                    }
                                    catch (InvalidStructureException e){
                                        System.err.println(e.getMessage());
                                    }
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
                                if (levelInfo.length == 5){
                                    width = Integer.parseInt(levelInfo[0]);
                                    height = Integer.parseInt(levelInfo[1]);
                                    playerX = Integer.parseInt(levelInfo[2]);
                                    playerY = Integer.parseInt(levelInfo[3]);
                                    switch(Integer.parseInt(levelInfo[4])){
                                        case 0:
                                            type = ObjectiveType.COINS;
                                            break;
                                        case 1:
                                            type = ObjectiveType.ENEMIES;
                                            break;
                                    }
                                }
                                break;
                        }
                    }
                }
                if (structLevel != null && p1 != null){
                    try{
                        return new Level(width, height, structLevel, enemies, p1, playerX, playerY, type);   
                    } catch (InvalidLevelException e){
                        throw e;
                    }
                }
            } catch(IOException e){
                throw e;
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

    /**
     * Get the list of enemies in the level
     * @return the enemies
     */
    public List<Enemy> getEnemies(){
        return this.enemies;
    }

    /**
     * Get the set of cells enemies are in
     * @return the enemyCells
     */
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
     * Get if the player has an open inventory or not
     * @return the open inventory
     */
    public boolean getOpenInventory(){
        return this.openInventory;
    }

    /**
     * Get the time enemies are frozen
     * @return the freeze time
     */
    public int getFreeze(){
        return this.freeze;
    }

    /**
     * Get the type of the level
     * @return the level type
     */
    public ObjectiveType getType(){
        return this.type;
    }

    /**
     * Decreases the number of coins in the level by one
     * Used by pickUp in item to pick up a coin
     */
    public void decreaseNbCoin(){
        this.nbCoins--;
    }

    /**
     * Adds a time to freeze for enemies
     * @param time the amount of time (usually 10 w/ hourglass) we add
     */
    public void freezeEnemies(int time){
        if (time >= 0){
            this.freeze += time;
        }
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
                level.append("\u001B[31m ❤︎ ⁠\u001B[0m");
            }
        }
        level.append('\n');
        level.append("x: " + this.player.getCoord().getX() + " y: " + this.player.getCoord().getY());
        switch(this.type){
            case ObjectiveType.COINS:
                level.append(" | \u001B[33mcoins left : "+ this.nbCoins + "⁠\u001B[0m\n");
                break;
            case ObjectiveType.ENEMIES:
                level.append(" | \u001B[31menemies left : "+ this.enemies.size() + "⁠\u001B[0m\n");
                break;
        }
        level.append("Z: Up | Q: Right | S: Down | D: Left | N: exit");
        if (this.freeze > 0){
            level.append("  |  Enemies frozen for \u001B[36m" + this.freeze + " mov.\u001B[0m");
        }

        return level.toString();
    }

    /**
     * Displays the inventory UI
     * @return The string to print out the inventory, its edges and the UI
     */
    public String displayInventory(){
        String RESET = "\u001B[0m";
        String BLUE = "\u001B[94m";
        StringBuilder inventory = new StringBuilder();

        for (int j = 0; j < this.width+2; j++) {
            inventory.append('#');
        }
        inventory.append('\n');

        inventory.append('#');
        for (int j=0;j<this.width;j++){
            inventory.append(' ');
        }
        inventory.append('#');
        inventory.append('\n');

        for (int i=0;i<player.getInventory().getMaxInventory()*2;i++){
            if (i%2 == 1 || this.player.getInventory().getBag().size() <= i/2){
                inventory.append('#');
                for (int j=0;j<this.width;j++){
                    inventory.append(' ');
                }
                inventory.append('#');
                inventory.append('\n');
            }
            else if (this.player.getInventory().getIndex() == i/2){
                inventory.append("#" + BLUE + " * " + this.player.getInventory().getBag().get(i/2).getName() + " [USE]" + RESET );
                for (int j=0;j<this.width - 9 - this.player.getInventory().getBag().get(i/2).getName().length();j++){
                    inventory.append(" ");
                }
                inventory.append("#");
                inventory.append('\n');
            }
            else{
                inventory.append("#   " + this.player.getInventory().getBag().get(i/2).getName());
                for (int j=0;j<this.width - 3 - this.player.getInventory().getBag().get(i/2).getName().length();j++){
                    inventory.append(" ");
                }
                inventory.append("#");
                inventory.append('\n');
            }
        }

        inventory.append('#');
        for (int j=0;j<this.width;j++){
            inventory.append(' ');
        }
        inventory.append('#');
        inventory.append('\n');

        for (int j = 0; j < this.width+2; j++) {
            inventory.append('#');
        }
        inventory.append('\n');


        inventory.append(this.player.toString() + " | ");
        for (int h=1; h<=this.player.getMaxHealth();h++){
            if (h > this.player.getHealthPoint()){
                inventory.append(" ♡ ");
            }
            else{
                inventory.append("\u001B[31m ❤︎ ⁠\u001B[0m");
            }
        }
        inventory.append('\n');
        inventory.append("Z: Up | S: Down | U: Use | N or I: exit");
        if (this.freeze > 0){
            inventory.append("  |  Enemies frozen for \u001B[36m" + this.freeze + " mov.\u001B[0m");
        }

        return inventory.toString();
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
     * Prints out the outer layer of the level and "LEVEL COMPLETE"
     * Usually printed out when all the coins a gathered
     * @return the winning screen as a string to print out
     */
    public String displayObjective(){
        StringBuilder objective = new StringBuilder();

        for (int j=0;j<this.width+2;j++){
            objective.append('#');
        }
        objective.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            objective.append('#');
            for (int j=0;j<this.width;j++){
                objective.append(' ');
            }
            objective.append('#');
            objective.append('\n');
        }

        objective.append('#');
        for (int j=0;j<(this.width/2)-6;j++){
            objective.append(' ');
        }
        objective.append("Objective : ");
        for (int j=0;j<(this.width/2)-6;j++){
            objective.append(' ');
        }
        if (this.width % 2 == 1){
            objective.append(' ');
        }
        objective.append('#');
        objective.append('\n');

        objective.append('#');
        for (int j=0;j<(this.width/2)-9;j++){
            objective.append(' ');
        }
        switch (this.type){
            case ObjectiveType.COINS:
                objective.append("\u001B[33mCollect all coins \u001B[0m");
                break;
            case ObjectiveType.ENEMIES:
                objective.append("\u001B[31mKill all enemies  \u001B[0m");
                break;
        }
        for (int j=0;j<(this.width/2)-9;j++){
            objective.append(' ');
        }
        if (this.width % 2 == 1){
            objective.append(' ');
        }
        objective.append('#');
        objective.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            objective.append('#');
            for (int j=0;j<this.width;j++){
                objective.append(' ');
            }
            objective.append('#');
            objective.append('\n');
        }
        for (int j=0;j<this.width+2;j++){
            objective.append('#');
        }
        objective.append('\n');
        objective.append(this.player.toString() + " | ");
        for (int h=1; h<=this.player.getMaxHealth();h++){
            if (h > this.player.getHealthPoint()){
                objective.append(" ♡ ");
            }
            else{
                objective.append(" \u001B[31m❤︎⁠\u001B[0m ");
            }
        }

        objective.append("\nPress any key to continue");

        return objective.toString();
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
     * @param character the character (player or enemy) that wants to check that place
     * @return true if the player can move to the space (x,y)
     */
    public boolean isAccessible(Position coord, Character character){
        if (character != null){
            return (coord.validPosition() && coord.getX() < this.width && coord.getY() < this.height && character.canMove(this.level[coord.getY()][coord.getX()]));
        }
        return (coord.validPosition() && coord.getX() < this.width && coord.getY() < this.height && !this.level[coord.getY()][coord.getX()].getCollision());        // If we just the position
    }

    /**
     * If there is AT LEAST ONE available space for the player to move to
     * @param taken the position already taken that cannot count
     * @return if such cell exists or not
     */
    public boolean hasAvailableSpace(Position taken){
        for (int i=0;i<this.height;i++){
            for (int j=0;j<this.width;j++){
                if (!taken.equals(j,i)){        // Don't take the same position as taken
                    if (this.level[i][j].getType() == CellType.EMPTY && !this.level[i][j].getCollision() && !this.enemyCells.contains(this.level[i][j])){       // Empty cell, no collision and no enemies
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Teleports the player to a random empty space
     * @return if the teleportation was done or not
     */
    public boolean teleportationPlayer(){
        Random rand = new Random();

        if (!hasAvailableSpace(this.player.getCoord())){
            return false;
        }

        int newX = this.player.getCoord().getX();
        int newY = this.player.getCoord().getY();

        while(this.player.getCoord().equals(newX,newY) || this.level[newY][newX].getType() != CellType.EMPTY || this.level[newY][newX].getCollision() || this.enemyCells.contains(this.level[newY][newX])){
            newX = rand.nextInt(this.width);
            newY = rand.nextInt(this.height);
        }
        this.player.moveTo(newX,newY);

        return true;
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
     * Handle the input of the player, and moves the player accordingly
     * @return the old position of the player to check enemy collision
     */
    public Position handleInput(){
        Position newPlayer = this.player.getCoord().clone();
        Position oldPlayer = this.player.getCoord().clone();
        Direction direction = Rule.getDirection();
        boolean playerMoving = false;

        switch (direction){
            case Direction.LEFT:
                newPlayer.addX(-1);
                playerMoving = true;
                break;
            case Direction.UP:
                newPlayer.addY(-1);
                playerMoving = true;
                break;
            case Direction.RIGHT:
                newPlayer.addX(1);
                playerMoving = true;
                break;
            case Direction.DOWN:
                newPlayer.addY(1);
                playerMoving = true;
                break;
            case Direction.INVENTORY:
                this.openInventory = true;
                break;
            case Direction.EXIT:
                System.out.println("Exiting...");
                return null;
            default:
                System.out.println("Input invalid");
                break;
        }

        Rule.tore(this,newPlayer);

        if (playerMoving && this.isAccessible(newPlayer, this.player)){
            this.player.moveTo(newPlayer.getX(),newPlayer.getY());
        }

        return oldPlayer;
    }

    /**
     * Handle the inventory inputs and action
     * @return if the player used an element or not
     */
    public boolean handleInventory(){
        Direction direction = Rule.getDirection();

        switch (direction){
            case Direction.UP:
                if (this.player.getInventory().getIndex() > 0){
                    this.player.getInventory().decreaseIndex();
                }
                return false;
            case Direction.DOWN:
                if (this.player.getInventory().getIndex() + 1 < this.getPlayer().getInventory().getBag().size()){
                    this.player.getInventory().addIndex();
                }
                return false;
            case Direction.USE:
                this.openInventory = false;
                if (this.player.getInventory().getIndex() < this.player.getInventory().getBag().size()){     // Checks if an actual element was chosen
                    return true;
                }
                else{
                    return false;
                }
            case Direction.INVENTORY:
                this.openInventory = false;
                this.player.getInventory().resetIndex();
                return false;
            case Direction.EXIT:
                this.openInventory = false;
                this.player.getInventory().resetIndex();
                return false;
            default:
                System.out.println("Input invalid");
                return false;
        }
    }

    /**
     * Updates the map after the player's move
     * Takes care of events (items and traps) and the enemies
     * @param oldPlayer used to check if the player moved and the collision to enemy
     */
    public void updateMap(Position oldPlayer){
        boolean trap = false;
        boolean playerMoving = !this.player.getCoord().equals(oldPlayer);

        if (this.level[this.player.getCoord().getY()][this.player.getCoord().getX()].getHasItem()){      // Get item
            Rule.collectItem(this,this.player.getCoord());
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

                if (this.freeze == 0){      // Don't move the enemies if frozen
                    enemy.move(this);
                }

                if ((player.getCoord().equals(enemy.getCoord())) || (old.equals(player.getCoord()) && oldPlayer.equals(enemy.getCoord()) && playerMoving && this.isAccessible(this.player.getCoord(), player))){      // Enemy collides with player
                    if (this.player.getInventory().getWeapon() > 0){     // Player have weapon
                        player.attackEnemy(enemy);
                        enemy.resetPosition();
                        player.getInventory().removeInventory(this.player.getInventory().getWeaponIndex());
                    }
                    else{       // Player gets hit
                        enemy.attackPlayer(this.player);
                        this.resetEnemies();
                        break;
                    }
                }

                if (enemy.getHealthPoint() <= 0){       // Delete an enemy
                    this.player.addKill();
                    iterator.remove();
                    continue;
                }

                enemyCells.add(this.level[enemy.getCoord().getY()][enemy.getCoord().getX()]);
            }
        }
        if (!this.player.getInventory().getLockpick() && this.player.getScore() >= 100){        // Adds lockpicking 
            this.player.getInventory().addInventory(new Competence("Lockpicking",CompetenceType.LOCKPICK));
        }
        if (!this.player.getInventory().getTeleportation() && this.player.getKills() >= 3){    // Adds teleportation
            this.player.getInventory().addInventory(new Competence("Teleportation", CompetenceType.TELEPORTATION));
        }

        if (freeze > 0){        // Each movement freeze decreases
            freeze--;
        }
    }
}