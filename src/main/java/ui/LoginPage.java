package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

public class LoginPage {
    /**
<<<<<<< HEAD
     * Method for creating the login page.
     *
     * @param primaryStage primary stage
     * @return scene
     */
    public static Scene loginScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("LoginPage");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //here the logo is created
        Image logo = new Image("placeholder 350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 0, 0, 3, 1);

        //fake login button at the top
        ToggleButton fakeLoginButton = new ToggleButton("Sign in");
        fakeLoginButton.setSelected(true);
        fakeLoginButton.setDisable(true);
        GridPane.setConstraints(fakeLoginButton, 0, 1);

        ToggleGroup loginRegister = new ToggleGroup();
        fakeLoginButton.setToggleGroup(loginRegister);

        //the button the redirects to the register page
        ToggleButton registerButton = new ToggleButton("Register");
        GridPane.setConstraints(registerButton, 1, 1);
        registerButton.setOnAction(e -> window.setScene(RegisterPage.registerScene(window)));
        registerButton.setToggleGroup(loginRegister);

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
        GridPane.setConstraints(rememberMe, 1, 4);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            clientSide.LoginHandler log = new clientSide.LoginHandler(usernameTextfield.getText(), passwordTextfield.getText(), rememberMe.isSelected());
            // ClientController.sendLogin(log.toString());
            final String uri = "http://oppy-project.herokuapp.com" + log.toString();
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);
            if (result.equals("true")) { // go to main page, now set to register as example
                window.setScene(RegisterPage.registerScene(window));
            }

        });
        GridPane.setConstraints(loginButton, 2, 4);

        Button forgotPasswordButton = new Button("forgot password?");
        GridPane.setConstraints(forgotPasswordButton, 0, 4);

        //Here all elements previously created are added to the view and the view is center
        grid.getChildren().addAll(loginButton, username, password, usernameTextfield,
                passwordTextfield, rememberMe, forgotPasswordButton, displayLogo,
                fakeLoginButton, registerButton);
        grid.setAlignment(Pos.CENTER);

        //here the create view is made into a scene and return when the method is called
        Scene scene = new Scene(grid, 500, 325);
        return scene;
    }


}