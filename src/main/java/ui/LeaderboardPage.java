package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import server.model.User;


public class LeaderboardPage {
    //here the tables are created
    private static  TableView<User> number1Player = new TableView<>();
    private static  TableView<User> bestPlayers = new TableView<>();
    private static  TableView<User> scoreOfuser = new TableView<>();
    //this part is only here for testing and showing how the tables work.
    //Will be deleted once it can be replaced\

    private static final ObservableList<User> user =
            FXCollections.observableArrayList(
                    Main.clientController.getUser()
            );

    private static final ObservableList<User> data =
            FXCollections.observableArrayList(
                    Main.clientController.getTop50()
            );

    private static final ObservableList<User> num1 =
            FXCollections.observableArrayList(
                    Main.clientController.getTop50().get(0)
            );
    //end of the testing part

    /**
     * Method for creating leaderboard scene.
     * @param primaryStage primary stage
     * @return scene
     */
    public static Scene leaderboardScene(Stage primaryStage) {
        Main.clientController.updateTop50();
        Main.clientController.updateUser();
        Stage window = primaryStage;
        window.setTitle("LeaderboardPage");

        /////////////////////////////////////////////////////////////////////////////////
        //CentralGrid////////////////////////////////////////////////////////////////////
        GridPane gridCenter = new GridPane();
        gridCenter.setPadding(new Insets(10, 10, 10, 10));
        gridCenter.setVgap(8);
        gridCenter.setHgap(10);

        //here all the buttons normal buttons and labels are added


        Button backButton = new Button("Back");
        GridPane.setConstraints(backButton, 0, 0);
        backButton.setOnAction(e -> {
            //because number1Player is static it needs to be reset every time you close the page
            number1Player = new TableView<>();
            bestPlayers = new TableView<>(); //same here
            scoreOfuser = new TableView<>(); //same here
            window.setScene(MainPage.mainScene(window));
        });

        //here the columns for the tables are created
        TableColumn rank = new TableColumn("rank");
        //needs to be linked to rank later
        rank.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn name = new TableColumn("name");
        name.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn score = new TableColumn("score");
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        //TableColumn profilePicture = new TableColumn("pf");

        //here the columns for the tables are created
        TableColumn rank2 = new TableColumn("rank");
        //needs to be linked to rank later
        rank2.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn name2 = new TableColumn("name");
        name2.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn score2 = new TableColumn("score");
        score2.setCellValueFactory(new PropertyValueFactory<>("score"));
        //TableColumn profilePicture = new TableColumn("pf");

        //here the columns for the tables are created
        TableColumn rank3 = new TableColumn("rank");
        //needs to be linked to rank later
        rank3.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn name3 = new TableColumn("name");
        name3.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn score3 = new TableColumn("score");
        score3.setCellValueFactory(new PropertyValueFactory<>("score"));
        //TableColumn profilePicture = new TableColumn("pf");


        //here the columns and content are added to the tables and settings of the tables are set
        //data needs tp be replaced with an ObservableList<User> only containing the number 1 player
        number1Player.setItems(num1);
        number1Player.getColumns().addAll(rank, name, score);
        number1Player.setPrefHeight(60);
        number1Player.setPrefWidth(300);
        number1Player.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setConstraints(number1Player,1,0);

        //data needs to be replaced with an ObservableList<User> containing the best 50 players
        bestPlayers.setItems(data);
        bestPlayers.getColumns().addAll(rank2, name2, score2);
        bestPlayers.setPrefHeight(400);
        bestPlayers.setPrefWidth(300);
        GridPane.setConstraints(bestPlayers,1,1);

        //data needs tp be replaced with an ObservableList<User> only containing the current User
        scoreOfuser.setItems(user);
        scoreOfuser.getColumns().addAll(rank3, name3, score3);
        scoreOfuser.setPrefHeight(60);
        scoreOfuser.setPrefWidth(300);
        scoreOfuser.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setConstraints(scoreOfuser,1,2);




        //////////////////////////////////////////////////////////////////////////////////////
        ////central page layout///////////////////////////////////////////////////////////////
        gridCenter.getChildren().addAll(backButton,number1Player,bestPlayers,scoreOfuser);
        BorderPane centralPageLayout = new BorderPane();
        centralPageLayout.setCenter(gridCenter);

        //here the create view is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1000, 600);
        return scene;

    }
}
