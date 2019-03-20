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

    public void delete(Activity activity) {

        activityRepository.delete(activity);

    }

    /**
     * Method that finds a user by his/her activities.
     * @param userId id of the user
     * @return returns the activities of the user.
     */
    public List<Activity> findByUser_id(long userId) {

        return activityRepository.findByUser_id(userId);

    }

    public Activity findById(long id) {

        return activityRepository.findById(id);

    }

}
