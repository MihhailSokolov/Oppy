package UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class LoginPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;
        window.setTitle("LoginPage");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Image logo = new Image("placeholder 350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 0, 0, 3, 1);


        //login register buttons at the top
        ToggleGroup loginRegister = new ToggleGroup();

        ToggleButton registerButton = new ToggleButton("Register");
//        registerButton.setOnAction(classthatwillhandleactionsnshit); this will be the button that redirects to the register page (something for client side fellas)
        GridPane.setConstraints(registerButton, 1, 1);

        //THIS BUTTON DOES NOTHING. TOUCH IT AND I WILL END YOU MOTHERFUCKER.
        ToggleButton fakeLoginButton = new ToggleButton("Sign in");
        fakeLoginButton.setSelected(true);
        fakeLoginButton.setDisable(true);
        GridPane.setConstraints(fakeLoginButton, 0,1);

        registerButton.setToggleGroup(loginRegister);
        fakeLoginButton.setToggleGroup(loginRegister);

        //login/email and password fields and labels
        Label username = new Label("Username/email");
        GridPane.setConstraints(username, 0, 2);

        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 3);

        TextField usernameTextfield = new TextField();
        usernameTextfield.setPromptText("Username");
        GridPane.setConstraints(usernameTextfield, 1, 2);

        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Password");
        GridPane.setConstraints(passwordTextfield, 1, 3);


        //remember forgot login
        CheckBox rememberMe = new CheckBox("remember me");
        GridPane.setConstraints(rememberMe, 1,4);

        Button loginButton = new Button("Login");
//        loginButton.setOnAction(classthatwillhandleactionsnshit); this will redirect you to the main page after sending the data in user and passwd fields to be authed
        GridPane.setConstraints(loginButton, 2, 4);

        Button forgotPasswordButton = new Button("forgot password?");
        GridPane.setConstraints(forgotPasswordButton, 0, 4);

        grid.getChildren().addAll(loginButton, username, password, usernameTextfield, passwordTextfield, rememberMe, forgotPasswordButton, displayLogo, fakeLoginButton, registerButton);

        grid.setAlignment(Pos.CENTER);

        Scene scene = new Scene(grid, 500, 325);

        window.setScene(scene);
        window.show();
    }


}