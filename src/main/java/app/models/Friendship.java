package app.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id_1;
    private Long user_id_2;
    private Timestamp last_update;

    public Long getUser_id_1() {
        return user_id_1;
    }

    public void setUser_id_1(Long user_id_1) {
        this.user_id_1 = user_id_1;
    }

    public Long getUser_id_2() {
        return user_id_2;
    }

    public void setUser_id_2(Long user_id_2) {
        this.user_id_2 = user_id_2;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
