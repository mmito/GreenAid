package app.models;

import java.sql.Timestamp;
import java.util.Objects;

public class UserProjection {

    String username;
    String first_name;
    String last_name;
    double experience_points;
    Timestamp last_update;
    boolean following;

    public UserProjection(String username, String first_name, String last_name, double experience_points, Timestamp last_update, boolean following) {

        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.experience_points = experience_points;
        this.last_update = last_update;
        this.following = following;

    }

    public UserProjection() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public double getExperience_points() {
        return experience_points;
    }

    public void setExperience_points(double experience_points) {
        this.experience_points = experience_points;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProjection that = (UserProjection) o;
        return Double.compare(that.experience_points, experience_points) == 0 &&
                following == that.following &&
                Objects.equals(username, that.username) &&
                Objects.equals(first_name, that.first_name) &&
                Objects.equals(last_name, that.last_name) &&
                Objects.equals(last_update, that.last_update);
    }
}
