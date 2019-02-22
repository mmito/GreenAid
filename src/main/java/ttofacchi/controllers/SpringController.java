package ttofacchi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import ttofacchi.models.*;

@RestController
public class SpringController {

    private Logger logger = LoggerFactory.getLogger(SpringController.class);

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String welcome() {
            return "WELCOME TEST";
    }

    @RequestMapping("/activities")
    public String display() {
        return "Post a JSON request";
    }

    @RequestMapping(value="/activities", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody String activityInformation(Activity act) {

        logger.info("Activity recieved");
        return "Activity information saved successfully: " + act.getName() + " " + act.getCo2();

    }
}