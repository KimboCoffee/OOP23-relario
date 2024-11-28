package it.unibo.oop.relario.view.impl;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.oop.relario.controller.api.MainController;
import it.unibo.oop.relario.view.api.MainView;

/**
 * Main container for View.
 */
public final class MainViewImpl implements MainView {

    private final JFrame frame;
    private final JPanel mainMenu;
    private final JPanel game;
    private final JPanel inventory;
    private final JPanel combat;
    private final MainController controller;

    /**
     * Inizializes the frame of the main view.
     */
    public MainViewImpl(final MainController controller) {
        this.frame = new JFrame();
        this.frameSetup();
        this.mainMenu = new MainMenuView();
        this.panelSetup(this.mainMenu);
        this.game = new GameView();
        this.panelSetup(this.game);
        this.inventory = new InventoryView();
        this.panelSetup(this.inventory);
        this.combat = new CombatView();
        this.panelSetup(this.combat);
        this.controller = controller;
    }

    private void frameSetup() {
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setLayout(new BorderLayout());
        this.frame.setLocationByPlatform(true);
        this.frame.setFocusable(true);
    }

    private void panelSetup(final JPanel panel) {
        this.frame.add(panel);
        panel.setVisible(false);
    }
}