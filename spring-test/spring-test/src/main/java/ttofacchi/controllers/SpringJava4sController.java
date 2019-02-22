package ttofacchi.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;

import ttofacchi.models.*;

@RestController
public class SpringJava4sController {

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String welcome() {
            return "WELCOME TEST";
    }

    @RequestMapping("/activities")
    public String display() {
        return "Post a JSON request";
    }

    @PostMapping("/activities")
    public String activityInformation(@RequestBody Activity act) {

        return "Activity information saved successfully: " + act.getName() + " " + act.getCo2();
    }
}