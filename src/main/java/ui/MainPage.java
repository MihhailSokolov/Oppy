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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.model.User;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


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
        BorderPane centralPageLayout = new BorderPane();

        //////////////////////////////////////////////////////////////////////////////////////
        ///centerPage contents////////////////////////////////////////////////////////////////

        //create the grid for the center of the page
        GridPane gridCenter = new GridPane();
        gridCenter.setPadding(new Insets(10, 10, 10, 10));
        gridCenter.setVgap(8);
        gridCenter.setHgap(10);

        //here the logo is created
        //here the image of the planet needs to be placed
        Image logo = new Image("placeholder 350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 1, 2, 3, 1);

        //TotalPoints, daily point loss and timer fields
        //here the number of points needs to be queried
        String result = Main.clientController.getScore();
        Text numberOfPoints = new Text(result);
        GridPane.setConstraints(numberOfPoints, 2, 0);

        //here the daily point loss needs to be queried
        Text pointLoss = new Text(Integer.toString(50));
        GridPane.setConstraints(pointLoss, 4, 1);

        // Here is the counter counting down until midnight
        Label timer = new Label();
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            int hours = 23 - new Date().getHours();
            int minutes = 59 - new Date().getMinutes();
            int seconds = 59 - new Date().getSeconds();
            //timer.setText(hours + ":"+ minutes+ ":" + seconds);
            timer.setText(sdf.format(new Date(0,0,0,hours,minutes,seconds)));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        GridPane.setConstraints(timer, 4,0);


        //Here all elements previously created are added to the view and the view is center
        gridCenter.getChildren().addAll(displayLogo, numberOfPoints, pointLoss,timer);
        gridCenter.setAlignment(Pos.CENTER);

        ///////////////////////////////////////////////////////////////////////////////////////
        //        ///hamburger contents////////////////////////////////////////////////////////

        //creating the layout of the hamburger menu
        GridPane gridHamburger = new GridPane();
        gridHamburger.setPadding(new Insets(10, 10, 10, 10));
        gridHamburger.setVgap(8);
        gridHamburger.setHgap(10);

        //creating the buttons for settings, leaderboard and addAction

        //Here your profile picture needs to be gotten from the database
        Image profilePicture = new Image("placeholder 100x100.png");
        ImageView displayProfilePicture = new ImageView(profilePicture);
        displayProfilePicture.setFitHeight(50);
        displayProfilePicture.setFitWidth(50);
        gridHamburger.setConstraints(displayProfilePicture,0,0,1,1);

        Button settingsButton = new Button("settings");
        settingsButton.setOnAction(e -> window.setScene(SettingsPage.settingsScene(window)));
        gridHamburger.setConstraints(settingsButton,1,0,1,1);

        Button leaderboardButton = new Button("Leaderboard");
        leaderboardButton.setOnAction(e -> {
            Main.clientController.updateTop50();
            List<User> list = Main.clientController.getTop50();
            System.out.println(list.get(0).getUsername());
            window.setScene(LeaderboardPage.leaderboardScene(window));
        });
        gridHamburger.setConstraints(leaderboardButton,0,1,2,1);


        Button addActionButton = new Button("Add action");
        addActionButton.setOnAction(e -> window.setScene(AddActionPage.addActionScene(window)));
        gridHamburger.setConstraints(addActionButton,0,2,2,1);

        //add all previously created elements to the hamburger layout
        gridHamburger.getChildren().addAll(settingsButton,leaderboardButton,addActionButton, displayProfilePicture);
        gridHamburger.setAlignment(Pos.TOP_LEFT);
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

        /////////////////////////////////////////////////////////////////////////
        ////CentralPageLayout/////////////////////////////////////////////////////

        centralPageLayout.setCenter(gridCenter);
        centralPageLayout.setTop(gridTop);

        //here the create view is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1000, 600);
        return scene;
    }
}
