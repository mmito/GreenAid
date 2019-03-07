package app.services;

import app.models.Activity;
import app.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl {

    @Autowired
    ActivityRepository activityRepository;

    public void save(Activity activity) {

        activityRepository.save(activity);

    }

}
