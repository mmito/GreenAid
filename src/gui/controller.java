import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

public class controller {

    @FXML
    Hyperlink register;
    @FXML
    Button login;

    public void handleClose(){
        System.exit(0);
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
}
