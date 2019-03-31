package app.services;

import app.models.Activity;
import app.models.ActivityProjection;
import app.models.User;
import app.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
     * Deletes activity.
     * @param activity activity to be deleted
     */
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

    /**
     * Finds an activity by ID
     * @param id id to be found
     * @return returns the activity
     */
    public Activity findById(long id) {

        return activityRepository.findById(id);
    }

    public List<ActivityProjection> getActivities(User user) {

        List<Activity> activities = activityRepository.findByUser_id(user.getId());
        List<ActivityProjection> activityArray = new LinkedList<>();

        for (Activity a : activities) {

            long id = a.getId();
            double amount = a.getAmount();
            double xp_points = a.getXp_points();
            String category;
            switch ((int) a.getCategory_id()) {

                case 1:
                    category = "Eating a vegetarian meal";
                    break;
                case 2:
                    category = "Buying local produce";
                    break;
                case 3:
                    category = "Using bike instead of car";
                    break;
                case 4:
                    category = "Using public transport instead of car";
                    break;
                case 5:
                    category = "Installing solar panels";
                    break;
                case 6:
                    category = "Lowering the temperature of your home";
                    break;
                default:
                    category = "unknown";
            }
            activityArray.add(new ActivityProjection(id, user.getUsername(), category, amount, xp_points));
        }
        return activityArray;
    }
}
