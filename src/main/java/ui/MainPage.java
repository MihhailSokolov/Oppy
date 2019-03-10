package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

//import javax.xml.soap.Text;

public class MainPage {
    public static Scene MainScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("MainPage");
        BorderPane centralPageLayout = new BorderPane();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///centerPage contents/////////////////////////////////////////////////////////////////////////////////////////////////////

        //create the grid for the center of the page
        GridPane gridCenter = new GridPane();
        gridCenter.setPadding(new Insets(10, 10, 10, 10));
        gridCenter.setVgap(8);
        gridCenter.setHgap(10);

        //here the logo is created
        Image logo = new Image("placeholder 350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 1, 2, 3, 1);

        //TotalPoints, daily point loss and timer fields
        Text numberOfPoints = new Text(Integer.toString(100)); //100 should be changed in reference to totla point in the database
        GridPane.setConstraints(numberOfPoints, 2,0);

        Text pointLoss = new Text(Integer.toString(100));
        GridPane.setConstraints(pointLoss, 4,1);


        Text timer = new Text("12:12:12"); // number should be replaced with a counting down live timer
        GridPane.setConstraints(timer, 4,0);

        //Here all elements previously created are added to the vieuw and the vieuw is centerd
        gridCenter.getChildren().addAll(displayLogo, numberOfPoints, pointLoss, timer);
        gridCenter.setAlignment(Pos.CENTER);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///hamburger contents/////////////////////////////////////////////////////////////////////////////////////////////////////

        //creating the layout of the hamburger menu
        GridPane gridHamburger = new GridPane();
        gridHamburger.setPadding(new Insets(10, 10, 10, 10));
        gridHamburger.setVgap(8);
        gridHamburger.setHgap(10);

        //creating the buttons for settings, leaderboard and addAction
        ToggleGroup mainHamburgerGroup = new ToggleGroup();

        ToggleButton settingsButton = new ToggleButton("settings");
        settingsButton.setOnAction(e->{
           window.setScene(SettingsPage.SettingsScene(window));
        });
        settingsButton.setToggleGroup(mainHamburgerGroup);
        gridHamburger.setConstraints(settingsButton,0,0);

        ToggleButton leaderboardButton = new ToggleButton("Leaderboard");
        leaderboardButton.setOnAction(e->{
            window.setScene(LeaderboardPage.LeaderboardScene(window));
        });
        leaderboardButton.setToggleGroup(mainHamburgerGroup);
        gridHamburger.setConstraints(leaderboardButton,0,1);

        ToggleButton addActionButton = new ToggleButton("Add action");
        addActionButton.setOnAction(e->{
            window.setScene(AddActionPage.addActionScene(window));
        });
        gridHamburger.setConstraints(addActionButton,0,2);

        //add all previously created elements to the hamburger layout
        gridHamburger.getChildren().addAll(settingsButton,leaderboardButton,addActionButton);
        gridHamburger.setAlignment(Pos.TOP_LEFT);
        gridHamburger.setStyle("-fx-background-color: #FFFFFF;");

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TopGrid//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

       // creating a new grid for the top field of the BorderPane
        GridPane gridTop = new GridPane();
        gridTop.setPadding(new Insets(10, 10, 10, 10));
        gridTop.setVgap(8);
        gridTop.setHgap(10);

        //here the hamburger icon is created and and functions are attached so that by clicking it it opens and closes the side menu
        JFXHamburger hamburger = new JFXHamburger();
        HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(hamburger);
        burgerTask.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if(burgerTask.getRate()==-1){centralPageLayout.setLeft(gridHamburger);}
            else{ centralPageLayout.setLeft(null);}
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
        });
        gridTop.setConstraints(hamburger,0,0);
        gridTop.getChildren().addAll(hamburger);
        gridTop.setStyle("-fx-background-color: #4c4242;");

        /////////////////////////////////////////////////////////////////////////
        ////CentralPageLayout/////////////////////////////////////////////////////

        centralPageLayout.setCenter(gridCenter);
        centralPageLayout.setTop(gridTop);

        //here the create vieuw is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1000, 600);
        return scene;
    }

}
