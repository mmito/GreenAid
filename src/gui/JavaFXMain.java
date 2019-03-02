import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class JavaFXMain extends Application {


    public static Stage stage = null;
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
            primaryStage.setScene(new Scene(root, 760.0, 420.0));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            this.stage = primaryStage;
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("An exception in the start method (JavaFXMain) had an error");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
