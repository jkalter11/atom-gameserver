package matchmaker;

import model.Player;
import model.session.GameSession;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides (searches or creates) {@link GameSession} for {@link Player}
 *
 * @author Alpi
 */
public interface MatchMaker {
  /**
   * Searches available game session or creates new one
   * @param player player to join the game session
   */
  void joinGame(@NotNull Player player);

  /**
   * @return Currently open game sessions
   */
  @NotNull
  List<GameSession> getActiveGameSessions();

  /**
   * Removes player from active session
   * @param player player who want leave
   */
  void leaveGame(@NotNull Player player);
}
