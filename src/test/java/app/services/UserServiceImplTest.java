package app.services;

import app.authentication.SecurityServiceImpl;
import app.models.Following;
import app.models.User;
import app.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoderMock;

    @MockBean
    private SecurityServiceImpl securityServiceMock;

    @MockBean
    private FollowingServiceImpl followingServiceMock;

    @MockBean
    private UserRepository userRepositoryMock;

    User user;

    @Before
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("password");
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

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

    @Test
    public void addFollowingUserNotFound() {
        String expected = "User not found.";

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(null);

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(expected);
        userService.addFollowing(new Following(), "username-test");
    }

    @Test
    public void addFollowingRecursiveFollow() {
        String expected = "You already follow yourself...";

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userService.findByUsername("username-test"))
                .thenReturn(new User());

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(expected);
        userService.addFollowing(new Following(), "username-test");
    }

    @Test
    public void addFollowingSuccess() {
        String expected = "Your followings have been updated!";

        User user2 = new User();
        user2.setId(2);

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");

        Mockito.when(userRepositoryMock.findByUsername("username-test2"))
                .thenReturn(user2);
        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(user);

        Mockito.doAnswer((i) -> null).when(followingServiceMock).save(any(Following.class));

        String result = userService.addFollowing(new Following(), "username-test2");

        Mockito.verify(securityServiceMock, Mockito.times(3)).findLoggedInUsername();

        assertEquals(expected, result);
    }

    @Test
    public void removeFollowingUserNotFound() {
        String expected = "User not found.";

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(null);

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(expected);
        userService.removeFollowing("username-test");

        Mockito.verify(securityServiceMock).findLoggedInUsername();
        Mockito.verify(userRepositoryMock).findByUsername("username-test");
    }

    @Test
    public void removeFollowingRecursiveUnfollow() {
        String expected = "You cannot unfollow yourself...";

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(new User());

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(expected);
        userService.removeFollowing("username-test");

        Mockito.verify(securityServiceMock).findLoggedInUsername();
        Mockito.verify(userRepositoryMock).findByUsername("username-test");
    }

    @Test
    public void removeFollowingSuccess() {
        String expected = "Your followings have been updated!";

        User user = new User();
        User user2 = new User();
        user.setId(1);
        user2.setId(2);

        Mockito.when(securityServiceMock.findLoggedInUsername())
                .thenReturn("username-test");
        Mockito.when(userRepositoryMock.findByUsername("username-test"))
                .thenReturn(user);
        Mockito.when(userRepositoryMock.findByUsername("username-test2"))
                .thenReturn(user2);
        Mockito.doAnswer((i) -> null).when(followingServiceMock).delete(any(Following.class));
        Mockito.when(followingServiceMock.findById1Id2(1, 2))
                .thenReturn(new Following());

        String result = userService.removeFollowing("username-test2");

        Mockito.verify(securityServiceMock, times(2)).findLoggedInUsername();
        Mockito.verify(userRepositoryMock).findByUsername("username-test");
        Mockito.verify(userRepositoryMock, times(2)).findByUsername("username-test2");
        Mockito.verify(followingServiceMock).delete(any(Following.class));
        Mockito.verify(followingServiceMock).findById1Id2(1, 2);

        assertEquals(expected, result);
    }
}