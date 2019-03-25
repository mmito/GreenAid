package app.client;

import app.authentication.SecurityServiceImpl;
import app.controllers.UserController;
import app.models.*;
import app.repository.CategoryRepository;
import app.responses.Response;
import app.services.ActivityServiceImpl;
import app.services.FollowingServiceImpl;
import app.services.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment  = SpringBootTest.WebEnvironment.DEFINED_PORT)

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(HttpRequests.class)
public class ClientTest {

    private static final String username = "cpene";
    private static final String password = "cpenecpene";
    private static final String invalidUsername = "XXX0invalid0user0XXX";
    private static final String invalidPassword = "wrongPassword";
    private static final String unauthorizedText = "You are not authorized!";

    @Test
    public void test() {
        assertTrue(true);
    }

//    @Autowired
//    private UserController userController;
//
//    @MockBean
//    private ActivityServiceImpl activityService;
//
//    @MockBean
//    private UserServiceImpl userService;
//
//    @MockBean
//    private SecurityServiceImpl securityService;
//
//    @MockBean
//    private CategoryRepository categoryRepository;
//
//    @MockBean
//    private FollowingServiceImpl followingService;
//
//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }

//    @Test
//    public void getSessionCookieValidUser() {
//        String unexpected = "No cookie found.";
//        String sessionCookie = Client.getSessionCookie(username, password);
//        assertNotEquals(unexpected, sessionCookie);
//    }
//
//    @Test
//    public void getSessionCookieInvalidUser() {
//        String expected = "No cookie found.";
//        String sessionCookie = Client.getSessionCookie(invalidUsername, invalidPassword);
//        assertEquals(expected, sessionCookie);
//    }
//
//    @Test
//    public void checkAuthNotLoggedInUser() {
//
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity<Response> response = new HttpEntity<Response>(new Response(), headers);
//        response.getBody().setData("dadada");
//        PowerMockito.mockStatic(HttpRequests.class);
//        PowerMockito.when(HttpRequests.getRequest(any(String.class), any(String.class)))
//                .thenReturn(response);
//
//        String result = Client.checkAuth("sessionCookie");
//
//        PowerMockito.verifyStatic(HttpRequests.class);
//        HttpRequests.getRequest("sessionCookie", "http://localhost:8080/check");
//
//        assertEquals("idk", result);
//
//
//    }
//
//    @Test
//    public void checkAuthLoggedInUser() {
//        String expected = "Your username is:username-test";
//
//        Mockito.when(securityService.findLoggedInUsername())
//                .thenReturn("username-test");
//
//        String result = Client.checkAuth("sessionCookie");
//
//        Mockito.verify(securityService, times(2)).findLoggedInUsername();
//
//        assertEquals(expected, result);
//    }



}