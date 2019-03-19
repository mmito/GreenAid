package app.repository;

        import app.models.User;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "SELECT * FROM user where id in (select user_id_2 from following where user_id_1 = :id)", nativeQuery = true)
    List<User> findFollowings(long id);

    @Query(value = "SELECT * FROM user where id in (select user_id_1 from following where user_id_2 = :id)", nativeQuery = true)
    List<User> findFollowedBy(long id);

}

