package ui;

import clientside.ClientController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import server.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LoginPage {
    /**
     * Method for creating the login page.
     *
     * @param primaryStage primary stage
     * @return scene
     */
    public static Scene loginScene(Stage primaryStage) throws IOException {
        Stage window = primaryStage;
        window.setTitle("LoginPage");
        window.setMaximized(true);

        GridPane grid = new GridPane();
        grid.setId("grid");

        //fake login button at the top
        ToggleButton fakeLoginButton = new ToggleButton("Sign in");
        fakeLoginButton.setId("login-register");
        fakeLoginButton.setSelected(true);
        fakeLoginButton.setDisable(true);
        GridPane.setConstraints(fakeLoginButton, 0, 0);

        ToggleGroup loginRegister = new ToggleGroup();
        fakeLoginButton.setToggleGroup(loginRegister);

        //the button the redirects to the register page
        ToggleButton registerButton = new ToggleButton("Register");
        registerButton.setId("login-register");
        GridPane.setConstraints(registerButton, 1, 0);
        registerButton.setOnAction(e -> window.setScene(RegisterPage.registerScene(window)));
        registerButton.setToggleGroup(loginRegister);

        //login/email and password fields and labels
        Label username = new Label("Username/email");
        GridPane.setConstraints(username, 0, 2);

        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 5);

        new FileWriter("remember.txt", false);
        File file = new File("remember.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String remUserName = br.readLine();

        TextField usernameTextfield = new TextField();
        usernameTextfield.setPromptText("Username");
        usernameTextfield.setText(remUserName);
        GridPane.setConstraints(usernameTextfield, 0, 3, 2, 1);

        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Password");
        GridPane.setConstraints(passwordTextfield, 0, 6, 2,  1);

        //remember forgot login
        CheckBox rememberMe = new CheckBox("remember me");
        GridPane.setConstraints(rememberMe, 0, 7);


        Button loginButton = new Button("Login");
        loginButton.setId("loginRegisterButton");
        loginButton.setOnAction(e -> {
            User user = new User(usernameTextfield.getText(), passwordTextfield.getText(), "", 0, new Date());
            ClientController clientHandler = new ClientController(user);
            String result = clientHandler.login();
            if (result.equals("true")) { // go to main page, now set to register as example
                Main.clientController = clientHandler;
                if (rememberMe.isSelected()) {
                    try {
                        clientside.Remember.storeUser(usernameTextfield.getText(), "remember.txt");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                window.setScene(MainPage.mainScene(window));
            } else {
                Alert failed = new Alert(Alert.AlertType.ERROR);
                failed.setContentText("That was not a valid username/email and password combination.");
                failed.setHeaderText("Failure.");
                failed.setTitle("Notification");
                failed.show();
            }
        });
        GridPane.setConstraints(loginButton, 0, 9, 2, 1);

        Button forgotPasswordButton = new Button("forgot password?");
        forgotPasswordButton.setId("forgotPasswordButton");
        GridPane.setConstraints(forgotPasswordButton, 1, 7);

        //Here all elements previously created are added to the view and the view is center
        grid.getChildren().addAll(loginButton, username, password, usernameTextfield,
                passwordTextfield, rememberMe, forgotPasswordButton,
                fakeLoginButton, registerButton);
        grid.setAlignment(Pos.CENTER);

        //TopGrid made here
        GridPane topGrid = new GridPane();
        topGrid.setPadding(new Insets(10, 10, 10, 10));
        topGrid.setVgap(8);
        topGrid.setHgap(10);
        topGrid.setId("topGrid");

        //here the logo is created
        Image logo = new Image("oppy350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 0, 0, 3, 1);
        topGrid.getChildren().add(displayLogo);
        topGrid.setAlignment(Pos.CENTER);

        //setting the sizes of the rows
        RowConstraints row0 = new RowConstraints();
        row0.setMinHeight(100);
        row0.setMaxHeight(100);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(10);
        row1.setMaxHeight(10);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(20);
        row2.setMaxHeight(20);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(50);
        row3.setMaxHeight(50);
        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(20);
        row5.setMaxHeight(20);
        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(50);
        row6.setMaxHeight(50);
        RowConstraints row7 = new RowConstraints();
        row7.setMinHeight(40);
        row7.setMaxHeight(40);
        RowConstraints row8 = new RowConstraints();
        row8.setMinHeight(10);
        RowConstraints row9 = new RowConstraints();
        row9.setMinHeight(65);
        row9.setMaxHeight(65);
        RowConstraints row10 = new RowConstraints();
        row10.setMinHeight(100);

        grid.getRowConstraints().addAll(row0, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10);
        //end of setting row sizes

        //here the create view is made into a scene and return when the method is called
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(grid);
        borderPane.setTop(topGrid);
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("LoginRegisterStyle.css");
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                loginButton.fire();
                ke.consume();
            }
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination register = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
            public void handle(KeyEvent ke) {
                if (register.match(ke)) {
                    registerButton.fire();
                    ke.consume();
                }
            }
        });
        return scene;
    }
}