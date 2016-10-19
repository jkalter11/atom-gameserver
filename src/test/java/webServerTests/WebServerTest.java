package webServerTests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import webserver.APIServlet;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by xakep666 on 19.10.16.
 * Class contains logic needed to run server and stop server
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AuthTest.class,DataTest.class,ProfileTest.class})
public class WebServerTest {
    static final String SERVICE_URL = "http://localhost:"+APIServlet.PORT+"/";
    private static Thread serverThread;
    private static SecureRandom sr = new SecureRandom();
    static String genRandomStr() {
        return new BigInteger(130, sr).toString(32);
    }

    @BeforeClass
    public static void startServer() {
        serverThread = new Thread(() -> {
            try {
                APIServlet.start();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail(e.toString());
            }
        });
        serverThread.start();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ignored) {

        }
    }

    @AfterClass
    public static void stopServer() {
        serverThread.interrupt();
    }
}
