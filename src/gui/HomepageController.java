import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Text activityText;
    @FXML
    private HBox hBox;

    private double xOffset;
    private double yOffset;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("Eating A Vegetarian Meal", "Buying Local Produce", "Using Bike Instead of Car", "Using Public transports instead of Car", "Installing Solar Panels", "Lowering the Temperature of your Home");
    }

    public void handleClose() {
        System.exit(0);
    }


    public void movingHome() {
        pane.setOnMousePressed(event -> {
            xOffset = controller.stage.getX() - event.getScreenX();
            yOffset = controller.stage.getY() - event.getScreenY();
        });
        pane.setOnMouseDragged(event -> {
            controller.stage.setX(event.getScreenX() + xOffset);
            controller.stage.setY(event.getScreenY() + yOffset);
        });
    }

    public void activityChoosed(){

        String activity = comboBox.getValue();

        Button addActivity = new Button("Add Activity !");

        hBox.getChildren().clear();

        if (!(activity.equals("Using Bike Instead of Car") || activity.equals("Using Public transports instead of Car"))) {
            Spinner<Integer> spinner = new Spinner<>();
            spinner.setEditable(true);
            SpinnerValueFactory<Integer> spinnerVal = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
            spinner.setValueFactory(spinnerVal);


            hBox.getChildren().addAll(spinner, addActivity);
        }
        else {
            Spinner<Double> spinnerDouble = new Spinner<>();
            spinnerDouble.setEditable(true);
            SpinnerValueFactory<Double> spinnerDoubleVal = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0.1);
            spinnerDouble.setValueFactory(spinnerDoubleVal);


            hBox.getChildren().addAll(spinnerDouble, addActivity);
        }

        if(activity.equals("Eating A Vegetarian Meal")){
            activityText.setText("How many servings have you ate ?");
        }
        else if(activity.equals("Using Bike Instead of Car") || activity.equals("Using Public transports instead of Car")){
            activityText.setText("For how many kilometer have you used it ?");
        }
        else if(activity.equals("Buying Local Produce")){
            activityText.setText("How many times have you bought local produces ?");
        }
        else if(activity.equals("Installing Solar Panels")){
            activityText.setText("How many days have you been using Solar Panels ?");
        }
        else if(activity.equals("Lowering the Temperature of your Home")){
            activityText.setText("Since how many days have you been lowering your home's temperature ?");
        }
    }

}

