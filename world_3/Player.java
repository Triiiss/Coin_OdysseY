/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_3;


/**
 * The player class to play a video game
 */
public class Player{
    private final String name;
    private int score;              // Ajouter position
    private int healthPoint;
    final private int MAXHEALTH;
    private Position coord;
    private static int nbPlayers = 0;

    /**
     * Consctuctor of the Player object (score is automatically at 0)
     * @param name name of the player
     */
    public Player(String name){
        this.name = name;
        this.score = 0;
        this.healthPoint = 5;
        this.MAXHEALTH = 5;
        this.coord = new Position(-1,-1);

        System.out.println("[Creation] Number of total players : " + Player.nbPlayers);
        Player.nbPlayers++;
    }

    /**
     * Constructor with no need of a name argument. The default name will be PlayerN with N the number of total players
     */
    public Player(){
        this("Player" + (Player.nbPlayers + 1));
    }

    /**
     * Get the name of the player
     * @return The name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get the score of the player
     * @return The score
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Get the health points of the player
     * @return The healthPoint
     */
    public int getHealthPoint(){
        return this.healthPoint;
    }

    /**
     * Get the max health points of the player
     * @return The MAXHEALTH
     */
    public int getMaxHealth(){
        return this.MAXHEALTH;
    }

    /**
     * Get the coordinates of the player
     * @return the position of the player
     */
    public Position getCoord(){
        return this.coord;
    }

    /**
     * Count the players that were created using the constructor method
     * @return the number of player total
     */
    public static int getNbPlayer() {
        return Player.nbPlayers;
    }

    /**
     * Displays the information of your Player object
     */
    public void display(){
        System.out.println("Player : " + this.name + " | score : " + this.score);
    }

    /**
     * Puts the player object in the form of a string
     * @return the string in the form of [name] : [score] pts
     */
    public String toString(){
        String s = this.score > 1 ? "s" : "";
        return this.name + " : " + this.score + " pt" + s;
    }

    /**
     * Two players are considered equals if their name match up (case sensitivity ignored)
     * @return true if it's equal or false if not
     */
    public boolean equals(Object obj){
        if (obj instanceof Player){
            Player P2 = (Player) obj;
            if (this.getName().equalsIgnoreCase(P2.getName())){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Resets the player in case of game over (no need to create it again, just reset the score and the health)
     */
    public void reset(){
        this.score = 0;
        this.healthPoint = this.MAXHEALTH;
    }

    /**
     * Adds points to the score of the player
     * @param points The number of points to add (positive)
     */
    public void addScore(int points){
        if (points >= 0){
            this.score += points;
        }
    }

    /**
     * Remove points to the score of the player
     * If the update would result in a score below zero, the score is reset to zero
     * @param points The number of points to remove (positive)
     */
    public void removeScore(int points){
        if (points >= 0){
            this.score -= this.score - points <= 0 ? this.score : points;
        }
    }

    /**
     * Adds health points to the player
     * @param heal The number of health points to add (positive)
     */
    public void addHealth(int heal){
        if (heal >= 0){
            this.healthPoint = (this.healthPoint + heal < this.MAXHEALTH) ? this.healthPoint + heal : this.MAXHEALTH;
        }
    }

    /**
     * Remove health points of the player
     * If the update would result in a health below zero, the health points is reset to zero
     * @param damage The number of points to remove (positive)
     */
    public void removeHealth(int damage){
        if (damage >= 0){
            this.healthPoint -= (this.healthPoint - damage <= 0) ? this.healthPoint : damage;
        }
    }

    /**
     * Moving the player
     * @param x the x coordinate to move it to
     * @param y the y coordinate to move it to
     */
    public void move(int x, int y){
        this.coord.setX(x);
        this.coord.setY(y);
    }
}