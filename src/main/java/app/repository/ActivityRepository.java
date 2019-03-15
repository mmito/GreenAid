package app.repository;

import app.client.ActivityRepresentation;
import app.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query(value = "SELECT * FROM activity WHERE user_id = :id", nativeQuery = true)
    List<Activity> findByUser_id(long id);

    @Query(value = "SELECT u.username as user, c.name as activity, a.amount, a.xp_points FROM activity a, user u, category c where a.user_id = u.id and a.category_id = c.id and a.user_id = :id", nativeQuery = true)
    List<Object> findNamesById(long id);

}
