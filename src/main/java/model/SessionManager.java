package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xakep666 on 07.10.16.
 *
 * Single game session manager
 */
public class SessionManager implements GameSession{
    private List<Player> players = new LinkedList<>();
    private GameField gameField = new GameField();
    private static Logger log = LogManager.getLogger(SessionManager.class);

    public SessionManager() {
        if (log.isDebugEnabled()) {
            log.debug("Created new session manager");
        }
    }

    public boolean join(@NotNull Player player) {
        if (players.size()==GameConstants.MAX_PLAYERS_IN_SESSION) return false;
        players.add(player);
        gameField.spawnPlayerCell(player);
        return true;
    }

    public void leave(@NotNull Player player) {
        gameField.removePlayerCells(player);
        players.removeIf(p -> p.equals(player));
    }
}
