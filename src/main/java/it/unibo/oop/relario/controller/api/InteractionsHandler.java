package it.unibo.oop.relario.controller.api;

import it.unibo.oop.relario.model.map.Room;

/**
 * Interface for the game's interactions handler.
 */
public interface InteractionsHandler {

    /**
     * Handles interactions requested by the user input.
     * @param curRoom the room surrounding the interaction request.
     */
    void handleInteraction(Room curRoom);

}
