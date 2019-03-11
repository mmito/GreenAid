package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.models.Activity;
import app.models.Category;
import app.models.User;
import app.repository.CategoryRepository;
import app.services.ActivityServiceImpl;
import app.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class SpringController {

    //private Logger logger = LoggerFactory.getLogger(SpringController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private ActivityServiceImpl activityService;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * mapping to /welcome.
     * @return return the text
     */
    @GetMapping("/welcome")
    public String welcome() {

        return "welcome";

    }

    /**
     * mapping to /check.
     * @return return the text
     */
    @GetMapping("/check")
    public String checkAuth() {

        return "Your username is:" + securityService.findLoggedInUsername();

    }

    /**
     * mapping to /activities.
     * @return return the text
     */
    @RequestMapping("/activities")
    public String display() {
        return "Post a JSON request";
    }

    /**
     * Get request for Mapping to /registration of app.
     * //@param model model that we will be using
     * @return returns  registration
    */
    @PostMapping("/registration")
    public String registration(User user) {

        if (userService.findByUsername(user.getUsername()) != null) {
            return "Username is already registered";
        } else {
            userService.save(user);
            return "You are now registered, " + user.getUsername() + "!";
        }

    }

    /**
     * mapping to /activity.
     * @return return the text
     */
    @PostMapping("/activity")
    public String activity(Activity activity) {

        activity.setXp_points(categoryRepository.findById(activity.getId()).getXp_points() * activity.getAmount());
        activityService.save(activity);
        return "Activity \"" + categoryRepository.findById(activity.getId()).getName() + "\" saved successfully!";

    }

    /**
     * mapping to /getcategories.
     * @return return the text/response
     */
    @GetMapping("/getcategories")
    public String getCategories() {

        List<Category> categories = categoryRepository.findAll();
        String response = "";
        for (Category c : categories) {

            response += c.getId() + " - " + c.getName();

        }

        return response;

    }

    /**
     * mapping to /findbyusername.
     * @return return the text
     */
    @PostMapping("/findbyusername")
    public String findByUsername(String username) {

        if (userService.findByUsername(username) != null) {
            return "Found: " + userService.findByUsername(username).getId();
        } else {
            return "User not found.";
        }

    }

}