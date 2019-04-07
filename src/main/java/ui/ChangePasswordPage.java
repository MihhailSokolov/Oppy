package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ChangePasswordPage {
    /**
     * <<<<<<< HEAD
     * Method for creating the change password page.
     *
     * @param primaryStage primary stage
     * @return scene
     */
    public static Scene changePasswordScene(Stage primaryStage) {
        //setting title of the window and creating the BorderPane, the central layout for the window
        Stage window = primaryStage;
        window.setTitle("Change Password");
        window.setMaximized(true);
        final BorderPane borderPane = new BorderPane();

        //create the grid for the center of the page
        GridPane grid = new GridPane();
        grid.setId("grid");

        //Here the newPassword Label is created
        Label newPassword = new Label("New Password");
        GridPane.setConstraints(newPassword, 0, 2);

        //Here the password Label is created
        Label password = new Label("Old Password");
        GridPane.setConstraints(password, 0, 5);

        //Here the newPassword textfield is created
        PasswordField newPasswordTextfield = new PasswordField();
        newPasswordTextfield.setPromptText("New Password");
        GridPane.setConstraints(newPasswordTextfield, 0, 3, 2, 1);

        //Here the password textfield is created
        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Old Password");
        GridPane.setConstraints(passwordTextfield, 0, 6, 2,  1);

        Button changeButton = new Button("Change Password");
        changeButton.setId("loginRegisterButton");
        changeButton.setOnAction(e -> {
            if (newPasswordTextfield.getText().length() >= 8) {
                String result = Main.clientController.updatePass(newPasswordTextfield.getText(),
                        passwordTextfield.getText());
                if (result.equals("true")) { // change password
                    window.setScene(SettingsPage.settingsScene(window));
                } else {
                    Alert failed = new Alert(Alert.AlertType.ERROR);
                    failed.setContentText("Incorrect password!");
                    failed.setHeaderText("Failure.");
                    failed.setTitle("Notification");
                    failed.show();
                }
            } else {
                Alert failed = new Alert(Alert.AlertType.ERROR);
                failed.setContentText("Password must contain at least 8 characters.");
                failed.setHeaderText("Failure.");
                failed.setTitle("Notification");
                failed.show();
            }
        });
        GridPane.setConstraints(changeButton, 0, 9, 2, 1);

        Button cancelButton = new Button("Cancel");
        cancelButton.setId("loginRegisterButton");
        cancelButton.setOnAction(e -> {
                window.setScene(SettingsPage.settingsScene(window));
        });
        GridPane.setConstraints(cancelButton, 0, 10, 2,1);

        //here all objects created above are placed in the central grid
        grid.getChildren().addAll(changeButton, newPassword, password, newPasswordTextfield,
                passwordTextfield, cancelButton);
        grid.setAlignment(Pos.CENTER);

        //create the grid for the top of the page
        GridPane topGrid = new GridPane();
        topGrid.setPadding(new Insets(10, 10, 10, 10));
        topGrid.setVgap(8);
        topGrid.setHgap(10);
        topGrid.setId("topGrid");

        //here the imageView of the logo is created
        Image logo = new Image("oppy350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 0, 0, 3, 1);
        topGrid.getChildren().add(displayLogo);
        topGrid.setAlignment(Pos.CENTER);

        //Here the column and row constraints of all sections of the page are set
        grid.getRowConstraints().addAll(gridRowConstraints());

        //here the top and center regions of the BorderPane are initialized to the desired gridPanes.
        borderPane.setCenter(grid);
        borderPane.setTop(topGrid);

        //here the top and center regions of the BorderPane are initialized to the desired gridPanes.
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("ConformationStyle.css");

        //here Key_events are added to the scene
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                changeButton.fire();
                ke.consume();
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
        row0.setMinHeight(100);
        row0.setMaxHeight(100);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(10);
        row1.setMaxHeight(10);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(20);
        row2.setMaxHeight(20);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(50);
        row3.setMaxHeight(50);
        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(20);
        row5.setMaxHeight(20);
        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(50);
        row6.setMaxHeight(50);
        RowConstraints row7 = new RowConstraints();
        row7.setMinHeight(40);
        row7.setMaxHeight(40);
        RowConstraints row8 = new RowConstraints();
        row8.setMinHeight(10);
        RowConstraints row9 = new RowConstraints();
        row9.setMinHeight(65);
        row9.setMaxHeight(65);
        RowConstraints row10 = new RowConstraints();
        row10.setMinHeight(100);

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
}
