package matchmaker;

import model.GameSession;
import model.Player;
import model.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates {@link GameSession} for single player
 *
 * @author Alpi
 */
public class SinglePlayerMatchMaker implements MatchMaker {
  @NotNull
  private final Logger log = LogManager.getLogger(SinglePlayerMatchMaker.class);
  @NotNull
  private final List<GameSession> activeGameSessions = new ArrayList<>();

  /**
   * Creates new GameSession for single player
   *
   * @param player single player
   */
  @Override
  public void joinGame(@NotNull Player player) {
    GameSession newGameSession = createNewGame();
    activeGameSessions.add(newGameSession);
    boolean joined = newGameSession.join(player);
    if (log.isInfoEnabled()) {
      if (joined) {
        log.info(player + " joined " + newGameSession);
      } else {
        log.info(player + " could not join to " + newGameSession);
      }
    }
  }

  @NotNull
  public List<GameSession> getActiveGameSessions() {
    return new ArrayList<>(activeGameSessions);
  }

  /**
   * @return new GameSession
   */
  @NotNull
  private GameSession createNewGame() {
    return new SessionManager();
  }
}
