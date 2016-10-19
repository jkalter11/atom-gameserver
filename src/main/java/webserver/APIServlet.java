package webserver;

import matchmaker.MatchMaker;
import matchmaker.SinglePlayerMatchMaker;
import model.database.InMemoryBase;
import model.database.UsersBase;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import webserver.auth.AuthenticationFilter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xakep666 on 12.10.16.
 *
 * Provides web-server to access all services using REST api
 */
public class APIServlet {
    public static final int PORT = 8080;
    private APIServlet() {}
    public static UsersBase base = new InMemoryBase();
    public static List<MatchMaker> matchMakers = new LinkedList<>();
    private static Server server;
    static {
        matchMakers.add(new SinglePlayerMatchMaker());
    }

    public static void start() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server = new Server(PORT);
        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                APIServlet.class.getPackage().getName()
        );

        jerseyServlet.setInitParameter(
                "com.sun.jersey.spi.container.ContainerRequestFilters",
                AuthenticationFilter.class.getCanonicalName()
        );

        server.start();
    }

    public static void join() throws Exception {
        server.join();
    }

    public static void destroy() {
        server.destroy();
    }

    public static void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
