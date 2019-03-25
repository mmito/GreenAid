package app.models;

import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("ALL")
@Entity
@Table(name = "following")
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id_1;
    private Long user_id_2;
    private Timestamp last_update;

    public Following() {
        this.user_id_1 = 0L;
        this.user_id_2 = 0L;
        this.last_update = null;
    }

    public Following(Long user_id_1, Long user_id_2, Timestamp last_update) {
        this.user_id_1 = user_id_1;
        this.user_id_2 = user_id_2;
        this.last_update = last_update;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Following following = (Following) o;
        return Objects.equals(id, following.id);
    }
}
