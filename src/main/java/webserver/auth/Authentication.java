package webserver.auth;

import model.database.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import webserver.APIServlet;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/auth")
public class Authentication {
    private static final Logger log = LogManager.getLogger(Authentication.class);

    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (user.equals("") || password.equals("")) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
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
    public Response authenticateUser(@FormParam("user") String user,
                                     @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (user.equals("") || password.equals("")) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        try {
            // Authenticate the user using the credentials provided
            Token token = APIServlet.base.requestToken(user,password);
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
        Token token = Token.parse(rawToken);
        if (!APIServlet.base.isValidToken(token)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", APIServlet.base.getTokenOwner(token));
    }

    @POST
    @Authorized
    @Path("logout")
    @Produces("text/plain")
    public Response logout(@Context HttpHeaders headers) {
        Token token = AuthenticationFilter.getTokenFromHeaders(headers);
        if (token==null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        APIServlet.base.logout(token);
        return Response.ok("Logged out").build();
    }
}