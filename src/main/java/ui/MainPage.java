package ui;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.model.User;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class for creating main page.
 */
public class MainPage {

    private static TableView<User> folowingList = new TableView<>();
    private static Button settingsButton;
    private static Button leaderboardButton;
    private static Button addActionButton;

    /**
     *  Method for main scene.
     *
     * @param primaryStage Primary stage
     * @return Scene
     */
    public static Scene mainScene(Stage primaryStage) {
        //setting title of the window and creating the BorderPane, the central layout for the window
        Stage window = primaryStage;
        window.setTitle("MainPage");
        window.setMaximized(true);
        final BorderPane centralPageLayout = new BorderPane();

        //create the grid for the center of the page
        GridPane gridCenter = new GridPane();
        gridCenter.setId("centerGrid");

        //here the Label witch displays your username is created
        String username = Main.clientController.getUser().getUsername();
        Label usernameLabel = new Label(username);
        Tooltip.install(usernameLabel, new Tooltip("This is your username"));
        usernameLabel.setId("username");
        GridPane.setConstraints(usernameLabel, 0, 1);

        //here the Label for your number of points is created, and set to a color dependent on your score
        String result = Main.clientController.getScore();
        int pointValue = Integer.parseInt(result);
        final Label numberOfPoints = new Label(result);
        numberOfPoints.setId("yourPoints");
        Tooltip.install(numberOfPoints, new Tooltip("Your current number of points"));
        if (pointValue < 0) {
            numberOfPoints.setStyle("-fx-text-fill: red");
        } else if (pointValue > 0) {
            numberOfPoints.setStyle("-fx-text-fill: green");
        } else {
            numberOfPoints.setStyle("-fx-text-fill: yellow");
        }
        GridPane.setConstraints(numberOfPoints, 0, 2);

        //here the image of the planet is set dependent on your number of points
        Image planet;
        if (pointValue >= 15000) {
            planet = new Image("oppy1.png");
        } else if (pointValue >= 10000) {
            planet = new Image("oppy2.png");
        } else if (pointValue >= 5000) {
            planet = new Image("oppy3.png");
        } else if (pointValue < -15000) {
            planet = new Image("oppy7.png");
        } else if (pointValue < -10000) {
            planet = new Image("oppy6.png");
        } else if (pointValue < -5000) {
            planet = new Image("oppy5.png");
        } else {
            planet = new Image("oppy4.png");
        }
        ImageView displayLogo = new ImageView(planet);
        displayLogo.setFitHeight(700);
        displayLogo.setFitWidth(700);
        GridPane.setConstraints(displayLogo, 1, 3);

        //here the Label that displays your daily point-loss is created
        Label pointLoss = new Label(Integer.toString(-3000));
        pointLoss.setId("pointLoss");
        Tooltip.install(pointLoss, new Tooltip("Number of points you lose each day"));
        GridPane.setConstraints(pointLoss, 2, 2);

        // Here is the counter counting down until midnight, when points-loss occurs
        Label timer = new Label();
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            int hours = 23 - new Date().getHours();
            int minutes = 59 - new Date().getMinutes();
            int seconds = 59 - new Date().getSeconds();
            //timer.setText(hours + ":"+ minutes+ ":" + seconds);
            timer.setText(sdf.format(new Date(0, 0, 0, hours, minutes, seconds)));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        Tooltip.install(timer, new Tooltip("Time before daily point-loss occurs"));
        timer.setId("timeTillPointLoss");
        GridPane.setConstraints(timer, 2, 1);

        //Here all elements previously created are added to the grid for the center of the page, and centered
        gridCenter.getChildren().addAll(displayLogo, numberOfPoints, pointLoss, timer, usernameLabel);
        gridCenter.setAlignment(Pos.CENTER);

        //here variables for the hamburger menu's, the top menu and the bottom menu are created
        final GridPane gridHamburgerLeft = gridHamburgerLeft(window);
        final GridPane gridHamburgerRight = gridHamburgerRight(window);
        final GridPane gridBot = gridBot(numberOfPoints, window);
        final GridPane gridTop = gridTop(centralPageLayout, gridHamburgerLeft, gridHamburgerRight,
                "Main Page", numberOfPoints, window);

        //Here the column and row constraints of all sections of the page are set
        gridCenter.getRowConstraints().addAll(gridRowConstraints());
        gridCenter.getColumnConstraints().addAll(gridColumnConstraints());
        gridHamburgerLeft.getRowConstraints().addAll(hamburgerRowConstraintsLeft());
        gridHamburgerLeft.getColumnConstraints().addAll(hamburgerColumnConstraintsLeft());
        gridHamburgerRight.getRowConstraints().addAll(hamburgerRowConstraintsRight());
        gridHamburgerRight.getColumnConstraints().addAll(hamburgerColumnConstraintsRight());
        gridTop.getColumnConstraints().addAll(girdTopColumnConstraints());

        //here the top, center and bottom regions of the BorderPane are initialized to the desired gridPanes.
        centralPageLayout.setCenter(gridCenter);
        centralPageLayout.setTop(gridTop);
        centralPageLayout.setBottom(gridBot);

        //Here an invisible button is created the allows you to log out from the mainPage
        Button invisLogoutbutton = new Button();
        invisLogoutbutton.setOnAction( e -> {
            try {
                window.setScene(LoginPage.loginScene(window));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        //here a scene is constructed out of the BorderPane and styleSheets are added to it
        Scene scene = new Scene(centralPageLayout, 1920, 1000);
        scene.getStylesheets().add("mainStyle.css");
        scene.getStylesheets().add("topHamburgerStyle.css");

        //here Key_events are added to the scene
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                invisLogoutbutton.fire();
                ke.consume();
            }
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination settings = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
            final KeyCombination leaderboard = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
            final KeyCombination addAction = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
            public void handle(KeyEvent ke) {
                if (settings.match(ke)) {
                    settingsButton.fire();
                    ke.consume();
                }
                if (leaderboard.match(ke)) {
                    leaderboardButton.fire();
                    ke.consume();
                }
                if (addAction.match(ke)) {
                    addActionButton.fire();
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
        row0.setMinHeight(0);
        row0.setMaxHeight(0);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(45);
        row1.setMaxHeight(45);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(45);
        row2.setMaxHeight(45);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(700);
        row3.setMaxHeight(700);
        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(0);
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(0);
        row5.setMaxHeight(0);
        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(0);
        row6.setMaxHeight(0);
        ArrayList<RowConstraints> rows = new ArrayList<RowConstraints>();
        rows.add(row0);
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);
        rows.add(row6);
        return rows;
    }

    /**
     * Method for column constraints of the central grid.
     *
     * @return ArrayList of ColumnConstraints
     */
    public static ArrayList<ColumnConstraints> gridColumnConstraints() {
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(200);
        column0.setMaxWidth(200);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(700);
        column1.setMaxWidth(700);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(200);
        column2.setMaxWidth(200);
        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        return columns;
    }


    /**
     * Method for Row constraints of the left hamburger grid.
     *
     * @return ArrayList of RowConstraints
     */
    public static ArrayList<RowConstraints> hamburgerRowConstraintsLeft() {
        RowConstraints row0 = new RowConstraints();
        row0.setMinHeight(100);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(100);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(100);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(100);
        ArrayList<RowConstraints> rows = new ArrayList<RowConstraints>();
        rows.add(row0);
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        return rows;
    }

    /**
     * Method for column constraints of the left hamburger grid.
     *
     * @return ArrayList of ColumnConstraints
     */
    public static ArrayList<ColumnConstraints> hamburgerColumnConstraintsLeft() {
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(100);
        column0.setMaxWidth(100);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(200);
        column1.setMaxWidth(200);
        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        return columns;
    }

    /**
     * Method for Row constraints of the right hamburger grid.
     *
     * @return ArrayList of RowConstraints
     */
    public static ArrayList<RowConstraints> hamburgerRowConstraintsRight() {
        RowConstraints row0 = new RowConstraints();
        row0.setMinHeight(125);
        row0.setMaxHeight(125);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(10);
        row1.setMaxHeight(10);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(125);
        row2.setMaxHeight(125);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(10);
        row3.setMaxHeight(10);
        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(125);
        row4.setMaxHeight(125);
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(30);
        row5.setMaxHeight(30);
        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(360);
        row6.setMaxHeight(360);
        RowConstraints row7 = new RowConstraints();
        row7.setMinHeight(30);
        row7.setMaxHeight(30);
        ArrayList<RowConstraints> rows = new ArrayList<RowConstraints>();
        rows.add(row0);
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);
        rows.add(row6);
        rows.add(row7);
        return rows;
    }

    /**
     * Method for column constraints of the right hamburger grid.
     *
     * @return ArrayList of ColumnConstraints
     */
    public static ArrayList<ColumnConstraints> hamburgerColumnConstraintsRight() {
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(133);
        column0.setMaxWidth(133);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(133);
        column1.setMaxWidth(133);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(34);
        column2.setMaxWidth(34);
        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        return columns;
    }

    /**
     * Method for column constraints of the top grid.
     *
     * @return ArrayList of ColumnConstraints
     */
    public static ArrayList<ColumnConstraints> girdTopColumnConstraints() {
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(60);
        column0.setMaxWidth(60);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(1780);
        column1.setMaxWidth(1780);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(40);
        column2.setMaxWidth(40);
        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        return columns;
    }

    /**
     * Method for main scene.
     *
     * @param primaryStage Primary stage
     * @return GridPane
     */
    public static GridPane gridHamburgerLeft(Stage primaryStage) {
        //creating the layout of the hamburger menu
        final Stage window = primaryStage;
        GridPane gridHamburger = new GridPane();
        gridHamburger.setId("hamburgerMenuLeft");

        //Here an image view from your profile picture is created
        BufferedImage serverProfilePicture = Main.clientController.getProfilePic(
                Main.clientController.getUser().getUsername());
        Image profilePicture;
        if (serverProfilePicture != null) {
            profilePicture = SwingFXUtils.toFXImage(serverProfilePicture, null);
        } else {
            profilePicture = new Image("oppy100x100.png");
        }
        ImageView displayProfilePicture = new ImageView(profilePicture);
        displayProfilePicture.setFitHeight(100);
        displayProfilePicture.setFitWidth(100);
        displayProfilePicture.setId("profilePicture");
        gridHamburger.setConstraints(displayProfilePicture, 0, 0, 1, 1);

        //here the toggleButton for the settingsPage is created
        ToggleButton settingsButton = new  ToggleButton("settings");
        settingsButton.setId("settingsButton");
        settingsButton.setOnAction(e -> {
            folowingList = new TableView<>();
            window.setScene(SettingsPage.settingsScene(window));
        });
        gridHamburger.setConstraints(settingsButton, 1, 0, 1, 1);

        //here the toggleButton for the leaderBoardPage is created
        ToggleButton leaderBoardButton = new  ToggleButton("Leaderboard");
        leaderBoardButton.setId("leaderActionButton");
        leaderBoardButton.setOnAction(e -> {
            //When clicked the button resets leaderBoard tables and loads data on the user and the Top50 players
            LeaderboardPage.resetTables();
            Main.clientController.getUser().setEmail(Main.clientController.getPosition());
            Main.clientController.top50Ranks(Main.clientController.getTop50());
            folowingList = new TableView<>();
            window.setScene(LeaderboardPage.leaderboardScene(window));
        });
        gridHamburger.setConstraints(leaderBoardButton, 0, 1, 2, 1);

        //Here the toggleButton for the addActionPage is created
        ToggleButton addActionButton = new  ToggleButton("Add action");
        addActionButton.setId("leaderActionButton");
        addActionButton.setOnAction(e -> {
            folowingList = new TableView<>();
            window.setScene(AddActionPage.addActionScene(window));
        });
        gridHamburger.setConstraints(addActionButton, 0, 2, 2, 1);

        //Here the toggleButton for the MainPage is created
        ToggleButton mainButton = new  ToggleButton("Main page");
        mainButton.setId("leaderActionButton");
        mainButton.setOnAction(e -> {
            folowingList = new TableView<>();
            window.setScene(MainPage.mainScene(window));
        });
        gridHamburger.setConstraints(mainButton, 0, 3, 2, 1);

        //Here the button the switch to the current window is disabled
        if (window.getTitle().equals("MainPage") ) {
            disableButton(mainButton);
        } else if (window.getTitle().equals("AddActionPage")) {
            disableButton(addActionButton);
        } else if (window.getTitle().equals("SettingsPage")) {
            disableButton(settingsButton);
        } else if (window.getTitle().equals("LeaderboardPage")) {
            disableButton(leaderBoardButton);
        }

        //Adds all previously created elements to the hamburger layout
        gridHamburger.getChildren().addAll(settingsButton, leaderBoardButton,
                addActionButton, displayProfilePicture,mainButton);
        gridHamburger.setAlignment(Pos.TOP_CENTER);

        //returns the hamburger grid
        return gridHamburger;
    }

    /**
     * Method for main scene.
     *
     * @param primaryStage Primary stage
     * @return GridPane
     */
    public static GridPane gridHamburgerRight(Stage primaryStage) {
        //creating the layout of the hamburger menu
        GridPane gridHamburger = new GridPane();
        gridHamburger.setId("hamburgerMenuRight");

        //Here some data is acquired, some stats are updated and some variables are created
        Main.clientController.updateUser();
        Date date = Main.clientController.getUser().getRegisterDate();
        Date now = new Date();
        long diffInMillies = Math.abs(now.getTime() - date.getTime());
        final long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        String result = Main.clientController.getScore();

        //Here all achievements are created and their conditions for unlocking are set
        Image preAchievement1 = new Image("AchievementNotUnlocked.png");//implement achievement not unlocked skin
        if (Integer.parseInt(result) >= 10000) {
            preAchievement1 = new Image("Achievement1.png");//implement achievement Image
        }
        ImageView achievement1 = new ImageView(preAchievement1);
        GridPane.setConstraints(achievement1, 0,0);
        Tooltip.install(achievement1, new Tooltip("Achievement for reaching 10,000 points"));

        Image preAchievement2 = new Image("AchievementNotUnlocked.png");
        if (Integer.parseInt(result) >= 100000) {
            preAchievement2 = new Image("Achievement2.png");
        }
        ImageView achievement2 = new ImageView(preAchievement2);
        GridPane.setConstraints(achievement2, 0,2);
        Tooltip.install(achievement2, new Tooltip("Achievement for reaching 100,000 points"));

        Image preAchievement3 = new Image("AchievementNotUnlocked.png");
        if (Integer.parseInt(result) >= 1000000) {
            preAchievement3 = new Image("Achievement3.png");
        }
        ImageView achievement3 = new ImageView(preAchievement3);
        GridPane.setConstraints(achievement3, 0,4);
        Tooltip.install(achievement3, new Tooltip("Achievement for reaching 1,000,000 points"));

        Image preAchievement4 = new Image("AchievementNotUnlocked.png");
        if (diff >= 7) {
            preAchievement4 = new Image("Achievement4.png");
        }
        ImageView achievement4 = new ImageView(preAchievement4);
        GridPane.setConstraints(achievement4, 1,0);
        Tooltip.install(achievement4, new Tooltip("Achievement for playing for 1 week"));

        Image preAchievement5 = new Image("AchievementNotUnlocked.png");
        if (diff >= 30) {
            preAchievement5 = new Image("Achievement5.png");
        }
        ImageView achievement5 = new ImageView(preAchievement5);
        GridPane.setConstraints(achievement5, 1,2);
        Tooltip.install(achievement5, new Tooltip("Achievement for playing for 1 month"));

        Image preAchievement6 = new Image("AchievementNotUnlocked.png");
        if (diff >= 365) {
            preAchievement6 = new Image("Achievement6.png");
        }
        ImageView achievement6 = new ImageView(preAchievement6);
        GridPane.setConstraints(achievement6, 1,4);
        Tooltip.install(achievement6, new Tooltip("Achievement for playing for 1 year"));

        //some data is updated
        Main.clientController.updateTop50();
        Main.clientController.updateFriendList();

        //here the followingList is created
        Label followLabel = new Label("people you follow:");
        followLabel.setId("followLabel");
        GridPane.setConstraints(followLabel,0,5,3,1);
        ObservableList<User> data =
                FXCollections.observableArrayList(
                        Main.clientController.getUser().getFriends()
                );
        TableColumn name = new TableColumn("name");
        name.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn score = new TableColumn("score");
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        folowingList.setItems(data);
        folowingList.getColumns().addAll(name, score);
        folowingList.setMaxHeight(300);
        folowingList.setPrefWidth(250);
        folowingList.setColumnResizePolicy(folowingList.CONSTRAINED_RESIZE_POLICY);
        GridPane.setConstraints(folowingList,0,6, 3, 1);
        folowingList.setRowFactory( tv -> {
            //Here the followingList is set to delete friends when their name is double-clicked in the table
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    User rowData = row.getItem();
                    System.out.println(rowData.getUsername());
                    Main.clientController.updateUser();
                    User friend = new User(rowData.getUsername(), null, null, 0, null);
                    Main.clientController.deleteFriend(friend);
                    Main.clientController.updateFriendList();
                    folowingList.setItems(FXCollections.observableArrayList(
                            Main.clientController.getUser().getFriends()));
                    folowingList.refresh();
                }
            });
            return row ;
        });
        Tooltip.install(folowingList, new Tooltip("People you follow, double click on them to delete them"));
        folowingList.setId("followList");
        folowingList.setPlaceholder(new Label("Start following people..."));

        //here a Label is created with the test "Search new people"
        Label newFollowLabel = new Label("Search new people:");
        newFollowLabel.setId("newFollowLabel");
        GridPane.setConstraints(newFollowLabel,0,7,3,1);

        //here the textfield is created in witch you type name of user you want to follow
        TextField followTextField = new TextField();
        followTextField.setPromptText("Start following...");
        GridPane.setConstraints(followTextField, 0, 8, 3, 1);

        //here the follow button is created
        Button followButton = new Button("follow");
        followButton.setId("followButton");
        followButtonSetOnAction(followButton, followTextField);
        GridPane.setConstraints(followButton, 0, 9, 3, 1);


        //add all previously created elements to the hamburger layout
        gridHamburger.getChildren().addAll(achievement1, achievement2, achievement3, achievement4, achievement5,
                achievement6, followLabel, folowingList, newFollowLabel,  followTextField, followButton);
        gridHamburger.setAlignment(Pos.TOP_CENTER);

        //here the hamburger grid is returned
        return gridHamburger;
    }

    /**
     * Method for main scene.
     *
     * @param centralPageLayout BorderPane of witch left and right are set
     * @param gridHamburgerLeft GridPane of left HamburgerMenu
     * @param gridHamburgerRight GridPane of right HamburgerMenu
     * @param text the name of the current Page
     * @param  scoreLabel gives the Label with the score to be passed to gridBot
     * @param  window Stage so that the window can be changed or updated
     * @return GridPane
     */
    public static GridPane gridTop(BorderPane centralPageLayout, GridPane gridHamburgerLeft,
                                   GridPane gridHamburgerRight, String text, Label scoreLabel, Stage window) {
        //creating the layout of the top menu
        final GridPane gridTop = new GridPane();

        //here the hamburger icons are created and and functions are attached
        //so that by clicking it it opens and closes the side menu's and
        //the  presets aren't on screen if one of the is open
        JFXHamburger hamburgerRight = new JFXHamburger();
        Tooltip.install(hamburgerRight, new Tooltip("Achievements/Friends menu"));
        hamburgerRight.setId("hamburgerButton");
        HamburgerSlideCloseTransition burgerTaskRight = new HamburgerSlideCloseTransition(hamburgerRight);
        burgerTaskRight.setRate(-1);

        JFXHamburger hamburgerLeft = new JFXHamburger();
        Tooltip.install(hamburgerLeft, new Tooltip("Options menu"));
        hamburgerLeft.setId("hamburgerButton");
        HamburgerSlideCloseTransition burgerTaskLeft = new HamburgerSlideCloseTransition(hamburgerLeft);
        burgerTaskLeft.setRate(-1);
        hamburgerLeft.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (burgerTaskLeft.getRate() == -1) {
                centralPageLayout.setLeft(gridHamburgerLeft);
                centralPageLayout.setBottom(null);
            } else {
                centralPageLayout.setLeft(null);
                if (burgerTaskRight.getRate() == -1 && window.getTitle().equals("MainPage")) {
                    centralPageLayout.setBottom(gridBot(scoreLabel, window));
                }
            }
            burgerTaskLeft.setRate(burgerTaskLeft.getRate() * -1);
            burgerTaskLeft.play();
        });
        gridTop.setConstraints(hamburgerLeft, 0, 0);


        hamburgerRight.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (burgerTaskRight.getRate() == -1) {
                centralPageLayout.setRight(gridHamburgerRight);
                centralPageLayout.setBottom(null);
            } else {
                centralPageLayout.setRight(null);
                folowingList = new TableView<>();
                if (burgerTaskLeft.getRate() == -1 && window.getTitle().equals("MainPage")) {
                    centralPageLayout.setBottom(gridBot(scoreLabel, window));
                }
            }
            burgerTaskRight.setRate(burgerTaskRight.getRate() * -1);

            burgerTaskRight.play();
        });
        gridTop.setConstraints(hamburgerRight, 2, 0);

        //here a Label the displays the pageName is created
        Label pageName = new Label(text);
        GridPane.setConstraints(pageName, 1,0);
        pageName.setId("pageName");
        Tooltip.install(pageName, new Tooltip("Your current page"));

        //here all previous created elements are added to the top layout
        gridTop.getChildren().addAll(hamburgerLeft, hamburgerRight, pageName);
        gridTop.setStyle("-fx-background-color: #4c4242;");

        //here the top grid is returned
        return gridTop;
    }

    /**
     * Method for main scene.
     *
     * @param scoreLabel gives the Label with the score to be update
     * @param window1 Stage so that the window can be reset
     * @return GridPane
     */
    public static GridPane gridBot(Label scoreLabel, Stage window1) {

        //creating the layout of the bottom grid
        final GridPane gridBot = new GridPane();
        final Stage window = window1;
        gridBot.setId("gridBot");

        //here all presets of the user are loaded and added to the bottom grid
        Main.clientController.updateUserPresets();

        //gets the names of the preSets and makes buttons of them
        for (int i = 0; i < Main.clientController.getUser().getPresets().size(); i++) {
            final int a = i;
            Button button = new Button(Main.clientController.getUser().getPresets().get(i).getName());
            button.setId("presetButton");
            GridPane.setConstraints(button, 2 * i,0);
            button.setOnAction(e -> {
                for (int j  = 0; j < Main.clientController.getUser().getPresets().get(a).getActionList().size(); j++) {
                    //adds functions the the preset buttons
                    Main.clientController.takeAction(
                            Main.clientController.getUser().getPresets().get(a).getActionList().get(j));
                    Main.clientController.updateUser();
                    scoreLabel.setText(Integer.toString(Main.clientController.getUser().getScore()));
                }
            });
            //gets the names of the actions in the presets
            ArrayList<String> alreadyDisplayed = new ArrayList<String>();
            Label actionName = new Label("");
            final int k = 0;
            for (int j = 0; j < Main.clientController.getUser().getPresets().get(i).getActionList().size()
                    && k < 4; j++) {
                if (! alreadyDisplayed.contains(
                        Main.clientController.getUser().getPresets().get(i).getActionList().get(j))) {
                    if (k == 3) {
                        actionName = new Label("etc.");
                    } else {
                        actionName = new Label("-"
                                + Main.clientController.getUser().getPresets().get(i).getActionList().get(j));
                        alreadyDisplayed.add(
                                Main.clientController.getUser().getPresets().get(i).getActionList().get(j));
                    }
                    actionName.setId("actionName");
                    GridPane.setConstraints(actionName, 2 * i,k + 1,2,1);
                    gridBot.getChildren().add(actionName);
                    k++;
                }

            }
            actionName.setId("actionNameLast");
            //Here the delete presetButton is created
            Button deletePreset = new Button("X");
            deletePreset.setId("presetDeleteButton");
            deletePreset.setOnAction(e -> {
                Main.clientController.deletePreset(Main.clientController.getUser().getPresets().get(a));
                window.setScene(MainPage.mainScene(window));
            });
            GridPane.setConstraints(deletePreset, 2 * i + 1, 0);

            //here al previously created elements are added the the Bottom grid
            gridBot.getChildren().addAll(button, deletePreset);
        }

        //here the bottom grid is returned
        return gridBot;
    }

    /**
     * Very short method that disables a button.
     */
    public static void disableButton(ToggleButton clicked) {
        clicked.setSelected(true);
        clicked.setDisable(true);
    }

    /**
     * Method for setting action to start following someone to a button.
     *
     * @param followButton button that needs to get the action
     * @param followTextField textField from witch text is gathered for the method
     */
    public static void followButtonSetOnAction(Button followButton, TextField followTextField) {
        followButton.setOnAction(e -> {
            final User friend = new User(followTextField.getText(), null, null, 0, null);
            ArrayList<String> friendNames = new ArrayList<String>();
            Main.clientController.updateUser();
            for (int i = 0; i < Main.clientController.getUser().getFriends().size(); i++) {
                friendNames.add( Main.clientController.getUser().getFriends().get(i).getUsername());
            }
            for (int i = 0; i  < friendNames.size(); i++) {
                System.out.println(friendNames.get(i));
            }
            if (followTextField.getText().equals(Main.clientController.getUser().getUsername())) {
                Alert failed = new Alert(Alert.AlertType.ERROR);
                failed.setContentText("You can't follow yourself");
                failed.setHeaderText("Failure.");
                failed.setTitle("Notification");
                failed.show();
            } else if (friendNames.contains(friend.getUsername())) {
                Alert failed = new Alert(Alert.AlertType.ERROR);
                failed.setContentText("You already follow the person");
                failed.setHeaderText("Failure.");
                failed.setTitle("Notification");
                failed.show();
            } else if (Main.clientController.addFriend(friend).equals("true")) {
                Main.clientController.updateFriendList();
                folowingList.setItems(FXCollections.observableArrayList(Main.clientController.getUser().getFriends()));
                folowingList.refresh();
                followTextField.clear();
            } else {
                Alert failed = new Alert(Alert.AlertType.ERROR);
                failed.setContentText("Such user does not exist");
                failed.setHeaderText("Failure.");
                failed.setTitle("Notification");
                failed.show();
            }
        });
    }

}

