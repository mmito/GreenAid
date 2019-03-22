package app.models;

import java.util.Objects;

public class ActivityProjection {

    long id;
    String username;
    String category;
    double amount;
    double xp_points;

    public ActivityProjection() {
        this.id = 0;
        this.username = new String();
        this.category = new String();
        this.amount = 0.0;
        this.xp_points = 0.0;
    }

    /**
     * Constructor of the class that  projects the activities in the homepage of the app.
     * @param id id of the activity
     * @param username username of the user
     * @param category category of the activity
     * @param amount amount of the activity
     * @param xp_points xp points of that activity given the amount
     */
    public ActivityProjection(long id, String username, String category, double amount, double xp_points) {
        this.id = id;
        this.username = username;
        this.category = category;
        this.amount = amount;
        this.xp_points = xp_points;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getXp_points() {
        return xp_points;
    }

    public void setXp_points(double xp_points) {
        this.xp_points = xp_points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityProjection that = (ActivityProjection) o;
        return Double.compare(that.amount, amount) == 0 &&
                Double.compare(that.xp_points, xp_points) == 0 &&
                Objects.equals(username, that.username) &&
                Objects.equals(category, that.category);
    }

}