package app.services;

import app.authentication.SecurityServiceImpl;
import app.models.Following;
import app.models.User;
import app.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepositoryMock;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoderMock;

    @MockBean
    private SecurityServiceImpl securityServiceMock;

    User user;

    @Before
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("password");
    }

    @Test
    public void saveUser() {
        String encodedPassword = "encoded";

        Mockito.when(bCryptPasswordEncoderMock.encode(user.getPassword())).thenReturn(encodedPassword);
        Mockito.when(userRepositoryMock.save(user)).thenReturn(user);

        this.userService.save(user);
        Mockito.verify(userRepositoryMock).save(user);
    }

    @Test
    public void saveUserEncryption() {
        String encodedPassword = "encoded";

        Mockito.when(bCryptPasswordEncoderMock.encode(user.getPassword())).thenReturn(encodedPassword);
        Mockito.when(userRepositoryMock.save(user)).thenReturn(user);

        this.userService.save(user);
        Assert.assertEquals(encodedPassword, user.getPassword());
    }

    @Test
    public void deleteExistingUser() {
        Mockito.when(userService.findByUsername("test")).thenReturn(user);

        Mockito.doAnswer((i) -> {
            Assert.assertEquals(user, i.getArgument(0));
            return null;
        }).when(userRepositoryMock).delete(user);

        this.userService.delete(user);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void deleteNonexistingUser() {
        Mockito.when(userService.findByUsername("test")).thenReturn(null);

        this.userService.delete(user);
    }

    @Test
    public void findByUsername() {
        Mockito.when(userRepositoryMock.findByUsername("test")).thenReturn(user);

        Assert.assertEquals(user, userService.findByUsername("test"));
    }

    @Test
    public void getExistingLoggedInUser() {
        Mockito.when(securityServiceMock.findLoggedInUsername()).thenReturn("test");
        Mockito.when(userRepositoryMock.findByUsername("test")).thenReturn(user);

        Assert.assertEquals(user, userService.getLoggedInUser());
    }

    @Test
    public void getNonexistingLoggedInUser() {
        Mockito.when(securityServiceMock.findLoggedInUsername()).thenReturn(null);

        Assert.assertEquals(null, userService.getLoggedInUser());
    }

    @Test
    public void findFollowings() {
        Following following1 = new Following();
        following1.setId(1L);
        following1.setUser_id_1(1L);
        following1.setUser_id_2(2L);

        Following following2 = new Following();
        following2.setId(2L);
        following2.setUser_id_1(1L);
        following2.setUser_id_2(3L);

        List followList = new ArrayList();
        followList.add(following1);
        followList.add(following2);

        Mockito.when(userRepositoryMock.findFollowings(1)).thenReturn(followList);

        List result = userService.findFollowings(1);

        Assert.assertEquals(followList, result);
    }

    @Test
    public void findFollowers() {
        Following following1 = new Following();
        following1.setId(1L);
        following1.setUser_id_1(1L);
        following1.setUser_id_2(3L);

        Following following2 = new Following();
        following2.setId(2L);
        following2.setUser_id_1(2L);
        following2.setUser_id_2(3L);

        List followList = new ArrayList();
        followList.add(following1);
        followList.add(following2);

        Mockito.when(userRepositoryMock.findFollowedBy(3)).thenReturn(followList);

        List result = userService.findFollowedBy(3);

        Assert.assertEquals(followList, result);
    }

    @Test
    public void findLeaderboard() {
        List userList = new ArrayList();
        userList.add(user);

        Mockito.when(userRepositoryMock.findLeaderboard()).thenReturn(userList);

        Assert.assertEquals(userList, userService.findLeaderboard());
    }
}