package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

public class SettingsPage {

    /**
     * Method for creating settings page.
     * @param primaryStage primary stage
     * @return settings scene
     */
    public static Scene settingsScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("SettingsPage");

        /////////////////////////////////////////////////////////////////////////////////
        //CentralGrid////////////////////////////////////////////////////////////////////
        GridPane gridCenter = new GridPane();
        gridCenter.setPadding(new Insets(10, 10, 10, 10));
        gridCenter.setVgap(8);
        gridCenter.setHgap(10);

        //here all the buttons normal buttons and labels are added
        Button backButton = new Button("Back");
        GridPane.setConstraints(backButton, 0, 0);
        backButton.setOnAction(e -> window.setScene(MainPage.mainScene(window)));
      

        //in this line the placeholder should be replaced with the actual profile picture
        Image profilePicture = new Image("placeholder 100x100.png"); //get profile pic
        ImageView displayProfilePicture = new ImageView(profilePicture);
        JFXButton profilePictureButton = new JFXButton();
        profilePictureButton.setGraphic(displayProfilePicture);
        GridPane.setConstraints(profilePictureButton,1,0,1,2);

        Label username = new Label(Main.clientHandler.getUser().getUsername());
        GridPane.setConstraints(username, 2,0);

        final String uri = "https://oppy-project.herokuapp.com/email?username=" + Main.clientHandler.getUser().getUsername();
        RestTemplate restTemplate = new RestTemplate();
        String userEmail = restTemplate.getForObject(uri, String.class);
        Label email = new Label(userEmail);
        GridPane.setConstraints(email,2,1);

        JFXToggleButton pushNotificationsButton = new JFXToggleButton();
        GridPane.setConstraints(pushNotificationsButton,1,2,2,1);
        pushNotificationsButton.setText("Push notifications");
        pushNotificationsButton.setSelected(true);
        pushNotificationsButton.setOnAction(e -> System.out.println("testNotification"));

        JFXToggleButton anonymousButton = new JFXToggleButton();
        GridPane.setConstraints(anonymousButton,1,3,2,1);
        anonymousButton.setText("Anonymous");
        anonymousButton.setSelected(false);
        anonymousButton.setOnAction(e -> System.out.println("testAnonymous"));//no priority to fix

        Button changeEmailButton = new Button("Change email");
        GridPane.setConstraints(changeEmailButton,1,4,2,1);
        changeEmailButton.setOnAction(e -> {
            window.setScene(ChangeEmailPage.changeEmailScene(window));
        });


        Button changePasswordButton = new Button("Change password");
        GridPane.setConstraints(changePasswordButton, 1,5,2,1);
        changePasswordButton.setOnAction(e -> {
            window.setScene(ChangePasswordPage.changePasswordScene(window));
        });


        Button logOutButton = new Button("Log out");
        GridPane.setConstraints(logOutButton,1,6,2,1);
        logOutButton.setOnAction(e -> {
            window.setScene(LoginPage.loginScene(window));
        });


        Button deleteAccountButton = new Button("Delete account");
        GridPane.setConstraints(deleteAccountButton,1,7,2,1);
        deleteAccountButton.setOnAction(e -> {
            window.setScene(DeleteUserPage.deleteUserScene(window));
        });


        Button resetButton = new Button("Reset Points");
        GridPane.setConstraints(resetButton,1,8,2,1);
        resetButton.setOnAction(e -> {
            window.setScene(ResetPointsPage.resetPointsScene(window));
        });




        //////////////////////////////////////////////////////////////////////////////////////
        ////central page layout///////////////////////////////////////////////////////////////
        gridCenter.getChildren().addAll(backButton, username, email,
                changeEmailButton, changePasswordButton,
                logOutButton, deleteAccountButton, resetButton, pushNotificationsButton,
                anonymousButton, displayProfilePicture, profilePictureButton);
        BorderPane centralPageLayout = new BorderPane();
        centralPageLayout.setCenter(gridCenter);

        //here the create vieuw is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1000, 600);
        return scene;
    }
}
