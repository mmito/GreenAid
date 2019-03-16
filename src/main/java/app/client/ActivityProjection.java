package app.client;

public class ActivityProjection {

    String username;
    String category;
    double amount;
    double xp_points;

    /**
     * Constructor of the class that  projects the activities in the homepage of the app.
     * @param username username of the user
     * @param category category of the activity
     * @param amount amount of the activity
     * @param xp_points xp points of that activity given the amount
     */
    public ActivityProjection(String username, String category, double amount, double xp_points) {
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
}
