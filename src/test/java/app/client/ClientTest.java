package app.client;


import app.models.ActivityProjection;
import app.responses.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@PrepareForTest(HttpRequests.class)
@RunWith(PowerMockRunner.class)
public class ClientTest {

    private static final String username = "cpene";
    private static final String password = "cpenecpene";



    @Test
    public void getSessionCookieValidUser() {


        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);


        HttpHeaders mockResponseHeaders = new HttpHeaders();
        mockResponseHeaders.set(HttpHeaders.SET_COOKIE, "cookieFirstPart;second");

        HttpEntity<Response> mockResponse = new HttpEntity<>(new Response(), mockResponseHeaders);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.postRequest("", "http://localhost:8080/login", params))
                .thenReturn(mockResponse);


        String sessionCookie = Client.getSessionCookie(username, password);

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.postRequest("", "http://localhost:8080/login", params);

        assertEquals("cookieFirstPart", sessionCookie);
    }

    @Test
    public void addActivity() {

        long categoryId = 12L;
        double amount = 3.14;

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("category_id", categoryId);
        params.add("amount", amount);

        HttpHeaders mockResponseHeaders = new HttpHeaders();
        HttpEntity<Response> mockResponse = new HttpEntity<>(new Response(), mockResponseHeaders);
        mockResponse.getBody().setData("responseTxt");

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.postRequest("cookie", "http://localhost:8080/user/add-activity", params))
                .thenReturn(mockResponse);


        String response = Client.addActivity("cookie", categoryId, amount);

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.postRequest("cookie", "http://localhost:8080/user/add-activity", params);

        assertEquals("responseTxt", response);
    }

    @Test
    public void getUserActivities() {

        ObjectMapper mapper = new ObjectMapper();

        List<ActivityProjection> actList = new ArrayList<>();
        ActivityProjection ap = new ActivityProjection(12, "user", "cat", 3.14, 12.1);
        actList.add(ap);
        JsonNode body = mapper.convertValue(actList, JsonNode.class);

        HttpEntity<Response> mockResponse = new HttpEntity<>(new Response(), null);
        mockResponse.getBody().setOk(true);
        mockResponse.getBody().setData(body);

        PowerMockito.mockStatic(HttpRequests.class);
        PowerMockito.when(HttpRequests.getRequest("cookie", "http://localhost:8080/user/activities"))
                .thenReturn(mockResponse);

        Object response = Client.getUserActivities("cookie");

        assertEquals(actList, response);

        PowerMockito.verifyStatic(HttpRequests.class);
        HttpRequests.getRequest("cookie", "http://localhost:8080/user/activities");
    }

}