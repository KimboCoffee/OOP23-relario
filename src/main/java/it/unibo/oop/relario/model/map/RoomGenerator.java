package it.unibo.oop.relario.model.map;

import it.unibo.oop.relario.model.entities.living.MainCharacter;
import it.unibo.oop.relario.utils.api.Dimension;
import it.unibo.oop.relario.utils.impl.DimensionImpl;

/**
 * This class creates new rooms and handles the generation of furniture and living beings.
 */
public final class RoomGenerator {

    /** The default dimension of a room. */
    public static final Dimension DEFAULT_DIMENSION = new DimensionImpl(0, 0);

    private final FurnitureGenerator furnitureGenerator;
    private final LivingBeingsGenerator livingBeingsGenerator;

    /**
     * Constructs a new RoomGenerator instance.
     */
    public RoomGenerator() {
        this.furnitureGenerator = new FurnitureGenerator();
        this.livingBeingsGenerator = new LivingBeingsGenerator();
    }

    /**
     * Creates a new room with furniture and living beings.
     * @param player the main character that is placed in the new room
     * @return a new room
     */
    public Room createNewRoom(final MainCharacter player) {
        final Room newRoom = new RoomImpl(DEFAULT_DIMENSION, player);
        this.furnitureGenerator.generateFurniture(newRoom);
        this.livingBeingsGenerator.generateLivingBeings(newRoom);
        return newRoom;
    }

}
