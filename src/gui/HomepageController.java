
import app.models.ActivityProjection;
import app.client.Client;
import app.models.User;

import app.models.UserProjection;
import app.responses.Response;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    ComboBox<String> comboBox;
    @FXML
    private ImageView Home;
    @FXML
    private Text activityText, field, firstName, lastName, level, xp, introText, errorUser;
    @FXML
    private TextField friend;
    @FXML
    private HBox hBox;
    @FXML
    private VBox followersPane, followingPane;
    @FXML
    private VBox history;
    @FXML
    private ScrollPane scrollPane, leaderScrollPane;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private BarChart<String, Double> leaderBoard;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;


    private long categoryId;

    private double xOffset;
    private double yOffset;

    private double y ;
    // counter for level
    private int a ;



    private User user = Client.getUserDetails(controller.sessionCookie);
    private List<ActivityProjection> activities = Client.getUserActivities(controller.sessionCookie);
    private List<UserProjection> followers = Client.getUserFollowedBy(controller.sessionCookie);
    private List<UserProjection> following = Client.getUserFollowings(controller.sessionCookie);


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        field.setText(controller.Name);
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("Eating A Vegetarian Meal", "Buying Local Produce", "Biking instead of Driving", "Using Public Transport instead of Driving", "Installing Solar Panels", "Lowering the Temperature of your Home");

        firstName.setText(user.getFirst_name());
        lastName.setText(user.getLast_name());
        xp.setVisible(false);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leaderScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leaderScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        experience();

        progress.setProgress(y / 100.0);

        showUserActivities();

        setLeaderBoard();

        setFriends(followers, followersPane);
        setFriends(following, followingPane);
    }


    public void Hover(){
        experience();
        xp.setText(new DecimalFormat("#.##").format(y)+ "/100");
        xp.setVisible(true);
    }

    public void exit(){
        xp.setVisible(false);
    }

    public void showUserActivities() {
        int sz = activities.size();

        if(activities.isEmpty()){
            Text start = new Text();
            start.setText("No activities");
            history.getChildren().add(start);

        }
        else {
            history.getChildren().clear();

            introText.setText("Dear " + activities.get(0).getUsername() + ", here is your list of activities!");

            for (int i = 0; i < sz; i++) {
                HBox hBox = new HBox();
                Text temp = new Text();
                Text cross = new Text();

                String ret = i + " - ";
                ret += activities.get(i).getCategory() + " done ";
                ret += activities.get(i).getAmount() + " times for a total of ";
                ret += new DecimalFormat("#.##").format(activities.get(i).getXp_points()) + " XP point.";

                temp.setText(ret);

                cross.setOnMouseClicked(event -> activities_removed(cross));

                hBoxHovered(hBox, cross);

                hBox.getChildren().addAll(temp, cross);

                history.getChildren().add(hBox);
            }
        }
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
            case "Biking instead of Driving":
                categoryId = 3;
                break;
            case "Using Public Transport instead of Driving" :
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

        if (!(activity.equals("Biking instead of Driving") || activity.equals("Using Public Transport instead of Driving"))) {

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
            activityText.setText("How many servings have you eaten ?");
        }
        else if(activity.equals("Biking instead of Driving") || activity.equals("Using Public Transport instead of Driving")){
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

        spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spinner.increment(0); // won't change value, but will commit editor
            }
        });

        addActivity.setOnMouseClicked(event ->{
            hBox.getChildren().clear();
            activityText.setText("");

            postActivity(controller.sessionCookie, categoryId, spinner);
            // Refreshing the user and getting the new info
            user = Client.getUserDetails(controller.sessionCookie);
            activities = Client.getUserActivities(controller.sessionCookie);
            experience();
            progress.setProgress(y/100.0);

            showUserActivities();
            setLeaderBoard();
        });
    }

    public void experience(){

        a= (int)user.getExperience_points()/100;
        y= user.getExperience_points() - a*100;
        level.setText("lvl"+ a);
    }

    public void postActivity(String sessionCookie, long CategoryId, Spinner<Double> spinner){
        double amount = spinner.getValue();

        Client.addActivity(sessionCookie, CategoryId, amount);

        showUserActivities();
    }

    public void setLeaderBoard(){
        xAxis.getCategories().clear();
        leaderBoard.getData().clear();

        int sz = following.size();

        yAxis.setTickLabelFill(Color.WHITE);
        xAxis.setTickLabelFill(Color.WHITE);
        xAxis.getCategories().add("Your Score");

        XYChart.Series<String, Double> chart = new XYChart.Series<>();
        chart.getData().add(new XYChart.Data<>("Your Score", user.getExperience_points()));

        if(!following.isEmpty()) {
            for (int i = 0; i < sz; i++) {
                xAxis.getCategories().add(following.get(i).getUsername());
                chart.getData().add(new XYChart.Data<>(following.get(i).getUsername(), following.get(i).getExperience_points()));
            }
        }

        leaderBoard.getData().addAll(chart);
    }

    public void setFriends(List<UserProjection> follow, VBox pane){
        int sz = follow.size();

        pane.getChildren().clear();

        if(!follow.isEmpty()) {
            for (int i = 0; i < sz; i++) {
                HBox hBox = new HBox();
                Text temp = new Text();
                Text cross = new Text();
                temp.setFill(Color.WHITE);

                String ret = i + " - " + follow.get(i).getUsername();

                temp.setText(ret);

                if(pane.equals(followingPane)) {
                    hBoxHovered(hBox, cross);
                }

                cross.setOnMouseClicked(event -> {
                    followerRemoved(temp);
                });

                hBox.getChildren().addAll(temp, cross);
                pane.getChildren().add(hBox);
            }
        }
    }

    public void activities_removed(Text cross){
        HBox temp = (HBox)cross.getParent();
        ObservableList<Node> list = temp.getChildren();

        Text user_info = (Text)list.get(0);

        String[] user_split = user_info.getText().split(" -");
        int nbr = Integer.parseInt(user_split[0]);

        String result = Client.removeActivity(controller.sessionCookie, activities.get(nbr).getId());
        System.out.println(result);

    }

    public void hBoxHovered(HBox hBox, Text cross){
        cross.setText("❌");
        cross.setVisible(false);
        hBox.setSpacing(15);

        hBox.setOnMouseEntered(event -> {
            cross.setVisible(true);
            cross.setCursor(Cursor.HAND);
        });
        hBox.setOnMouseExited(event -> cross.setVisible(false));
    }

    public void followerRemoved(Text text){
        String[] temp = text.getText().split(" - ");

        Client.removeFollow(controller.sessionCookie, temp[1]);

        user = Client.getUserDetails(controller.sessionCookie);
        following = Client.getUserFollowings(controller.sessionCookie);

        setFriends(following, followingPane);
    }

    public void addFollow(){
        errorUser.setText("");
        String add = friend.getText();

        String response = Client.addFollow(controller.sessionCookie, add);

        if(!response.equals("Your followings have been updated!")) {
            errorUser.setText(response);
            errorUser.setFill(Color.RED);
            errorUser.setFont(Font.font(20));
        }
    }
}

