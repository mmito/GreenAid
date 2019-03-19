package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.client.ActivityProjection;
import app.models.Activity;
import app.models.Category;
import app.models.User;
import app.repository.CategoryRepository;
import app.responses.Response;
import app.services.ActivityServiceImpl;
import app.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


@RestController
public class MainController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private ActivityServiceImpl activityService;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Routes to /welcome.
     * @return returns the given response.
     */
    @GetMapping("/welcome")
    public Response welcome() {

        return new Response( true, "welcome");

    }

    /**
     * Routes to /check.
     * @return returns the given response to check the authentication
     */
    @GetMapping("/check")
    public Response checkAuth() {

        if (securityService.findLoggedInUsername() != null) {
            return new Response(true, "Your username is:" + securityService.findLoggedInUsername());
        } else {
            return new Response(false, "User not logged in");
        }
    }

    /**
     * Get request for Mapping to /registration of app.
     * @param user that we will be using
     * @return returns registration
     */
    @PostMapping("/registration")
    public Response registration(User user) {

        if (userService.findByUsername(user.getUsername()) != null) {
            return new Response(false, "Username is already registered");
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            user.setLast_update(timestamp);
            userService.save(user);
            return new Response(true, "You are now registered, " + user.getUsername() + "!");
        }

    }

    /**
     * Creates a GET request based mapping to /getcategories.
     * @return returns the categories
     */
    @GetMapping("/getcategories")
    public Response getCategories() {

        User user = userService.findByUsername(securityService.findLoggedInUsername());

        if (user != null) {
            List<Category> categories = categoryRepository.findAll();
            String response = "";
            for (Category c : categories) {

                response += "\n" + c.getId() + " - " + c.getName();

            }

            return new Response(true, response);
        }
        else {
            return new Response(false, "You are not authorized!");
        }
    }

}
