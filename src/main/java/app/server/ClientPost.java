package app.server;

import app.models.Activity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class ClientPost {

    private static final String URL_CREATE_ACTIVITY = "http://localhost:8080/activities";

    private static final String USER_NAME = "tommy";
    private static final String PASSWORD = "123";

    /**
     * Class that runs a basic base-64 authentication via POST requests.
     * @param args arguments of the main method
     */
    public static void main(String[] args) throws JsonProcessingException {

        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        //
        // Authentication
        //
        String auth = USER_NAME + ":" + PASSWORD;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        //        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        //        params.add("name", "car");
        //        params.add("co2", "100");

        //        String requestJson = "{\"name\":\"car\", \"co2\": \"100\"}";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Activity activity = new Activity("car", "100");
        String requestJson = ow.writeValueAsString(activity);

        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);
        //HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // Send request with POST method.
        //  ResponseEntity<String> response =
        //  restTemplate.postForEntity( URL_CREATE_ACTIVITY, request, String.class );
        String response = restTemplate.postForObject(URL_CREATE_ACTIVITY, request, String.class);

        System.out.println(response);

    }

}
