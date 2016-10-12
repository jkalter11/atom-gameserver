package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by xakep666 on 07.10.16.
 *
 * Single game session manager
 */
public class SessionManager implements GameSession{
    private List<Player> players = new LinkedList<>();
    private GameField gameField = new GameField();
    private static Logger log = LogManager.getLogger(SessionManager.class);
    private int maxPlayers;
    private UUID sessionId;

    public SessionManager(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.sessionId = UUID.randomUUID();
        if (log.isDebugEnabled()) {
            log.debug("Created new session manager, maxPlayers: "+maxPlayers);
        }
    }

    @Override
    public boolean join(@NotNull Player player) {
        if (players.size()==maxPlayers) return false;
        players.add(player);
        gameField.spawnPlayerCell(player);
        return true;
    }

    @Override
    public void leave(@NotNull Player player) {
        gameField.removePlayerCells(player);
        players.removeIf(p -> p.equals(player));
        log.info(String.format("Player \"%s\" left from session \"%s\"",player,this));
    }

    @Override
    public int players() {
        return players.size();
    }

    @Override
    public UUID getSessionID() {
        return sessionId;
    }
}
