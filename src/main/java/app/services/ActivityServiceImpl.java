package app.services;

import app.models.Activity;
import app.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl {

    @Autowired
    ActivityRepository activityRepository;

    /**
     * Method that adds an activity to a repository and saves it to the database.
     * @param activity activity to add
     */
    public void save(Activity activity) {

        activityRepository.save(activity);

    }

    /**
     * Method that finds a user by his/her ID.
     * @param userId id of the user
     * @return returns the ID of the user.
     */
    public List<Activity> findByUser_id(long userId) {

        return activityRepository.findByUser_id(userId);

    }

}
