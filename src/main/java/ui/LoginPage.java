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
import java.util.ArrayList;
import java.util.Date;

public class LoginPage {
    /**
     * Method for creating the login page.
     *
     * @param primaryStage primary stage
     * @return scene
     */
    public static Scene loginScene(Stage primaryStage) throws IOException {
        //setting title of the window and creating the BorderPane, the central layout for the window
        Stage window = primaryStage;
        window.setTitle("LoginPage");
        window.setMaximized(true);
        final BorderPane borderPane = new BorderPane();

        //create the grid for the center of the page
        GridPane grid = new GridPane();
        grid.setId("grid");

        //Here a toggleGroup for the login/register switch buttons is created
        final ToggleGroup loginRegister = new ToggleGroup();

        //here the fake login button is created, at the top of the page
        ToggleButton fakeLoginButton = new ToggleButton("Sign in");
        fakeLoginButton.setId("login-register");
        fakeLoginButton.setSelected(true);
        fakeLoginButton.setDisable(true);
        GridPane.setConstraints(fakeLoginButton, 0, 0);
        fakeLoginButton.setToggleGroup(loginRegister);

        //Here the register button is created, at the top of the page
        ToggleButton registerButton = new ToggleButton("Register");
        registerButton.setId("login-register");
        GridPane.setConstraints(registerButton, 1, 0);
        registerButton.setOnAction(e -> window.setScene(RegisterPage.registerScene(window)));
        registerButton.setToggleGroup(loginRegister);

        //Here the login/email Label is created
        Label username = new Label("Username");
        GridPane.setConstraints(username, 0, 2);

        //Here the password Label is created
        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 5);

        //Here the remember me data is collected
        new FileWriter("remember.txt", false);
        File file = new File("remember.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String remUserName = br.readLine();

        //Here the username textfield is created
        TextField usernameTextfield = new TextField();
        usernameTextfield.setPromptText("Username");
        usernameTextfield.setText(remUserName);
        GridPane.setConstraints(usernameTextfield, 0, 3, 2, 1);

        //Here the password textfield is created
        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Password");
        GridPane.setConstraints(passwordTextfield, 0, 6, 2,  1);

        //Here the remember me checkBox is created
        CheckBox rememberMe = new CheckBox("remember me");
        GridPane.setConstraints(rememberMe, 0, 7);

        //Here the login button is created
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

        //here all objects created above are placed in the central grid
        grid.getChildren().addAll(loginButton, username, password, usernameTextfield,
                passwordTextfield, rememberMe, fakeLoginButton, registerButton);
        grid.setAlignment(Pos.CENTER);

        //create the grid for the top of the page
        GridPane topGrid = new GridPane();
        topGrid.setPadding(new Insets(10, 10, 10, 10));
        topGrid.setVgap(8);
        topGrid.setHgap(10);
        topGrid.setId("topGrid");

        //here the imageView of the logo is created
        Image logo = new Image("oppy350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 0, 0, 3, 1);
        topGrid.getChildren().add(displayLogo);
        topGrid.setAlignment(Pos.CENTER);

        //Here the column and row constraints of all sections of the page are set
        grid.getRowConstraints().addAll(gridRowConstraints());

        //here the top and center regions of the BorderPane are initialized to the desired gridPanes.
        borderPane.setCenter(grid);
        borderPane.setTop(topGrid);

        //here the top and center regions of the BorderPane are initialized to the desired gridPanes.
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("LoginRegisterStyle.css");

        //here Key_events are added to the scene
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

        //here the scene is returned
        return scene;
    }

    /**
     * Method for Row constraints of the central grid.
     *
     *
     * @return ArrayList of RowConstraints
     */
    public static ArrayList<RowConstraints> gridRowConstraints() {
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

        ArrayList<RowConstraints> rows = new ArrayList<RowConstraints>();
        rows.add(row0);
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);
        rows.add(row6);
        rows.add(row7);
        rows.add(row8);
        rows.add(row9);
        rows.add(row10);
        return rows;

    }
}