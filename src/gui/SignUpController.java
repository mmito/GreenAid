
import javafx.fxml.FXML;
import javafx.scene.control.*;

import app.models.User;
import javafx.scene.input.MouseEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SignUpController {

    private static final String url_registration = "http://localhost:8080/registration";

    @FXML
    private Button saveUser;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirm;
    @FXML
    private TextField username;

    public String getUsername() {
        return username.getText();

    }

    public String getPassword() {
        return password.getText();
    }

    public String getPasswordConfirm() {
        return passwordConfirm.getText();
    }

    public void handleClose() {
        System.exit(0);
    }

    @FXML
    private void saveUser(MouseEvent mouseEvent) {

        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", getUsername());
        params.add("password", getPassword());
        params.add("passwordConfirm", getPasswordConfirm());


        headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String registration = restTemplate.postForObject(url_registration, request, String.class);


        clearFields();

    }
    private void clearFields() {
        username.setText(null);
        password.clear();
        passwordConfirm.clear();
    }

}


