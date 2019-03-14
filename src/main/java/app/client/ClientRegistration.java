package app.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class ClientRegistration {


    private static final String url_registration = "http://localhost:8080/registration";

    /**
     * main method of client registration.
     * @param args arguments of the main
     */
    public static void main(String[] args) {

        boolean retype = true;
        String username = "";
        String password = "";
        String passwordConfirm = "";
        String first_name = "";
        String last_name = "";

        while (retype) {

            System.out.println("Hello user, please insert your credentials.");
            Scanner sc = new Scanner(System.in);
            System.out.print("Username: ");
            username = sc.nextLine();
            System.out.print("Password: ");
            password = sc.nextLine();
            System.out.print("Confirm Password: ");
            passwordConfirm = sc.nextLine();
            System.out.print("What is your first name? ");
            first_name = sc.nextLine();
            System.out.print("What is your last name? ");
            last_name = sc.nextLine();

            if (password.equals(passwordConfirm)) {
                retype = false;
            }

            else {
                System.out.println("Passwords do not match.");
            }

        }

        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);
        params.add("passwordConfirm", passwordConfirm);
        params.add("first_name", first_name);
        params.add("last_name", last_name);


        headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String registration = restTemplate.postForObject(url_registration, request, String.class);

        System.out.println(registration);

    }

}
