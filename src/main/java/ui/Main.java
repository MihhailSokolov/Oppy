package ui;

import clientside.ClientController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public static ClientController clientController;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start ui.
     *
     * @param primaryStage primStage
     */
    public void start(Stage primaryStage) throws IOException {
        Stage window = primaryStage;

        window.setTitle("LoginPage");
        window.setScene(LoginPage.loginScene(window));
        window.show();
    }
}