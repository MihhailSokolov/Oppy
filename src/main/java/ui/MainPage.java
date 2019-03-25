package ui;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;


//import javax.xml.soap.Text;

/**
 * Class for creating main page.
 */
public class MainPage {
    /**
     * Method for main scene.
     *
     * @param primaryStage Primary stage
     * @return Scene
     */
    public static Scene mainScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("MainPage");
        window.setMaximized(true);
        BorderPane centralPageLayout = new BorderPane();

        //////////////////////////////////////////////////////////////////////////////////////
        ///centerPage contents////////////////////////////////////////////////////////////////

        //create the grid for the center of the page
        GridPane gridCenter = new GridPane();
        gridCenter.setId("centerGrid");

        //here the logo is created
        //here the image of the planet needs to be placed
        Image logo = new Image("placeholder 700x700.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 1, 3);

        //Username, TotalPoints, daily point loss and timer fields
        //here the username label is created
        String username = Main.clientController.getUser().getUsername();
        if (username.equals("") || username == null) {
            username = "lazy ass cunt";  //just here to troll people
        }
        Label usernameLabel = new Label(username);
        Tooltip.install(usernameLabel, new Tooltip("this is the most hated person in the world"));
        usernameLabel.setId("username");
        GridPane.setConstraints(usernameLabel, 0, 1);

        //here the number of points needs to be queried
        String result = Main.clientController.getScore();
        Label numberOfPoints = new Label(result);
        numberOfPoints.setId("yourPoints");
        Tooltip.install(numberOfPoints, new Tooltip("Your current number of points"));
        if (Integer.parseInt(result) < 0) {
            numberOfPoints.setStyle("-fx-text-fill: red");
        } else if (Integer.parseInt(result) > 0) {
            numberOfPoints.setStyle("-fx-text-fill: green");
        } else {
            numberOfPoints.setStyle("-fx-text-fill: yellow");
        }
        GridPane.setConstraints(numberOfPoints, 0, 2);

        //here the daily point loss needs to be queried
        Label pointLoss = new Label(Integer.toString(-150));
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

        ///////////////////////////////////////////////////////////////////////////////////////
        // ///hamburger contents////////////////////////////////////////////////////////

        //creating the layout of the hamburger menu
        GridPane gridHamburger = new GridPane();
        gridHamburger.setId("hamburgerMenu");

        //creating the buttons for settings, leaderboard and addAction

        //Here your profile picture needs to be gotten from the database
        Image profilePicture = new Image("placeholder 100x100.png");
        ImageView displayProfilePicture = new ImageView(profilePicture);
        displayProfilePicture.setFitHeight(100);
        displayProfilePicture.setFitWidth(100);
        displayProfilePicture.setId("profilePicture");
        gridHamburger.setConstraints(displayProfilePicture, 0, 0, 1, 1);

        Button settingsButton = new Button("settings");
        settingsButton.setId("settingsButton");
        settingsButton.setOnAction(e -> window.setScene(SettingsPage.settingsScene(window)));
        gridHamburger.setConstraints(settingsButton, 1, 0, 1, 1);

        Button leaderboardButton = new Button("Leaderboard");
        leaderboardButton.setId("leaderActionButton");
        leaderboardButton.setOnAction(e -> {
            window.setScene(LeaderboardPage.leaderboardScene(window));
        });
        gridHamburger.setConstraints(leaderboardButton, 0, 1, 2, 1);


        Button addActionButton = new Button("Add action");
        addActionButton.setId("leaderActionButton");
        addActionButton.setOnAction(e -> window.setScene(AddActionPage.addActionScene(window)));
        gridHamburger.setConstraints(addActionButton, 0, 2, 2, 1);

        //add all previously created elements to the hamburger layout
        gridHamburger.getChildren().addAll(settingsButton, leaderboardButton, addActionButton, displayProfilePicture);
        gridHamburger.setAlignment(Pos.TOP_CENTER);
        gridHamburger.setStyle("-fx-background-color: #FFFFFF;");

        //////////////////////////////////////////////////////////////////////////////////
        //TopGrid/////////////////////////////////////////////////////////////////////////

        // creating a new grid for the top field of the BorderPane
        GridPane gridTop = new GridPane();
        gridTop.setPadding(new Insets(10, 10, 10, 10));
        gridTop.setVgap(8);
        gridTop.setHgap(10);

        //here the hamburger icon is created and and functions are attached
        //so that by clicking it it opens and closes the side menu
        JFXHamburger hamburger = new JFXHamburger();
        hamburger.setId("hamburgerButton");
        HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(hamburger);
        burgerTask.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (burgerTask.getRate() == -1) {
                centralPageLayout.setLeft(gridHamburger);
            } else {
                centralPageLayout.setLeft(null);
            }
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
        });
        gridTop.setConstraints(hamburger, 0, 0);
        gridTop.getChildren().addAll(hamburger);
        gridTop.setStyle("-fx-background-color: #4c4242;");

        ////////////////////////////////////////////////////////////////
        ////setting the sizes of the rows///////////////////////////////
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
        gridCenter.getRowConstraints().addAll(row0, row1, row2, row3, row4, row5, row6);

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(200);
        column0.setMaxWidth(200);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(700);
        column1.setMaxWidth(700);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(200);
        column2.setMaxWidth(200);
        gridCenter.getColumnConstraints().addAll(column0, column1, column2);

        ColumnConstraints hamColumn0 = new ColumnConstraints();
        hamColumn0.setMinWidth(100);
        hamColumn0.setMaxWidth(100);
        ColumnConstraints hamColumn1 = new ColumnConstraints();
        hamColumn1.setMinWidth(200);
        hamColumn1.setMaxWidth(200);
        gridHamburger.getColumnConstraints().addAll(hamColumn0, hamColumn1);

        RowConstraints hamRow0 = new RowConstraints();
        hamRow0.setMinHeight(100);
        RowConstraints hamRow1 = new RowConstraints();
        hamRow1.setMinHeight(100);
        RowConstraints hamRow2 = new RowConstraints();
        hamRow2.setMinHeight(100);
        gridHamburger.getRowConstraints().addAll(hamRow0, hamRow1, hamRow2);
        /////////////////////////////////////////////////////////////////////////
        ////CentralPageLayout/////////////////////////////////////////////////////

        centralPageLayout.setCenter(gridCenter);
        centralPageLayout.setTop(gridTop);

        //here the create view is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout);
        scene.getStylesheets().add("mainStyle.css");
        return scene;
    }
}
