package app.server;

import app.models.Activity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Scanner;


public class ClientDemo {

    private static final String URL_CREATE_ACTIVITY = "http://localhost:8080/activities";


    /**
     * Body of the class that will ask user to type in his/her credentials.
     * @param args arguments of the main method.
     */
    public static void main(String[] args) throws JsonProcessingException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Hello user, please insert your credentials.");
        System.out.print("Username: ");
        String userName = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Insert your activity: ");
        String activityName = sc.nextLine();
        System.out.print("Insert its CO2 emission: ");
        int co2 = sc.nextInt();

        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        //
        // Authentication
        //
        String auth = userName + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

//        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
//        params.add("name", activityName);
//        params.add("co2", co2);

//        String requestJson = "{\"name\":\"" + activityName + "\", \"co2\": \"" + co2 + "\"}";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Activity activity = new Activity(activityName,"" + co2);
        String requestJson = ow.writeValueAsString(activity);

        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        //HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);

        // Send request with POST method.
        //  ResponseEntity<String>
        //  response = restTemplate.postForEntity( URL_CREATE_ACTIVITY, request, String.class );
        String response = restTemplate.postForObject(URL_CREATE_ACTIVITY, request, String.class);

        System.out.println(response);

    }
}
