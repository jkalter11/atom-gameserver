package model;

import org.jetbrains.annotations.NotNull;

/**
 * Single agar.io game session
 * <p>Single game session take place in square map, where players battle for food
 * <p>Max {@link GameConstants#MAX_PLAYERS_IN_SESSION} players can play within single game session
 *
 * @author Alpi
 */
public interface GameSession {
  /**
   * Player can join session whenever there are less then {@link GameConstants#MAX_PLAYERS_IN_SESSION} players within game session
   *
   * @param player player to join the game
   * @return true if player joined, false otherwise (no free slots, etc.)
   */
  boolean join(@NotNull Player player);

  /**
   * Leave player from session
   * @param player player who leaves
   */
  void leave(@NotNull Player player);
}
