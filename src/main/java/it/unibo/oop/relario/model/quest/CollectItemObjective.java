package it.unibo.oop.relario.model.quest;

import it.unibo.oop.relario.model.entities.living.MainCharacter;
import it.unibo.oop.relario.model.inventory.InventoryItem;

public class CollectItemObjective implements ObjectiveStrategy {

    private final MainCharacter player;
    private final InventoryItem item; // ??

    public CollectItemObjective(final MainCharacter player, final InventoryItem item) {
        this.player = player;
        this.item = item;
    }

    @Override
    public boolean check() {
        return this.player.getItems().contains(item);
    }
    
}