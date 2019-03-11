package app.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class Client {

    private static final String url_login = "http://localhost:8080/login";
    private static final String url_check = "http://localhost:8080/login";



    /**
     * Main method that starts a client login page.
     * @param args arguments of main method
     */
    public static void main(String[] args) {

        System.out.println("Hello user, please insert your credentials.");
        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        String sessionCookie = getSessionCookie(username, password);
        System.out.println(sessionCookie);
        System.out.println(checkAuth(sessionCookie));

    }

    /**
     * Method that returns session cookies.
     * @param username username of the user
     * @param password password of the user
     * @return returns the session cookie
     */
    public static String getSessionCookie(String username, String password) {

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        HttpEntity<String> response = restTemplate.exchange(url_login,
                HttpMethod.POST, request, String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        String sessionCookie =
                responseHeaders.getFirst(HttpHeaders.SET_COOKIE).split(";")[0];//.substring(12);

        //String login = response.getBody();

        return sessionCookie;

    }

    /**
     * Class that checks authentication.
     * @param sessionCookie cookie of the session
     * @return returns the http message
     */
    public static String checkAuth(String sessionCookie) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add("Set-Cookie", sessionCookie);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        HttpEntity<String> response =
                restTemplate.exchange(url_check, HttpMethod.GET, request, String.class);


        return response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        //return request.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

    }

}
