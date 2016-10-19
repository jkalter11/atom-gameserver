import webserver.APIServlet;

/**
 * Created by xakep666 on 15.10.16.
 *
 * Launcher class
 */
public class Main {
    public static void main(String[] args) {
        try {
            APIServlet.start();
            APIServlet.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            APIServlet.destroy();
        }
    }
}
