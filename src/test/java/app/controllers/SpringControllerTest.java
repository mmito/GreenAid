package app.controllers;

import app.models.Activity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(SpringController.class)
public class SpringControllerTest {

    private static final String USER_NAME = "tommy";
    private static final String PASSWORD = "123";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void welcomeEndpoint() throws Exception {

        HttpHeaders headers = new HttpHeaders();

        String authHeader = getAuthenticationHeader();
        headers.set("Authorization", authHeader);

        // https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4

        this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON).headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().string("WELCOME TEST"));

    }

    @Test
    public void activityEndpointPost() throws Exception {

        HttpHeaders headers = new HttpHeaders();

        String authHeader = getAuthenticationHeader();
        headers.set("Authorization", authHeader);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Activity activity = new Activity("car", "100");
        String requestJson = ow.writeValueAsString(activity);

        this.mockMvc.perform(
                post("/activities")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .headers(headers)
        )
                .andExpect(status().isOk())
                .andExpect(content().string("Activity information saved successfully: car 100"));

    }

    private String getAuthenticationHeader() {
        String auth = USER_NAME + ":" + PASSWORD;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(encodedAuth);
    }

}
