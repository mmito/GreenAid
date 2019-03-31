
import app.models.ActivityProjection;
import Services.Client;
import app.models.User;

import app.models.UserProjection;
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
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.layout.HBox;


import java.net.URL;
import java.text.DecimalFormat;
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
    private VBox followersPane, followingPane , history , list;
    @FXML
    private ScrollPane scrollPane, leaderScrollPane , scrollInfo;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private BarChart<String, Double> leaderBoard;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private TextFlow info;
    @FXML
    ChoiceBox<String> choiceBox;


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
    private List <UserProjection> top20leaderboard = Client.getLeaderboard(controller.sessionCookie);


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        field.setText(controller.Name);
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("Eating A Vegetarian Meal", "Buying Local Produce", "Biking instead of Driving", "Using Public Transport instead of Driving", "Installing Solar Panels", "Lowering the Temperature of your Home");


        choiceBox.getItems().add("Friends");
        choiceBox.getItems().add("Top 20");

        firstName.setText(user.getFirst_name());
        lastName.setText(user.getLast_name());
        xp.setVisible(false);

        text();

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        leaderScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leaderScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        experience();

        progress.setProgress(y / 100.0);

        showUserActivities();

        leaderboardChoosed();


        setFriends(followers, followersPane);
        setFriends(following, followingPane);

    }


    public void Hover(){
        experience();
        xp.setText(new DecimalFormat("#.##").format(y)+ "/100");
        xp.setVisible(true);
    }

    public void text(){
        Text text = new Text();
        text.setFont(Font.font("ALgerian",18));
        text.setText("How do we calculate your XP Points? \n");


        info.getChildren().add(text);

        Text text1 = new Text();
        text1.setFont(Font.font(16 ));
        text1.setText("As you know, a user earns XP Points based on the activity done." +
                "We calculated the XP Points based on the amount of Carbon Dioxide that activity saves." +
                "Because we couldn't find any external APIs that we found directly applicable for our activity scope" +
                "and we decided to store each activity's value on the database.\n\n" +
                "Using the internet (only reliable .gov, .net and well-known .com websites) we found the amount" +
                "of carbon dioxide that activity saves. In some cases, for example using public transportation instead of" +
                "a car, we needed to find how many CO2 a car uses per km and how many CO2 a transportation vehicle" +
                "uses per km and we calculated the deficit.\n\n" +
                "Because of the available information online, we used the" +
                "following parameters for adding an activity:" +
                "Eating a vegetarian meal/per serving" +
                "Buying a local produce/per number of items" +
                "Using a bike instead of car/per km" +
                "Using public transport instead of car/per km" +
                "Lowering the temperature of your home by 1 degree Celcius/per day" +
                "Installing solar panels/per day");
        info.getChildren().add(text1);

        Text text2 = new Text();

        text2.setText("After the calculation of the amount of carbon dioxide saved, we divided each number by 100 to have different xp points for each activity. We decided to divide by 100 because we thought that it will be easier to convert xp points from amount of co2 saved and vice versa. This also helped us along the design process.  If we used 1000 or more, the value of points would have been too low. We also considered the level ranking of the user and wanted to keep the calculation for gamification as simple as possible for the sustainability of the project.\n\n");
        info.getChildren().add(text2);


        Text text3 = new Text();
        text3.setText("How do the recommendations work? \n" );
        text3.setFont(Font.font("Calibre", FontWeight.BOLD,16));
        info.getChildren().add(text3);

        Text text4 = new Text();
        text4.setText("Simple. Efficient. Easy-to-understand. We track your activities and categorize them into 3 groups which are food, household, transportation. Some activities like eating a vegetarian meal only belong to one category, that being food. But some like buying local produce belong to more than categories like food and transportation as this both contributes to the prevention of transfer of products and the usage of local food. Based on the number of activities done on each super-category, we give you some recommendations from our recommendation pool. We hope you'll like them!");
        info.getChildren().add(text4);

        Text text5 = new Text();
        text5.setText("Credits\n" +
                "\n" +
                "The TEAM \n" +
                "Sina Sen\n" +
                "Cosmin Octavian Pene\n" +
                "Sven Van Collenburg\n" +
                "Warren Akrum\n" +
                "Julien Lamon\n" +
                "Tommaso Tofacchi\n" +
                "\n" +
                "The Consultant\n" +
                "Issa Hanou\n" +
                "\n" +
                "The TEAM Base\n" +
                "Pulse Technology Room\n" +
                "\n" +
                "\n" +
                "Special thanks to everyone who supported us! ❤\uD83C\uDF33");
        info.getChildren().add(text5);
    }

    public void exit(){
        xp.setVisible(false);
    }

    public  void refresh(){

        user = Client.getUserDetails(controller.sessionCookie);
        activities = Client.getUserActivities(controller.sessionCookie);
        followers = Client.getUserFollowedBy(controller.sessionCookie);
        following = Client.getUserFollowings(controller.sessionCookie);
        top20leaderboard = Client.getLeaderboard(controller.sessionCookie);
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

    public void leaderboardChoosed(){
        choiceBox.getSelectionModel().selectedItemProperty().addListener( (v,oldValue,newValue) -> {
            if (newValue.equals("Friends")){
                setLeaderBoard();
            }
            else {
                top20leaderboard();
            }
        }  );
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
            refresh();
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


    public void top20leaderboard(){
        leaderBoard.setVisible(false);
        list.setVisible(true);
        list.getChildren().clear();
        int sz = top20leaderboard.size();
        Text empty = new Text();
        empty.setText("\n");
        list.getChildren().add(empty);

        Text title = new Text();
        title.setText("Top 20 LeaderBoard");
        title.setFont(Font.font(18));
        title.setTextAlignment(TextAlignment.CENTER);
        list.getChildren().add(title);

        if(activities.isEmpty()){
            Text start = new Text();
            start.setText("No activities");
            list.getChildren().add(start);

        }
        else {
            //list.getChildren().clear();
            Text blank = new Text();
            blank.setText("\n");
            list.getChildren().add(blank);

            for (int i = 0; i < sz; i++) {
                HBox hBox = new HBox();
                Text temp = new Text();


                String ret = "  ";
                ret += i+1 + " - ";
                ret += top20leaderboard.get(i).getUsername() + " with ";
                ret += top20leaderboard.get(i).getExperience_points() + " XP points " ;
//                ret += new DecimalFormat("#.##").format(activities.get(i).getXp_points()) + " XP point.";

                temp.setText(ret);
                temp.setFont(Font.font(18));

                hBox.getChildren().addAll(temp);

                list.getChildren().add(hBox);
            }

        }

    }

    public void setLeaderBoard(){
        leaderBoard.setVisible(true);
        list.setVisible(false);
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

        refresh();
        setLeaderBoard();
        experience();
        showUserActivities();
        progress.setProgress(y/100.0);
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
        refresh();
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

        refresh();
        setFriends(following, followingPane);
        setLeaderBoard();
    }
}

