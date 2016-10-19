package webServerTests;

import com.squareup.okhttp.*;
import org.junit.Assert;
import org.junit.Test;
import webserver.APIServlet;

import java.util.UUID;

import static webServerTests.WebServerTest.SERVICE_URL;
import static webServerTests.WebServerTest.genRandomStr;

/**
 * Created by xakep666 on 12.10.16.
 *
 * Unit tests for authentication
 */
public class AuthTest {

    @Test
    public void testRegister() {
        String user = genRandomStr();
        String pass = genRandomStr();
        MediaType mType = MediaType.parse("raw");
        RequestBody body = RequestBody.create(mType,String.format("user=%s&password=%s",user,pass));
        String requestUrl = SERVICE_URL + "auth/register";
        Request request =new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            OkHttpClient httpClient = new OkHttpClient();
            Response resp = httpClient.newCall(request).execute();
            resp.body().close();
            Assert.assertTrue(resp.isSuccessful());
            //try again, must get error
            resp = httpClient.newCall(request).execute();
            Assert.assertEquals(resp.code(),javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE.getStatusCode());
            resp.body().close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testAuth() {
        String user = genRandomStr();
        String pass = genRandomStr();
        APIServlet.base.register(user,pass);
        MediaType mType = MediaType.parse("raw");
        RequestBody body = RequestBody.create(mType,String.format("user=%s&password=%s",user,pass));
        RequestBody body2 = RequestBody.create(mType, "user=a&password=b");
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
            OkHttpClient httpClient = new OkHttpClient();
            Response resp = httpClient.newCall(request).execute();
            UUID token = UUID.fromString(resp.body().string());
            Assert.assertTrue(resp.isSuccessful());
            resp.body().close();
            //try with not registered user
            resp = httpClient.newCall(request2).execute();
            Assert.assertEquals(resp.code(),javax.ws.rs.core.Response.Status.UNAUTHORIZED.getStatusCode());
            resp.body().close();
            resp = httpClient.newCall(request).execute();
            Assert.assertEquals(token, UUID.fromString(resp.body().string()));
            Assert.assertTrue(APIServlet.base.isValidToken(token));
            resp.body().close();
            //remove from logged in
            APIServlet.base.logout(token);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testLogout() {
        String user = genRandomStr();
        String pass = genRandomStr();
        APIServlet.base.register(user,pass);
        UUID token = APIServlet.base.requestToken(user,pass);
        Assert.assertNotNull(token);
        MediaType mType = MediaType.parse("raw");
        RequestBody body = RequestBody.create(mType,"");
        String requestUrl = SERVICE_URL + "auth/logout";
        Request request =new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer "+token)
                .build();
        try {
            OkHttpClient httpClient = new OkHttpClient();
            Response resp = httpClient.newCall(request).execute();
            Assert.assertTrue(resp.isSuccessful());
            Assert.assertFalse(APIServlet.base.isValidToken(token));
            resp.body().close();
            //try again, must get error
            resp = httpClient.newCall(request).execute();
            Assert.assertEquals(resp.code(),javax.ws.rs.core.Response.Status.UNAUTHORIZED.getStatusCode());
            resp.body().close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
