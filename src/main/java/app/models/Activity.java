package app.models;

public class Activity {

    private String name;
    private String co2;

    public Activity() {

    }

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
}
