package it.unibo.oop.relario.model.entities.furniture.api;

import it.unibo.oop.relario.model.entities.enemies.Enemy;

/**
 * Interface for handling walkable furniture items.
 */
public interface WalkableFurniture extends Furniture {
    /**
     * Checks if the furniture contains an enemy.
     * @return true if the furniture contains an enemy, false otherwise.
     */
    boolean hasEnemy();

    /**
     * Adds an enemy into the furniture.
     * @param enemy is the enemy to add into the furniture.
     */
    void addEnemy(Enemy enemy);

    /**
     * Removes the enemy from inside the walkable furniture item.
     */
    void removeEnemy();

}
