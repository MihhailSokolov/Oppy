package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SettingsPage {

    public static Scene SettingsScene(Stage primaryStage){
        Stage window = primaryStage;
        window.setTitle("SettingsPage");
        BorderPane centralPageLayout = new BorderPane();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //CentralGrid////////////////////////////////////////////////////////////////////////////////////////////////////////////
        GridPane gridCenter = new GridPane();
        gridCenter.setPadding(new Insets(10, 10, 10, 10));
        gridCenter.setVgap(8);
        gridCenter.setHgap(10);

        //here all the buttons normal buttons and labels are added
        Button backButton = new Button("Back");
        GridPane.setConstraints(backButton, 0, 0);
        backButton.setOnAction(e->{
            window.setScene(MainPage.MainScene(window));
        });


        Image profilePicture = new Image("placeholder 100x100.png"); //in this line the placeholder should be replaced with the actual profile picture
        ImageView displayProfilePicture = new ImageView(profilePicture);
        JFXButton profilePictureButton = new JFXButton();
        profilePictureButton.setGraphic(displayProfilePicture);
        GridPane.setConstraints(profilePictureButton,1,0,1,2);

        Label username = new Label("username");
        GridPane.setConstraints(username, 2,0);

        Label email = new Label("email@email.com");
        GridPane.setConstraints(email,2,1);

        JFXToggleButton pushNotificationsButton = new JFXToggleButton();
        GridPane.setConstraints(pushNotificationsButton,1,2,2,1);
        pushNotificationsButton.setText("Push notifications");

        JFXToggleButton anonymousButton = new JFXToggleButton();
        GridPane.setConstraints(anonymousButton,1,3,2,1);
        anonymousButton.setText("Anonymous");


        Button changeEmailButton = new Button("Change email");
        GridPane.setConstraints(changeEmailButton,1,4,2,1);
        changeEmailButton.setOnAction(e->{
            //implements change email method
        });


        Button changePasswordButton = new Button("Change password");
        GridPane.setConstraints(changePasswordButton, 1,5,2,1);
        changePasswordButton.setOnAction(e->{
            //implements change password method
        });


        Button logOutButton = new Button("Log out");
        GridPane.setConstraints(logOutButton,1,6,2,1);
        logOutButton.setOnAction(e->{
            //implement log out method
        });


        Button deleteAccountButton = new Button("Delete account");
        GridPane.setConstraints(deleteAccountButton,1,7,2,1);
        deleteAccountButton.setOnAction(e->{
            //implement delete account method
        });


        Button resetButton = new Button("Reset");
        GridPane.setConstraints(resetButton,1,8,2,1);
        resetButton.setOnAction(e->{
            //implement reset method
        });




        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////central page layout/////////////////////////////////////////////////////////////////////////////////////////////////////
        gridCenter.getChildren().addAll(backButton, username,email,changeEmailButton,changePasswordButton,logOutButton,deleteAccountButton,resetButton,pushNotificationsButton,anonymousButton,displayProfilePicture,profilePictureButton);
        centralPageLayout.setCenter(gridCenter);

        //here the create vieuw is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1000, 600);
        return scene;
    }
}
