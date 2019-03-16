import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class JavaFXMain extends Application {

    private double xOffset;
    private double yOffset;

    public static Stage stage = null;
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
            primaryStage.setScene(new Scene(root, 760.0, 420.0));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            this.stage = primaryStage;


            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();


            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("An exception in the start method (JavaFXMain) had an error");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
