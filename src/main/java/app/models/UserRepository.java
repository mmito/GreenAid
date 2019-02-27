package app.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    // custom query to search by username
    //List<User> findByUsername(String text);

}
