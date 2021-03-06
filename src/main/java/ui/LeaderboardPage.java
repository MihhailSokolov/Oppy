package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import server.model.User;

import java.util.ArrayList;


public class LeaderboardPage {
    //here the tables are created
    private static  TableView<User> number1Player = new TableView<>();
    private static  TableView<User> bestPlayers = new TableView<>();
    private static  TableView<User> scoreOfuser = new TableView<>();

    //Here we create the 3 observable lists we are going to put in the tables
    private static ObservableList<User> user;

    private static ObservableList<User> top50;

    private static ObservableList<User> num1;

    /**
     * Method for creating leaderboard scene.
     * @param primaryStage primary stage
     * @return scene
     */
    public static Scene leaderboardScene(Stage primaryStage) {
        //Here we put the values in the list so they get reloaded with the page everytime.
        user = FXCollections.observableArrayList(Main.clientController.getUser());
        top50 = FXCollections.observableArrayList(Main.clientController.getTop50());
        num1 = FXCollections.observableArrayList(Main.clientController.getTop50().get(0));

        //setting title of the window and creating the BorderPane, the central layout for the window
        Stage window = primaryStage;
        window.setTitle("LeaderboardPage");
        final BorderPane centralPageLayout = new BorderPane();

        //create the grid for the center of the page
        final  GridPane gridCenter = new GridPane();

        //here the 3 columns for the first table are created
        TableColumn rank = new TableColumn("rank");
        rank.setCellValueFactory(new PropertyValueFactory<>("email")); //the ranks are stored locally under email
        TableColumn name = new TableColumn("name");
        name.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn score = new TableColumn("score");
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        //here the 3 columns for the second table are created
        TableColumn rank2 = new TableColumn("rank");
        rank2.setCellValueFactory(new PropertyValueFactory<>("email")); //the ranks are stored locally under email
        TableColumn name2 = new TableColumn("name");
        name2.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn score2 = new TableColumn("score");
        score2.setCellValueFactory(new PropertyValueFactory<>("score"));
        //TableColumn profilePicture = new TableColumn("pf");

        //here the 3 columns for last table are created
        TableColumn rank3 = new TableColumn("rank");
        rank3.setCellValueFactory(new PropertyValueFactory<>("email")); //the ranks are stored locally under email
        TableColumn name3 = new TableColumn("name");
        name3.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn score3 = new TableColumn("score");
        score3.setCellValueFactory(new PropertyValueFactory<>("score"));


        //here the columns and content are added to the tables and settings of the tables are set
        number1Player.setItems(num1);
        number1Player.getColumns().addAll(rank, name, score);
        number1Player.setId("tableNum1");
        number1Player.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setConstraints(number1Player,1,0);
        Label number1PlayerLabel = new Label("#1 Player");
        GridPane.setConstraints(number1PlayerLabel, 1,0 );
        number1PlayerLabel.setId("number1PlayerLabel");

        //here we place the second set of columns in the second table with the top50 players
        bestPlayers.setItems(top50);
        bestPlayers.getColumns().addAll(rank2, name2, score2);
        bestPlayers.setId("tableBest50");
        bestPlayers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setConstraints(bestPlayers,1,1);
        Label bestPlayersLabel = new Label("Top 50 Player Worldwide");
        GridPane.setConstraints(bestPlayersLabel, 1,1);
        bestPlayersLabel.setId("bestPlayersLabel");

        //here we place the third set of columns in the third table with the user
        scoreOfuser.setItems(user);
        scoreOfuser.getColumns().addAll(rank3, name3, score3);
        scoreOfuser.setId("tableUser");
        scoreOfuser.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setConstraints(scoreOfuser,1,2);
        Label scoreOfUserLabel = new Label("Your Score");
        GridPane.setConstraints(scoreOfUserLabel,1,2);
        scoreOfUserLabel.setId("scoreOfUserLabel");

        //here all above created tables are added to the central Grid
        gridCenter.getChildren().addAll(number1Player, bestPlayers, scoreOfuser,
                number1PlayerLabel, bestPlayersLabel, scoreOfUserLabel);
        gridCenter.setId("gridCenter");

        //here variables for the hamburger menu's and the top menu are initialized
        final GridPane gridHamburgerLeft = MainPage.gridHamburgerLeft(window);
        final GridPane gridHamburgerRight = MainPage.gridHamburgerRight(window);
        final GridPane gridTop = MainPage.gridTop(centralPageLayout, gridHamburgerLeft,
                gridHamburgerRight, "Leaderboard Page", new Label(), window);

        //Here the column and row constraints of all sections of the page are set
        gridCenter.getRowConstraints().addAll(gridRowConstraints());
        gridCenter.getColumnConstraints().addAll(gridColumnConstraints());
        gridHamburgerLeft.getRowConstraints().addAll(MainPage.hamburgerRowConstraintsLeft());
        gridHamburgerLeft.getColumnConstraints().addAll(MainPage.hamburgerColumnConstraintsLeft());
        gridHamburgerRight.getRowConstraints().addAll(MainPage.hamburgerRowConstraintsRight());
        gridHamburgerRight.getColumnConstraints().addAll(MainPage.hamburgerColumnConstraintsRight());
        gridTop.getColumnConstraints().addAll(MainPage.girdTopColumnConstraints());

        //here the top and center regions of the BorderPane are initialized to the desired gridPanes.
        centralPageLayout.setCenter(gridCenter);
        centralPageLayout.setTop(gridTop);

        //here a scene is constructed out of the BorderPane and styleSheets are added to it
        Scene scene = new Scene(centralPageLayout, 1920, 1080);
        scene.getStylesheets().add("topHamburgerStyle.css");
        scene.getStylesheets().add("leaderBoardStyle.css");
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination mainPage = new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN);
            final KeyCombination settings = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
            final KeyCombination leaderBoard = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
            final KeyCombination addAction = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
            public void handle(KeyEvent ke) {
                if (mainPage.match(ke)) {
                    window.setScene(MainPage.mainScene(window));
                    ke.consume();
                }
                if (settings.match(ke)) {
                    window.setScene(SettingsPage.settingsScene(window));
                    ke.consume();
                }
                if (leaderBoard.match(ke)) {
                    window.setScene(LeaderboardPage.leaderboardScene(window));
                    ke.consume();
                }
                if (addAction.match(ke)) {
                    window.setScene(AddActionPage.addActionScene(window));
                    ke.consume();
                }
            }
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                window.setScene(MainPage.mainScene(window));
            }
        });
        return scene;
    }

    /**
     * A method that resets the tableViews form leaderBoardPage and updates top50 and user.
     */
    public static void resetTables() {
        //because number1Player is static it needs to be reset every time you close the page
        number1Player = new TableView<>();
        bestPlayers = new TableView<>(); //same here
        scoreOfuser = new TableView<>(); //same here

        //updating the to keep the score accurate as possible
        Main.clientController.updateTop50();
        Main.clientController.updateUser();
    }

    /**
     * Method for Row constraints of the central grid.
     *
     *
     * @return ArrayList of RowConstraints
     */
    public static ArrayList<RowConstraints> gridRowConstraints() {
        RowConstraints row0 = new RowConstraints();
        row0.setMinHeight(85);
        row0.setMaxHeight(85);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(425);
        row1.setMaxHeight(425);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(85);
        row2.setMaxHeight(85);

        ArrayList<RowConstraints> rows = new ArrayList<RowConstraints>();
        rows.add(row0);
        rows.add(row1);
        rows.add(row2);
        return rows;
    }

    /**
     * Method for Column constraints of the central grid.
     *
     *
     * @return ArrayList of ColumnConstraints
     */
    public static ArrayList<ColumnConstraints> gridColumnConstraints() {
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(50);
        column0.setMaxWidth(50);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(500);
        column1.setMaxWidth(500);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(50);
        column2.setMaxWidth(50);


        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        return columns;
    }
}
