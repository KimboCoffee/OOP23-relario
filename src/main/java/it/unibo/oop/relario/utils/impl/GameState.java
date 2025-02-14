package it.unibo.oop.relario.utils.impl;

/**
 * Utility static class containing all the game phases.
 */
public enum GameState {

    /** Start menu phase. */
    MENU("Menu"),

    /** In game menu phase. */
    MENU_IN_GAME("InGameMenu"),

    /** Exploring phase. */
    GAME("Game"),

    /** Inventory phase. */
    INVENTORY("Inventory"),

    /** Combat phase. */
    COMBAT("Combat"),

    /** Game over. */
    GAME_OVER("GameOver"),

    /** Positive ending of the game. */
    VICTORY_GOOD("Victory"),

    /** Negative ending of the game. */
    VICTORY_BAD("BadVictory"),

    /** Cut scene. */
    CUT_SCENE("CutScene"),

    /** Initial phase when application is launched. */
    NONE(null);

    private final String state;

    GameState(final String state) {
        this.state = state;
    }

    /**
     * Retrieves the state of the game.
     * @return the game state.
     */
    public String getState() {
        return this.state;
    }

}
