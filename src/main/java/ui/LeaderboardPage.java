package ui;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import server.model.User;


public class LeaderboardPage {
    //here the tables are created
    private static  TableView<User> number1Player = new TableView<>();
    private static  TableView<User> bestPlayers = new TableView<>();
    private static  TableView<User> scoreOfuser = new TableView<>();

    //this part is only here for testing and showing how the tables work. Will be deleted once it can be replaced
    private static final ObservableList<User> data =
            FXCollections.observableArrayList(
                    new User("hoi","hoihoi123","email@email.com",20)
            );
    //end of the testing part

    public static Scene LeaderboardScene(Stage primaryStage){
        Stage window = primaryStage;
        window.setTitle("LeaderboardPage");
        BorderPane centralPageLayout = new BorderPane();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //CentralGrid////////////////////////////////////////////////////////////////////////////////////////////////////////////
        GridPane gridCenter = new GridPane();
        gridCenter.setPadding(new Insets(10, 10, 10, 10));
        gridCenter.setVgap(8);
        gridCenter.setHgap(10);

        //here all the buttons normal buttons and labels are added


        Button backButton = new Button("Back");
        GridPane.setConstraints(backButton, 0, 0);
        backButton.setOnAction(e->{
            number1Player = new TableView<>(); //because number1Player is static it needs to be reset every time you close the page
            bestPlayers = new TableView<>(); //same here
            scoreOfuser = new TableView<>(); //same here
            window.setScene(MainPage.MainScene(window));
        });





        //here the columns for the tables are created
        TableColumn rank = new TableColumn("rank");
        rank.setCellValueFactory(new PropertyValueFactory<>("email")); //needs to be linked to rank later

        TableColumn name = new TableColumn("name");
        name.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn score = new TableColumn("score");
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        //TableColumn profilePicture = new TableColumn("pf");

        //here the columns and content are added to the tables and settings of the tables are set
        number1Player.setItems(data); //data needs tp be replaced with an ObservableList<User> only containing the number 1 player
        number1Player.getColumns().addAll(rank, name, score);
        number1Player.setPrefHeight(60);
        number1Player.setPrefWidth(300);
        number1Player.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setConstraints(number1Player,1,0);

        bestPlayers.setItems(data); //data needs to be replaced with an ObservableList<User> containing the best 50 players
        bestPlayers.getColumns().addAll(rank, name, score);
        bestPlayers.setPrefHeight(400);
        bestPlayers.setPrefWidth(300);
        GridPane.setConstraints(bestPlayers,1,1);

        scoreOfuser.setItems(data);//data needs tp be replaced with an ObservableList<User> only containing the current User
        scoreOfuser.getColumns().addAll(rank, name, score);
        scoreOfuser.setPrefHeight(60);
        scoreOfuser.setPrefWidth(300);
        scoreOfuser.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setConstraints(scoreOfuser,1,2);




        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////central page layout/////////////////////////////////////////////////////////////////////////////////////////////////////
        gridCenter.getChildren().addAll(backButton,number1Player,bestPlayers,scoreOfuser);
        centralPageLayout.setCenter(gridCenter);

        //here the create vieuw is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1000, 600);
        return scene;

    }
}
