package it.unibo.oop.relario.model.entities;

import java.util.Optional;

import it.unibo.oop.relario.utils.api.Position;
import it.unibo.oop.relario.utils.impl.Direction;
import it.unibo.oop.relario.utils.impl.PositionImpl;

/**
 * This class represents all living beings of the game and their common properties.
*/

public abstract class LivingBeingImpl implements LivingBeing {

    /** The number of updates after which the character changes direction. */
    public static final int DIRECTION_RANGE = 1;

    private boolean moving;
    private final String name;
    private Position position;
    private Direction direction;
    private int counter;

    /**
     * Constructs a new instance of living being.
     * @param name of the living being
     * @param position where the living being has to be set
     */
    public LivingBeingImpl(final String name, final Position position) {
        this.name = name;
        this.position = new PositionImpl(position.getX(), position.getY());
        this.direction = Direction.RIGHT;
        this.counter = 0;
        this.moving = true;
    }

    @Override
    public final Optional<Position> getPosition() {
        return Optional.of(new PositionImpl(this.position.getX(), this.position.getY()));
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final void update() {
        if (moving) {
            counter++;
            if (counter > DIRECTION_RANGE) {
                changeDirection();
                counter = 1;
            } 
            setPosition(this.direction.move(position));
        }
    }

    @Override
    public final Direction getDirection() {
        return this.direction;
    }

    /**
     * Allows to change the direction of the living beings.
     */
    public final void changeDirection() {
        this.direction = this.direction.equals(Direction.RIGHT) ? Direction.LEFT : Direction.RIGHT;
    }

    /**
     * Sets the moving status of the living being.
     * @param moving true if the entity is moving, false otherwise
     */
    public void setMoving(final boolean moving) {
        this.moving = moving;
    }

    private void setPosition(final Position position) {
        this.position = position;
    }

}
