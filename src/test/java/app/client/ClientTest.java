package app.client;

import app.responses.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment  = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
public class ClientTest {

    private static final String username = "cpene";
    private static final String password = "cpenecpene";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setHeaders() {
        String expected = "[Accept:\"application/json\", Content-Type:\"application/x-www-form-urlencoded\", Cookie:\"sessionCookie\"]";
        HttpHeaders headers = HttpRequests.setHeaders("sessionCookie");
        assertEquals(expected, headers.toString());
    }


    @Test
    public void getRequest() {
        String URL = "http://localhost:8080/welcome";
        String sessionCookie = "sessionCookie";

        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, URL);
        assertEquals(true, response.getBody().isOk());
    }

    @Test
    public void postRequest() {
        String URL = "http://localhost:8080/login";
        String sessionCookie = "sessionCookie";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);
        HttpEntity<Response> response = HttpRequests.postRequest(sessionCookie, URL, params);
        assertEquals(true, response.getBody().isOk());
    }

    @Test
    public void getSessionCookieValidUser() {
        String unexpected = "No cookie found.";
        String sessionCookie = Client.getSessionCookie(username, password);
        assertNotEquals(unexpected, sessionCookie);
    }

    @Test
    public void getSessionCookieInvalidUser() {
        String expected = "No cookie found.";
        String sessionCookie = Client.getSessionCookie("XXX0invalid0user0XXX", "wrongPassword");
        assertEquals(expected, sessionCookie);
    }

    @Test
    public void checkAuthNotLoggedInUser() {
        String expected = "User not logged in";
        String sessionCookie = "sessionCookie";
        assertEquals(expected, Client.checkAuth(sessionCookie));
    }

    @Test
    public void checkAuthLoggedInUser() {
        String unexpected = "User not logged in";
        String sessionCookie = Client.getSessionCookie(username, password);
        assertNotEquals(unexpected, Client.checkAuth(sessionCookie));
    }

//    @Test
//    public void getCategoriesInvalidUser() {
//        String expected = "idk";
//        String sessionCookie = Client.getSessionCookie("XXX0invalid0user0XXX", "wrongPassword");
//        assertEquals(expected, Client.getCategories(sessionCookie));
//    }

    @Test
    public void getCategoriesValidUser() {
        String unexpected = "You are not authorized";
        String sessionCookie = Client.getSessionCookie(username, password);
        assertNotEquals(unexpected, Client.getCategories(sessionCookie));
    }

//    @Test
//    public void addActivityInvalidUser() {
//    }

//    @Test
//    public void addActivityValidUser() {
//    }

//    @Test
//    public void getUserActivitiesInvalidUser() {
//    }

    @Test
    public void getUserActivitiesValidUser() {
        String unexpected = "You are not authorized";
        String sessionCookie = Client.getSessionCookie(username, password);
        assertNotEquals(unexpected, Client.getUserActivities(sessionCookie));
    }

//    @Test
//    public void getUserFirstInvalidUser() {
//    }

    @Test
    public void getUserFirstValidUser() {
        String expected = "Cosmin Octavian";
        String sessionCookie = Client.getSessionCookie(username, password);
        //assertEquals(expected, Client.getUserFirst(sessionCookie));
    }

//    @Test
//    public void getUserLastInvalidUser() {
//    }

    @Test
    public void getUserLastValidUser() {
        String expected = "Pene";
        String sessionCookie = Client.getSessionCookie(username, password);
        //assertEquals(expected, Client.getUserLast(sessionCookie));
    }
}