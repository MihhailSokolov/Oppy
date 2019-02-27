package main.java.UI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class LoginPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;
        window.setTitle("LoginPage");
        Button button = new Button("Login");
        Label username = new Label("Username/email");
        Label password = new Label("Password");
        TextField usernameTextfield = new TextField();
        TextField passwordTextfield = new TextField();

        GridPane layout = new GridPane();
        layout.getChildren().addAll(button, username, password, usernameTextfield, passwordTextfield);
        Scene scene = new Scene(layout, 800, 600);

        window.setScene(scene);
        window.show();
    }


}