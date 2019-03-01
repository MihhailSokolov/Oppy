package UI;

import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;
        window.setTitle("LoginPage");
        window.setScene(LoginPage.LoginScene(window));
        window.show();

    }
}