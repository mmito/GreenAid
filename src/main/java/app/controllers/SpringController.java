package app.controllers;

import app.models.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringController {

    private Logger logger = LoggerFactory.getLogger(SpringController.class);

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
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody
    String activityInformation(Activity act) {

        logger.info("Activity received");
        return "Activity information saved successfully: " + act.getName() + " " + act.getCo2();

    }
}