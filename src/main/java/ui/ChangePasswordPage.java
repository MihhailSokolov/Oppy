package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordPage {
    /**
     <<<<<<< HEAD
     * Method for creating the change password page.
     *
     * @param primaryStage primary stage
     * @return scene
     */
    public static Scene changePasswordScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("Change Password");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label password = new Label("Old Password");
        GridPane.setConstraints(password, 0, 2);

        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Old Password");
        GridPane.setConstraints(passwordTextfield, 1, 2);

        Label newPassword = new Label("New Password");
        GridPane.setConstraints(newPassword, 0, 3);

        PasswordField newPasswordTextfield = new PasswordField();
        newPasswordTextfield.setPromptText("New Password");
        GridPane.setConstraints(newPasswordTextfield, 1, 3);

        Button changeButton = new Button("Change Password");
        changeButton.setOnAction(e -> {
            String result = Main.clientController.updatePass(newPasswordTextfield.getText());
            if (result.equals("true")) { // change password

                try {
                    window.setScene(LoginPage.loginScene(window));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        GridPane.setConstraints(changeButton, 2, 4);

        Button cancelButton = new Button("cancel");
        cancelButton.setOnAction(e -> {
            window.setScene(SettingsPage.settingsScene(window));
        });
        GridPane.setConstraints(cancelButton, 1, 4);

        //Here all elements previously created are added to the view and the view is center
        grid.getChildren().addAll(changeButton, password, passwordTextfield, newPassword,
                newPasswordTextfield, cancelButton);
        grid.setAlignment(Pos.CENTER);

        //here the create view is made into a scene and return when the method is called
        Scene scene = new Scene(grid, 500, 325);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                changeButton.fire();
                ke.consume();

            }
            if (ke.getCode() == KeyCode.ESCAPE) {
                cancelButton.fire();
                ke.consume();

            }
        });
        return scene;
    }
}
