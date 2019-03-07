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

    @GetMapping("/welcome")
    public String welcome() {

        return "welcome";

    }

    @GetMapping("/check")
    public String checkAuth() {

        return "Your username is:" + securityService.findLoggedInUsername();

    }


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

        activity.setCarbon_emission(categoryRepository.findById(activity.getCategory_id()).get().getNormalized_emission() * activity.getAmount());
        activityService.save(activity);
        return "Activity \"" + activity.getActivity_name() + "\" saved successfully!";

    }

    @GetMapping("/getcategories")
    public String getCategories() {

        List<Category> categories= categoryRepository.findAll();
        String response = "";
        for(Category c : categories) {

            response += c.getId() + " - " + c.getCategory_name();

        }

        return response;

    }

    @PostMapping("/findbyusername")
    public String findByUsername(String username) {

        if(userService.findByUsername(username) != null) return "Found: " + userService.findByUsername(username).getId();
        else return "User not found.";

    }

}