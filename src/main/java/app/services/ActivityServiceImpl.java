package app.services;

import app.client.ActivityRepresentation;
import app.models.Activity;
import app.repository.ActivityRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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

    public List<Activity> findByUser_id(long userId) {

        return activityRepository.findByUser_id(userId);

    }

    public List<Object> findNameById(long userId) {

        return  activityRepository.findNamesById(userId);

    }

}
