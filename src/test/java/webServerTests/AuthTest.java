package webServerTests;

import com.squareup.okhttp.*;
import org.junit.Assert;
import org.junit.Test;
import webserver.APIServlet;

import java.util.UUID;

/**
 * Created by xakep666 on 12.10.16.
 *
 * Unit tests for authentication
 */
public class AuthTest {
    private static final String SERVICE_URL = "http://localhost:"+APIServlet.PORT+"/";
    @Test
    public void testRegister() {
        MediaType mType = MediaType.parse("raw");
        RequestBody body = RequestBody.create(mType,"login=user&password=pass");
        String requestUrl = SERVICE_URL + "auth/register";
        Request request =new Request.Builder()
                .url(requestUrl)
                .post(body)
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
            //try again, must get error
            resp = httpClient.newCall(request).execute();
            Assert.assertEquals(resp.code(),javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testAuth() {
        APIServlet.base.register("user","pass");
        MediaType mType = MediaType.parse("raw");
        RequestBody body = RequestBody.create(mType,"login=user&password=pass");
        RequestBody body2 = RequestBody.create(mType, "login=a&password=b");
        String requestUrl = SERVICE_URL + "auth/login";
        Request request =new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Request request2 = new Request.Builder()
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
            Response resp = httpClient.newCall(request).execute();
            UUID token = UUID.fromString(resp.body().string());
            Assert.assertTrue(resp.isSuccessful());
            //try with not registered user
            resp = httpClient.newCall(request2).execute();
            Assert.assertEquals(resp.code(),javax.ws.rs.core.Response.Status.UNAUTHORIZED.getStatusCode());
            resp = httpClient.newCall(request).execute();
            Assert.assertEquals(token, UUID.fromString(resp.body().string()));
            Assert.assertTrue(APIServlet.base.isValidToken(token));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }

    }
}
