/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.3
 */

package world_5.environnement;

import world_5.characters.*;
import world_5.types.*;
import world_5.exceptions.*;
import world_5.inventory.item.*;
import world_5.inventory.competence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * Level class
 * The coordonates are (0,0) from the top left corner, and (x,y) x is the horizontal, and y the vertical
 */
public class Level{
    /**The width of the level (horizontal) */
    private int width;          // level info
    /**The height of the level (vertical) */
    private int height;
    /**The total number of coins left on the map */
    private int nbCoins;
    /**The type of objective of this level */
    private ObjectiveType type;

    /**The grid of cells that constitutes the map */
    private Cell[][] level;     // The actual level

    /**The list of all the enemies */
    private List<Enemy> enemies;// The entities
    /**The set of Cell enemies occupy */
    private Set<Cell> enemyCells;
    /**The player :D */
    private Player player;
    /**The starting coordinates of the player in this level */
    private Position startPlayer;

    /**If the inventory is open or not */
    private boolean openInventory;  // handling events
    /**How much time/movement enemies are frozen for */
    private int freeze;

    private static String CUR = System.getProperty("user.dir");

    /**
     * Constructor method
     * @param width length of the x coordonate
     * @param height length of the y coordonate
     * @param structs a list (can be empty) of structures to add to the level
     * @param enemies the list of enemies in the level
     * @param player The player (same throughout all levels of a game)
     * @param playerX the starting x coordinate of the player
     * @param playerY the starting y coordinate of the player
     * @param type the type of objective for the level
     * @throws InvalidLevelException if the width and height are invalid
     */
    public Level(int width, int height, Structure[] structs, Player player, int playerX, int playerY, List<Enemy> enemies, ObjectiveType type) throws InvalidLevelException{
        if (width > 0 && height > 0){
            this.width = width;
            this.height = height;
            this.nbCoins = 0;
            this.type = type;

            this.level = new Cell[this.height][this.width];

            this.enemies = enemies;
            this.enemyCells = new HashSet<Cell>();

            this.freeze = 0;
            this.openInventory = false;


            for (int i=0;i<this.height;i++){        // Creates all cells as empty ones
                for (int j=0;j<this.width;j++){
                    this.level[i][j] = new Cell(new Position(j,i),CellType.EMPTY);
                }
            }

            if (structs != null){
                for (int i=0;i<structs.length; i++){            // Fill the structures
                    if (structs[i] != null){
                        if (this.isInLevel(structs[i])){
                            for (int j=0;j<structs[i].getHeight();j++){
                                for (int k=0;k<structs[i].getWidth();k++){
                                    switch(structs[i].getType()){
                                        case 0:             // If it's a wall
                                            if (this.level[j+structs[i].getY()][k+structs[i].getX()].getType() == CellType.EMPTY && !this.level[j+structs[i].getY()][k+structs[i].getX()].hasItem()){
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
                                            if ((this.level[j+structs[i].getY()][k+structs[i].getX()].getType() != CellType.WALL || !this.level[j+structs[i].getY()][k+structs[i].getX()].getCollision()) && !this.level[j+structs[i].getY()][k+structs[i].getX()].hasItem()){       // Items can be anywhere except walls (except if there's no collision) or write over other items
                                                this.nbCoins += 1;
                                                this.level[j+structs[i].getY()][k+structs[i].getX()].addItem(new Coin("Coin"));
                                            }
                                            break;
                                        case 101:
                                            if ((this.level[j+structs[i].getY()][k+structs[i].getX()].getType() != CellType.WALL || !this.level[j+structs[i].getY()][k+structs[i].getX()].getCollision()) && !this.level[j+structs[i].getY()][k+structs[i].getX()].hasItem()){
                                                this.level[j+structs[i].getY()][k+structs[i].getX()].addItem(new Weapon("Weapon"));
                                            }
                                            break;
                                        case 102:
                                            if ((this.level[j+structs[i].getY()][k+structs[i].getX()].getType() != CellType.WALL || !this.level[j+structs[i].getY()][k+structs[i].getX()].getCollision()) && !this.level[j+structs[i].getY()][k+structs[i].getX()].hasItem()){
                                                this.level[j+structs[i].getY()][k+structs[i].getX()].addItem(new Hourglass("Hourglass"));
                                            }
                                            break;
                                        case 103:
                                            if ((this.level[j+structs[i].getY()][k+structs[i].getX()].getType() != CellType.WALL || !this.level[j+structs[i].getY()][k+structs[i].getX()].getCollision()) && !this.level[j+structs[i].getY()][k+structs[i].getX()].hasItem()){
                                                this.level[j+structs[i].getY()][k+structs[i].getX()].addItem(new Potion("Health Potion"));
                                            }
                                            break;

                                    }
                                }
                            }
                        }
                    }
                }
            }

            Iterator<Enemy> iterator = this.enemies.iterator();
            while (iterator.hasNext()){
                Enemy enemy = iterator.next();
                if (enemy.getCoord().validPosition() && enemy.getCoord().getY() < this.height && enemy.getCoord().getX() < this.width){
                    enemyCells.add(this.level[enemy.getCoord().getY()][enemy.getCoord().getX()]);
                }
            }

            if (!this.isAccessible(new Position(playerX,playerY))){      // Player not in map
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
     * [line break]
     * n STRUCT INFO (type width height x y)
     * [line break]
     * ENEMIES INFO (name x y maxHP type)
     * [line break]
     * LEVEL INFO (level.width level.height playerX playerY objectiveType)
     * 
     * @param file The file path in the directory files
     * @param p1 The player. It doesn't have to be created with the level, it is given so the player can stay the same in different levels
     * @throws IOException If the format read is not valid
     * @throws FileNotFoundException If the file given isn't there
     * @throws InvalidLevelException if the width or height aren't valid
     * @throws NumberFormatException if the Interger.parseInt fails
     * 
     * @return a level object based on the info of the file
     */
    public static Level getLevelFromFile(String file, Player p1) throws FileNotFoundException, IOException, InvalidLevelException, NumberFormatException{
        Path filesPath = Paths.get(CUR+"/files/"+file);
        Structure[] structuresOfLevel = null;
        List<Enemy> enemies = new ArrayList<Enemy>();

        int width = -1;
        int height = -1;
        int playerX = -1;
        int playerY = -1;
        ObjectiveType type = ObjectiveType.COINS;

        int section = 0;
        int structureIndex = 0;
        
        if (Files.exists(filesPath) && Files.isRegularFile(filesPath) && Files.isReadable(filesPath)){
            try{
                List<String> lignes = Files.readAllLines(filesPath);
                for (String ligne : lignes) {
                    if (ligne.isEmpty()){
                        section += 1;
                        structureIndex = 0;
                    }
                    else{
                        switch (section){
                            case 0:     // nbStructures
                                structuresOfLevel = new Structure[Integer.parseInt(ligne)];
                                if (Integer.parseInt(ligne) == 0){
                                    section += 1;
                                }
                                break;
                            case 1:     // Structures info
                                String[] structInfo = ligne.split(" ");
                                if (structInfo.length == 5){
                                    try{
                                        structuresOfLevel[structureIndex] = new Structure(Integer.parseInt(structInfo[0]),Integer.parseInt(structInfo[1]),Integer.parseInt(structInfo[2]),Integer.parseInt(structInfo[3]),Integer.parseInt(structInfo[4]));
                                        structureIndex += 1;
                                    }
                                    catch (InvalidStructureException e){
                                        System.err.println(e.getMessage());
                                    }
                                }
                                break;
                            case 2:     // Enemies info
                                String[] enemiesInfo = ligne.split(" ");
                                if (enemiesInfo.length == 5){
                                    switch (Integer.parseInt(enemiesInfo[4])){      // The enemy type
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
                if (p1 != null){
                    try{
                        return new Level(width, height, structuresOfLevel, p1, playerX, playerY, enemies, type);   
                    } catch (InvalidLevelException e){
                        throw e;
                    }
                }
            } catch(IOException e){
                throw e;
            } catch (NumberFormatException e){
                throw e;
            }
            }
        else{
            throw new FileNotFoundException("File not found");
        }
        return null;
    }

    /**
     * @return the height of the level
    */
    public int getHeight(){
        return this.height;
    }

    /**
     * @return the width of the level
    */
    public int getWidth(){
        return this.width;
    }

    /**
     * @return the player 
    */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * @return the nbCoins of the level
    */
    public int getNbCoins(){
        return this.nbCoins;
    }

    /**
     * Get the level as cell grid
     * @return the level
     */
    public Cell[][] getLevel(){
        return this.level;
    }

    /**
     * @return the list of enemies in the level
     */
    public List<Enemy> getEnemies(){
        return this.enemies;
    }

    /**
     * @return the set of cells enemies are in
     */
    public Set<Cell> getEnemyCells(){
        return this.enemyCells;
    }

    /**
     * @return The starting position of the player
     */
    public Position getStartPlayer(){
        return this.startPlayer;
    }

    /**
     * @return the level type
     */
    public ObjectiveType getType(){
        return this.type;
    }

    /**
     * @return the time enemies are frozen
     */
    public int getFreeze(){
        return this.freeze;
    }

    /**
     * @return if the player has an open inventory or not
     */
    public boolean isInventoryOpen(){
        return this.openInventory;
    }
    /**
     * Check if the structure can be fitted inside the level
     * @param struct The structure to check
     * @return true if it can be in the level, false if not
     */
    public boolean isInLevel(Structure struct){     // x, x+width, y et y+height are inbounds    
        return ((struct.getX() >= 0) && (struct.getX() <= this.width) && (struct.getY() >= 0) && (struct.getY() <= this.height) &&(struct.getX() + struct.getWidth() >= 0) && (struct.getX() + struct.getWidth() <= this.width) && (struct.getY() + struct.getHeight() >= 0) && (struct.getY() + struct.getHeight() <= this.height)) ? true : false;
    }

    /**
     * Checks the space for movePlayer functions
     * @param coord The coordinate of the cell
     * @param character the character (player or enemy) that wants to check that place
     * @return true if the player can move to the space (x,y)
     */
    public boolean isAccessible(Position coord, world_5.characters.Character character){
        return (coord.validPosition() && coord.getX() < this.width && coord.getY() < this.height && character.canMove(this.level[coord.getY()][coord.getX()]));
    }

    /**
     * Checks the space for movePlayer functions
     * Usually used in the creation of Level to see if player is inbound
     * @param coord The coordinate of the cell
     * @return true if the player can move to the space (x,y)
     */
    public boolean isAccessible(Position coord){
        return (coord.validPosition() && coord.getX() < this.width && coord.getY() < this.height && !this.level[coord.getY()][coord.getX()].getCollision());        // If we just the position
    }

    /**
     * If there is AT LEAST ONE available space for the player to move to other the one where they already are
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
     * Decreases the number of coins in the level by one
     * Used by pickUp to pick up a coin
     */
    public void decreaseNbCoin(){
        this.nbCoins--;
    }

    /**
     * Displays the map and the structures within
     * @return The string to print out of the map, its edges
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

        level.append(this.displayUI());

        return level.toString();
    }

    /**
     * Displays the HealthBar and the name/score of the player
     * @return The string to print out the healthBar part of the UI
     */
    public String displayHealthBar(){
        StringBuilder healthBar = new StringBuilder();
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";

        healthBar.append(this.player.toString() + " | ");       
        for (int h=1; h<=this.player.getMaxHealth();h++){       // Healthbar
            if (h > this.player.getHealthPoint()){
                healthBar.append(" ♡ ");
            }
            else{
                healthBar.append(RED + " ❤︎ " + RESET);
            }
        }

        return healthBar.toString();
    }

    /**
     * Displays the UI (health bar, player's info, coins/enemies left, etc)
     * @return The string to print out the UI
     */
    public String displayUI(){
        StringBuilder ui = new StringBuilder();
        String RESET = "\u001B[0m";
        String YELLOW = "\u001B[33m";
        String BLUE = "\u001B[94m";
        String RED = "\u001B[31m";

        ui.append(this.displayHealthBar());
        ui.append('\n');

        ui.append("x: " + this.player.getCoord().getX() + " y: " + this.player.getCoord().getY());       // Position
        switch(this.type){      // Objective
            case ObjectiveType.COINS:
                ui.append(" | " + YELLOW + "coins left : "+ this.nbCoins + RESET + "\n");
                break;
            case ObjectiveType.ENEMIES:
                ui.append(" | " + RED + "enemies left : "+ this.enemies.size() + RESET + "\n");
                break;
        }
        ui.append("Z: Up | Q: Right | S: Down | D: Left | N: exit");     // Direction keys
        if (this.freeze > 0){       // Eventual freeze time
            ui.append("  |  Enemies frozen for " + BLUE + this.freeze + " mov." + RESET);
        }

        return ui.toString();
    }

    /**
     * Displays the inventory UI
     * @return The string to print out the inventory, its edges and the UI
     */
    public String displayInventory(){
        StringBuilder inventory = new StringBuilder();
        String RESET = "\u001B[0m";
        String BLUE = "\u001B[94m";

        int goodWidth = this.width;
        if (goodWidth < 22){     // So the inventory is big enough to print out words
            goodWidth = 22;
        }

        for (int j = 0; j < goodWidth+2; j++) {
            inventory.append('#');
        }
        inventory.append('\n');

        inventory.append('#');
        for (int j=0;j<goodWidth;j++){
            inventory.append(' ');
        }
        inventory.append('#');
        inventory.append('\n');

        for (int i=0;i<player.getInventory().getMaxInventory()*2;i++){
            if (i%2 == 1 || this.player.getInventory().getBagSize() <= i/2){
                inventory.append('#');
                for (int j=0;j<goodWidth;j++){
                    inventory.append(' ');
                }
                inventory.append('#');
                inventory.append('\n');
            }
            else if (this.player.getInventory().getIndex() == i/2){
                inventory.append("#" + BLUE + " * " + this.player.getInventory().getBagElementName(i/2) + " [USE]" + RESET );
                for (int j=0;j<goodWidth - 9 - this.player.getInventory().getBagElementName(i/2).length();j++){
                    inventory.append(" ");
                }
                inventory.append("#");
                inventory.append('\n');
            }
            else{
                inventory.append("#   " + this.player.getInventory().getBagElementName(i/2));
                for (int j=0;j<goodWidth - 3 - this.player.getInventory().getBagElementName(i/2).length();j++){
                    inventory.append(" ");
                }
                inventory.append("#");
                inventory.append('\n');
            }
        }

        inventory.append('#');
        for (int j=0;j<goodWidth;j++){
            inventory.append(' ');
        }
        inventory.append('#');
        inventory.append('\n');

        for (int j = 0; j < goodWidth+2; j++) {
            inventory.append('#');
        }
        inventory.append('\n');

        inventory.append(this.displayUI());

        return inventory.toString();
    }

    /**
     * Prints out the outer layer of the objective needed
     * @return the winning screen as a string to print out
     */
    public String displayObjective(){
        StringBuilder objective = new StringBuilder();
        String RESET = "\u001B[0m";
        String YELLOW = "\u001B[33m";
        String RED = "\u001B[31m";

        int goodWidth = this.width;     // Good width that can print LEVEL COMPLETED without "crossing the lines"
        if (goodWidth < 19){
            goodWidth = 19;
        }

        for (int j=0;j<goodWidth+2;j++){
            objective.append('#');
        }
        objective.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            objective.append('#');
            for (int j=0;j<goodWidth;j++){
                objective.append(' ');
            }
            objective.append('#');
            objective.append('\n');
        }

        objective.append('#');
        for (int j=0;j<(goodWidth/2)-6;j++){
            objective.append(' ');
        }
        objective.append("Objective : ");
        for (int j=0;j<(goodWidth/2)-6;j++){
            objective.append(' ');
        }
        if (goodWidth % 2 == 1){
            objective.append(' ');
        }
        objective.append('#');
        objective.append('\n');

        objective.append('#');
        for (int j=0;j<(goodWidth/2)-8;j++){
            objective.append(' ');
        }
        switch (this.type){
            case ObjectiveType.COINS:
                objective.append(YELLOW + "Collect all coins" + RESET);
                break;
            case ObjectiveType.ENEMIES:
                objective.append(RED + "Kill all enemies " + RESET);
                break;
        }
        for (int j=0;j<(goodWidth/2)-9;j++){
            objective.append(' ');
        }
        if (goodWidth % 2 == 1){
            objective.append(' ');
        }
        objective.append('#');
        objective.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            objective.append('#');
            for (int j=0;j<goodWidth;j++){
                objective.append(' ');
            }
            objective.append('#');
            objective.append('\n');
        }
        for (int j=0;j<goodWidth+2;j++){
            objective.append('#');
        }
        objective.append('\n');
        objective.append(this.displayHealthBar());

        objective.append("\nPress any key to continue");

        return objective.toString();
    }

    /**
     * Prints out the outer layer of the level and "LEVEL COMPLETE"
     * Usually printed out when all the coins a gathered
     * @return the winning screen as a string to print out
     */
    public String displayLevelComplete(){
        StringBuilder winScreen = new StringBuilder();

        int goodWidth = this.width;     // Good width that can print LEVEL COMPLETED without "crossing the lines"
        if (goodWidth < 16){
            goodWidth = 16;
        }

        for (int j=0;j<goodWidth+2;j++){
            winScreen.append('#');
        }
        winScreen.append('\n');

        for (int i=0;i<this.height/2;i++){
            winScreen.append('#');
            for (int j=0;j<goodWidth;j++){
                winScreen.append(' ');
            }
            winScreen.append('#');
            winScreen.append('\n');
        }

        winScreen.append('#');
        for (int j=0;j<(goodWidth/2)-7;j++){
            winScreen.append(' ');
        }
        winScreen.append("LEVEL COMPLETE");
        for (int j=0;j<(goodWidth/2)-7;j++){
            winScreen.append(' ');
        }
        if (goodWidth % 2 == 1){
            winScreen.append(' ');
        }
        winScreen.append('#');
        winScreen.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            winScreen.append('#');
            for (int j=0;j<goodWidth;j++){
                winScreen.append(' ');
            }
            winScreen.append('#');
            winScreen.append('\n');
        }
        for (int j=0;j<goodWidth+2;j++){
            winScreen.append('#');
        }
        winScreen.append('\n');
        winScreen.append(this.displayHealthBar());
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

        int goodWidth = this.width;     // Good width that can print LEVEL COMPLETED without "crossing the lines"
        if (goodWidth < 13){
            goodWidth = 13;
        }

        for (int j=0;j<goodWidth+2;j++){
            winScreen.append('#');
        }
        winScreen.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            winScreen.append('#');
            for (int j=0;j<goodWidth;j++){
                winScreen.append(' ');
            }
            winScreen.append('#');
            winScreen.append('\n');
        }

        winScreen.append('#');
        for (int j=0;j<(goodWidth/2)-4;j++){
            winScreen.append(' ');
        }
        winScreen.append("THANK YOU");
        for (int j=0;j<(goodWidth/2)-5;j++){
            winScreen.append(' ');
        }
        if (goodWidth % 2 == 1){
            winScreen.append(' ');
        }
        winScreen.append('#');
        winScreen.append('\n');


        winScreen.append('#');
        for (int j=0;j<(goodWidth/2)-5;j++){
            winScreen.append(' ');
        }
        winScreen.append("FOR PLAYING");
        for (int j=0;j<(goodWidth/2)-6;j++){
            winScreen.append(' ');
        }
        if (goodWidth % 2 == 1){
            winScreen.append(' ');
        }
        winScreen.append('#');
        winScreen.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            winScreen.append('#');
            for (int j=0;j<goodWidth;j++){
                winScreen.append(' ');
            }
            winScreen.append('#');
            winScreen.append('\n');
        }
        for (int j=0;j<goodWidth+2;j++){
            winScreen.append('#');
        }
        winScreen.append('\n');
        winScreen.append(this.displayHealthBar());

        return winScreen.toString();
    }

    /**
     * Prints out the outer layer of the level and "GAME OVER"
     * Usually printed out when the health is negative or equal to 0
     * @return the losing screen as a string to print out
     */
    public String displayGameOver(){
        StringBuilder gameOverScreen = new StringBuilder();

        int goodWidth = this.width;     // Good width that can print LEVEL COMPLETED without "crossing the lines"
        if (goodWidth < 11){
            goodWidth = 11;
        }

        for (int j=0;j<goodWidth+2;j++){
            gameOverScreen.append('#');
        }
        gameOverScreen.append('\n');

        for (int i=0;i<this.height/2;i++){
            gameOverScreen.append('#');
            for (int j=0;j<goodWidth;j++){
                gameOverScreen.append(' ');
            }
            gameOverScreen.append('#');
            gameOverScreen.append('\n');
        }

        gameOverScreen.append('#');
        for (int j=0;j<(goodWidth/2)-4;j++){
            gameOverScreen.append(' ');
        }
        gameOverScreen.append("GAME OVER");
        for (int j=0;j<(goodWidth/2)-5;j++){
            gameOverScreen.append(' ');
        }
        if (goodWidth % 2 == 1){
            gameOverScreen.append(' ');
        }
        gameOverScreen.append('#');
        gameOverScreen.append('\n');

        for (int i=0;i<this.height/2-1;i++){
            gameOverScreen.append('#');
            for (int j=0;j<goodWidth;j++){
                gameOverScreen.append(' ');
            }
            gameOverScreen.append('#');
            gameOverScreen.append('\n');
        }
        for (int j=0;j<goodWidth+2;j++){
            gameOverScreen.append('#');
        }
        gameOverScreen.append("\nR: restart the game | N: exit");

        return gameOverScreen.toString();
    }

    /**
     * Adds an amount of time to freeze enemies for (has to be positive)
     * @param time the amount of time (usually 10 w/ hourglass) we add
     */
    public void freezeEnemies(int time){
        if (time > 0){
            this.freeze += time;
        }
    }

    /**
     * Teleports the player to a random empty space
     * @return if the teleportation was possible or not
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

        if (this.level[this.player.getCoord().getY()][this.player.getCoord().getX()].hasItem() && this.player.getInventory().pickUp(this.level[this.player.getCoord().getY()][this.player.getCoord().getX()].getItem(), this)){      // Gets the item on the Cell the player landed on
            this.level[this.player.getCoord().getY()][this.player.getCoord().getX()].removeItem();
        }

        return true;
    }

    /**
     * Handle the input of the player, and moves the player accordingly
     * @return the old position of the player to check enemy collision (if they cross)
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
                if (this.player.getInventory().getIndex() + 1 < this.getPlayer().getInventory().getBagSize()){
                    this.player.getInventory().increaseIndex();
                }
                return false;
            case Direction.USE:
                this.openInventory = false;
                return (this.player.getInventory().getIndex() < this.player.getInventory().getBagSize()) ? true : false;
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
     * Updates the map after the player's move
     * Takes care of events (items and traps) and the enemies
     * @param oldPlayer used to check if the player moved and the collision to enemy (if they cross)
     */
    public void updateMap(Position oldPlayer){
        boolean trap = false;
        boolean playerMoving = !this.player.getCoord().equals(oldPlayer);

        if (this.level[this.player.getCoord().getY()][this.player.getCoord().getX()].hasItem() && this.player.getInventory().pickUp(this.level[this.player.getCoord().getY()][this.player.getCoord().getX()].getItem(), this)){      // Get item
            this.level[this.player.getCoord().getY()][this.player.getCoord().getX()].removeItem();
        }
        if (this.level[this.player.getCoord().getY()][this.player.getCoord().getX()].getType() == CellType.TRAP && this.player.getHealthPoint() > 0){         // Get on a trap
            trap = true;
            Rule.activateTrap(this,this.player.getCoord());
        }

        if (!trap){     // If not a trap => enemies moving
            this.enemyCells.clear();
            Iterator<Enemy> iterator = this.enemies.iterator();
            while (iterator.hasNext()){         // Check if enemy hits whether the player moved or not
                Enemy enemy = iterator.next();
                Position oldEnemy = enemy.getCoord().clone();

                if (this.freeze == 0){      // Don't move the enemies if frozen
                    enemy.move(this);
                }

                if ((player.getCoord().equals(enemy.getCoord())) || (oldEnemy.equals(player.getCoord()) && oldPlayer.equals(enemy.getCoord()) && playerMoving && this.isAccessible(this.player.getCoord(), player))){      // Enemy collides with player
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
            this.player.getInventory().stock(new Lockpick("Lockpicking"));
        }
        if (!this.player.getInventory().getTeleportation() && this.player.getKills() >= 3){    // Adds teleportation
            this.player.getInventory().stock(new Teleportation("Teleportation"));
        }

        if (freeze > 0){        // Each movement freeze decreases
            freeze--;
        }
    }
}