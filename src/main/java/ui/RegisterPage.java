package ui;

import clientside.ClientController;
import clientside.RegisterCheck;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class RegisterPage {
    /**
     * Register page.
     *
     * @param primaryStage primStage
     * @return scene.
     */
    public static Scene registerScene(Stage primaryStage) {
        //setting title of the window and creating the BorderPane, the central layout for the window
        Stage window = primaryStage;
        window.setTitle("RegisterPage");
        window.setMaximized(true);
        final BorderPane borderPane = new BorderPane();

        //create the grid for the center of the page
        GridPane grid = new GridPane();
        grid.setId("grid2");

        //Here a toggleGroup for the login/register switch buttons is created
        final ToggleGroup loginRegister = new ToggleGroup();

        //here the fake register button is created, at the top of the page
        ToggleButton fakeRegisterButton = new ToggleButton("Register");
        fakeRegisterButton.setId("login-register");
        fakeRegisterButton.setSelected(true);
        fakeRegisterButton.setDisable(true);
        GridPane.setConstraints(fakeRegisterButton, 1, 0);
        fakeRegisterButton.setToggleGroup(loginRegister);


        //Here the login button is created, at the top of the page
        ToggleButton loginButton = new ToggleButton("Sign in");
        loginButton.setId("login-register");
        GridPane.setConstraints(loginButton, 0, 0);
        loginButton.setOnAction(e -> {
            try {
                window.setScene(LoginPage.loginScene(window));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        loginButton.setToggleGroup(loginRegister);

        //Here the email Label is created
        Label email = new Label("email");
        GridPane.setConstraints(email, 0, 2);

        //Here the username Label is created
        Label username = new Label("Username");
        GridPane.setConstraints(username, 0, 5);

        //Here the password Label is created
        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 8);

        //Here the confirm password Label is created
        Label confirmPassword = new Label("Confirm password");
        GridPane.setConstraints(confirmPassword, 0, 11);

        //Here the email textfield is created
        TextField emailTextfield = new TextField();
        emailTextfield.setPromptText("email");
        GridPane.setConstraints(emailTextfield, 0, 3, 2, 1);

        //Here the username textfield is created
        TextField usernameTextfield = new TextField();
        usernameTextfield.setPromptText("Username");
        GridPane.setConstraints(usernameTextfield, 0, 6, 2, 1);

        //Here the password textfield is created
        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Password");
        GridPane.setConstraints(passwordTextfield, 0, 9, 2, 1);

        //Here the confirm password textfield is created
        PasswordField confirmPasswordTextfield = new PasswordField();
        confirmPasswordTextfield.setPromptText("Confirm password");
        GridPane.setConstraints(confirmPasswordTextfield, 0, 12, 2, 1);

        //Here the register button is created
        Button registerButton = new Button("Register");
        registerButton.setId("loginRegisterButton");
        GridPane.setConstraints(registerButton, 0, 15, 2, 1);
        registerButton.setOnAction(e -> {
            register(usernameTextfield, emailTextfield, passwordTextfield, confirmPasswordTextfield, window);
        });

        //Here the check availability button is created
        Button checkA = new Button("Check Availability");
        checkA.setId("checkAvailability");
        GridPane.setConstraints(checkA, 1, 5);
        checkA.setOnAction(e -> {
            checkAvailibility(usernameTextfield);
        });

        //here all objects created above are placed in the central grid
        grid.getChildren().addAll(email, username, password, confirmPassword, emailTextfield,
                usernameTextfield, passwordTextfield, confirmPasswordTextfield,
                registerButton, loginButton, fakeRegisterButton, checkA);
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
                registerButton.fire();
                ke.consume();
            }
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination login = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
            final KeyCombination availability = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
            public void handle(KeyEvent ke) {
                if (login.match(ke)) {
                    loginButton.fire();
                    ke.consume();
                }
                if (availability.match(ke)) {
                    checkA.fire();
                    ke.consume();
                }
            }
        });

        //here the scene is returned
        return scene;
    }

    /**
     * Method for setting grid row constraints.
     * @return ArrayList of row constraints
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
        row7.setMinHeight(10);
        RowConstraints row8 = new RowConstraints();
        row8.setMinHeight(20);
        row8.setMaxHeight(20);
        RowConstraints row9 = new RowConstraints();
        row9.setMinHeight(50);
        row9.setMaxHeight(50);
        RowConstraints row10 = new RowConstraints();
        row10.setMinHeight(10);
        RowConstraints row11 = new RowConstraints();
        row11.setMinHeight(20);
        row11.setMaxHeight(20);
        RowConstraints row12 = new RowConstraints();
        row12.setMinHeight(50);
        row12.setMaxHeight(50);
        RowConstraints row13 = new RowConstraints();
        row13.setMinHeight(40);
        row13.setMaxHeight(40);
        RowConstraints row14 = new RowConstraints();
        row14.setMinHeight(10);
        RowConstraints row15 = new RowConstraints();
        row15.setMinHeight(65);
        row15.setMaxHeight(65);
        RowConstraints row16 = new RowConstraints();
        row16.setMinHeight(100);
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
        rows.add(row11);
        rows.add(row12);
        rows.add(row13);
        rows.add(row14);
        rows.add(row15);
        rows.add(row16);
        return rows;
    }

    /**
     * Method for registering an account.
     *
     * @param usernameTextfield  Textfield with the username
     * @param emailTextfield Textfield with the email
     * @param passwordTextfield Textfield with the password
     * @param confirmPasswordTextfield Textfield to confirm the password
     * @param window The window that needs to be changed
     */
    public static void register(TextField usernameTextfield, TextField emailTextfield, TextField passwordTextfield,
                         TextField confirmPasswordTextfield, Stage window) {
        String result;
        if (RegisterCheck.checkUser(usernameTextfield.getText())
                && RegisterCheck.checkEmail(emailTextfield.getText())
                && RegisterCheck.checkPassword(passwordTextfield.getText(), confirmPasswordTextfield.getText())) {
            ClientController clientController = new ClientController(new User(usernameTextfield.getText(),
                    passwordTextfield.getText(), emailTextfield.getText(), 0, new Date()));
            result = clientController.register();
        } else {
            result = "username, email, or password is too short "
                    + "(password is less 8 characters or any other field is left empty)";
        }
        if (result.equals("true")) {
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("Success!");
            success.setContentText("You have successfully registered!");
            success.setTitle("Notification");
            success.show();
            try {
                window.setScene(LoginPage.loginScene(window));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            Alert failed = new Alert(Alert.AlertType.ERROR);
            failed.setHeaderText("Failed.");
            failed.setContentText("Registration failed with the following message: " + result);
            failed.setTitle("Notification");
            failed.show();
        }
    }

    /**
     * Method for Checking if a name is available.
     *
     * @param usernameTextfield  Textfield with the username
     */
    public static void checkAvailibility(TextField usernameTextfield) {
        if (!(usernameTextfield.getText().equals(""))) {
            String result = new ClientController().checkAvailability(usernameTextfield.getText());
            if (result.equals("true")) {
                usernameTextfield.setStyle("-fx-background-color: #00ff00;"
                        + "-fx-text-fill: #000000");
            } else {
                usernameTextfield.setStyle("-fx-background-color: #ff0000");
            }
        } else {
            usernameTextfield.setStyle("-fx-background-color: #ff0000");
        }
    }

}
