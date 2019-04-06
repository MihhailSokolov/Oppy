package ui;

import com.jfoenix.controls.JFXToggleButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

public class SettingsPage {

    public static Button logOutButton;
    private static Image profilePicture;

    /**
     * Method for creating settings page.
     * @param primaryStage primary stage
     * @return settings scene
     */

    public static Scene settingsScene(Stage primaryStage) {
        //setting title of the window and creating the BorderPane, the central layout for the window
        Stage window = primaryStage;
        window.setTitle("SettingsPage");
        final BorderPane centralPageLayout = new BorderPane();

        //create the grid for the center of the page
        final GridPane gridCenter = new GridPane();
        gridCenter.setId("gridCenter");

        //Here a Button is created with the profile picture as it's skin, that lets you change your profile picture
        final Button profilePictureButton = pfButton(window);

        //here a label displaying your username is created
        Label username = new Label(Main.clientController.getUser().getUsername());
        GridPane.setConstraints(username, 2,0);
        username.setId("label");

        //here a label displaying your email is created
        String userEmail = Main.clientController.getEmail();
        Label email = new Label(userEmail);
        GridPane.setConstraints(email,2,1);
        email.setId("label");

        //Here a toggleButton is created the allows you the set your account to anonymous
        JFXToggleButton anonymousButton = new JFXToggleButton();
        GridPane.setConstraints(anonymousButton,1,3,2,1);
        anonymousButton.setText("Anonymous");
        anonymousButton.setSelected(Main.clientController.getUser().getAnonymous());
        anonymousButton.setOnAction(e -> {
            Main.clientController.updateAnonymous(anonymousButton.isSelected());
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("Success!");
            success.setContentText("Your anonymous settings are changed");
            success.setTitle("Notification");
            success.show();
        });
        anonymousButton.setId("JFXToggleButton");

        //here a button is created that let's you change your email
        Button changeEmailButton = new Button("Change email");
        GridPane.setConstraints(changeEmailButton,1,4,2,1);
        changeEmailButton.setOnAction(e -> {
            window.setScene(ChangeEmailPage.changeEmailScene(window));
        });

        //here a button is created that let's you change your password
        Button changePasswordButton = new Button("Change password");
        GridPane.setConstraints(changePasswordButton, 1,5,2,1);
        changePasswordButton.setOnAction(e -> {
            window.setScene(ChangePasswordPage.changePasswordScene(window));
        });

        //here a button is created that let's allows you to log out
        logOutButton = new Button("Log out");
        GridPane.setConstraints(logOutButton,1,6,2,1);
        logOutButton.setOnAction(e -> {
            try {
                window.setScene(LoginPage.loginScene(window));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        //here a button is created that let's you change you delete your entire account
        Button deleteAccountButton = new Button("Delete account");
        GridPane.setConstraints(deleteAccountButton,1,7,2,1);
        deleteAccountButton.setOnAction(e -> {
            window.setScene(DeleteUserPage.deleteUserScene(window));
        });

        //here a button is created that let's you reset your entire account
        Button resetButton = new Button("Reset Points");
        GridPane.setConstraints(resetButton,1,8,2,1);
        resetButton.setOnAction(e -> {
            window.setScene(ResetPointsPage.resetPointsScene(window));
        });

        //here all objects created above are placed in the central grid
        gridCenter.getChildren().addAll(username, email,
                changeEmailButton, changePasswordButton,
                logOutButton, deleteAccountButton, resetButton,
                anonymousButton, profilePictureButton);


        //here variables for the hamburger menu's and the top menu are initialized
        final GridPane gridHamburgerLeft = MainPage.gridHamburgerLeft(window);
        final GridPane gridHamburgerRight = MainPage.gridHamburgerRight(window);
        final GridPane gridTop = MainPage.gridTop(centralPageLayout, gridHamburgerLeft,
                gridHamburgerRight, "Settings Page", new Label(), window);

        //Here the column and row constraints of all sections of the page are set
        gridCenter.getRowConstraints().addAll(gridRowConstraints());
        gridCenter.getColumnConstraints().addAll(gridColumnConstraints());
        gridHamburgerLeft.getRowConstraints().addAll(MainPage.hamburgerRowConstraintsLeft());
        gridHamburgerLeft.getColumnConstraints().addAll(MainPage.hamburgerColumnConstraintsLeft());
        gridHamburgerRight.getRowConstraints().addAll(MainPage.hamburgerRowConstraintsRight());
        gridHamburgerRight.getColumnConstraints().addAll(MainPage.hamburgerColumnConstraintsRight());
        gridTop.getColumnConstraints().addAll(MainPage.girdTopColumnConstraints());


        //here the top and center regions of the BorderPane are initialized to the desired gridPanes.
        centralPageLayout.setCenter(gridCenter);
        centralPageLayout.setTop(gridTop);

        //here a scene is constructed out of the BorderPane and styleSheets are added to it
        Scene scene = new Scene(centralPageLayout, 1920, 1080);
        scene.getStylesheets().add("topHamburgerStyle.css");
        scene.getStylesheets().add("settingsStyle.css");

        //here Key_events are added to the scene
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.E) {
                changeEmailButton.fire();
            }
            if (ke.getCode() == KeyCode.P) {
                changePasswordButton.fire();
            }
            if (ke.getCode() == KeyCode.L) {
                logOutButton.fire();
            }
            if (ke.getCode() == KeyCode.D || ke.getCode() == KeyCode.DELETE) {
                deleteAccountButton.fire();
            }
            if (ke.getCode() == KeyCode.R) {
                resetButton.fire();
            }
            if (ke.getCode() == KeyCode.ESCAPE) {
                window.setScene(MainPage.mainScene(window));
            }
            ke.consume();
        });

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination mainPage = new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN);
            final KeyCombination settings = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
            final KeyCombination leaderBoard = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
            final KeyCombination addAction = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
            public void handle(KeyEvent ke) {
                if (mainPage.match(ke)) {
                    window.setScene(MainPage.mainScene(window));
                    ke.consume();
                }
                if (settings.match(ke)) {
                    window.setScene(SettingsPage.settingsScene(window));
                    ke.consume();
                }
                if (leaderBoard.match(ke)) {
                    window.setScene(LeaderboardPage.leaderboardScene(window));
                    ke.consume();
                }
                if (addAction.match(ke)) {
                    window.setScene(AddActionPage.addActionScene(window));
                    ke.consume();
                }
            }
        });
        //here the scene is returned
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
        row0.setMinHeight(75);
        row0.setMaxHeight(75);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(50);
        row1.setMaxHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(10);
        row2.setMaxHeight(10);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(50);
        row3.setMaxHeight(50);
        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(80);
        row4.setMaxHeight(80);
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(80);
        row5.setMaxHeight(80);
        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(80);
        row6.setMaxHeight(80);
        RowConstraints row7 = new RowConstraints();
        row7.setMinHeight(80);
        row7.setMaxHeight(80);
        RowConstraints row8 = new RowConstraints();
        row8.setMinHeight(80);
        row8.setMaxHeight(80);

        ArrayList<RowConstraints> rows = new ArrayList<RowConstraints>();
        rows.add(row0);
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);
        rows.add(row6);
        rows.add(row7);
        rows.add(row8);
        return rows;
    }

    /**
     * Method for Column constraints of the central grid.
     *
     *
     * @return ArrayList of ColumnConstraints
     */
    public static ArrayList<ColumnConstraints> gridColumnConstraints() {
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(50);
        column0.setMaxWidth(50);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(100);
        column1.setMaxWidth(100);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(400);
        column2.setMaxWidth(400);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setMinWidth(50);
        column3.setMaxWidth(50);

        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        return columns;
    }

    /**
     *Method the created the profilePictureButton.
     *
     * @param window the current window of application
     * @return Button the lest you set your profile picture
     */
    public static Button pfButton(Stage window) {
        BufferedImage serverProfilePicture =
                Main.clientController.getProfilePic(Main.clientController.getUser().getUsername());
        if (serverProfilePicture != null) {
            profilePicture = SwingFXUtils.toFXImage(serverProfilePicture, null);
        } else {
            profilePicture = new Image("oppy100x100.png");
        }
        ImageView displayProfilePicture = new ImageView(profilePicture);
        Button profilePictureButton = new Button();
        profilePictureButton.setGraphic(displayProfilePicture);
        profilePictureButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose profile picture");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(window);
            if (selectedFile != null) {
                Image selectedImage = new Image(selectedFile.toURI().toString(), 100, 100, true, true);
                profilePicture = selectedImage;
                Main.clientController.updateProfilePic(SwingFXUtils.fromFXImage(selectedImage, null));
                window.setScene(settingsScene(window));
            }
        });
        GridPane.setConstraints(profilePictureButton,1,0,1,2);
        profilePictureButton.setId("pfButton");
        return profilePictureButton;
    }
}
