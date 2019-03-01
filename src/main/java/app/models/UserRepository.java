package app.models;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

    // custom query to search by username
    //List<User> findByUsername(String text);

}
