package app.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;
    private Long category_id;
    private double carbon_emission;
    private Timestamp last_update;
    private String carbon_activity;
    private double xp_points;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public double getCarbon_emission() {
        return carbon_emission;
    }

    public void setCarbon_emission(double carbon_emission) {
        this.carbon_emission = carbon_emission;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public String getCarbon_activity() {
        return carbon_activity;
    }

    public void setCarbon_activity(String carbon_activity) {
        this.carbon_activity = carbon_activity;
    }

    public double getXp_points() {
        return xp_points;
    }

    public void setXp_points(double xp_points) {
        this.xp_points = xp_points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
