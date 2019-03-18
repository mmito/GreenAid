package app.services;

import app.authentication.SecurityServiceImpl;
import app.models.User;
import app.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment  = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class UserServiceImplTest {

    private final String username = "cpene";
    private final String password = "cpenecpene";
    private final String hashedPassword = "$2a$10$5FEZ/RsoT4An68p4e6xCkuB6nVI8AVSelSxvAT.JXY6B6vWg4j5KO";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private SecurityServiceImpl securityService;

    @Test
    public void saveWithoutModifyingDB() {
        User user = new User();
        user.setUsername("XXXtest0User0NameXXZ");
        user.setPassword("testUser_password");
        user.setFirst_name("testUser_firstname");
        user.setLast_name("testUser_lastname");
        userService.save(user);
        assertEquals(user, userService.findByUsername("XXXtest0User0NameXXZ"));
        userService.delete(user);
    }

    @Test
    public void deleteExistingUser() {
        User user = new User();
        user.setUsername("XXXtest0User0NameXXZ");
        user.setPassword("testUser_password");
        user.setFirst_name("testUser_firstname");
        user.setLast_name("testUser_lastname");
        userService.save(user);
        userService.delete(user);
        assertNull(userService.findByUsername("XXXtest0User0NameXXZ"));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void deleteNotExistingUser() {
        User user = new User();
        user.setUsername("XXXtest0User0NameXXZ");
        user.setPassword("testUser_password");
        user.setFirst_name("testUser_firstname");
        user.setLast_name("testUser_lastname");
        userService.delete(user);
    }

    @Test
    public void findByUsernameCorrectUsername() {
        User user = userService.findByUsername(username);
        assertEquals(hashedPassword, user.getPassword());
    }

    @Test
    public void findByUsernameWrongUsername() {
        User user = userService.findByUsername("wrong_username");
        assertEquals(null, user);
    }

    @Test
    public void getLoggedInUserNotLoggedIn() {
        assertNull(userService.getLoggedInUser());
    }

    @Test
    public void getLoggedInUserLoggedIn() throws Exception {

        securityService.autoLogin(username, password);
        User user = userService.getLoggedInUser();
        assertEquals(hashedPassword, user.getPassword());
    }
}