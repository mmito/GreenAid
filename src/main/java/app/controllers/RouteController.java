package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.client.ActivityRepresentation;
import app.models.Activity;
import app.models.Category;
import app.repository.CategoryRepository;
import app.responses.Response;
import app.services.ActivityServiceImpl;
import app.services.UserServiceImpl;
import app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RouteController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private ActivityServiceImpl activityService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/welcome")
    public Response welcome() {

        return new Response( true, "welcome");

    }

    @GetMapping("/check")
    public Response checkAuth() {

        if (securityService.findLoggedInUsername() != null) {
            return new Response(true, "Your username is:" + securityService.findLoggedInUsername());
        }
        else return new Response(true, "User not logged in");
    }

    /**
     * Get request for Mapping to /registration of app.
     * @param user that we will be using
     * @return returns registration
    */
    @PostMapping("/registration")
    public Response registration(User user) {

        if(userService.findByUsername(user.getUsername()) != null) {
            return new Response(false, "Username is already registered");
        }
        else {
            userService.save(user);
            return new Response(true, "You are now registered, " + user.getUsername() + "!");
        }

    }

    @PostMapping("/activity")
    public Response activity(Activity activity) throws Exception{

        try {

            String username = securityService.findLoggedInUsername();
            //User user = userService.findByUsername(username);

            activity.setUser_id(userService.findByUsername(username).getId());
            activity.setXp_points(categoryRepository.findById(activity.getCategory_id()).getXp_points() * activity.getAmount());
            activityService.save(activity);

            // The following method to update a user's EXP Points doesn't work:
            // it re-saves the whole user's account, meaning that it re-saves the
            // password re-encrypting the already encrypted password...
            // We need to set a trigger on the database for that.
            //user.setExperience_points(userService.findByUsername(username).getExperience_points() + activity.getXp_points());
            //userService.save(user);

            return new Response(true, "Activity \"" + categoryRepository.findById(activity.getCategory_id()).getName() + "\" saved successfully!");

        } catch (Exception e) {
            return new Response(false, e);
        }
    }

    @GetMapping("/getcategories")
    public Response getCategories() {

        List<Category> categories = categoryRepository.findAll();
        String response = "";
        for(Category c : categories) {

            response += "\n" + c.getId() + " - " + c.getName();

        }

        return new Response(true, response);

    }

    @GetMapping("/showactivities")
    public Response showActivities() {

        User user = userService.findByUsername(securityService.findLoggedInUsername());

        List<Object> activities = activityService.findNameById(user.getId());

        return new Response(true, activities);

    }

}