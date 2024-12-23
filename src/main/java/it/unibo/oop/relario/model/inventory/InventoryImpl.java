package it.unibo.oop.relario.model.inventory;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the player's inventory.
 */
public final class InventoryImpl implements Inventory {

    private static final int MAX_OCCUPATION = 8;

    private final List<InventoryItem> items;
    private int occupation;

    /**
     * Initialises a new empty inventory.
     */
    public InventoryImpl() {
        this.items = new LinkedList<>();
        this.occupation = 0;
    }

    @Override
    public boolean removeItem(final InventoryItem item) {
        if (items.remove(item)) {
            occupation--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addItem(final InventoryItem item) {
        if (occupation < MAX_OCCUPATION && items.add(item)) {
            occupation++;
            return true;
        }
        return false;
    }

    @Override
    public List<InventoryItem> getItemsList() {
        return List.copyOf(items);
    }
}
