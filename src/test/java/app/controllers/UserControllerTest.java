package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.models.*;
import app.repository.CategoryRepository;
import app.responses.Response;
import app.services.ActivityServiceImpl;
import app.services.FollowingServiceImpl;
import app.services.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest//(webEnvironment  = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private ActivityServiceImpl activityService;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private SecurityServiceImpl securityService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private FollowingServiceImpl followingService;

    @Test
    public void toUserProjectionSuccess() {
        List<UserProjection> expected = new ArrayList<>();

        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(2);
        user.setUsername("username-test2");
        user.setFirst_name("first-name-test2");
        user.setLast_name("last-name-test2");
        user.setExperience_points(1.0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setLast_update(timestamp);

        users.add(user);

        expected.add(new UserProjection("username-test2", "first-name-test2", "last-name-test2", 1.0, timestamp, true));

        Mockito.when(followingService.findById1Id2(1, 2))
                .thenReturn(new Following());

        List<UserProjection> result = userController.toUserProjection(users, 1);

        Mockito.verify(followingService).findById1Id2(1, 2);

        assertEquals(expected, result);
    }

    @Test
    public void getUserDetailsFail() {
        Response expected = new Response(false, "User not logged in.");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.getUserDetails();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getUserDetailsSuccess() {
        Response expected;

        User user = new User();
        user.setId(1);
        user.setUsername("username-test");

        expected = new Response(true, user);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);

        Response result = userController.getUserDetails();

        Mockito.verify(securityService, times(2)).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void getUserInfoFail() {
        Response expected = new Response(false, "User not logged in.");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.getUserInfo("username-test");

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getUserInfoSuccess() {
        Response expected;

        User user = new User();
        user.setId(1);

        expected = new Response(true, user);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);

        Response result = userController.getUserInfo("username-test");

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername(any(String.class));

        assertEquals(expected, result);
    }

    @Test
    public void showActivitiesFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.showActivities();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void showActivitiesSuccessMealCase() {
        Response expected;

        List<ActivityProjection> list = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setUsername("username-test");

        List<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        activity.setId(1);
        activity.setAmount(1.0);
        activity.setXp_points(1.0);
        activity.setCategory_id(1);
        activities.add(activity);

        list.add(new ActivityProjection("username-test", "Eating a vegetarian meal", 1.0, 1.0));

        expected = new Response(true, list);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(activityService.findByUser_id(1))
                .thenReturn(activities);

        Response result = userController.showActivities();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername(any(String.class));
        Mockito.verify(activityService).findByUser_id(1);

        assertEquals(expected, result);
    }

    @Test
    public void showActivitiesSuccessLocalProduceCase() {
        Response expected;

        List<ActivityProjection> list = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setUsername("username-test");

        List<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        activity.setId(1);
        activity.setAmount(1.0);
        activity.setXp_points(1.0);
        activity.setCategory_id(2);
        activities.add(activity);

        list.add(new ActivityProjection("username-test", "Buying local produce", 1.0, 1.0));

        expected = new Response(true, list);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(activityService.findByUser_id(1))
                .thenReturn(activities);

        Response result = userController.showActivities();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername(any(String.class));
        Mockito.verify(activityService).findByUser_id(1);

        assertEquals(expected, result);
    }

    @Test
    public void showActivitiesSuccessBikeCase() {
        Response expected;

        List<ActivityProjection> list = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setUsername("username-test");

        List<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        activity.setId(1);
        activity.setAmount(1.0);
        activity.setXp_points(1.0);
        activity.setCategory_id(3);
        activities.add(activity);

        list.add(new ActivityProjection("username-test", "Using bike instead of car", 1.0, 1.0));

        expected = new Response(true, list);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(activityService.findByUser_id(1))
                .thenReturn(activities);

        Response result = userController.showActivities();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername(any(String.class));
        Mockito.verify(activityService).findByUser_id(1);

        assertEquals(expected, result);
    }

    @Test
    public void showActivitiesSuccessPublicTransportCase() {
        Response expected;

        List<ActivityProjection> list = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setUsername("username-test");

        List<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        activity.setId(1);
        activity.setAmount(1.0);
        activity.setXp_points(1.0);
        activity.setCategory_id(4);
        activities.add(activity);

        list.add(new ActivityProjection("username-test", "Using public transport instead of car", 1.0, 1.0));

        expected = new Response(true, list);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(activityService.findByUser_id(1))
                .thenReturn(activities);

        Response result = userController.showActivities();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername(any(String.class));
        Mockito.verify(activityService).findByUser_id(1);

        assertEquals(expected, result);
    }

    @Test
    public void showActivitiesSuccessSolarPanelsCase() {
        Response expected;

        List<ActivityProjection> list = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setUsername("username-test");

        List<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        activity.setId(1);
        activity.setAmount(1.0);
        activity.setXp_points(1.0);
        activity.setCategory_id(5);
        activities.add(activity);

        list.add(new ActivityProjection("username-test", "Installing solar panels", 1.0, 1.0));

        expected = new Response(true, list);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(activityService.findByUser_id(1))
                .thenReturn(activities);

        Response result = userController.showActivities();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername(any(String.class));
        Mockito.verify(activityService).findByUser_id(1);

        assertEquals(expected, result);
    }

    @Test
    public void showActivitiesSuccessHomeTemperatureCase() {
        Response expected;

        List<ActivityProjection> list = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setUsername("username-test");

        List<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        activity.setId(1);
        activity.setAmount(1.0);
        activity.setXp_points(1.0);
        activity.setCategory_id(6);
        activities.add(activity);

        list.add(new ActivityProjection("username-test", "Lowering the temperature of your home", 1.0, 1.0));

        expected = new Response(true, list);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(activityService.findByUser_id(1))
                .thenReturn(activities);

        Response result = userController.showActivities();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername(any(String.class));
        Mockito.verify(activityService).findByUser_id(1);

        assertEquals(expected, result);
    }

    @Test
    public void showActivitiesSuccessUnknownCase() {
        Response expected;

        List<ActivityProjection> list = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setUsername("username-test");

        List<Activity> activities = new ArrayList<>();
        Activity activity = new Activity();
        activity.setId(1);
        activity.setAmount(1.0);
        activity.setXp_points(1.0);
        activity.setCategory_id(0);
        activities.add(activity);

        list.add(new ActivityProjection("username-test", "unknown", 1.0, 1.0));

        expected = new Response(true, list);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(activityService.findByUser_id(1))
                .thenReturn(activities);

        Response result = userController.showActivities();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername(any(String.class));
        Mockito.verify(activityService).findByUser_id(1);

        assertEquals(expected, result);
    }

    @Test
    public void addActivityFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.addActivity(new Activity());

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void addActivitySuccess() {
        Response expected = new Response(true, "Activity \"category-test\" saved successfully!");

        Activity activity = new Activity();
        activity.setCategory_id(1);
        activity.setAmount(1.0);

        User user = new User();
        user.setId(1L);

        Category category = new Category();
        category.setXp_points(1.0);
        category.setName("category-test");

        Mockito.doAnswer((i) -> null).when(activityService).save(any(Activity.class));
        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.when(categoryRepository.findById(1))
                .thenReturn(category);

        Response result = userController.addActivity(activity);

        Mockito.verify(activityService).save(any(Activity.class));
        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername(any(String.class));
        Mockito.verify(categoryRepository, times(2)).findById(1);

        assertEquals(expected, result);
    }

    @Test
    public void removeActivityFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.removeActivity(1);

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void removeActivityNotFoundActivity() {
        Response expected = new Response(false, "Activity not found!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(activityService.findById(1))
                .thenReturn(null);

        Response result = userController.removeActivity(1);

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(activityService).findById(1);

        assertEquals(expected, result);
    }

    @Test
    public void removeActivitySomeoneElseActivity() {
        Response expected = new Response(false, "You can't remove someone else's activity!");

        Activity activity = new Activity();
        activity.setUser_id(1);

        User user = new User();
        user.setId(2);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(activityService.findById(1))
                .thenReturn(activity);
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);

        Response result = userController.removeActivity(1);

        Mockito.verify(securityService, times(2)).findLoggedInUsername();
        Mockito.verify(activityService).findById(1);
        Mockito.verify(userService).findByUsername((any(String.class)));

        assertEquals(expected, result);
    }

    @Test
    public void removeActivitySuccess() {
        Response expected = new Response(true, "Activity \"category-test\" removed successfully!");

        Activity activity = new Activity();
        activity.setUser_id(1);
        activity.setCategory_id(1);

        User user = new User();
        user.setId(1);

        Category category = new Category();
        category.setName("category-test");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(activityService.findById(1))
                .thenReturn(activity);
        Mockito.when(userService.findByUsername(any(String.class)))
                .thenReturn(user);
        Mockito.doAnswer((i) -> null).when(activityService).delete(any(Activity.class));
        Mockito.when(categoryRepository.findById(1))
                .thenReturn(category);

        Response result = userController.removeActivity(1);

        Mockito.verify(securityService, times(2)).findLoggedInUsername();
        Mockito.verify(activityService).findById(1);
        Mockito.verify(userService).findByUsername((any(String.class)));
        Mockito.verify(activityService).delete(any(Activity.class));
        Mockito.verify(categoryRepository).findById(1);

        assertEquals(expected, result);
    }

    @Test
    public void getFollowingsFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.getFollowings();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getUserFollowingsSuccess() {
        Response expected;

        User user = new User();
        user.setId(1);

        List<User> userList = new ArrayList<>();
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user-following-test");
        user2.setLast_name("last-name-test");
        user2.setFirst_name("first-name-test");
        user2.setExperience_points(1.0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user2.setLast_update(timestamp);
        userList.add(user2);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(userService.findFollowings(1))
                .thenReturn(userList);
        Mockito.when(followingService.findById1Id2(1, 2))
                .thenReturn(new Following());

        expected = new Response(true, userController.toUserProjection(userList, user.getId()));

        Response result = userController.getFollowings();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(userService).findFollowings(1);
        Mockito.verify(followingService, times(2)).findById1Id2(1, 2);

        assertEquals(expected, result);
    }

    @Test
    public void getFollowedByFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.getFollowedBy();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getFollowedBySuccess() {
        Response expected;

        User user = new User();
        user.setId(1);

        List<User> userList = new ArrayList<>();
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user-following-test");
        user2.setLast_name("last-name-test");
        user2.setFirst_name("first-name-test");
        user2.setExperience_points(1.0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user2.setLast_update(timestamp);
        userList.add(user2);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(userService.findFollowedBy(1))
                .thenReturn(userList);
        Mockito.when(followingService.findById1Id2(1, 2))
                .thenReturn(null);

        expected = new Response(true, userController.toUserProjection(userList, user.getId()));

        Response result = userController.getFollowedBy();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(userService).findFollowedBy(1);
        Mockito.verify(followingService, times(2)).findById1Id2(1, 2);

        assertEquals(expected, result);
    }

    @Test
    public void addFollowingFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.addFollowing(new Following(), "username-test");

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void addFollowingUserNotFound() {
        Response expected = new Response(false, "User not found.");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(null);

        Response result = userController.addFollowing(new Following(), "username-test");

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void addFollowingRecursiveFollow() {
        Response expected = new Response(false, "You already follow yourself...");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(new User());

        Response result = userController.addFollowing(new Following(), "username-test");

        Mockito.verify(securityService, times(2)).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void addFollowingSuccess() {
        Response expected = new Response(true, "Your followings have been updated!");

        User user = new User();
        User user2 = new User();
        user.setId(1);
        user2.setId(2);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(userService.findByUsername("username-test2"))
                .thenReturn(user2);
        Mockito.doAnswer((i) -> null).when(followingService).save(any(Following.class));

        Response result = userController.addFollowing(new Following(), "username-test2");

        Mockito.verify(securityService, times(3)).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(userService, times(2)).findByUsername("username-test2");
        Mockito.verify(followingService).save(any(Following.class));

        assertEquals(expected, result);
    }

    @Test
    public void removeFollowingFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.removeFollowing("username-test");

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void removeFollowingUserNotFound() {
        Response expected = new Response(false, "User not found.");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(null);

        Response result = userController.removeFollowing("username-test");

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void removeFollowingRecursiveUnfollow() {
        Response expected = new Response(false, "You cannot unfollow yourself...");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(new User());

        Response result = userController.removeFollowing("username-test");

        Mockito.verify(securityService, times(2)).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void removeFollowingSuccess() {
        Response expected = new Response(true, "Your followings have been updated!");

        User user = new User();
        User user2 = new User();
        user.setId(1);
        user2.setId(2);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(userService.findByUsername("username-test2"))
                .thenReturn(user2);
        Mockito.doAnswer((i) -> null).when(followingService).delete(any(Following.class));
        Mockito.when(followingService.findById1Id2(1, 2))
                .thenReturn(new Following());

        Response result = userController.removeFollowing("username-test2");

        Mockito.verify(securityService, times(3)).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(userService, times(2)).findByUsername("username-test2");
        Mockito.verify(followingService).delete(any(Following.class));
        Mockito.verify(followingService).findById1Id2(1, 2);

        assertEquals(expected, result);
    }

    @Test
    public void getRecommendationFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(null);

        Response result = userController.getRecommendation();

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");

        assertEquals(expected, result);
    }

    @Test
    public void getRecommendationSuccessFood() {
        String expectedToContain = "Category: Food";

        User user = new User();
        user.setId(1);

        List<Activity> activities = new ArrayList<>();
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        activity1.setCategory_id(1);
        activity2.setCategory_id(2);
        activity1.setAmount(1.0);
        activity2.setAmount(1.0);

        activities.add(activity1);
        activities.add(activity2);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(activityService.findByUser_id(1))
                .thenReturn(activities);

        String result = (String)(userController.getRecommendation().getData());

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(activityService).findByUser_id(1);

        assertTrue(result.contains(expectedToContain));
    }

    @Test
    public void getRecommendationSuccessHousehold() {
        String expectedToContain = "Category: Household";

        User user = new User();
        user.setId(1);

        List<Activity> activities = new ArrayList<>();
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        Activity activity3 = new Activity();
        activity1.setCategory_id(5);
        activity2.setCategory_id(6);
        activity3.setCategory_id(40);
        activity1.setAmount(1.0);
        activity2.setAmount(1.0);
        activity3.setAmount(1.0);

        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(activityService.findByUser_id(1))
                .thenReturn(activities);

        String result = (String)(userController.getRecommendation().getData());

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(activityService).findByUser_id(1);

        assertTrue(result.contains(expectedToContain));
    }

    @Test
    public void getRecommendationSuccessTransportation() {
        String expectedToContain = "Category: Transportation";

        User user = new User();
        user.setId(1);

        List<Activity> activities = new ArrayList<>();
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        Activity activity3 = new Activity();
        activity1.setCategory_id(3);
        activity2.setCategory_id(4);
        activity3.setCategory_id(1);
        activity1.setAmount(1.0);
        activity2.setAmount(1.0);
        activity3.setAmount(1.0);

        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(activityService.findByUser_id(1))
                .thenReturn(activities);

        String result = (String)(userController.getRecommendation().getData());

        Mockito.verify(securityService).findLoggedInUsername();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(activityService).findByUser_id(1);

        assertTrue(result.contains(expectedToContain));
    }

    @Test
    public void getLeaderboardFail() {
        Response expected = new Response(false, "You are not authorized!");

        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn(null);

        Response result = userController.getLeaderboard();

        Mockito.verify(securityService).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void getLeadeboardSuccess() {
        Response expected;

        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        User user1 = new User();
        user1.setId(2);
        user1.setUsername("username-test2");
        user1.setFirst_name("first-name-test2");
        user1.setLast_name("last-name-test2");
        user1.setExperience_points(1.0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user1.setLast_update(timestamp);

        users.add(user1);


        Mockito.when(securityService.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findLeaderboard())
                .thenReturn(users);
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(followingService.findById1Id2(1, 2))
                .thenReturn(new Following());

        expected = new Response(true, userController.toUserProjection(users, 1));

        Response result = userController.getLeaderboard();

        Mockito.verify(securityService, times(2)).findLoggedInUsername();
        Mockito.verify(userService).findLeaderboard();
        Mockito.verify(userService).findByUsername("username-test");
        Mockito.verify(followingService, times(2)).findById1Id2(1, 2);

        assertEquals(expected, result);
    }
}