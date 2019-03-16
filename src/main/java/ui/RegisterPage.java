package ui;

import clientside.RegisterHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterPage {
    /**
     * Register page.
     *
     * @param primaryStage primStage
     * @return scene.
     */
    public static Scene registerScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setTitle("RegisterPage");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //here the logo is created
        Image logo = new Image("placeholder 350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 0, 0, 3, 1);

        //fake register button at the top
        ToggleButton fakeRegisterButton = new ToggleButton("Register");
        fakeRegisterButton.setSelected(true);
        fakeRegisterButton.setDisable(true);
        GridPane.setConstraints(fakeRegisterButton, 1, 1);
        ToggleGroup loginRegister = new ToggleGroup();
        fakeRegisterButton.setToggleGroup(loginRegister);

        //The button the redirects to the login page
        ToggleButton loginButton = new ToggleButton("Sign in");
        GridPane.setConstraints(loginButton, 0, 1);
        loginButton.setOnAction(e -> {
            window.setScene(LoginPage.loginScene(window));
        });
        loginButton.setToggleGroup(loginRegister);

        //email, username, password  and confirm password fields and labels
        Label email = new Label("email");
        GridPane.setConstraints(email, 0, 2);

        Label username = new Label("Username");
        GridPane.setConstraints(username, 0, 3);

        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 4);

        Label confirmPassword = new Label("Confirm password");
        GridPane.setConstraints(confirmPassword, 0, 5);

        TextField emailTextfield = new TextField();
        emailTextfield.setPromptText("email");
        GridPane.setConstraints(emailTextfield, 1, 2);

        TextField usernameTextfield = new TextField();
        usernameTextfield.setPromptText("Username");
        GridPane.setConstraints(usernameTextfield, 1, 3);

        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Password");
        GridPane.setConstraints(passwordTextfield, 1, 4);

        PasswordField confirmPasswordTextfield = new PasswordField();
        confirmPasswordTextfield.setPromptText("Confirm password");
        GridPane.setConstraints(confirmPasswordTextfield, 1, 5);


        //Here the register button is created
        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 2, 6);
        registerButton.setOnAction(e -> {
            RegisterHandler register = new RegisterHandler(usernameTextfield.getText(),
                    emailTextfield.getText(), passwordTextfield.getText());
            String result = register.sendRegister();
            if (result.equals("true")) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText("Success!");
                success.setContentText("You have successfully registered!");
                success.setTitle("Notification");
                success.show();
                window.setScene(LoginPage.loginScene(window));
            } else {
                Alert failed = new Alert(Alert.AlertType.ERROR);
                failed.setHeaderText("Failed.");
                failed.setContentText("Registration failed with the following message: " + result);
                failed.setTitle("Notification");
                failed.show();
            }
        });

        //The Check-availability button
        Button checkA = new Button("Check Availability");
        GridPane.setConstraints(checkA, 2, 3);
        checkA.setOnAction(e -> {
            if (!(usernameTextfield.getText().equals(""))) {
                RegisterHandler availability = new RegisterHandler(usernameTextfield.getText());
                String result = availability.sendAvailabilityCheck();
                if (result.equals("true")) {
                    checkA.setStyle("-fx-background-color: #00ff00");
                } else {
                    checkA.setStyle("-fx-background-color: #ff0000");
                }
            } else {
                checkA.setStyle("-fx-background-color: #ff0000");
            }
        });

        //Here all elements previously created are added to the view and the view is centered
        grid.getChildren().addAll(email, username, password, confirmPassword, emailTextfield,
                usernameTextfield, passwordTextfield, confirmPasswordTextfield,
                registerButton, loginButton, fakeRegisterButton, displayLogo, checkA);
        grid.setAlignment(Pos.CENTER);

        //here the create view is made into a scene and return when the method is called
        Scene registerScene = new Scene(grid, 500, 400);
        return registerScene;
    }
}
