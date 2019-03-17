import app.client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class controller implements Initializable{

    @FXML
    Hyperlink register;
    @FXML
    Button login;
    @FXML
    Button signup;
    @FXML
    AnchorPane pane;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Text invalid;

    public static String sessionCookie = null;

    public static Stage stage = null;

    public static String Name = null;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        invalid.setVisible(false);
    }
    public void handleClose(){
        System.exit(0);
    }

    public void handleMinimizeButton(MouseEvent mouse){

        JavaFXMain.stage.setIconified(true);
    }
    private void clearFields() {
        username.setText(null);
        password.clear();
    }
    public void handleRegisterClicked(){
        try{
            Window window = register.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
            Stage stage = new Stage();
            stage.setTitle("signUp");
            stage.setScene(new Scene(root, 700, 655));
            stage.initStyle(StageStyle.UNDECORATED);
            this.stage= stage;
            stage.show();
            window.hide();
        }
        catch(IOException e){
            System.out.println("Error found in the handleRegisterClicked class");
        }
    }


    public String getUsername() {
        this.Name = username.getText();
        return username.getText();

    }

    public String getPassword() {
        return password.getText();
    }

    public void handleLoginClicked(){

        String response = Client.getSessionCookie(getUsername(), getPassword());

        if(!response.equals("No cookie found.")){

            this.sessionCookie= response;


            try{
                Window window = login.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("HomePage");
                stage.setScene(new Scene(root, 1398, 954));
                stage.initStyle(StageStyle.UNDECORATED);
                this.stage=stage;
                stage.show();
                window.hide();
            }
            catch(IOException e){
                System.out.println("Error found in the handleLoginClicked");
            }

        }
        else if(getUsername().equals("") && getPassword().equals("") ){
            invalid.setText("Fill in username and password");
            invalid.setVisible(true);
        }

           else if(!getUsername().equals("") && getPassword().equals("") ){
                invalid.setText("Fill in password");
                invalid.setVisible(true);

        }

        else if(getUsername().equals("") && !getPassword().equals("") ) {
            invalid.setText("Fill in username");
            invalid.setVisible(true);
        }

        else{
                invalid.setText("Username or password is incorrect");
                invalid.setVisible(true);

        }


    }


}
