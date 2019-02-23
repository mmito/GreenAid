package app.models;

public class Activity {

    private String name;
    private String co2;

    public Activity() {

    }

    /**
     * Constructor of activity class that a user does.
     * @param name name of the activity.
     * @param co2 amount of co2 saved from that activity.
     */
    public Activity(String name, String co2) {

        this.name = name;
        this.co2 = co2;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", co2='" + co2 + '\'' +
                '}';
    }
}
