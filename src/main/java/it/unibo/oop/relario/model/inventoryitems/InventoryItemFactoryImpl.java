package it.unibo.oop.relario.model.inventoryitems;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Implementation of the item factory. 
 * This class provides the logic to create different types of inventory items.
 * It has a Random object to generate random items and a map that associates each inventory item type
 * with its corresponding creation logic.
 */

public final class InventoryItemFactoryImpl implements InventoryItemFactory {

    private final Random random = new Random();
    private final Map<InventoryItemType, Supplier<InventoryItem>> itemCreator = Arrays.stream(InventoryItemType.values())
        .collect(Collectors.toMap(type -> type, type -> () -> createItemByType(type)));

    @Override
    public InventoryItem createItem(final InventoryItemType type) {
        return itemCreator.get(type).get();
    }

    @Override
    public EquippableItem createEquippableItem(final InventoryItemType type) {
        final InventoryItem item = createItem(type);
        if (!(item instanceof EquippableItem)) {
            throw new IllegalArgumentException();
        }
        return (EquippableItem) item;
    }

    @Override
    public InventoryItem createRandomItem() {
        return itemCreator.get(InventoryItemType.values()[random.nextInt(InventoryItemType.values().length)]).get();
    }

    @Override
    public InventoryItem createRandomItemByEffect(final EffectType effect) {
        final List<InventoryItemType> matchingTypes = itemCreator.keySet().stream()
            .filter(type -> type.getEffect().equals(effect)).toList();

        return itemCreator.get(matchingTypes.get(random.nextInt(matchingTypes.size()))).get();
    }

    private InventoryItem createItemByType(final InventoryItemType type) {
        switch (type) {
            case SWORD:
                return new EquippableItem("Spada", "Spada affilata, perfetta per attacchi rapidi e precisi", 
                    InventoryItemType.SWORD.getEffect(), 10, 10);
            case BOW:
                return new EquippableItem("Arco", "Arco leggero e preciso, ideale per attacchi a lunga distanza", 
                    InventoryItemType.BOW.getEffect(), 8, 5);
            case DAGGER:
                return new EquippableItem("Pugnale", "Un'arma leggera e affilata, perfetta per attacchi rapidi e furtivi", 
                    InventoryItemType.DAGGER.getEffect(), 5, 3);
            case HAMMER:
                return new EquippableItem("Martello", "Un'arma pesante e devastante, progettata per infliggere danni enormi", 
                    InventoryItemType.HAMMER.getEffect(), 15, 8);
            case SHIELD:
                return new EquippableItem("Scudo", "Uno scudo robusto e affidabile, capace di bloccare colpi potenti", 
                    InventoryItemType.SHIELD.getEffect(), 10, 5);
            case BASICARMOR:
                return new EquippableItem("Armatura semplice", "Un'armatura leggera che offre protezione di base", 
                    InventoryItemType.BASICARMOR.getEffect(), 5, 3);
            case POTION:
                return new EquippableItem("Pozione", "Un liquido rosso che ripristina rapidamente la salute", 
                    InventoryItemType.POTION.getEffect(), 10, 1);
            case APPLE:
                return new EquippableItem("Mela", "Una mela fresca e succosa, perfetta per recuperare un po' di energia", 
                    InventoryItemType.APPLE.getEffect(), 3, 1);
            case AMULET:
                return new EquippableItem("Amuleto", "Un ciondolo antico e luminoso che emana un'aura di guarigione", 
                    InventoryItemType.AMULET.getEffect(), 15, 1);
            case COIN:
                return new InventoryItemImpl("Moneta", "Una moneta luccicante", 
                    InventoryItemType.COIN.getEffect());
            case GEMSTONE:
                return new InventoryItemImpl("Pietra preziosa", "Una gemma scintillante di rara bellezza", 
                    InventoryItemType.GEMSTONE.getEffect());
            default:
                throw new IllegalArgumentException();
        }

    }

}
