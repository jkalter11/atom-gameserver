package webServerTests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import webserver.APIServlet;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by xakep666 on 19.10.16.
 * Class contains logic needed to run server and stop server
 */
public class WebServerTest {
    static final String SERVICE_URL = "http://localhost:"+APIServlet.PORT+"/";
    private static SecureRandom sr = new SecureRandom();
    static String genRandomStr() {
        return new BigInteger(130, sr).toString(32);
    }

    @BeforeClass
    public static void startServer() {
        try {
            APIServlet.start();
            Thread.sleep(20000);
        } catch (InterruptedException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @AfterClass
    public static void stopServer() {
        APIServlet.stop();
        APIServlet.destroy();
    }
}
