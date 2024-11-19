package it.unibo.oop.relario.model.inventoryitems;

/** 
 * This class represents an equippable item, extending the InventoryItemImpl class.
 * It adds fields that are specific to items that can be equipped.
 */

public class EquippableItem extends InventoryItemImpl {

    private final int intensity;
    private int durability;

    /**
     * Constructs an equippable item with the specified name, description, effect, intensitity and durability.
     * @param name of the item
     * @param description of the item
     * @param effect of the item
     * @param intensity of the item's effect
     * @param durability of the item, that states how long it can be used
     */
    public EquippableItem(final String name, final String description, final EffectType effect, 
    final int intensity, final int durability) {
        super(name, description, effect);
        this.intensity = intensity;
        this.durability = durability;
    }

    /**
     * Retrieves the intensity of the equippable item.
     * @return the intensity of the item
     */
    public int getIntensity() {
        return this.intensity;
    }

    /**
     * Retrieves the durability of the equippable item.
     * @return the durability of the item
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * Sets the durability of the equippable item.
     * @param durability new durability of the item
     */
    public void setDurability(final int durability) {
        this.durability = durability;
    } 

    /*
    public boolean decreaseDurability() {
        this.durability--;
        return this.durability > 0;
    }
    */
}