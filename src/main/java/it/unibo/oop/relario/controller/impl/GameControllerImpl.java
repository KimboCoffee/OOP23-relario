package it.unibo.oop.relario.controller.impl;

import java.util.Map;

import it.unibo.oop.relario.controller.api.GameController;
import it.unibo.oop.relario.controller.api.MainController;
import it.unibo.oop.relario.utils.impl.Direction;
import it.unibo.oop.relario.utils.impl.Event;
import it.unibo.oop.relario.utils.impl.GameState;
import it.unibo.oop.relario.view.api.MainView;
import it.unibo.oop.relario.view.impl.GameView;

/**
 * Implementation for the Game Controller.
 */
public final class GameControllerImpl implements GameController {

    private final Map<Event, Direction> inputToDirection;
    private final MainController controller;
    private final MainView view;
    private Thread gameLoop;

    /**
     * Constructor for the game controller.
     * @param controller its own container class, used to access the Model.
     * @param view the main View of the application, used to access the Game View.
     */
    public GameControllerImpl(final MainController controller, final MainView view) {
        this.controller = controller;
        this.view = view;
        this.inputToDirection = Map.of(
            Event.MOVE_UP,
            Direction.UP,
            Event.MOVE_DOWN,
            Direction.DOWN,
            Event.MOVE_RIGHT,
            Direction.RIGHT,
            Event.MOVE_LEFT,
            Direction.LEFT
        );
    }

    @Override
    public void run() {
        this.controller.moveToNextRoom();
        if (this.controller.getCurRoom().isPresent()) {
            this.startGameLoop();
        }
    }

    @Override
    public void resume(final boolean isExploring) {
        if (!isExploring) {
            this.view.showPanel(GameState.GAME_OVER.getState());
        } else {
            this.startGameLoop();
        }
    }

    @Override
    public void notify(final Event e) {
        switch (e) {
            case INTERACT -> new InteractionsHandlerImpl().handleInteraction(
                this.controller.getCurRoom().get(),
                this.controller
            );
            case INVENTORY -> this.changeGameState(GameState.INVENTORY.getState());
            case ESCAPE -> this.changeGameState(GameState.MENU_IN_GAME.getState());
            default -> this.handleMovement(e);
        }
    }

    private void startGameLoop() {
        this.view.showPanel(GameState.GAME.getState());
        this.gameLoop = new GameLoop(
            (GameView) this.view.getPanel(GameState.GAME.getState()),
            this.controller.getCurRoom().get()
        );
        this.gameLoop.start();
    }

    private void changeGameState(final String gameState) {
        this.gameLoop.interrupt();
        this.view.showPanel(gameState);
    }

    private void handleMovement(final Event e) {
        this.controller.getCurRoom().get().getPlayer().setMovement(
            this.inputToDirection.get(e)
        );
    }
}
