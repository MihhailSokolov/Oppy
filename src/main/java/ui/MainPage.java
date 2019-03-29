package ui;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class for creating main page.
 */
public class MainPage {

    private static TableView<User> folowingList = new TableView<>();
    private static Button settingsButton;
    private static Button leaderboardButton;
    private static Button addActionButton;

    /**
     *  Method for main scene.
     *
     * @param primaryStage Primary stage
     * @return Scene
     */
    public static Scene mainScene(Stage primaryStage) {

        Stage window = primaryStage;
        window.setTitle("MainPage");
        window.setMaximized(true);
        final BorderPane centralPageLayout = new BorderPane();
        //centerPage contents

        //create the grid for the center of the page
        GridPane gridCenter = new GridPane();
        gridCenter.setId("centerGrid");

        //here the username label is created
        String username = Main.clientController.getUser().getUsername();
        if (username == null || username.equals("")) {
            username = "test account";
        }
        Label usernameLabel = new Label(username);
        Tooltip.install(usernameLabel, new Tooltip("This is your username"));
        usernameLabel.setId("username");
        GridPane.setConstraints(usernameLabel, 0, 1);

        //here the number of points needs to be queried
        String result = Main.clientController.getScore();
        int pointValue = Integer.parseInt(result);
        Label numberOfPoints = new Label(result);
        numberOfPoints.setId("yourPoints");
        Tooltip.install(numberOfPoints, new Tooltip("Your current number of points"));
        if (pointValue < 0) {
            numberOfPoints.setStyle("-fx-text-fill: red");
        } else if (pointValue > 0) {
            numberOfPoints.setStyle("-fx-text-fill: green");
        } else {
            numberOfPoints.setStyle("-fx-text-fill: yellow");
        }
        GridPane.setConstraints(numberOfPoints, 0, 2);

        //here the image of the planet needs to be placed
        Image planet;
        if(pointValue >= 15000){
        planet = new Image("oppy1.png");
        } else if(pointValue >= 10000){
            planet = new Image("oppy2.png");
        } else if(pointValue >= 5000){
            planet = new Image("oppy3.png");
        } else if(pointValue < -15000){
            planet = new Image("oppy7.png");
        } else if(pointValue < -10000){
            planet = new Image("oppy6.png");
        } else if(pointValue < -5000){
            planet = new Image("oppy5.png");
        } else {
            planet = new Image("oppy4.png");
        }
        ImageView displayLogo = new ImageView(planet);
        GridPane.setConstraints(displayLogo, 1, 3);

        //here the daily point loss needs to be queried
        Label pointLoss = new Label(Integer.toString(-3000));
        pointLoss.setId("pointLoss");
        Tooltip.install(pointLoss, new Tooltip("Number of points you lose each day"));
        GridPane.setConstraints(pointLoss, 2, 2);

        // Here is the counter counting down until midnight
        Label timer = new Label();
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            int hours = 23 - new Date().getHours();
            int minutes = 59 - new Date().getMinutes();
            int seconds = 59 - new Date().getSeconds();
            //timer.setText(hours + ":"+ minutes+ ":" + seconds);
            timer.setText(sdf.format(new Date(0, 0, 0, hours, minutes, seconds)));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        Tooltip.install(timer, new Tooltip("Time before daily point-loss occurs"));
        timer.setId("timeTillPointLoss");
        GridPane.setConstraints(timer, 2, 1);
        //Here all elements previously created are added to the view and the view is center
        gridCenter.getChildren().addAll(displayLogo, numberOfPoints, pointLoss, timer, usernameLabel);
        gridCenter.setAlignment(Pos.CENTER);

        //here the hamburger menu's and the top menu are initialized
        final GridPane gridHamburgerLeft = gridHamburgerLeft(window);
        final GridPane gridHamburgerRight = gridHamburgerRight(window);
        final GridPane gridTop = gridTop(centralPageLayout, gridHamburgerLeft, gridHamburgerRight, "Main Page");

        ////setting the sizes of the rows///////////////////////////////

        gridCenter.getRowConstraints().addAll(gridRowConstraints());
        gridCenter.getColumnConstraints().addAll(gridColumnConstraints());
        gridHamburgerLeft.getRowConstraints().addAll(hamburgerRowConstraintsLeft());
        gridHamburgerLeft.getColumnConstraints().addAll(hamburgerColumnConstraintsLeft());
        gridHamburgerRight.getRowConstraints().addAll(hamburgerRowConstraintsRight());
        gridHamburgerRight.getColumnConstraints().addAll(hamburgerColumnConstraintsRight());
        gridTop.getColumnConstraints().addAll(girdTopColumnConstraints());

        //CentralPageLayout
        centralPageLayout.setCenter(gridCenter);
        centralPageLayout.setTop(gridTop);
        //logout from main page functionality
        Button invisLogoutbutton = new Button();
        invisLogoutbutton.setOnAction( e -> window.setScene(LoginPage.loginScene(window)));
        //here the create view is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1920, 1080);
        scene.getStylesheets().add("mainStyle.css");
        scene.getStylesheets().add("topHamburgerStyle.css");
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.S) {
                settingsButton.fire();
            }
            if (ke.getCode() == KeyCode.L) {
                leaderboardButton.fire();
            }
            if (ke.getCode() == KeyCode.A) {
                addActionButton.fire();
            }
            if (ke.getCode() == KeyCode.ESCAPE) {
                invisLogoutbutton.fire();
            }
        });
        return scene;
    }
    /**
     * Method for Row constraints of the central grid.
     *
     *
     * @return ArrayList of RowConstraints
     */

    public static ArrayList<RowConstraints> gridRowConstraints() {

        RowConstraints row0 = new RowConstraints();
        row0.setMinHeight(0);
        row0.setMaxHeight(0);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(45);
        row1.setMaxHeight(45);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(45);
        row2.setMaxHeight(45);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(700);
        row3.setMaxHeight(700);
        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(0);
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(0);
        row5.setMaxHeight(0);
        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(0);
        row6.setMaxHeight(0);
        ArrayList<RowConstraints> rows = new ArrayList<RowConstraints>();
        rows.add(row0);
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);
        rows.add(row6);
        return rows;
    }
    /**
     * Method for column constraints of the central grid.
     *
     * @return ArrayList of ColumnConstraints
     */

    public static ArrayList<ColumnConstraints> gridColumnConstraints() {

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(200);
        column0.setMaxWidth(200);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(700);
        column1.setMaxWidth(700);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(200);
        column2.setMaxWidth(200);
        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        return columns;
    }


    /**
     * Method for Row constraints of the left hamburger grid.
     *
     * @return ArrayList of RowConstraints
     */
    public static ArrayList<RowConstraints> hamburgerRowConstraintsLeft() {
        RowConstraints row0 = new RowConstraints();
        row0.setMinHeight(100);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(100);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(100);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(100);
        ArrayList<RowConstraints> rows = new ArrayList<RowConstraints>();
        rows.add(row0);
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        return rows;
    }

    /**
     * Method for column constraints of the left hamburger grid.
     *
     * @return ArrayList of ColumnConstraints
     */
    public static ArrayList<ColumnConstraints> hamburgerColumnConstraintsLeft() {
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(100);
        column0.setMaxWidth(100);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(200);
        column1.setMaxWidth(200);
        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        return columns;
    }

    /**
     * Method for Row constraints of the right hamburger grid.
     *
     * @return ArrayList of RowConstraints
     */
    public static ArrayList<RowConstraints> hamburgerRowConstraintsRight() {
        RowConstraints row0 = new RowConstraints();
        row0.setMinHeight(125);
        row0.setMaxHeight(125);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(10);
        row1.setMaxHeight(10);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(125);
        row2.setMaxHeight(125);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(10);
        row3.setMaxHeight(10);
        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(125);
        row4.setMaxHeight(125);
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(30);
        row5.setMaxHeight(30);
        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(360);
        row6.setMaxHeight(360);
        RowConstraints row7 = new RowConstraints();
        row7.setMinHeight(30);
        row7.setMaxHeight(30);
        ArrayList<RowConstraints> rows = new ArrayList<RowConstraints>();
        rows.add(row0);
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);
        rows.add(row6);
        rows.add(row7);
        return rows;
    }

    /**
     * Method for column constraints of the right hamburger grid.
     *
     * @return ArrayList of ColumnConstraints
     */
    public static ArrayList<ColumnConstraints> hamburgerColumnConstraintsRight() {
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(133);
        column0.setMaxWidth(133);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(133);
        column1.setMaxWidth(133);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(34);
        column2.setMaxWidth(34);
        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        return columns;
    }

    /**
     * Method for column constraints of the top grid.
     *
     * @return ArrayList of ColumnConstraints
     */
    public static ArrayList<ColumnConstraints> girdTopColumnConstraints() {
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(60);
        column0.setMaxWidth(60);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(1780);
        column1.setMaxWidth(1780);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(40);
        column2.setMaxWidth(40);
        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        return columns;
    }

    /**
     * Method for main scene.
     *
     * @param primaryStage Primary stage
     * @return GridPane
     */
    public static GridPane gridHamburgerLeft(Stage primaryStage) {
        //creating the layout of the hamburger menu
        final Stage window = primaryStage;
        GridPane gridHamburger = new GridPane();
        gridHamburger.setId("hamburgerMenuLeft");

        //creating the buttons for settings, leaderboard and addAction

        //Here your profile picture needs to be gotten from the database
        Image profilePicture = new Image("placeholder 100x100.png");
        ImageView displayProfilePicture = new ImageView(profilePicture);
        displayProfilePicture.setFitHeight(100);
        displayProfilePicture.setFitWidth(100);
        displayProfilePicture.setId("profilePicture");
        gridHamburger.setConstraints(displayProfilePicture, 0, 0, 1, 1);

        ToggleButton settingsButton = new  ToggleButton("settings");

        settingsButton.setId("settingsButton");
        settingsButton.setOnAction(e -> {
            folowingList = new TableView<>();
            window.setScene(SettingsPage.settingsScene(window));
        });
        gridHamburger.setConstraints(settingsButton, 1, 0, 1, 1);

        ToggleButton leaderboardButton = new  ToggleButton("Leaderboard");
        leaderboardButton.setId("leaderActionButton");
        leaderboardButton.setOnAction(e -> {
            //sets the email locally stored in the User objects to their rank
            Main.clientController.getUser().setEmail(Main.clientController.getPosition());
            Main.clientController.top50Ranks(Main.clientController.getTop50());
            //because number1Player is static it needs to be reset every time you close the page
            LeaderboardPage.resetTables();

            folowingList = new TableView<>();
            window.setScene(LeaderboardPage.leaderboardScene(window));
        });
        gridHamburger.setConstraints(leaderboardButton, 0, 1, 2, 1);

        ToggleButton addActionButton = new  ToggleButton("Add action");
        addActionButton.setId("leaderActionButton");
        addActionButton.setOnAction(e -> {
            folowingList = new TableView<>();
            window.setScene(AddActionPage.addActionScene(window));
        });
        gridHamburger.setConstraints(addActionButton, 0, 2, 2, 1);

        ToggleButton mainButton = new  ToggleButton("Main page");
        mainButton.setId("leaderActionButton");
        mainButton.setOnAction(e -> {
            folowingList = new TableView<>();
            window.setScene(MainPage.mainScene(window));
        });
        gridHamburger.setConstraints(mainButton, 0, 3, 2, 1);

        if (window.getTitle().equals("MainPage") ) {
            disableButton(mainButton);
        } else if (window.getTitle().equals("AddActionPage")) {
            disableButton(addActionButton);
        } else if (window.getTitle().equals("SettingsPage")) {
            disableButton(settingsButton);
        } else if (window.getTitle().equals("LeaderboardPage")) {
            disableButton(leaderboardButton);
        }

        //add all previously created elements to the hamburger layout
        gridHamburger.getChildren().addAll(settingsButton, leaderboardButton,
                addActionButton, displayProfilePicture,mainButton);
        gridHamburger.setAlignment(Pos.TOP_CENTER);
        return gridHamburger;
    }

    /**
     * Method for main scene.
     *
     * @param primaryStage Primary stage
     * @return GridPane
     */
    public static GridPane gridHamburgerRight(Stage primaryStage) {
        //creating the layout of the hamburger menu
        GridPane gridHamburger = new GridPane();
        gridHamburger.setId("hamburgerMenuRight");

        //here the achievement images are created and the achievements you unlocked are displayed
        String result = Main.clientController.getScore();

        //Date date = Main.clientController.getDate();   //Still needs to be implemented

        Image preAcivement1 = new Image("placeholder 100x100.png");//implement achievement not unlocked skin
        if (Integer.parseInt(result) >= 10000) {
            preAcivement1 = new Image("placeholder2 100x100.png");//implement achievement Image
        }
        ImageView acivement1 = new ImageView(preAcivement1);
        GridPane.setConstraints(acivement1, 0,0);
        Tooltip.install(acivement1, new Tooltip("Achievement for reaching 10,000 points"));

        Image preAcivement2 = new Image("placeholder 100x100.png");
        if (Integer.parseInt(result) >= 100000) {
            preAcivement2 = new Image("placeholder2 100x100.png");
        }
        ImageView acivement2 = new ImageView(preAcivement2);
        GridPane.setConstraints(acivement2, 0,2);
        Tooltip.install(acivement2, new Tooltip("Achievement for reaching 100,000 points"));

        Image preAcivement3 = new Image("placeholder 100x100.png");
        if (Integer.parseInt(result) >= 1000000) {
            preAcivement3 = new Image("placeholder2 100x100.png");
        }
        ImageView acivement3 = new ImageView(preAcivement3);
        GridPane.setConstraints(acivement3, 0,4);
        Tooltip.install(acivement3, new Tooltip("Achievement for reaching 1,000,000 points"));

        Image preAcivement4 = new Image("placeholder 100x100.png");
        ImageView acivement4 = new ImageView(preAcivement4);
        GridPane.setConstraints(acivement4, 1,0);
        Tooltip.install(acivement4, new Tooltip("Achievement for playing for 1 week"));

        Image preAcivement5 = new Image("placeholder 100x100.png");
        ImageView acivement5 = new ImageView(preAcivement5);
        GridPane.setConstraints(acivement5, 1,2);
        Tooltip.install(acivement5, new Tooltip("Achievement for playing for 1 month"));

        Image preAcivement6 = new Image("placeholder 100x100.png");
        ImageView acivement6 = new ImageView(preAcivement6);
        GridPane.setConstraints(acivement6, 1,4);
        Tooltip.install(acivement6, new Tooltip("Achievement for playing for 1 year"));

        //here the followingList is displayed and the follow option is created
        Label followLabel = new Label("people you follow:");
        followLabel.setId("followLabel");
        GridPane.setConstraints(followLabel,0,5,3,1);

        try {
            Main.clientController.updateFriendList();
        }
        catch (Exception e){}
        Main.clientController.updateUser();
        ObservableList<User> data =
                FXCollections.observableArrayList(
                        Main.clientController.getUser().getFriends()  //should become list of people you follow
                );

        TableColumn name = new TableColumn("name");
        name.setCellValueFactory(new PropertyValueFactory<>("username"));
        folowingList.setItems(data);
        folowingList.getColumns().addAll(name);
        folowingList.setMaxHeight(300);
        folowingList.setPrefWidth(250);
        folowingList.setColumnResizePolicy(folowingList.CONSTRAINED_RESIZE_POLICY);
        GridPane.setConstraints(folowingList,0,6, 3, 1);

        Label newFollowLabel = new Label("Search new people:");
        newFollowLabel.setId("newFollowLabel");
        GridPane.setConstraints(newFollowLabel,0,7,3,1);

        TextField followTextField = new TextField();
        followTextField.setPromptText("Start following...");
        GridPane.setConstraints(followTextField, 0, 8, 3, 1);

        Button followButton = new Button("follow");
        followButton.setId("followButton");
        followButton.setOnAction(e -> {
            Main.clientController.addFriend(followTextField.getText());
        });
        GridPane.setConstraints(followButton, 0, 9, 3, 1);


        //add all previously created elements to the hamburger layout
        gridHamburger.getChildren().addAll(acivement1, acivement2, acivement3, acivement4, acivement5,
                acivement6, followLabel, folowingList, newFollowLabel,  followTextField, followButton);
        gridHamburger.setAlignment(Pos.TOP_CENTER);
        return gridHamburger;
    }

    /**
     * Method for main scene.
     *
     * @param centralPageLayout BorderPane of witch left and right are set
     * @param gridHamburgerLeft GridPane of left HamburgerMenu
     * @param gridHamburgerRight GridPane of right HamburgerMenu
     * @return GridPane
     */
    public static GridPane gridTop(BorderPane centralPageLayout, GridPane gridHamburgerLeft,
                                   GridPane gridHamburgerRight, String text) {
        final GridPane gridTop = new GridPane();

        //here the hamburger icons are created and and functions are attached
        //so that by clicking it it opens and closes the side menu's
        JFXHamburger hamburgerLeft = new JFXHamburger();
        Tooltip.install(hamburgerLeft, new Tooltip("Options menu"));
        hamburgerLeft.setId("hamburgerButton");
        HamburgerSlideCloseTransition burgerTaskLeft = new HamburgerSlideCloseTransition(hamburgerLeft);
        burgerTaskLeft.setRate(-1);
        hamburgerLeft.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (burgerTaskLeft.getRate() == -1) {
                centralPageLayout.setLeft(gridHamburgerLeft);
            } else {
                centralPageLayout.setLeft(null);
            }
            burgerTaskLeft.setRate(burgerTaskLeft.getRate() * -1);
            burgerTaskLeft.play();
        });
        gridTop.setConstraints(hamburgerLeft, 0, 0);

        JFXHamburger hamburgerRight = new JFXHamburger();
        Tooltip.install(hamburgerRight, new Tooltip("Achievements/Friends menu"));
        hamburgerRight.setId("hamburgerButton");
        HamburgerSlideCloseTransition burgerTaskRight = new HamburgerSlideCloseTransition(hamburgerRight);
        burgerTaskRight.setRate(-1);
        hamburgerRight.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (burgerTaskRight.getRate() == -1) {
                centralPageLayout.setRight(gridHamburgerRight);
            } else {
                centralPageLayout.setRight(null);
                folowingList = new TableView<>();
            }
            burgerTaskRight.setRate(burgerTaskRight.getRate() * -1);
            burgerTaskRight.play();
        });
        gridTop.setConstraints(hamburgerRight, 2, 0);

        Label pageName = new Label(text);
        GridPane.setConstraints(pageName, 1,0);
        pageName.setId("pageName");
        Tooltip.install(pageName, new Tooltip("Your current page"));

        gridTop.getChildren().addAll(hamburgerLeft, hamburgerRight, pageName);
        gridTop.setStyle("-fx-background-color: #4c4242;");
        return gridTop;
    }

    public static void disableButton(ToggleButton clicked) {
        clicked.setSelected(true);
        clicked.setDisable(true);
    }

}

