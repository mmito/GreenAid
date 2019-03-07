package app.controllers;

import app.authentication.SecurityServiceImpl;
import app.services.UserServiceImpl;
import app.models.User;
import app.repository.UserRepository;
import app.validator.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(SpringController.class)
public class SpringControllerTest {

    private static final String USER_NAME = "tommy";
    private static final String PASSWORD = "123";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private SecurityServiceImpl securityService;

    @MockBean
    private UserValidator userValidator;

    @MockBean
    private UserRepository userRepository;


    @Test
    public void welcomeEndpointIncorrectUserCredentials() throws Exception {

        HttpHeaders headers = new HttpHeaders();

        String authHeader = getAuthenticationHeader();
        headers.set("Authorization", authHeader);

        // https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4

        this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON).headers(headers))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://*/login"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void welcomeEndpointValidUserCredentials() throws Exception {

        this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("welcome"));
    }

    @Test
    public void registrationGetRequest() throws Exception {

        this.mockMvc.perform(get("/registration").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("registration"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void registrationPostRequest() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        User userForm = new User();
        userForm.setUsername("aName");
        userForm.setPassword("aPassword");
        //userForm.setPasswordConfirm("aPassword");
        String requestJson = ow.writeValueAsString(userForm);


        this.mockMvc.perform(
            post("/registration")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(content().string("redirect:/welcome"));

    }

    /*@Test
    @WithMockUser(value = "spring")
    public void activityEndpointPost() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Activity activity = new Activity("car", "100");
        String requestJson = ow.writeValueAsString(activity);

        this.mockMvc.perform(
                post("/activities")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(content().string("Activity information saved successfully: car 100"));

    }*/


    private String getAuthenticationHeader() {
        String auth = USER_NAME + ":" + PASSWORD;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(encodedAuth);
    }

}
