package app.authentication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityServiceImplTest {

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findLoggedInUsernameSuccess() throws Exception {

        this.mockMvc.perform(post("/login")
                .param("username", "cpene")
                .param("password", "cpenecpene"));

        this.mockMvc.perform(get("/check"))
                .andExpect(jsonPath("$.data").value("Your username is:cpene"));
    }

    @Test
    public void findLoggedInUsernameNotLoggedIn() throws Exception{
        this.mockMvc.perform(get("/check"))
                .andExpect(jsonPath("$.data").value("User not logged in"));
    }

    @Test
    public void autoLoginSuccess(){
        securityService.autoLogin("cpene", "cpenecpene");
        assertEquals("cpene", securityService.findLoggedInUsername());
    }
}