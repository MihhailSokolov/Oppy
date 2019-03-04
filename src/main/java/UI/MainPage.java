package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

//import javax.xml.soap.Text;

public class MainPage {
    public static Scene MainScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("MainPage");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

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
           //add function to switch vieuw to settingsPage
        });
        settingsButton.setToggleGroup(mainHamburgerGroup);
        gridHamburger.setConstraints(settingsButton,0,0);

        ToggleButton leaderboardButton = new ToggleButton("Leaderboard");
        leaderboardButton.setOnAction(e->{
            //add function to switch vieuw to leaderboardPage;
        });
        leaderboardButton.setToggleGroup(mainHamburgerGroup);
        gridHamburger.setConstraints(leaderboardButton,0,1);

        ToggleButton addActionButton = new ToggleButton("Add action");
        addActionButton.setOnAction(e->{
            //add function to switch vieuw to addActionPage
        });
        gridHamburger.setConstraints(addActionButton,0,2);

        //add all previously created elements to the hamburger layout
        gridHamburger.getChildren().addAll(settingsButton,leaderboardButton,addActionButton);
        gridHamburger.setAlignment(Pos.CENTER);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JFXHamburger hamburger = new JFXHamburger();













        //Here all elements previously created are added to the vieuw and the vieuw is centerd
        grid.getChildren().addAll(displayLogo, numberOfPoints, pointLoss, timer, hamburger);
        grid.setAlignment(Pos.CENTER);

        //here the create vieuw is made into a scene and returned when the method is called
        Scene scene = new Scene(grid, 1000, 600);
        return scene;
    }

}
