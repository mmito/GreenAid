package app.authentication;

import app.models.User;
import app.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepositoryMock;

    @Test
    public void returnUserDetails() {

        User user = new User();
        user.setUsername("Name");
        user.setPassword("Password");

        Mockito.when(userRepositoryMock.findByUsername("Name")).thenReturn(user);
        UserDetails result = userDetailsService.loadUserByUsername("Name");

        Assert.assertEquals("Name", result.getUsername());
        Assert.assertEquals("Password", result.getPassword());
    }

    @Test
    public void returnAuthorities(){
        Collection<? extends GrantedAuthority> authorities = userDetailsService.getAuthorities();
        Assert.assertEquals(0, authorities.size());
    }
}
