/**
 * @author Thémis Tran Tu Thien :D
 * @version 1.2
 */

package world_5.characters;

import world_5.environnement.Position;

/**
 * The character class to play a video game
 */
public abstract class Character{
    /**The unique name of the character */
    protected final String name;
    /**The position of a character */
    protected Position coord;
    /**The current health points */
    protected int healthPoint;
    /**The maximum amount of health */
    final protected int maxhealth;

    /**
     * The character, parent to enemy and player
     * @param name the name of the player
     * @param coord their current coords
     * @param maxhealth The max health of the player
     */
    public Character(String name, Position coord, int maxhealth){
        this.name = name;
        this.coord = coord;
        this.healthPoint = maxhealth;
        this.maxhealth = maxhealth;
    }

    /**
     * Get the name of the character
     * @return The name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get the health points of the character
     * @return The healthPoint
     */
    public int getHealthPoint(){
        return this.healthPoint;
    }

    /**
     * Get the max health points of the character
     * @return The maxhealth
     */
    public int getMaxHealth(){
        return this.maxhealth;
    }

    /**
     * Get the position of the character
     * @return The position of the character
     */
    public Position getCoord(){
        return this.coord;
    }

    /**
     * Two characters are considered equals if their name match up (case sensitivity ignored)
     * @return true if it's equal or false if not
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Character){
            Character P2 = (Character) obj;
            if (this.name.equalsIgnoreCase(P2.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * Redefine the hashCode
     * @return The hash of an object based on equals
     */
    @Override
    public int hashCode(){
        int result = 11;     // My favorite prime number
        result = 31*result + this.name.toLowerCase().hashCode();

        return result;
    }

    /**
     * Adds health points to the character
     * @param heal The number of health points to add (positive)
     */
    public void addHealth(int heal){
        if (heal >= 0){
            this.healthPoint = (this.healthPoint + heal < this.maxhealth) ? this.healthPoint + heal : this.maxhealth;
        }
    }

    /**
     * Remove health points of the character
     * If the update would result in a health below zero, the health points is reset to zero
     * @param damage The number of points to remove (positive)
     */
    public void removeHealth(int damage){
        if (damage >= 0){
            this.healthPoint -= (this.healthPoint - damage <= 0) ? this.healthPoint : damage;
        }
    }

    /**
     * Moves the character
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public void moveTo(int x, int y){
        this.coord.setX(x);
        this.coord.setY(y);
    }
}