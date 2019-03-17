package app.authentication;

import app.WebSecurityConfig;
import app.controllers.RouteController;
import app.repository.CategoryRepository;
import app.repository.UserRepository;
import app.responses.Response;
import app.services.ActivityServiceImpl;
import app.services.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import app.client.Client;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static app.client.Client.postRequest;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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