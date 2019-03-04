import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class controller {

    @FXML
    Hyperlink register;
    @FXML
    Button login;
    @FXML
    Button signup;
    @FXML
    AnchorPane pane;


    private double xOffset =0;
    private double yOffset =0;



    public void handleClose(){
        System.exit(0);
    }

    public void movingLogin(){
        pane.setOnMousePressed(event -> {
            xOffset =event.getSceneX();
            yOffset =event.getSceneY();
        });
        pane.setOnMouseDragged(event -> {
            JavaFXMain.stage.setX(event.getScreenX()-xOffset);
            JavaFXMain.stage.setY(event.getScreenY()-yOffset);
        });

    }


    public void handleRegisterClicked(){
        try{
            Window window = register.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
            Stage stage = new Stage();
            stage.setTitle("signUp");
            stage.setScene(new Scene(root, 700, 655));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            window.hide();
        }
        catch(IOException e){
            System.out.println("Error found in the handleRegisterClicked class");
        }
    }

    public void handleLoginClicked(){
        try{
            Window window = login.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("HomePage");
            stage.setScene(new Scene(root, 1398, 954));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            window.hide();
        }
        catch(IOException e){
            System.out.println("Error found in the handleLoginClicked");
        }
    }
    public void handleSignupClicked(){
        try{
            Window window = signup.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root,760.0, 420.0));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            window.hide();
        }
        catch (IOException e){
            System.out.print("Error found in the handleSignupClicked");
        }
    }
}
