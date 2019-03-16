package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.models.Activity;
import app.models.Category;
import app.repository.CategoryRepository;
import app.services.ActivityServiceImpl;
import app.services.CategoryServiceImpl;
import app.services.UserServiceImpl;
import app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RouteController {

    //private Logger logger = LoggerFactory.getLogger(SpringController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private ActivityServiceImpl activityService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/welcome")
    public String welcome() {

        return "welcome";

    }

    @GetMapping("/check")
    public String checkAuth() {

        return "Your username is:" + securityService.findLoggedInUsername();

    }

    /**
     * Get request for Mapping to /registration of app.
     * @param user that we will be using
     * @return returns registration
    */
    @PostMapping("/registration")
    public String registration(User user) {

        if(userService.findByUsername(user.getUsername()) != null) {
            return "Username is already registered";
        }
        else {
            userService.save(user);
            return "You are now registered, " + user.getUsername() + "!";
        }

    }

    @PostMapping("/activity")
    public String activity(Activity activity) {

        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);

        activity.setUser_id(userService.findByUsername(username).getId());
        activity.setXp_points(categoryRepository.findById(activity.getCategory_id()).getXp_points() * activity.getAmount());
        activityService.save(activity);

        // The following method to update a user's EXP Points doesn't work:
        // it re-saves the whole user's account, meaning that it re-saves the
        // password re-encrypting the already encrypted password...
        // We need to set a trigger on the database for that.
        //user.setExperience_points(userService.findByUsername(username).getExperience_points() + activity.getXp_points());
        //userService.save(user);

        return "Activity \"" + categoryRepository.findById(activity.getCategory_id()).getName() + "\" saved successfully!";

    }

    @GetMapping("/getcategories")
    public String getCategories() {

        List<Category> categories= categoryRepository.findAll();
        String response = "";
        for(Category c : categories) {

            response += "\n" + c.getId() + " - " + c.getName();

        }

        return response;

    }

}