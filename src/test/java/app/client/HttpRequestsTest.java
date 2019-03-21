package app.client;

import app.responses.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;

public class HttpRequestsTest {

    HttpRequests httpRequests;

    private static final String username = "cpene";
    private static final String password = "cpenecpene";

    @Test
    public void constructorDefault() {
        httpRequests = new HttpRequests();
        assertNotNull(httpRequests);
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

}