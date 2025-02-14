package it.unibo.oop.relario.model.entities.living;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.oop.relario.model.inventory.EffectType;
import it.unibo.oop.relario.model.inventory.InventoryItem;
import it.unibo.oop.relario.model.inventory.InventoryItemFactory;
import it.unibo.oop.relario.model.inventory.InventoryItemFactoryImpl;
import it.unibo.oop.relario.model.inventory.InventoryItemType;
import it.unibo.oop.relario.utils.impl.Constants;
import it.unibo.oop.relario.utils.impl.Direction;
import it.unibo.oop.relario.utils.impl.PositionImpl;

/*
 * CHECKSTYLE: MagicNumber OFF
 * Used to avoid CheckStyle violations for magic numbers, here used for test scenarios. 
 */

/**
 * The test class for the Main Character class.
 */
class MainCharacterTest {
    /**
     * A method to test the player's inventory.
     */
    @Test
    void testInventory() {
        final MainCharacter chara = new MainCharacterImpl();
        final InventoryItemFactory itemFactory = new InventoryItemFactoryImpl();
        final InventoryItem healing = itemFactory.createRandomItemByEffect(EffectType.HEALING);
        final InventoryItem weapon = itemFactory.createRandomItemByEffect(EffectType.DAMAGE);
        final InventoryItem armor = itemFactory.createRandomItemByEffect(EffectType.PROTECTION);

        assertFalse(chara.useItem(healing));

        assertTrue(chara.addToInventory(healing));
        assertTrue(chara.addToInventory(weapon));
        assertTrue(chara.addToInventory(armor));
        assertEquals(
            List.of(healing, weapon, armor),
            chara.getItems()
        );

        for (int i = 0; i < Constants.PLAYER_INVENTORY_CAPACITY - 3; i++) {
            assertTrue(chara.addToInventory(itemFactory.createRandomItem()));
        }
        assertFalse(chara.addToInventory(itemFactory.createRandomItem()));

        assertTrue(chara.getEquippedArmor().isEmpty());
        assertTrue(chara.getEquippedWeapon().isEmpty());

        final int baseAtk = chara.attack();
        assertTrue(chara.useItem(weapon));
        assertTrue(chara.getEquippedWeapon().isPresent());
        assertEquals(weapon, chara.getEquippedWeapon().get());
        assertEquals(chara.attack(), baseAtk + weapon.getIntensity());
        assertTrue(chara.addToInventory(itemFactory.createRandomItem()));

        assertTrue(chara.useItem(armor));
        assertTrue(chara.getEquippedArmor().isPresent());
        assertEquals(armor, chara.getEquippedArmor().get());
        chara.attacked(Constants.PLAYER_LIFE);
        assertEquals(armor.getIntensity(), chara.getLife());

        assertTrue(chara.useItem(healing));
        assertEquals(armor.getIntensity() + healing.getIntensity(), chara.getLife());

        assertFalse(chara.useItem(healing));

        assertTrue(chara.discardItem(chara.getItems().get(0)));
    }

    /**
     * A method to test the player's movement mechanisms.
     */
    @Test
    void testMovement() {
        final MainCharacter chara = new MainCharacterImpl();
        chara.setPosition(new PositionImpl(0, 0));
        for (int i = 0; i < 5; i++) {
            chara.update();
        }

        chara.setMovement(Direction.RIGHT);
        for (int i = 0; i < 5; i++) {
            chara.update();
        }

        chara.stop();
        for (int i = 0; i < 5; i++) {
            chara.update();
        }

        assertSame(chara.getDirection(), Direction.RIGHT);

        chara.setMovement(Direction.DOWN);
        for (int i = 0; i < 5; i++) {
            chara.update();
        }

        assertTrue(
            chara.getPosition().get().getX() == 5
            && chara.getPosition().get().getY() == 5
        );
    }

    /**
     * A method to test the player's behavior in combat scenarios.
     */
    @Test
    void testCombatScenarios() {
        final MainCharacter chara = new MainCharacterImpl();

        assertEquals(chara.attack(), Constants.PLAYER_ATK);

        final InventoryItem toyKnife = new InventoryItemFactoryImpl().createItem(InventoryItemType.DAGGER);
        chara.addToInventory(toyKnife);
        chara.useItem(toyKnife);

        for (int i = 0; i < 3; i++) {
            assertEquals(chara.attack(), Constants.PLAYER_ATK + toyKnife.getIntensity());
        }
        assertEquals(Constants.PLAYER_ATK, chara.attack());

        final InventoryItem oldTutu = new InventoryItemFactoryImpl().createItem(InventoryItemType.BASICARMOR);
        chara.addToInventory(oldTutu);
        chara.useItem(oldTutu);

        chara.attacked(oldTutu.getIntensity() - 1);
        assertEquals(Constants.PLAYER_LIFE, chara.getLife());

        for (int i = 0; i < 3; i++) {
            chara.attacked(Constants.PLAYER_LIFE / 3);
            assertTrue(chara.getLife() > 0);
        }
        chara.attacked(Constants.PLAYER_LIFE);
        assertEquals(0, chara.getLife());
    }
}
