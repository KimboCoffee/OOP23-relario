package it.unibo.oop.relario.model.entities.furniture.impl;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unibo.oop.relario.model.entities.enemies.Enemy;
import it.unibo.oop.relario.model.entities.enemies.EnemyFactory;
import it.unibo.oop.relario.model.entities.enemies.EnemyFactoryImpl;
import it.unibo.oop.relario.model.entities.furniture.api.Furniture;
import it.unibo.oop.relario.model.entities.furniture.api.FurnitureFactory;
import it.unibo.oop.relario.model.inventory.InventoryItem;
import it.unibo.oop.relario.model.inventory.InventoryItemFactory;
import it.unibo.oop.relario.model.inventory.InventoryItemFactoryImpl;
import it.unibo.oop.relario.model.inventory.InventoryItemType;
import it.unibo.oop.relario.utils.api.Position;

/**
 * Factory used to create different types of furniture.
 */
public final class FurnitureFactoryImpl implements FurnitureFactory {

    private final Map<FurnitureType, String> furnitureInfo = new EnumMap<>(FurnitureType.class);
    private final Random random = new Random();

    /**
     * Costructor of furniture factory.
     * Initializes the furniture with some basic information.
     */
    public FurnitureFactoryImpl() {
        furnitureInfo.put(FurnitureType.ARMORSTAND,
            "Un armorstand per esporre armature, con la possibilità di nascondere oggetti preziosi.");
        furnitureInfo.put(FurnitureType.CARPET,
            "Un elegante tappeto che arreda con stile, ma talvolta cela pericoli nascosti sotto di sé.");
        furnitureInfo.put(FurnitureType.CHEST,
            "Un scrigno misterioso, ideale per custodire oggetti di valore e sorprese nascoste.");
        furnitureInfo.put(FurnitureType.STATUE,
            "Una statua semplice e raffinata, perfetta per aggiungere un tocco decorativo all'ambiente.");
        furnitureInfo.put(FurnitureType.TRAPDOOR,
            "Una botola curiosa, che custodisce sorprese… alcune meno amichevoli di altre!");
        furnitureInfo.put(FurnitureType.VASE,
            "Un vaso incantevole, pronto a custodire piccoli oggetti e segreti preziosi.");
        furnitureInfo.put(FurnitureType.WARDROBE,
            "Un vaso incantevole, pronto a custodire piccoli oggetti e segreti preziosi.");
    }

    @Override
    public Furniture createWalkableFurnitureByItemEmpty(final Position pos, final FurnitureType type) {
        if (!type.getFurniturePropriety().equals(FurniturePropriety.WALKABLE)) {
            throw new IllegalArgumentException();
        }
        final var desc = furnitureInfo.get(type);
        return new WalkableFurnitureImpl(pos, type.getName(), desc, type);
    }

    @Override
    public Furniture createWalkableFurnitureByItem(final Position pos, final FurnitureType type) {
        if (!type.getFurniturePropriety().equals(FurniturePropriety.WALKABLE)) {
            throw new IllegalArgumentException();
        }
        final var desc = furnitureInfo.get(type);
        final EnemyFactory enemy = new EnemyFactoryImpl();
        return new WalkableFurnitureImpl(pos, type.getName(), desc, type, 
        enemy.createRandomEnemy(pos));
    }

    @Override
    public Furniture createObstructingFurnitureByItem(final Position pos, final FurnitureType type) {
        if (!type.getFurniturePropriety().equals(FurniturePropriety.OBSTRUCTING)) {
            throw new IllegalArgumentException();
        }
        final var desc = furnitureInfo.get(type);
        return new ObstructingFurniture(pos, type.getName(), desc, type);
    }

    @Override
    public Furniture createInteractiveFurnitureByItem(final Position pos, final FurnitureType type) {
        if (!type.getFurniturePropriety().equals(FurniturePropriety.INTERACTIVE)) {
            throw new IllegalArgumentException();
        }
        final var desc = furnitureInfo.get(type);
        final InventoryItemFactory loot = new InventoryItemFactoryImpl();
        return new InteractiveFurnitureImpl(pos, type.getName(), desc, type, loot.createRandomItem());
    }

    @Override
    public Furniture createRandomWalkableFurnitureEmpty(final Position pos) {
        final var matchingProperties = filterByPropriety(FurniturePropriety.WALKABLE);
        return createWalkableFurnitureByItemEmpty(pos, matchingProperties.get(random.nextInt(matchingProperties.size())));
    }

    @Override
    public Furniture createRandomWalkableFurniture(final Position pos) {
        final var matchingProperties = filterByPropriety(FurniturePropriety.WALKABLE);
        return createWalkableFurnitureByItem(pos, matchingProperties.get(random.nextInt(matchingProperties.size())));
    }

    @Override
    public Furniture createRandomInteractiveFurniture(final Position pos) {
        final var matchingProperties = filterByPropriety(FurniturePropriety.INTERACTIVE);
        return createInteractiveFurnitureByItem(pos, matchingProperties.get(random.nextInt(matchingProperties.size())));
    }

    @Override
    public Furniture createRandomObstructingFurniture(final Position pos) {
        final var matchingProperties = filterByPropriety(FurniturePropriety.OBSTRUCTING);
        return createObstructingFurnitureByItem(pos, matchingProperties.get(random.nextInt(matchingProperties.size())));
    }

    @Override
    public Furniture createInteractiveFurnitureLoot(final Position pos, final InventoryItemType itemType) {
        final var matchingProperties = filterByPropriety(FurniturePropriety.INTERACTIVE);
        final FurnitureType type = matchingProperties.get(random.nextInt(matchingProperties.size()));
        final var desc = furnitureInfo.get(type);
        final InventoryItem item = new InventoryItemFactoryImpl().createItem(itemType);
        return new InteractiveFurnitureImpl(pos, type.getName(), desc, type, item);
    }

    @Override
    public Furniture createWalkableFurnitureEnemy(final Position pos, final Enemy enemy) {
        final var matchingProperties = filterByPropriety(FurniturePropriety.INTERACTIVE);
        final FurnitureType type = matchingProperties.get(random.nextInt(matchingProperties.size()));
        final var desc = furnitureInfo.get(type);
        return new WalkableFurnitureImpl(pos, type.getName(), desc, type, enemy);
    }

    private List<FurnitureType> filterByPropriety(final FurniturePropriety type) {
        return furnitureInfo.keySet().stream()
        .filter(t -> t.getFurniturePropriety().equals(type)).toList();
    }

}
