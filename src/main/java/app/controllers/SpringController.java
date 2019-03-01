package app.controllers;

import app.models.Activity;
import app.models.User;
import app.models.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SpringController {

    private Logger logger = LoggerFactory.getLogger(SpringController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {
        return "WELCOME TEST";
    }

    @RequestMapping("/activities")
    public String display() {
        return "Post a JSON request";
    }

    /**
     *  Maps to localhost:8080/activities  and does the following method.
      * @param act type of activity
     * @return returns the following string
     */
    @RequestMapping(value = "/activities", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
        String activityInformation(@RequestBody Activity act) {

        logger.info("Activity received: " + act);
        return "Activity information saved successfully: " + act.getName() + " " + act.getCo2();

    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

}