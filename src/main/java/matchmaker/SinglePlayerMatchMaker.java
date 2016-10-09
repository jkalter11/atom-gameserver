package matchmaker;

import model.GameConstants;
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
    boolean joined;
    for(GameSession activeSession : activeGameSessions) {
      joined = activeSession.join(player);
      if (joined) {
        log.info("Player " + player + " joined to active session " + activeSession);
        break;
      }
    }
    log.info("Could not find acceptable sessions to join player " + player + ", creating new");
    GameSession newSession = createNewGame();
    joined = newSession.join(player);
    if (!joined) {
      log.error("Could not join new session, coding errors?");
    } else {
      activeGameSessions.add(newSession);
      log.info("Added session " + newSession + " to active sessions");
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
    return new SessionManager(GameConstants.MAX_PLAYERS_IN_SINGLE_SESSION);
  }

  @Override
  public void leaveGame(@NotNull Player player) {
    activeGameSessions.forEach(session -> session.leave(player));
    activeGameSessions.removeIf(session -> session.players()==0);
  }
}
