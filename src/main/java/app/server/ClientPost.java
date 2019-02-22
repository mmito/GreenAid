package app.server;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "car");
        params.add("co2", "100");

        headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // Send request with POST method.
        //  ResponseEntity<String> response =
        //  restTemplate.postForEntity( URL_CREATE_ACTIVITY, request, String.class );
        String activity = restTemplate.postForObject(URL_CREATE_ACTIVITY, request, String.class);

        System.out.println(activity);

    }

}
