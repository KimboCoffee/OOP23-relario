package it.unibo.oop.relario.model.entities.furniture.api;

import it.unibo.oop.relario.model.entities.enemies.Enemy;
import it.unibo.oop.relario.model.entities.furniture.impl.FurnitureType;
import it.unibo.oop.relario.model.inventory.InventoryItemType;
import it.unibo.oop.relario.utils.api.Position;

/**
 * Interface for handling the creation of new funiture items.
 */
public interface FurnitureFactory {
    /**
     * Creates the specified type of walkable furniture. It's empty.
     * @param pos is the position of the furniture item in the map.
     * @param type is the type of the furniture to create.
     * @return a furniture item.
     */
    Furniture createWalkableFurnitureByItemEmpty(Position pos, FurnitureType type);

    /**
     * Creates the specified type of walkable furniture.
     * @param pos is the position of the furniture item in the map.
     * @param type is the type of the furniture to create.
     * @return a furniture item.
     */
    Furniture createWalkableFurnitureByItem(Position pos, FurnitureType type);

    /**
     * Creates the specified type of interactive furniture. It's empty.
     * @param pos is the position of the furniture item in the map.
     * @param type is the type of the furniture to create.
     * @return a furniture item.
     */
    Furniture createInteractiveFurnitureByItem(Position pos, FurnitureType type);

    /**
     * Creates the specified type of obstructing furniture. It's empty.
     * @param pos is the position of the furniture item in the map.
     * @param type is the type of the furniture to create.
     * @return a furniture item.
     */
    Furniture createObstructingFurnitureByItem(Position pos, FurnitureType type);

    /**
     * Creates a random empty walkable furniture item.
     * @param pos is the position of the furniture item in the map.
     * @return a furniture item.
     */
    Furniture createRandomWalkableFurnitureEmpty(Position pos);

    /**
     * Creates a random walkable furniture item.
     * @param pos is the position of the furniture item in the map.
     * @return a furniture item.
     */
    Furniture createRandomWalkableFurniture(Position pos);

    /**
     * Creates a random interactive furniture item.
     * @param pos is the position of the furniture item in the map.
     * @return a new furniture item.
     */
    Furniture createRandomInteractiveFurniture(Position pos);

    /**
     * Creates a random obstructing furniture item.
     * @param pos is the position of the furniture item in the map.
     * @return a furnture item.
     */
    Furniture createRandomObstructingFurniture(Position pos);

    /**
     * Creates a random interactive furniture with a specific loot.
     * @param pos is the position of the furniture item in the map.
     * @param itemType is the item's type.
     * @return a furniture item.
     */
    Furniture createInteractiveFurnitureLoot(Position pos, InventoryItemType itemType);

    /**
     * Creates a random walkable furniture with a specific enemy.
     * @param pos is the position of the furniture item in the map.
     * @param enemy is the enemy inside the furniture.
     * @return a furniture item.
     */
    Furniture createWalkableFurnitureEnemy(Position pos, Enemy enemy);

}
