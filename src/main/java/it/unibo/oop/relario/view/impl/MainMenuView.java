package it.unibo.oop.relario.view.impl;

import java.awt.BorderLayout;
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
import it.unibo.oop.relario.utils.impl.GameKeyListener;
import it.unibo.oop.relario.view.api.MainView;

/**
 * View implementation for the main menu.
 */
public final class MainMenuView extends JPanel {

    private static final long serialVersionUID = 1L;
    private final static int INSETS = 3;
    private final static String GAME_NAME  = "RELARIO";
    private final MainView view;
    private final MainController controller;

    /**
     * Initializes a new menu view.
     * @param view is the main view that contains all the game panels.
     * @param elements are the menu elements that need to be added to the view.
     */
    public MainMenuView(final MainView view, final List<MenuElement> elements, 
    final MainController controller) {
        this.view = view;
        this.controller = controller;
        this.setLayout(new GridBagLayout());
        final GridBagConstraints gridc = new GridBagConstraints();
        gridc.gridy = 0;
        gridc.insets = new Insets(INSETS, INSETS, INSETS, INSETS);
        gridc.fill = GridBagConstraints.CENTER;

        final JLabel title = new JLabel(GAME_NAME);
        this.add(title);
        gridc.gridy++;

        for (final var elem: elements) {
            this.add(createButton(elem));
            gridc.gridy++;
        }

        this.addKeyListener(new GameKeyListener(view, controller));
        this.add(this, BorderLayout.CENTER);
    }

    private JButton createButton(MenuElement elem) {
        final JButton mybutton = new JButton(elem.getElemName());
        mybutton.addActionListener(e -> {
            if (e.getActionCommand().equals(Command.PLAY.getName())) {
                this.controller.getGameController().run();
            } else if (e.getActionCommand().equals(Command.CLOSE.getName())) {
                this.view.showPreviousPanel();
            } else if (e.getActionCommand().equals(Command.QUIT.getName())) {
                final int dialogResult = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to quit the game?", "Warning",
                    JOptionPane.YES_NO_OPTION);

                if (dialogResult == JOptionPane.YES_OPTION) {
                    for (final var f : Frame.getFrames()) {
                        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    }
                }
            }
        });
        return mybutton;
    }

}
