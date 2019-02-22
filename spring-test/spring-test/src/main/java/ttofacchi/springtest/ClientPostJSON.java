package ttofacchi.springtest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ttofacchi.models.Activity;

import java.nio.charset.Charset;

public class ClientPostJSON {

    static final String URL_CREATE_ACTIVITY = "http://localhost:8080/activities";

    public static final String USER_NAME = "tom";
    public static final String PASSWORD = "123";

    static final String URL_WELCOME = "http://localhost:8080/";

    public static void main(String[] args) {

        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        //
        // Authentication
        //
        String auth = USER_NAME + ":" + PASSWORD;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        String activity = "{ \"name\": \"Car\", \"co2\": 100 }";

       // headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<String> requestBody = new HttpEntity<>(activity, headers);

        // Send request with POST method.
        Activity a = restTemplate.postForObject(URL_CREATE_ACTIVITY, requestBody, Activity.class);

        if (a != null && a.getName() != null) {

            System.out.println("Activity information saved successfully: " + a.getName() + " " + a.getCo2());

        } else {
            System.out.println("Error");
        }

    }

}
