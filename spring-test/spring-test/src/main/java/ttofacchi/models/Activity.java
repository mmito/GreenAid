package ttofacchi.models;

public class Activity {

    private String name;
    private int co2;

    public Activity() {

    }

    public Activity(String name, int co2) {

        this.name = name;
        this.co2 = co2;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }
}
