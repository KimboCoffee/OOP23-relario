package it.unibo.oop.relario.controller.impl;

import it.unibo.oop.relario.controller.api.InteractionsHandler;
import it.unibo.oop.relario.controller.api.MainController;
import it.unibo.oop.relario.model.entities.enemies.Enemy;
import it.unibo.oop.relario.model.entities.furniture.api.InteractiveFurniture;
import it.unibo.oop.relario.model.entities.furniture.api.WalkableFurniture;
import it.unibo.oop.relario.model.entities.npc.Npc;
import it.unibo.oop.relario.model.map.Room;
import it.unibo.oop.relario.utils.impl.GameState;
import it.unibo.oop.relario.view.impl.GameView;

/**
 * Implementation for the game's interactions handler.
 */
public final class InteractionsHandlerImpl implements InteractionsHandler {

    private final MainController controller;

    /**
     * Constructor for the game's interaction handler.
     * @param controller the controller for return calls.
     */
    public InteractionsHandlerImpl(final MainController controller) {
        this.controller = controller;
    }

    @Override
    public void handleInteraction(final Room curRoom) {
        if (
            curRoom.getPlayer().getPosition().get().equals(curRoom.getExit())
            && (curRoom.getQuest().isEmpty() || curRoom.getQuest().get().isCompleted(curRoom))
        ) {
            this.controller.getCutSceneController().show(GameState.GAME);
        } else {
            final var entity = curRoom.getCellContent(
                curRoom.getPlayer().getDirection().move(curRoom.getPlayer().getPosition().get())
            );
            if (entity.isPresent()) {
                if (entity.get() instanceof Npc) {
                    this.interactWithNpc((Npc) entity.get(), curRoom);
                } else if (entity.get() instanceof Enemy) {
                    this.startEnemyCombat();
                } else if (entity.get() instanceof InteractiveFurniture) {
                    this.interactWithFurniture((InteractiveFurniture) entity.get(), curRoom);
                } else if (entity.get() instanceof WalkableFurniture) {
                    if (((WalkableFurniture) entity.get()).hasEnemy()) {
                        this.startEnemyCombat();
                    } else {
                        this.showOutputText("<html> Qui non c'e' nessuno<html>");
                        this.resumeGame();
                    }
                } else {
                    this.resumeGame();
                }
            } else {
                this.resumeGame();
            }
        }
    }

    private void interactWithNpc(final Npc npc, final Room curRoom) {
        final var output = npc.interact();
        if (output.getLoot().isPresent()) {
            if (curRoom.getPlayer().addToInventory(output.getLoot().get())) {
                npc.confirmLootTaken();
                this.showOutputText("<html>" + output.getDialogue() + "<br>+ 1 " + output.getLoot().get().getName() + "<html>");
            } else {
                this.showOutputText("<html>Sembra che io non abbia pi&ugrave spazio per questo oggetto...<html>");
            }
        } else {
            this.showOutputText(output.getDialogue());
        }
        this.resumeGame();
    }

    private void startEnemyCombat() {
        final var panel = controller.getMainView().getPanel(GameState.GAME);
        if (panel instanceof GameView) {
            ((GameView) panel).stopSoundTrack();
        }
        this.controller.getCombatController().initializeCombat();
    }

    private void interactWithFurniture(final InteractiveFurniture furniture, final Room curRoom) {
        if (furniture.hasLoot()) {
            final var loot = furniture.dropLoot();
            if (curRoom.getPlayer().addToInventory(loot)) {
                this.showOutputText("<html>Ecco qualcosa che mi torner&agrave utile!<br>+ 1 " + loot.getName() + "<html>");
            } else {
                this.showOutputText("<html>Sembra che io non abbia pi&ugrave spazio per questo oggetto...<html>");
                furniture.addLoot(loot);
            }
        } else {
            this.showOutputText("<html>Qui dentro non c'&egrave niente<html>");
        }
        this.resumeGame();
    }

    private void showOutputText(final String text) {
        final var gameView = this.controller.getMainView().getPanel(GameState.GAME);
        if (gameView instanceof GameView) {
            ((GameView) gameView).showInteractionText(text);
        }
    }

    private void resumeGame() {
        this.controller.getGameController().run(this.controller.getCurRoom().isPresent());
    }

}
