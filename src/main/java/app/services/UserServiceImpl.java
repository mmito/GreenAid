package app.services;

import app.authentication.SecurityServiceImpl;
import app.models.User;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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
}

