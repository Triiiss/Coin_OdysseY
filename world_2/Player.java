/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.1
 */

package world_2;


/**
 * The player class to play a video game
 */
public class Player{
    private final String name;
    private int score;
    private static int nbPlayers = 0;

    /**
     * Consctuctor of the Player object (score is automatically at 0)
     * @param name name of the player
     */
    public Player(String name){
        this.name = name;
        this.score = 0;

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
     * Displays the information of your Player object
     */
    public void display(){
        System.out.println("Player : " + this.name + " | score : " + this.score);
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
     * @param points The number of points to add (positive)
     */
    public void removeScore(int points){
        if (points >= 0){
            this.score -= this.score - points <= 0 ? this.score : points;
        }
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
     * Count the players that were created using the constructor method
     * @return the number of player total
     */
    public static int getNbPlayer() {
        return Player.nbPlayers;
    }
}