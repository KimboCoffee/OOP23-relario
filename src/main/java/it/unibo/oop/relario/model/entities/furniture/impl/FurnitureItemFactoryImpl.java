package it.unibo.oop.relario.model.entities.furniture.impl;

import it.unibo.oop.relario.model.entities.enemies.EnemyFactory;
import it.unibo.oop.relario.model.entities.enemies.EnemyFactoryImpl;
import it.unibo.oop.relario.model.entities.furniture.api.FurnitureItem;
import it.unibo.oop.relario.model.entities.furniture.api.FurnitureItemFactory;
import it.unibo.oop.relario.model.inventory.InventoryItemFactory;
import it.unibo.oop.relario.model.inventory.InventoryItemFactoryImpl;
import it.unibo.oop.relario.utils.api.Position;

/**
 * Factory used to create different types of furniture.
 */
public final class FurnitureItemFactoryImpl implements FurnitureItemFactory {

    @Override
    public FurnitureItem createWalkableFurnitureItemEmpty(final Position pos) {
        return new WalkableFurnitureImpl(pos);
    }

    @Override
    public FurnitureItem createWalkableFurnitureItem(final Position pos) {
        final EnemyFactory enemy = new EnemyFactoryImpl();
        return new WalkableFurnitureImpl(pos, enemy.createRandomEnemy(pos));
    }

    @Override
    public FurnitureItem createInteractiveFurnitureItemEmpty(final Position pos) { 
        return new InteractiveFurnitureItem(pos);
    }

    @Override
    public FurnitureItem createInteractiveFurnitureItem(final Position pos) {
        final InventoryItemFactory loot = new InventoryItemFactoryImpl();
        return new InteractiveFurnitureItem(pos, loot.createRandomItem());
    }

    @Override
    public FurnitureItem createObstructingFurnitureItem(final Position pos) {
        return new ObstructingFurnitureItem(pos);
    }
}
