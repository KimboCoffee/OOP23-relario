package it.unibo.oop.relario.view.api;

import javax.swing.JPanel;

/**
 * An interface modelling a factory of inventory view panels.
 */
public interface InventoryViewFactory {

    /**
     * Creates a panel with a string describing commands.
     * @return a JPanel with a command string.
     */
    JPanel createCommandPanel();

    /**
     * Creates a panel with title, items list, item description and equipped items subpanels. 
     * The panel uses border layout and subpanels are respectivly added in positions: north, west, center and east.
     * @return a JPanel showing title, items list, item description of the selected item and equipped items.
     */
    JPanel createContentPanel();

}
