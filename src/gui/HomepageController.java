import app.client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    ComboBox<String> comboBox;
    @FXML
    private ImageView Home;
    @FXML
    private Text activityText;
    @FXML
    private HBox hBox;
    @FXML
    private Text  field;
    @FXML
    private Text firstName;
    @FXML
    private Text lastName;
    @FXML
    private Text test;

    private double xOffset;
    private double yOffset;

    private long categoryId;




    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        field.setText(controller.Name);
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("Eating A Vegetarian Meal", "Buying Local Produce", "Using Bike Instead of Car", "Using Public transports instead of Car", "Installing Solar Panels", "Lowering the Temperature of your Home");

        firstName.setText(Client.getUserFirst(controller.sessionCookie));
        lastName.setText(Client.getUserLast(controller.sessionCookie));
        test.setText(Client.getUserActivities(controller.sessionCookie));

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
    @FXML
    private void Home(){

        Window window = Home.getScene().getWindow();
        Stage stage = JavaFXMain.stage;
        stage.show();
        window.hide();
    }

    public void activityChoosed(){

        String activity = comboBox.getValue();

        switch(activity) {

            case "Eating A Vegetarian Meal":
                categoryId = 1;
                break;
            case "Buying Local Produce":
                categoryId = 2;
                break;
            case "Using Bike Instead of Car":
                categoryId = 3;
                break;
            case "Using Public transports instead of Car" :
                categoryId = 4;
                break;
            case "Installing Solar Panels":
                categoryId = 5;
                break;
            case "Lowering the Temperature of your Home":
                categoryId = 6;
                break;

        }


        Button addActivity = new Button("Add Activity !");
        Spinner<Double> spinner = new Spinner<>();


        hBox.getChildren().clear();

        if (!(activity.equals("Using Bike Instead of Car") || activity.equals("Using Public transports instead of Car"))) {

            spinner.setEditable(true);
            SpinnerValueFactory<Double> spinnerVal = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, Double.MAX_VALUE, 1);
            spinner.setValueFactory(spinnerVal);


            hBox.getChildren().addAll(spinner, addActivity);
        }
        else {

            spinner.setEditable(true);
            SpinnerValueFactory<Double> spinnerDoubleVal = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0.1);
            spinner.setValueFactory(spinnerDoubleVal);


            hBox.getChildren().addAll(spinner, addActivity);
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

        addActivity.setOnMouseClicked(event ->{

            postActivity(controller.sessionCookie, categoryId, spinner);

        });

    }

    public void postActivity(String sessionCookie, long categoryId, Spinner<Double> spinner) {

        double amount = spinner.getValue();

        Client.addActivity(sessionCookie, categoryId, amount);
        test.setText(Client.getUserActivities(controller.sessionCookie));
    }


}

