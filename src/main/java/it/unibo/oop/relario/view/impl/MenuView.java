package it.unibo.oop.relario.view.impl;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.oop.relario.controller.api.MainController;
import it.unibo.oop.relario.model.menu.Command;
import it.unibo.oop.relario.model.menu.MenuElement;
import it.unibo.oop.relario.utils.impl.Constants;
import it.unibo.oop.relario.utils.impl.Event;
import it.unibo.oop.relario.utils.impl.GameKeyListener;
import it.unibo.oop.relario.utils.impl.GameState;
import it.unibo.oop.relario.view.api.MainView;

/**
 * View implementation for the main menu.
 */
public final class MenuView extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int INSETS = 3;
    private static final String GAME_NAME  = "RELARIO";
    private static final float FONT_SIZE = 28f;
    private final transient MainView view;
    private final transient MainController controller;

    /**
     * Initializes a new menu view.
     * @param view is the main view that contains all the game panels.
     * @param elements are the menu elements that need to be added to the view.
     * @param controller is the main controller.
     */
    public MenuView(final MainView view, final List<MenuElement> elements, 
    final MainController controller) {
        this.view = view;
        this.controller = controller;
        this.setLayout(new GridBagLayout());
        final GridBagConstraints gridc = new GridBagConstraints();
        gridc.gridy = 0;
        gridc.insets = new Insets(INSETS, INSETS, INSETS, INSETS);
        gridc.fill = GridBagConstraints.CENTER;

        if (this.view.getCurrentPanel().equals(GameState.MENU)) {
            final JLabel title = new JLabel(GAME_NAME);
            title.setFont(Constants.FONT.deriveFont(FONT_SIZE));
            this.add(title, gridc);
        }
        gridc.gridy++;
        gridc.fill = GridBagConstraints.BOTH;

        for (final var elem: elements) {
            this.add(createButton(elem), gridc);
            gridc.gridy++;
        }

        this.setBackground(Color.BLACK);
        this.addKeyListener(new GameKeyListener(controller.getMenuController()));
    }

    private JButton createButton(final MenuElement elem) {
        final JButton myButton = new JButton(elem.getElemName());
        myButton.setFont(Constants.FONT.deriveFont(FONT_SIZE));
        myButton.addActionListener(e -> {
            if (e.getActionCommand().equals(Command.PLAY.getName())) {
                this.controller.getCutSceneController().show(GameState.MENU);
            } else if (e.getActionCommand().equals(Command.CLOSE.getName())) {
                this.controller.getMenuController().notify(Event.ESCAPE);
            } else if (e.getActionCommand().equals(Command.QUIT.getName())) {
                final int dialogResult = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to quit the game?", "Warning",
                    JOptionPane.YES_NO_OPTION);

                if (dialogResult == JOptionPane.YES_OPTION) {
                    for (final var f : Frame.getFrames()) {
                        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    }
                } else {
                    this.requestFocus(true);
                }
            }
        });
        return myButton;
    }

}
