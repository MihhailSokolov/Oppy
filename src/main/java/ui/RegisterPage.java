package ui;

import clientside.ClientController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import server.model.User;

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
        Stage window = primaryStage;
        window.setTitle("RegisterPage");
        window.setMaximized(true);

        GridPane grid = new GridPane();
        grid.setId("grid2");

        //fake register button at the top
        ToggleButton fakeRegisterButton = new ToggleButton("Register");
        fakeRegisterButton.setId("login-register");
        fakeRegisterButton.setSelected(true);
        fakeRegisterButton.setDisable(true);
        GridPane.setConstraints(fakeRegisterButton, 1, 0);
        ToggleGroup loginRegister = new ToggleGroup();
        fakeRegisterButton.setToggleGroup(loginRegister);

        //The button the redirects to the login page
        ToggleButton loginButton = new ToggleButton("Sign in");
        loginButton.setId("login-register");
        GridPane.setConstraints(loginButton, 0, 0);
        loginButton.setOnAction(e -> {
            window.setScene(LoginPage.loginScene(window));
        });
        loginButton.setToggleGroup(loginRegister);

        //email, username, password  and confirm password fields and labels
        Label email = new Label("email");
        GridPane.setConstraints(email, 0, 2);

        Label username = new Label("Username");
        GridPane.setConstraints(username, 0, 5);

        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 8);

        Label confirmPassword = new Label("Confirm password");
        GridPane.setConstraints(confirmPassword, 0, 11);

        TextField emailTextfield = new TextField();
        emailTextfield.setPromptText("email");
        GridPane.setConstraints(emailTextfield, 0, 3, 2, 1);

        TextField usernameTextfield = new TextField();
        usernameTextfield.setPromptText("Username");
        GridPane.setConstraints(usernameTextfield, 0, 6, 2, 1);

        PasswordField passwordTextfield = new PasswordField();
        passwordTextfield.setPromptText("Password");
        GridPane.setConstraints(passwordTextfield, 0, 9, 2, 1);

        PasswordField confirmPasswordTextfield = new PasswordField();
        confirmPasswordTextfield.setPromptText("Confirm password");
        GridPane.setConstraints(confirmPasswordTextfield, 0, 12, 2, 1);

        //Here the register button is created
        Button registerButton = new Button("Register");
        registerButton.setId("loginRegisterButton");
        GridPane.setConstraints(registerButton, 0, 15, 2, 1);
        registerButton.setOnAction(e -> {
            ClientController clientController = new ClientController(new User(usernameTextfield.getText(),
                    passwordTextfield.getText(), emailTextfield.getText(), 0, new Date()));
            String result = clientController.register();
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
        checkA.setId("checkAvailability");
        GridPane.setConstraints(checkA, 1, 5);
        checkA.setOnAction(e -> {
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
        });

        //Here all elements previously created are added to the view and the view is centered
        grid.getChildren().addAll(email, username, password, confirmPassword, emailTextfield,
                usernameTextfield, passwordTextfield, confirmPasswordTextfield,
                registerButton, loginButton, fakeRegisterButton, checkA);
        grid.setAlignment(Pos.CENTER);
        //TopGrid made here/////////////////////////////////
        ///////////////////////////////////////////////////
        GridPane topGrid = new GridPane();
        topGrid.setPadding(new Insets(10, 10, 10, 10));
        topGrid.setVgap(8);
        topGrid.setHgap(10);
        topGrid.setId("topGrid");

        //here the logo is created
        Image logo = new Image("placeholder 350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 0, 0, 3, 1);
        topGrid.getChildren().add(displayLogo);
        topGrid.setAlignment(Pos.CENTER);

        ////////////////////////////////////////////////////////////////
        ////setting the sizes of the rows///////////////////////////////

        grid.getRowConstraints().addAll(gridRowConstraints());
        ////end of setting row sizes////////////////////////////////////
        ////////////////////////////////////////////////////////////////

        //here the create view is made into a scene and return when the method is called
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(grid);
        borderPane.setTop(topGrid);
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("LoginRegisterStyle.css");
        return scene;
    }

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
}
