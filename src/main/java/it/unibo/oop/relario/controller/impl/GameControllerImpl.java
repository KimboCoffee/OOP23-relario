package it.unibo.oop.relario.controller.impl;

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
    }

    @Override
    public void run() {
        if (this.progressRoom()) {
            this.startGameLoop();
        }
    }

    @Override
    public void resume(boolean isExploring) {
        if (!isExploring) {
            this.view.showPanel(GameState.GAME_OVER.getState());
        } else {
            this.startGameLoop();
        }
    }

    @Override
    public void notify(Event e) {
        switch (e) {
            case Event.INVENTORY -> this.view.showPanel(GameState.INVENTORY);
            case Event.ESCAPE -> this.view.showPanel(GameState.MENU_IN_GAME);
            case Event.INTERACT -> this.handleInteraction();
            default -> /* [TODO]: handle movement events. */ { }
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

    private boolean progressRoom() {
        this.controller.moveToNextRoom();
        return this.controller.getCurRoom().isPresent();
    }

    private void handleInteraction() {
        /* [TODO]: handle interaction scenarios. */
    }
}
