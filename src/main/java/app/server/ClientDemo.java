package app.server;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Scanner;

public class ClientDemo {

    private static final String URL_CREATE_ACTIVITY = "http://localhost:8080/activities";


    /**
     * Body of the class that will ask user to type in his/her credentials.
     * @param args arguments of the main method.
     */
    public static void main(String[] args) {

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

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("name", activityName);
        params.add("co2", co2);
        headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);

        // Send request with POST method.
        //  ResponseEntity<String>
        //  response = restTemplate.postForEntity( URL_CREATE_ACTIVITY, request, String.class );
        String activity = restTemplate.postForObject(URL_CREATE_ACTIVITY, request, String.class);

        System.out.println(activity);

    }
}
