package it.unibo.oop.relario.view.impl;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Base class for View.
 */
public final class MainView extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Inizializes the frame of the main view.
     */
    public MainView() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());
        this.setLocationByPlatform(true);
        this.setFocusable(true);
    }
}