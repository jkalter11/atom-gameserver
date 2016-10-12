package webserver.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import webserver.APIServlet;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/auth")
public class Authentication {
    private static final Logger log = LogManager.getLogger(Authentication.class);

    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("login") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (!APIServlet.base.register(user,password)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("login") String user,
                                     @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            // Authenticate the user using the credentials provided
            UUID token = APIServlet.base.requestToken(user,password);
            if (token == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            // Issue a token for the user
            log.info("User '{}' logged in", user);

            // Return the token on the response
            return Response.ok(token.toString()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    static void validateToken(String rawToken) throws Exception {
        UUID token = UUID.fromString(rawToken);
        if (!APIServlet.base.isValidToken(token)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", APIServlet.base.getTokenOwner(token));
    }
}