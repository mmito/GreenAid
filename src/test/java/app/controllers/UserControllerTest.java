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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment  = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

        list.add(new ActivityProjection(1, "username-test", "Eating a vegetarian meal", 1.0, 1.0));

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

        list.add(new ActivityProjection(1, "username-test", "Buying local produce", 1.0, 1.0));

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

        list.add(new ActivityProjection(1, "username-test", "Using bike instead of car", 1.0, 1.0));

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

        list.add(new ActivityProjection(1, "username-test", "Using public transport instead of car", 1.0, 1.0));

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

        list.add(new ActivityProjection(1, "username-test", "Installing solar panels", 1.0, 1.0));

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

        list.add(new ActivityProjection(1, "username-test", "Lowering the temperature of your home", 1.0, 1.0));

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

        list.add(new ActivityProjection(1, "username-test", "unknown", 1.0, 1.0));

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


}