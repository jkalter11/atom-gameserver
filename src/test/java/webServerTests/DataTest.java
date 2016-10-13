package webServerTests;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.junit.Assert;
import org.junit.Test;
import webserver.APIServlet;

/**
 * Created by xakep666 on 13.10.16.
 *
 * Unit tests for Data API
 */
public class DataTest {
    private static final String SERVICE_URL = "http://localhost:"+ APIServlet.PORT+"/";
    @Test
    public void getLoggedInTest() {
        APIServlet.base.register("user1","pass");
        APIServlet.base.register("user2","pass");
        APIServlet.base.requestToken("user1","pass");

        String requestUrl = SERVICE_URL + "data/users";
        Request request =new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            new Thread(() -> {
                try {
                    APIServlet.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    Assert.fail(e.toString());
                }
            }).start();
            Thread.sleep(30000); //wait till server starts
            OkHttpClient httpClient = new OkHttpClient();
            Response resp = httpClient.newCall(request).execute();
            Assert.assertTrue(resp.isSuccessful());
            Assert.assertEquals(resp.body().string(),"{\"users\":[\"user1\"]}");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
