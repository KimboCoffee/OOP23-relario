package it.unibo.oop.relario.model.entities.enemies;

import it.unibo.oop.relario.model.GameEntityType;
import it.unibo.oop.relario.model.inventory.EffectType;

/**
 * Enumeration representing the different types of enemies.
 * Each type is associated with a specific effect and every enemy 
 * can drop only a reward with an effect that corresponds to it.
 */

public enum EnemyType implements GameEntityType {

    /** Represents a thief enemy, who can drop only inventory items with no effect. */
    THIEF("Ladro", EffectType.NONE),

    /** Represents a soldier enemy, who can drop only inventory items with damage effect. */
    SOLDIER("Soldato", EffectType.DAMAGE),

    /** Represents a knight enemy, who can drop only inventory items with protection effect. */
    KNIGHT("Cavaliere", EffectType.PROTECTION),

    /** Represents a wizard enemy, who can drop only inventory items with healing effect. */
    WIZARD("Mago", EffectType.HEALING),

    /** Represents a boss enemy, who can drop only inventory items with quest effect. */
    BOSS("Boss", EffectType.QUEST);

    private final EffectType effect;
    private final String name;

    EnemyType(final String name, final EffectType effect) {
        this.name = name;
        this.effect = effect;
    }

    /**
     * Retrieves the effect associated with the items that the enemy can drop.
     * @return the effect of the enemy
     */
    public EffectType getEffect() {
        return this.effect;
    }

    /**
     * Retrieves the name of the enemy type.
     * @return the name of the enemy
     */
    public String getName() {
        return this.name;
    }
}
