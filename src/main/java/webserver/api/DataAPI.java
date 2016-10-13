package webserver.api;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import webserver.APIServlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by xakep666 on 13.10.16.
 *
 * Provides REST API for work with users
 */
@Path("/data")
public class DataAPI {
    @NotNull
    private static final Logger log = LogManager.getLogger(MatchMakerAPI.class);

    private static final class UserInfo {
        String[] users;
    }

    /**
     * Method retrieves logged in users (with valid tokens) and serializes it to json
     * like {users: ["user1","user2",...]}
     * @return serialized list
     */
    @GET
    @Produces("application/json")
    @Path("users")
    public Response loggedInUsers() {
        log.info("Logged in users list requested");
        Gson gson = new Gson();
        List<String> users = APIServlet.base.getLoggedInUsers();
        UserInfo ret = new UserInfo();
        ret.users=users.toArray(new String[0]);
        return Response.ok(gson.toJson(ret)).build();
    }
}
