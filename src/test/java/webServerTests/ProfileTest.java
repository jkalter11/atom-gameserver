package webServerTests;

import com.squareup.okhttp.*;
import org.junit.Assert;
import org.junit.Test;
import webserver.APIServlet;

import java.util.UUID;

/**
 * Created by xakep666 on 13.10.16.
 *
 * Unit tests for Profile API
 */
public class ProfileTest {
    private static final String SERVICE_URL = "http://localhost:"+ APIServlet.PORT+"/";
    @Test
    public void testSetNewName() {
        APIServlet.base.register("user1","pass");
        APIServlet.base.register("user2","pass");
        UUID token1 = APIServlet.base.requestToken("user1","pass");
        UUID token2 = APIServlet.base.requestToken("user2","pass");

        String requestUrl = SERVICE_URL + "profile/name";
        MediaType mType = MediaType.parse("raw");
        RequestBody body = RequestBody.create(mType,"name=user3");
        Request request1 =new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer "+token1)
                .build();
        Request request2 =new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer "+token2)
                .build();
        requestUrl = SERVICE_URL + "auth/login";
        RequestBody body2 = RequestBody.create(mType,"user=user3&password=pass");
        Request request3 =new Request.Builder()
                .url(requestUrl)
                .post(body2)
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
            Response resp = httpClient.newCall(request1).execute();
            Assert.assertTrue(resp.isSuccessful());
            resp = httpClient.newCall(request2).execute();
            Assert.assertEquals(resp.code(),javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE.getStatusCode());
            resp = httpClient.newCall(request3).execute();
            Assert.assertEquals(UUID.fromString(resp.body().string()),token1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
