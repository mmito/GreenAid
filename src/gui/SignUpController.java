
import app.responses.Response;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import app.models.User;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

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
    @FXML
    private Text exists;
    @FXML
    private Text emptyFirst;
    @FXML
    private Text emptyLast;
    @FXML
    private Text emptyUser;
    @FXML
    private Text emptyPass;
    @FXML
    private Text emptyPassCon;
    @FXML
    private ImageView Home;

    private double xOffset =0;
    private double yOffset =0;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
    exists.setVisible(false);
    emptyFirst.setVisible(false);
    emptyLast.setVisible(false);
    emptyUser.setVisible(false);
    emptyPass.setVisible(false);
    emptyPassCon.setVisible(false);
    }

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
    @FXML
    private void home(){
        Window window = saveUser.getScene().getWindow();
        Stage stage = JavaFXMain.stage;
        stage.show();
        window.hide();

    }


    public void handleClose() {
        System.exit(0);
    }

    private void emptyFirst(){
        emptyFirst.setText("Fill in Firstname");
        emptyFirst.setVisible(true);
    }

    private void emptyLast(){
        emptyLast.setText("Fill in LastName");
        emptyLast.setVisible(true);
    }

    private void emptyUser(){
        emptyUser.setText("Fill in Username");
        emptyUser.setVisible(true);
    }

    private void emptyPass(){
        emptyPass.setText("Fill in password");
        emptyPass.setVisible(true);
    }

    private void emptyPassCon(){
        emptyPassCon.setText("Fill in password");
        emptyPassCon.setVisible(true);
    }

    @FXML
    private void saveUser(MouseEvent mouseEvent) {

        if(getFirstname().isEmpty()){
            emptyFirst();
        }
        else{
            emptyFirst.setVisible(false);
        }
        if(getLastname().isEmpty()){
            emptyLast();
        }
        else {
            emptyLast.setVisible(false);
        }
        if(getUsername().isEmpty()){
            emptyUser();
        }
        else {
            emptyUser.setVisible(false);
        }
        if(getPassword().isEmpty()){
            emptyPass();
        }
        else {
            emptyPass.setVisible(false);
        }
        if(getPasswordConfirm().isEmpty()) {
            emptyPassCon();
        }
        else {
            emptyPassCon.setVisible(false);
        }


        if (getPassword().equals(getPasswordConfirm())) {

            // HttpHeaders
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("first_name", getFirstname());
            params.add("last_name", getLastname());
            params.add("username", getUsername());
            params.add("password", getPassword());


            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            RestTemplate restTemplate = new RestTemplate();

            // Data attached to the request.
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            Response registration = restTemplate.postForObject(url_registration, request, Response.class);

            if (registration.getData().equals("Username is already registered")) {
                exists.setText("Username already exists");
                exists.setVisible(true);
                clearFields();


            }

            else {
                    Window window = saveUser.getScene().getWindow();
                    Stage stage = JavaFXMain.stage;
                    stage.show();
                    window.hide();
            }
        }
        else {
            exists.setText("Passwords do not match");
            exists.setVisible(true);

        }
    }

    private void clearFields() {
        username.setText(null);
        password.clear();
        passwordConfirm.clear();
    }



}


