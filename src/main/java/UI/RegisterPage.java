package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterPage {

    public static Scene registerScene(Stage primaryStage){
        Stage window = primaryStage;
        window.setTitle("RegisterPage");

        GridPane gridRegisterVieuw = new GridPane();
        gridRegisterVieuw.setPadding(new Insets(10, 10, 10, 10));
        gridRegisterVieuw.setVgap(8);
        gridRegisterVieuw.setHgap(10);

        //here the logo is created
        Image logo = new Image("sample/placeholder350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 0, 0, 3, 1);

        //fake register button at the top
        ToggleGroup loginRegister = new ToggleGroup();
        ToggleButton fakeRegisterButton = new ToggleButton("Register");
        fakeRegisterButton.setSelected(true);
        fakeRegisterButton.setDisable(true);
        GridPane.setConstraints(fakeRegisterButton, 1, 1);
        fakeRegisterButton.setToggleGroup(loginRegister);

        //The button the redirects to the login page
        ToggleButton loginButton = new ToggleButton("Sign in");
        GridPane.setConstraints(loginButton, 0, 1);
        loginButton.setOnAction(e ->{
            window.setScene(LoginPage.LoginScene(primaryStage));
        });
        loginButton.setToggleGroup(loginRegister);

        //email, username, password  and confirm password fields and labels
        Label email = new Label("email");
        GridPane.setConstraints(email, 0,2);

        Label username = new Label("Username");
        GridPane.setConstraints(username, 0, 3);

        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 4);

        Label confirmPassword = new Label("Confirm password");
        GridPane.setConstraints(confirmPassword,0,5);

        TextField emailTextfield = new TextField();
        emailTextfield.setPromptText("email");
        GridPane.setConstraints(emailTextfield, 1, 2);

        TextField usernameTextfield = new TextField();
        usernameTextfield.setPromptText("Username");
        GridPane.setConstraints(usernameTextfield, 1, 3);

        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Password");
        GridPane.setConstraints(passwordTextfield, 1, 4);

        PasswordField confirmPasswordTextfiel = new PasswordField();
        confirmPasswordTextfiel.setPromptText("Confirm password");
        GridPane.setConstraints(confirmPasswordTextfiel,1,5);


        //Here the register button is created
        Button registerButton = new Button("Register");
//      registerButton.setOnAction(to be done); this will check if your email and username are unique and if they are create your account in the database
        GridPane.setConstraints(registerButton, 2, 6);

        //Here all elements previously created are added to the vieuw and the vieuw is centerd
        gridRegisterVieuw.getChildren().addAll(email, username, password, confirmPassword, emailTextfield, usernameTextfield, passwordTextfield, confirmPasswordTextfiel, registerButton, loginButton, fakeRegisterButton, displayLogo);
        gridRegisterVieuw.setAlignment(Pos.CENTER);

        //here the create vieuw is made into a scene and returnd when the method is called
        Scene registerScene = new Scene(gridRegisterVieuw, 500, 400);
        return registerScene;
    }
}
