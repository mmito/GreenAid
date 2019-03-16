package app.authentication;

import app.controllers.RouteController;
import app.responses.Response;
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
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class SecurityServiceImplTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//        securityService = new SecurityServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
//        securityService = null;
    }

    @Test
    public void findLoggedInUsername() throws Exception {

        this.mockMvc.perform(get("/")).andDo(print());

//        this.mockMvc.perform(get("/login")).andDo(print());

//        assertEquals("idk", Client.getSessionCookie("cpene", "cpenecpene"));
    }

    @Test
    public void autoLogin() {
    }


}