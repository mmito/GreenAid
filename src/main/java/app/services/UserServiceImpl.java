package app.services;

import app.authentication.SecurityServiceImpl;
import app.models.User;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@SuppressWarnings("ALL")
@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SecurityServiceImpl securityService;

    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(User user) {
        if (this.findByUsername(user.getUsername()) != null)
            userRepository.delete(user);
        else
            throw new UsernameNotFoundException("Username not found");
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * gets the user.
     * @return returns the username
     */
    public User getLoggedInUser() {
        String loggedInUserUsername = securityService.findLoggedInUsername();

        if (loggedInUserUsername == null) {
            return null;
        }

        return userRepository.findByUsername(loggedInUserUsername);
    }

    public List<User> findFollowings(long id) {

        return userRepository.findFollowings(id);

    }

    public List<User> findFollowedBy(long id) {

        return userRepository.findFollowedBy(id);

    }

    public List<User> findLeaderboard() {
        return userRepository.findLeaderboard();
    }

}

