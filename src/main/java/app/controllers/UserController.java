package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.models.ActivityProjection;
import app.models.UserProjection;
import app.models.Activity;
import app.models.Following;
import app.models.User;
import app.repository.CategoryRepository;
import app.responses.Response;
import app.services.ActivityServiceImpl;
import app.services.FollowingServiceImpl;
import app.services.RecommendationRepository;
import app.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@RestController()
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private ActivityServiceImpl activityService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FollowingServiceImpl followingService;

    public List<UserProjection> toUserProjection(List<User> input, long id1) {

        List<UserProjection> output = new LinkedList<>();
        for (User u : input) {
            boolean following = followingService.findById1Id2(id1, u.getId()) != null;
            output.add(new UserProjection(u.getUsername(), u.getFirst_name(), u.getLast_name(), u.getExperience_points(), u.getLast_update(), following));

        }

        return output;

    }

    @GetMapping("/details")
    public Response getUserDetails() {
        if(securityService.findLoggedInUsername() != null) {
            return new Response(true, userService.findByUsername(securityService.findLoggedInUsername()));
        }
        else return new Response(false, "User not logged in.");
    }

    @PostMapping("/info")
    public Response getUserInfo(String username) {
        if(securityService.findLoggedInUsername() != null) {
            return new Response(true, userService.findByUsername(username));
        }
        else return new Response(false, "User not logged in.");
    }

    /**
     * Get request to /showactivities to show recent activities of a user.
     * @return returns recent activities of a user
     */
    @GetMapping("/activities")
    public Response showActivities() {

        User user = userService.findByUsername(securityService.findLoggedInUsername());

        if (user != null) {
            String username = user.getUsername();

            List<Activity> activities = activityService.findByUser_id(user.getId());
            List<ActivityProjection> response = new LinkedList<>();

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

                response.add(new ActivityProjection(id, username, category, amount, xp_points));

            }

            return new Response(true, response);
        }
        else {
            return new Response(false, "You are not authorized!");
        }
    }

    /**
     * Maps to /activity with a POST request.
     * @param activity adds and activity to user profile.
     * @return returns the updated version of activities.
     * @throws Exception throws exception in case things go wrong.
     */
    @PostMapping("/add-activity")
    public Response addActivity(Activity activity) {

        String username = securityService.findLoggedInUsername();
        if (username != null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            activity.setUser_id(userService.findByUsername(username).getId());
            activity.setXp_points(categoryRepository.findById(activity
                    .getCategory_id()).getXp_points() * activity.getAmount());
            activity.setLast_update(timestamp);
            activityService.save(activity);

            return new Response(true, "Activity \""
                    + categoryRepository.findById(activity.getCategory_id()).getName() + "\" saved successfully!");
        }

        else
            return new Response(false, "You are not authorized!");

    }

    @PostMapping("/remove-activity")
    public Response removeActivity(long id) {

        if(securityService.findLoggedInUsername() != null) {

            Activity activity = activityService.findById(id);

            if(activity != null) {

                if(activity.getUser_id() == userService.findByUsername(securityService.findLoggedInUsername()).getId()) {

                    activityService.delete(activity);
                    return new Response(true, "Activity \""
                            + categoryRepository.findById(activity.getCategory_id()).getName() + "\" removed successfully!");

                }

                else
                    return new Response(false, "You can't remove someone else's activity!");

            }

            else
                return new Response(false, "Activity not found!");

        }

        else
            return new Response(false, "You are not authorized!");

    }

    @GetMapping("/followings")
    public Response getFollowings() {

        if(securityService.findLoggedInUsername() != null) {

            List<User> query = userService.findFollowings(userService.findByUsername(securityService.findLoggedInUsername()).getId());
            return new Response(true, toUserProjection(query, userService.findByUsername(securityService.findLoggedInUsername()).getId()));

        }

        else
            return new Response(false, "You are not authorized!");

    }

    @GetMapping("/followed-by")
    public Response getFollowedBy() {

        if (securityService.findLoggedInUsername() != null) {

            List<User> query = userService.findFollowedBy(userService.findByUsername(securityService.findLoggedInUsername()).getId());
            return new Response(true, toUserProjection(query, userService.findByUsername(securityService.findLoggedInUsername()).getId()));

        }

        else
            return new Response(false, "You are not authorized!");

    }

    @PostMapping("/add-following")
    public Response addFollowing(Following following, String username) {

        if(securityService.findLoggedInUsername() != null) {

            if(userService.findByUsername(username) != null) {

                if (securityService.findLoggedInUsername().equals(username)) {

                    return new Response(false, "You already follow yourself...");

                }

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                following.setUser_id_1(userService.findByUsername(securityService.findLoggedInUsername()).getId());
                following.setUser_id_2(userService.findByUsername(username).getId());
                following.setLast_update(timestamp);
                followingService.save(following);
                return new Response(true, "Your followings have been updated!");

            }

            else
                return new Response(false, "User not found.");

        }

        else
            return new Response(false, "You are not authorized!");

    }

    @PostMapping("/remove-following")
    public Response removeFollowing(String username) {

        if(securityService.findLoggedInUsername() != null) {

            if(userService.findByUsername(username) != null) {

                if(securityService.findLoggedInUsername().equals(username)) {

                    return new Response(false, "You cannot unfollow yourself...");

                }

                followingService.delete(followingService.findById1Id2(userService.findByUsername(securityService.findLoggedInUsername()).getId(),
                        userService.findByUsername(username).getId()));
                return new Response(true, "Your followings have been updated!");

            }

            else
                return new Response(false, "User not found.");

        }

        else
            return new Response(false, "You are not authorized!");

    }
    @GetMapping("/recommendation")
    public Response getRecommendation(){
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        RecommendationRepository repo = new RecommendationRepository();
        List<String> eatRecommendations = repo.getEatRecommendations();
        List<String> householdRecommendations = repo.getHouseholdRecommendations();
        List<String> transportRecommendations = repo.getTransportRecommendations();
        if (user != null) {
            String username = user.getUsername();

            List<Activity> activities = activityService.findByUser_id(user.getId());
            List<ActivityProjection> response = new LinkedList<>();
            int eat = 0, transport = 0 , household = 0;
            String activityRecom = "";
            for (Activity a : activities) {

                double amount = a.getAmount();

                switch ((int) a.getCategory_id()){
                    case 1:
                        eat += amount;
                        break;
                    case 2:
                        eat += amount;
                        household += amount;
                        break;
                    case 3:
                        transport += amount;
                        break;
                    case 4:
                        transport += amount;
                        break;
                    case 5:
                        household += amount;
                    case 6:
                        household += amount;
                        break;

                }
            }
            if (eat > household && eat > transport ){
                int rand = (int) (Math.random() * 8);
                activityRecom += eatRecommendations.get(rand);
            }
            else if (household > eat && household > transport){
                int rand = (int) (Math.random() * 6);
                activityRecom += householdRecommendations.get(rand);
            }
            else if (transport > eat && transport > household) {
                int rand = (int) (Math.random() * 6);
                activityRecom += transportRecommendations.get(rand);
            }

            return new Response(true, "Based on your activities, here's an activity recommendation:\n " + activityRecom);

        }
        else {
            return new Response(false, "You are not authorized!");
        }
    }

    @GetMapping("/leaderboard")
    public Response getLeaderboard(){

        if(securityService.findLoggedInUsername() != null) {
            List<User> query = userService.findLeaderboard();
            return new Response(true, toUserProjection(query, userService.findByUsername(securityService.findLoggedInUsername()).getId()));
        }
        else {
            return new Response(false, "You are not authorized!");
        }
    }

}
