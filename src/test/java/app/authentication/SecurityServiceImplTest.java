package app.authentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SecurityServiceImplTest {

    SecurityServiceImpl securityService;

    @Before
    public void setUp() throws Exception {
        securityService = new SecurityServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
        securityService = null;
    }

    @Test
    public void findLoggedInUsername() {
        assertEquals("idk", securityService.findLoggedInUsername());
    }

    @Test
    public void autoLogin() {
    }
}