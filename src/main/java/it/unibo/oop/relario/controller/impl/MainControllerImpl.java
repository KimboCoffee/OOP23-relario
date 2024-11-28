package it.unibo.oop.relario.controller.impl;

import it.unibo.oop.relario.controller.api.CombatController;
import it.unibo.oop.relario.controller.api.GameController;
import it.unibo.oop.relario.controller.api.InventoryController;
import it.unibo.oop.relario.controller.api.MainController;
import it.unibo.oop.relario.controller.api.MainMenuController;
import it.unibo.oop.relario.view.api.MainView;
import it.unibo.oop.relario.view.impl.MainViewImpl;

public class MainControllerImpl implements MainController {

    private final CombatController combat;
    private final GameController game;
    private final InventoryController inventory;
    private final MainMenuController mainMenu;
    private final MainView view;

    public MainControllerImpl() {
        this.combat = null;
        this.game = null;
        this.inventory = null;
        this.mainMenu = null;
        this.view = new MainViewImpl(this);
    }

    @Override
    public CombatController getCombatController() {
        return this.combat;
    }

    @Override
    public GameController getGameController() {
        return this.game;
    }

    @Override
    public InventoryController getInventoryController() {
        return this.inventory;
    }

    @Override
    public MainMenuController getMainMenuController() {
        return this.mainMenu;
    }
}