package it.unibo.oop.relario.controller.impl;

import java.util.Map;

import it.unibo.oop.relario.controller.api.GameController;
import it.unibo.oop.relario.controller.api.InteractionsHandler;
import it.unibo.oop.relario.controller.api.MainController;
import it.unibo.oop.relario.model.Interactions;
import it.unibo.oop.relario.utils.impl.Direction;
import it.unibo.oop.relario.utils.impl.Event;
import it.unibo.oop.relario.utils.impl.GameState;
import it.unibo.oop.relario.view.api.MainView;
import it.unibo.oop.relario.view.impl.GameView;

/**
 * Implementation for the Game Controller.
 */
public final class GameControllerImpl implements GameController {

    private final InteractionsHandler interactionsHandler;
    private final Map<Event, Direction> inputToDirection;
    private final MainController controller;
    private final MainView mainView;
    private Thread gameLoop;

    /**
     * Constructor for the game controller.
     * @param controller its own container class, used to access the Model.
     */
    public GameControllerImpl(final MainController controller) {
        this.controller = controller;
        this.mainView = this.controller.getMainView();
        this.interactionsHandler = new InteractionsHandlerImpl(this.controller);
        this.inputToDirection = Map.of(
            Event.MOVE_UP, Direction.UP,
            Event.MOVE_DOWN, Direction.DOWN,
            Event.MOVE_RIGHT, Direction.RIGHT,
            Event.MOVE_LEFT, Direction.LEFT
        );
    }

    @Override
    public void run(final boolean isExploring) {
        if (this.gameLoop == null || this.gameLoop.isInterrupted()) {
            if (!isExploring) {
                this.endGame();
            } else {
                this.startGameLoop();
            }
        }
    }

    @Override
    public void notify(final Event e) {
        switch (e) {
            case INTERACT -> {
                if (this.canInteract()) {
                    this.gameLoop.interrupt();
                    this.interactionsHandler.handleInteraction(
                        this.controller.getCurRoom().get()
                    );
                }
            }
            case INVENTORY -> this.changeGameState(GameState.INVENTORY);
            case ESCAPE -> this.changeGameState(GameState.MENU_IN_GAME);
            default -> this.handleMovement(e);
        }
    }

    private void startGameLoop() {
        this.mainView.showPanel(GameState.GAME);
        final var panel = this.mainView.getPanel(GameState.GAME);
        if (panel instanceof GameView) {
            ((GameView) panel).startSoundTrack();
        }
        this.gameLoop = new GameLoop(this.controller);
        this.gameLoop.start();
    }

    private void changeGameState(final GameState state) {
        this.gameLoop.interrupt();
        switch (state) {
            case INVENTORY -> this.controller.getInventoryController().init(GameState.GAME);
            case MENU_IN_GAME -> this.controller.getMenuController().showMenu(GameState.MENU_IN_GAME, GameState.GAME);
            default -> this.endGame();
        }
    }

    private boolean canInteract() {
        return this.controller.getCurRoom().get().getPlayer().getPosition().isPresent()
            && (
                (this.controller.getCurRoom().get().getExit().equals(
                    this.controller.getCurRoom().get().getPlayer().getPosition().get()
                ) && (this.controller.getCurRoom().get().getQuest().isEmpty()
                || this.controller.getCurRoom().get().getQuest().get().isCompleted(this.controller.getCurRoom().get()))
                ) || Interactions.canInteract(
                    this.controller.getCurRoom().get().getPlayer().getPosition().get(),
                    this.controller.getCurRoom().get().getPlayer().getDirection(),
                    this.controller.getCurRoom().get().getPopulation(),
                    this.controller.getCurRoom().get().getFurniture()
                )
            );
    }

    private void handleMovement(final Event e) {
        if (e.equals(Event.RELEASED)) {
            this.controller.getCurRoom().get().getPlayer().stop();
        } else if (this.inputToDirection.get(e) != null) {
            this.controller.getCurRoom().get().getPlayer().setMovement(
                this.inputToDirection.get(e)
            );
        }
    }

    private void endGame() {
        if (this.controller.getCurRoom().isEmpty()) {
            this.controller.getCutSceneController().show(GameState.VICTORY_GOOD);
        } else {
            this.controller.getCutSceneController().show(GameState.GAME_OVER);
        }
    }
}
