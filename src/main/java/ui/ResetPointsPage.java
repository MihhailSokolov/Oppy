package ui;

import clientside.LoginHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

public class ResetPointsPage {
    /**
     * Method for creating reset points page.
     * @param primaryStage primary stage
     * @return settings scene
     */
    public static Scene resetPointsScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("Reset Points");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 2);

        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Password");
        GridPane.setConstraints(passwordTextfield, 1, 2);

        Button resetButton = new Button("Reset my Points");
        resetButton.setOnAction(e -> {
            LoginHandler log = new LoginHandler(Main.userLog.getUsername(),
                    passwordTextfield.getText(), Main.userLog.getRememberMe());
            String result = log.sendLogin();
            if (result.equals("true")) { // go to login page, delete account
                final String uri = "https://oppy-project.herokuapp.com/reset?username=" + Main.userLog.getUsername()
                        + "&pass=" + Main.userLog.hash(passwordTextfield.getText());
                RestTemplate restTemplate = new RestTemplate();
                String status = restTemplate.getForObject(uri, String.class);
                if (status.equals("true")) {
                    window.setScene(SettingsPage.settingsScene(window));
                }
            }
        });
        GridPane.setConstraints(resetButton, 2, 4);

        Button cancelButton = new Button("cancel");
        cancelButton.setOnAction(e -> {
            window.setScene(SettingsPage.settingsScene(window));
        });
        GridPane.setConstraints(cancelButton, 1, 4);

        //Here all elements previously created are added to the view and the view is center
        grid.getChildren().addAll(resetButton, password, passwordTextfield, cancelButton);
        grid.setAlignment(Pos.CENTER);

        //here the create view is made into a scene and return when the method is called
        Scene scene = new Scene(grid, 500, 325);
        return scene;
    }
}
