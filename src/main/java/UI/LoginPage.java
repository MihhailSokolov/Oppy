package UI;
import javafx.application.Application;
import javafx.geometry.Insets;
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

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(8);
        layout.setHgap(10);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 2, 2);

        Label username = new Label("Username/email");
        GridPane.setConstraints(username, 0, 0);

        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 1);

        TextField usernameTextfield = new TextField();
        usernameTextfield.setPromptText("Username");
        GridPane.setConstraints(usernameTextfield, 1, 0);

        TextField passwordTextfield = new TextField();
        passwordTextfield.setPromptText("Password");
        GridPane.setConstraints(passwordTextfield, 1, 1);

        layout.getChildren().addAll(loginButton, username, password, usernameTextfield, passwordTextfield);

        Scene scene = new Scene(layout, 400, 300);

        window.setScene(scene);
        window.show();
    }


}