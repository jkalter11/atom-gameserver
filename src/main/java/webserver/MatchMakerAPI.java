package webserver;

import com.google.gson.Gson;
import model.GameSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import webserver.auth.Authorized;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by xakep666 on 12.10.16.
 *
 * Provides REST API for MatchMaker
 */
@Path("/")
public class MatchMakerAPI {
    @NotNull
    private static final Logger log = LogManager.getLogger(MatchMakerAPI.class);

    /**
     * Method retrieves information about active game sessions and returns session ids as json
     * @return json-encoded session id list
     */
    @GET
    @Authorized
    @Produces("application/json")
    @Path("getActiveGameSessions")
    public Response getActiveGameSessions() {
        log.info("Active sessions list requested");
        Gson gson = new Gson();
        List<GameSession> activeSessions = new LinkedList<>();
        Server.matchMakers.forEach(mm -> activeSessions.addAll(mm.getActiveGameSessions()));
        List<UUID> sessionIDs = new ArrayList<>(activeSessions.size());
        activeSessions.forEach(as -> sessionIDs.add(as.getSessionID()));
        return Response.ok(gson.toJson(sessionIDs)).build();
    }
}
