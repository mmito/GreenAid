package app.client;

import app.responses.Response;
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
    private static final String url_check = "http://localhost:8080/check";
    private static final String url_categories = "http://localhost:8080/getcategories";
    private static final String url_activity = "http://localhost:8080/activity";


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
        System.out.println(getCategories(sessionCookie));
        System.out.println("Which kind of activity would you like to add?");
        long categoryId = sc.nextLong();
        System.out.println("How many times have you performed that activity?");
        long amount = sc.nextLong();

        sc.close();
        System.out.println(addActivity(sessionCookie, categoryId, amount));

    }

    public static HttpHeaders setHeaders(String sessionCookie) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Cookie", sessionCookie);

        return headers;

    }

    public static HttpEntity<Response> getRequest(String sessionCookie, String url) {

        HttpHeaders headers = setHeaders(sessionCookie);
        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, request, Response.class);

    }

    public static HttpEntity<Response> postRequest(String sessionCookie, String url, MultiValueMap<String, Object> params) {

        HttpHeaders headers = setHeaders(sessionCookie);
        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);

        return restTemplate.exchange(url, HttpMethod.POST, request, Response.class);

    }

    // With login data the method retrieves its session cookie.
    public static String getSessionCookie(String username, String password) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        HttpEntity<Response> response = postRequest("", url_login, params);
        HttpHeaders responseHeaders = response.getHeaders();
        if (responseHeaders.getFirst(HttpHeaders.SET_COOKIE) != null) {
            return responseHeaders.getFirst(HttpHeaders.SET_COOKIE).split(";")[0];
        }

        else return "No cookie found.";

    }

        // Test method just to check whether the session cookies are working or not.
    public static String checkAuth(String sessionCookie){

        HttpEntity<Response> response = getRequest(sessionCookie, url_check);

        return (String) response.getBody().getData();
    }

    // Method used to display the list of categories, just if the session is authenticated.
    public static String getCategories(String sessionCookie) {

        HttpEntity<Response> categories = getRequest(sessionCookie, url_categories);

        return ((String) categories.getBody().getData()).replaceFirst("\n", "");

    }

    // Method used to add an activity in the database given a session, a category id and the amount of times that activity has been made.
    public static String addActivity(String sessionCookie, long categoryId, long amount) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("category_id", categoryId);
        params.add("amount", amount);

        HttpEntity<Response> response = postRequest(sessionCookie, url_activity, params);

        return (String) response.getBody().getData();

    }

}
