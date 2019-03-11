package ui;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start ui.
     *
     * @param primaryStage primStage
     */
    public void start(Stage primaryStage) {
        Stage window = primaryStage;

        window.setTitle("LoginPage");
        window.setScene(LoginPage.loginScene(window));
        window.show();
    }
}