
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import app.models.User;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private AnchorPane pane;

    private double xOffset =0;
    private double yOffset =0;



    public String getUsername() {
        return username.getText();

    }
    public String getFirstname(){
        return firstname.getText();
    }
    public String getLastname(){
        return lastname.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getPasswordConfirm() {
        return passwordConfirm.getText();
    }

    public void movingSignup() {
        pane.setOnMousePressed(event -> {
            xOffset = controller.stage.getX() - event.getScreenX();
            yOffset = controller.stage.getY() - event.getScreenY();
        });
        pane.setOnMouseDragged(event -> {
            controller.stage.setX(event.getScreenX() + xOffset);
            controller.stage.setY(event.getScreenY() + yOffset);
        });
    }


    public void handleClose() {
        System.exit(0);
    }

    @FXML
    private void saveUser(MouseEvent mouseEvent) {

        if (getPassword().equals(getPasswordConfirm())) {

            // HttpHeaders
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("first_name", getFirstname());
            params.add("last_name", getLastname());
            params.add("username", getUsername());
            params.add("password", getPassword());


            headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            RestTemplate restTemplate = new RestTemplate();

            // Data attached to the request.
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            String registration = restTemplate.postForObject(url_registration, request, String.class);

            if (registration.equals("Username is already registered")) {

                clearFields();

            } else {


                try {
                    Window window = saveUser.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 760.0, 420.0));
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                    window.hide();
                } catch (IOException e) {
                    System.out.print("Error found in the handleSignupClicked");
                }
            }
        }
        else{

            clearFields();

        }
    }
    private void clearFields() {
        username.setText(null);
        password.clear();
        passwordConfirm.clear();
    }

}


