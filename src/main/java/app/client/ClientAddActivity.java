package app.client;

import app.models.Category;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientAddActivity {

    private static final String url_activity = "http://localhost:8080/activity";
    private static final String url_categories = "http://localhost:8080/getcategories";
    private static final String url_users = "http://localhost:8080/findbyusername";

    private static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {

        boolean retype = false;
        String username;
        long category_id = 0;
        long user_id = 0;
        String activity_name;
        int amount = 0;

        do {

            System.out.println("Hello user, please insert your username.");
            username = sc.nextLine();

            HttpHeaders headers = new HttpHeaders();

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("username", username);

            headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            RestTemplate restTemplate = new RestTemplate();

            // Data attached to the request.
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            String response = restTemplate.postForObject(url_users, request, String.class);

            if(response.equals("User not found."))  {

                retype = true;

            }

            else {

                user_id = Long.parseLong(response.substring(7));
                retype = false;

            }

        }  while (retype);

        System.out.println("Your user_id is: " + user_id);

        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();
        String categories = restTemplate.getForObject(url_categories, String.class);

        System.out.println("Choose one of these categories for your activity:");
        System.out.println(categories);
        category_id = sc.nextLong();
        System.out.println("Enter the name of your activity:");
        sc.nextLine();
        activity_name = sc.nextLine();
        System.out.println("Enter the amount of your activity:");
        amount = sc.nextInt();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("user_id", user_id);
        params.add("category_id", category_id);
        params.add("activity_name", activity_name);
        params.add("amount", amount);

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);

        String response = restTemplate.postForObject(url_activity, request, String.class);
        System.out.println(response);

    }

}
