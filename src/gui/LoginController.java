package main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import java.io.IOException;

import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import javafx.scene.*;

public class LoginController{
    private  double xOffset=0;
    private double yOffset=0;

    @FXML
    private Hyperlink register;

    public void xclicked(){
        System.exit(0);
    }

    public void registerclicked() throws IOException
    {
        Stage primaryStage = (Stage)register.getScene().getWindow();

        Stage signup = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                signup.setX(event.getScreenX() - xOffset);
                signup.setY(event.getScreenY() - yOffset);
            }
        });
        Scene scene = new Scene(root);

        signup.initStyle(StageStyle.UNDECORATED);
        signup.setScene(scene);
        signup.show();
        primaryStage.hide();
    }
}
