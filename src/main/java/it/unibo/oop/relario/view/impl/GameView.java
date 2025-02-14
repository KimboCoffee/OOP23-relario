package it.unibo.oop.relario.view.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.awt.GridLayout;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.JPanel;

import it.unibo.oop.relario.controller.api.MainController;
import it.unibo.oop.relario.utils.api.Dimension;
import it.unibo.oop.relario.utils.api.Position;
import it.unibo.oop.relario.utils.impl.Constants;
import it.unibo.oop.relario.utils.impl.GameKeyListener;
import it.unibo.oop.relario.utils.impl.GameTexturesLocator;
import it.unibo.oop.relario.utils.impl.SoundLocators;
import it.unibo.oop.relario.view.api.GameViewComponentManager;

/**
 * View implementations for the exploration phase of the game.
 */
public final class GameView extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final double SCREEN_TO_MAP_RATIO = 1.5;
    private static final double VOLUME = 0.5;
    private static final int GRID_GAP = 0;
    private static final String SONG_URL = "soundtrack/game";

    private final JPanel upperPanel;
    private final JPanel mapPanel;
    private final JPanel lowerPanel;
    private final transient GameViewComponentManager componentManager;
    private transient Clip song;
    private final List<BackgroundTile> background;
    private final List<String> commands;
    private final List<Position> foreground;
    private transient Dimension mapDimension;
    private int tileDimension;

    /**
     * The constructor for the game view.
     * @param controller the observer for user input events.
     */
    public GameView(final MainController controller) {
        this.componentManager = new GameViewComponentManagerImpl();
        this.commands = List.of("WASD - movimento / ", "E - interazione / ", "I - inventario");

        this.upperPanel = this.componentManager.getGamePanel();
        this.mapPanel = this.componentManager.getGamePanel();
        this.lowerPanel = this.componentManager.getGamePanel();
        this.add(this.upperPanel);
        this.add(this.mapPanel);
        this.add(this.lowerPanel);
        this.setBackground(Constants.BACKGROUND_SCENE_COLOR);

        this.background = new LinkedList<>();
        this.foreground = new LinkedList<>();
        this.addKeyListener(new GameKeyListener(controller.getGameController()));
    }

    /**
     * Renders the scene's background, sized to fit in the current frame.
     * @param dimension the map dimension.
     * @param textures the textures to be rendered on the background, apart from the floor.
     */
    public void renderBackground(final Dimension dimension, final Map<Position, Image> textures) {
        this.mapDimension = dimension;
        this.tileDimension = (int) Math.min(
            this.getHeight() / SCREEN_TO_MAP_RATIO / this.mapDimension.getHeight(),
            this.getWidth() / SCREEN_TO_MAP_RATIO / this.mapDimension.getWidth()
        );
        this.renderFloor(dimension);
        this.renderBackgroundTextures(textures);
    }

    /**
     * Renders the scene's foreground, sizing the textures to fit on their background.
     * @param textures the textures to be rendered on the foreground of the scene.
     */
    public void renderTextures(final Map<Position, Image> textures) {
        this.foreground.forEach(pos -> {
                this.background.get(this.computeIndex(pos.getX(), pos.getY())).removeAll();
                this.refresh(this.background.get(this.computeIndex(pos.getX(), pos.getY())));
        });

        textures.forEach((pos, texture) -> {
            this.background.get(this.computeIndex(pos.getX(), pos.getY())).add(
                this.componentManager.getForegroundTile(texture, this.tileDimension)
            );
            this.foreground.add(pos);
            this.refresh(this.background.get(this.computeIndex(pos.getX(), pos.getY())));
        });
    }

    /**
     * Renders the text resulting from an interaction in the lower panel.
     * @param text the text to be shown.
     */
    public void showInteractionText(final String text) {
        this.updateComponent(this.lowerPanel, List.of(text));
    }

    /**
     * Starts the menu sound track.
     */
    public void startSoundTrack() {
        if (this.song == null || !this.song.isRunning()) {
            this.song = SoundLocators.getAudio(SONG_URL, VOLUME);
            this.song.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Stops the menu sound track.
     */
    public void stopSoundTrack() {
        this.song.close();
    }

    private void renderFloor(final Dimension dimension) {
        this.mapPanel.removeAll();
        this.background.clear();

        this.resizePanels();
        this.mapPanel.setLayout(new GridLayout(
            this.mapDimension.getHeight(),
            this.mapDimension.getWidth(),
            GRID_GAP,
            GRID_GAP
        ));

        final var texture = GameTexturesLocator.getFloorTexture();

        for (int y = 0; y < dimension.getHeight(); y++) {
            for (int x = 0; x < dimension.getWidth(); x++) {
                final var tile = this.componentManager.getBackgroundTile(texture, this.tileDimension);
                this.background.add(
                    this.computeIndex(x, y),
                    tile
                );
                this.mapPanel.add(
                    tile,
                    this.computeIndex(x, y)
                );
            }
        }
    }

    private void renderBackgroundTextures(final Map<Position, Image> textures) {
        textures.forEach((pos, texture) -> {
            final var innerTile = this.componentManager.getBackgroundTile(texture, this.tileDimension);
            innerTile.setOpaque(false);
            final var outerTile = this.background.get(this.computeIndex(pos.getX(), pos.getY()));

            outerTile.add(innerTile);
            this.refresh(outerTile);

            this.background.remove(this.computeIndex(pos.getX(), pos.getY()));
            this.background.add(this.computeIndex(pos.getX(), pos.getY()), innerTile);
        });
    }

    private void resizePanels() {
        this.componentManager.resizeComponent(
            this.mapPanel,
            this.mapDimension.getWidth() * this.tileDimension,
            this.mapDimension.getHeight() * this.tileDimension
        );

        this.componentManager.resizeComponent(
            this.upperPanel,
            this.getWidth(),
            (this.getHeight() - (int) this.mapPanel.getPreferredSize().getHeight()) / 3
        );
        this.updateComponent(this.upperPanel, this.commands);

        this.componentManager.resizeComponent(
            this.lowerPanel,
            this.getWidth(),
            (this.getHeight() - (int) this.mapPanel.getPreferredSize().getHeight()) / 2
        );
        this.refresh(this);
    }

    private void updateComponent(final JComponent component, final List<String> text) {
        component.removeAll();
        text.forEach(string -> this.componentManager.showText(component, string));
    }

    private void refresh(final JComponent component) {
        component.revalidate();
        component.repaint();
    }

    private int computeIndex(final int x, final int y) {
        return this.mapDimension == null ? 0 : (x + y * this.mapDimension.getWidth());
    }
}
