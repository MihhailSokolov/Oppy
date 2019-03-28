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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SettingsPage {

    /**
     * Method for creating settings page.
     * @param primaryStage primary stage
     * @return settings scene
     */
    public static Scene settingsScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("SettingsPage");
        final BorderPane centralPageLayout = new BorderPane();

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

        Label username = new Label(Main.clientController.getUser().getUsername());
        GridPane.setConstraints(username, 2,0);

        String userEmail = Main.clientController.getEmail();
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
        anonymousButton.setOnAction(e ->{
            Main.clientController.updateAnonymous(anonymousButton.isSelected());
        });

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

        //here all objects created above are placed in the central grid
        gridCenter.getChildren().addAll(backButton, username, email,
                changeEmailButton, changePasswordButton,
                logOutButton, deleteAccountButton, resetButton, pushNotificationsButton,
                anonymousButton, displayProfilePicture, profilePictureButton);


        //here the hamburger menu's and the top menu are initialized
        final GridPane gridHamburgerLeft = MainPage.gridHamburgerLeft(window);
        final GridPane gridHamburgerRight = MainPage.gridHamburgerRight(window);
        final GridPane gridTop = MainPage.gridTop(centralPageLayout, gridHamburgerLeft, gridHamburgerRight);

        ////setting the sizes of the rows///////////////////////////////
        //gridCenter.getRowConstraints().addAll(gridRowConstraints());
        //gridCenter.getColumnConstraints().addAll(gridColumnConstraints());
        gridHamburgerLeft.getRowConstraints().addAll(MainPage.hamburgerRowConstraintsLeft());
        gridHamburgerLeft.getColumnConstraints().addAll(MainPage.hamburgerColumnConstraintsLeft());
        gridHamburgerRight.getRowConstraints().addAll(MainPage.hamburgerRowConstraintsRight());
        gridHamburgerRight.getColumnConstraints().addAll(MainPage.hamburgerColumnConstraintsRight());
        gridTop.getColumnConstraints().addAll(MainPage.girdTopColumnConstraints());
        //////////////////////////////////////////////////////////////////////////////////////
        ////central page layout///////////////////////////////////////////////////////////////
        centralPageLayout.setCenter(gridCenter);
        centralPageLayout.setTop(gridTop);

        //here the create vieuw is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1920, 1080);
        scene.getStylesheets().add("topHamburgerStyle.css");
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
        row1.setMinHeight(50);
        row1.setMaxHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(40);
        row2.setMaxHeight(420);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(10);
        row3.setMaxHeight(10);
        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(40);
        row4.setMaxHeight(420);
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(10);
        row5.setMaxHeight(10);
        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(40);
        row6.setMaxHeight(420);
        RowConstraints row7 = new RowConstraints();
        row7.setMinHeight(10);
        row7.setMaxHeight(10);
        RowConstraints row8 = new RowConstraints();
        row8.setMinHeight(40);
        row8.setMaxHeight(420);
        RowConstraints row9 = new RowConstraints();
        row9.setMinHeight(10);
        row9.setMaxHeight(10);
        RowConstraints row10 = new RowConstraints();
        row10.setMinHeight(50);
        row10.setMaxHeight(50);
        RowConstraints row11 = new RowConstraints();
        row11.setMinHeight(0);
        row11.setMaxHeight(380);

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
        rows.add(row9);
        rows.add(row10);
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
        column1.setMinWidth(237);
        column1.setMaxWidth(237);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(26);
        column2.setMaxWidth(26);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setMinWidth(237);
        column3.setMaxWidth(237);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setMinWidth(50);
        column4.setMaxWidth(50);

        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        columns.add(column3);
        columns.add(column4);
        return columns;
    }
}
