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

public class DeleteUserPage {
    /**
     <<<<<<< HEAD
     * Method for creating the delete user page.
     *
     * @param primaryStage primary stage
     * @return scene
     */
    public static Scene deleteUserScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("Delete account");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 2);

        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Password");
        GridPane.setConstraints(passwordTextfield, 1, 2);

        Button deleteButton = new Button("Delete my account");
        deleteButton.setOnAction(e -> {
            // Maybe a pop up or the like asking the user if they're sure they want to delete?
            String result = Main.clientController.deleteAccount();
            if (result.equals("true")) {
                window.setScene(LoginPage.loginScene(window));
            }

        });
        GridPane.setConstraints(deleteButton, 2, 4);

        Button cancelButton = new Button("cancel");
        cancelButton.setOnAction(e -> {
            window.setScene(SettingsPage.settingsScene(window));
        });
        GridPane.setConstraints(cancelButton, 1, 4);

        //Here all elements previously created are added to the view and the view is center
        grid.getChildren().addAll(deleteButton, password, passwordTextfield, cancelButton);
        grid.setAlignment(Pos.CENTER);

        //here the create view is made into a scene and return when the method is called
        Scene scene = new Scene(grid, 500, 325);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                deleteButton.fire();
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
