package app.client;

import app.models.ActivityProjection;
import app.models.User;
import app.models.UserProjection;
import app.responses.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class Client {

    private static final String url_login = "http://localhost:8080/login";
    private static final String url_check = "http://localhost:8080/check";
    private static final String url_categories = "http://localhost:8080/getcategories";
    private static final String url_add_activity = "http://localhost:8080/user/add-activity";
    private static final String url_user_activities = "http://localhost:8080/user/activities";
    private static final String url_user_details = "http://localhost:8080/user/details";
    private static final String url_user_followings = "http://localhost:8080/user/followings";
    private static final String url_user_followed_by = "http://localhost:8080/user/followed-by";
    private static final String url_add_follow = "http://localhost:8080/user//add-following";

    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * Method that retrieves its session cookie with the login data.
     * @param username username of the user
     * @param password passwrod of the user
     * @return returns cookie of the header
     */
    public static String getSessionCookie(String username, String password) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        HttpEntity<Response> response = HttpRequests.postRequest("", url_login, params);
        HttpHeaders responseHeaders = response.getHeaders();
        if (responseHeaders.getFirst(HttpHeaders.SET_COOKIE) != null) {
            return responseHeaders.getFirst(HttpHeaders.SET_COOKIE).split(";")[0];
        } else {
            return "No cookie found.";
        }

    }


    /**
     * Test method just to check whether the session cookies are working or not.
     * @param sessionCookie sessionCookie is used for checking
     * @return returns the data of the user to show whether he/she's logged in or not.
     */
    public static String checkAuth(String sessionCookie) {

        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, url_check);

        return (String) response.getBody().getData();
    }

    /**
     * Method used to display the list of categories, just if the session is authenticated.
     * @param sessionCookie sessionCookie is used for checking
     * @return returns the list of categories
     */
    public static String getCategories(String sessionCookie) {

        HttpEntity<Response> categories = HttpRequests.getRequest(sessionCookie, url_categories);

        return ((String) categories.getBody().getData()).replaceFirst("\n", "");

    }

    // Method used to add an activity in the database given a session, a category id and the amount of times that activity has been made.
    /**
     * Method used to add an activity in the database given a session, a category id
     * and the amount of times that activity has been made.
     * @param sessionCookie sessionCookie is used for this operation
     * @param categoryId categoryID is used to determine which activity wiil be added
     * @param amount amount of the activity
     * @return returns the data of the added activity
     */
    public static String addActivity(String sessionCookie, long categoryId, double amount) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("category_id", categoryId);
        params.add("amount", amount);

        HttpEntity<Response> response = HttpRequests.postRequest(sessionCookie, url_add_activity, params);

        return (String) response.getBody().getData();

    }

    /**
     * Lists the activities of the user.
     * @param sessionCookie uses sessionCookies to achieve this.
     * @return returns the list of the activities done by the user
     */
    public static List<ActivityProjection> getUserActivities(String sessionCookie) {

        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, url_user_activities);
        List<ActivityProjection> activities = mapper.convertValue(response.getBody().getData(), new TypeReference<List<ActivityProjection>>() {});
        return activities;

    }

    public static User getUserDetails(String sessionCookie) {

        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, url_user_details);
        User user = mapper.convertValue(response.getBody().getData(), User.class);
        return user;

    }

    public static List<UserProjection> getUserFollowings(String sessionCookie) {

        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, url_user_followings);
        List<UserProjection> followings = mapper.convertValue(response.getBody().getData(), new TypeReference<List<UserProjection>>() {});
        return followings;


    }

    public static List<UserProjection> getUserFollowedBy(String sessionCookie) {

        HttpEntity<Response> response = HttpRequests.getRequest(sessionCookie, url_user_followed_by);
        List<UserProjection> followedBy = mapper.convertValue(response.getBody().getData(), new TypeReference<List<UserProjection>>() {});
        return followedBy;

    }

    public static String addFollow(String sessionCookie, String username) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", username);

        HttpEntity<Response> response = HttpRequests.postRequest(sessionCookie, url_add_follow, params);

        return (String) response.getBody().getData();

    }

     public static void main(String[] args) {

        String sessionCookie = getSessionCookie("tommytest", "quadronno");
        User user = getUserDetails(sessionCookie);
        System.out.println(user.getExperience_points());
        List<ActivityProjection> activities = getUserActivities(sessionCookie);
        System.out.println(activities.size());
        List<UserProjection> followings = getUserFollowings(sessionCookie);
        System.out.println(followings.size());
        List<UserProjection> followedBy = getUserFollowedBy(sessionCookie);
        System.out.println(followedBy.size());
        System.out.println(addFollow(sessionCookie,"UsainBolt"));
        List<UserProjection> followings2 = getUserFollowings(sessionCookie);
        System.out.println(followings2.size());

     }

}
