package ui;

import clientside.ActionHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import server.model.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for adding the action page.
 */
public class AddActionPage {

    /**
     * Class for adding the action page.
     * @param primaryStage Primary stage
     * @return Scene
     */
    public static Scene addActionScene(Stage primaryStage) {
        ArrayList<CheckBox> listCheckboxes = new ArrayList<CheckBox>();
        Stage window = primaryStage;
        window.setTitle("AddActionPage");

        //CentralGrid////////////////////////////////////////////////////////////////////
        GridPane gridCenter = new GridPane();
        gridCenter.setPadding(new Insets(10, 10, 10, 10));
        gridCenter.setVgap(8);
        gridCenter.setHgap(10);

        //adding the back, submit and save as buttons
        Button backButton = new Button("Back");
        GridPane.setConstraints(backButton, 0, 0);
        backButton.setOnAction(e -> window.setScene(MainPage.mainScene(window)));

        Button saveAsButton = new Button("Save as ...");
        GridPane.setConstraints(saveAsButton, 1,4);
        saveAsButton.setOnAction(e -> {
            //implement a save as method
        });

        // ActionHandler example below:
        ActionHandler actionHandler = new ActionHandler(Main.userLog.getUsername());
        actionHandler.updateActionList();
        List<Action> actionList = actionHandler.getActionList();
        for (Action act : actionList) {
            System.out.println(act.getActionName());
        }
        
        Button submitButton = new Button("submit");
        GridPane.setConstraints(submitButton, 2,4);
        submitButton.setOnAction(e -> {
            for (int i = 0; i < listCheckboxes.size(); i++) {
                if (listCheckboxes.get(i).isSelected()) {
                    System.out.println(listCheckboxes.get(i).getText());
                    //action handler submit new action
                    System.out.println(actionHandler
                            .submitAction(new Action(listCheckboxes.get(i).getText(), "food", 20 )));
                }
            }
        });


        //here the drop down menu transport is created //////////////////////////////////
        TitledPane transportCategory = new TitledPane();
        transportCategory.setText("Transport");
        GridPane.setConstraints(transportCategory, 1,1,2,1);
        transportCategory.setExpanded(false);

        //Set the layout for the TiteldPane's contents
        GridPane gridTransport = new GridPane();
        gridTransport.setPadding(new Insets(10, 10, 10, 10));
        gridTransport.setVgap(8);
        gridTransport.setHgap(10);

        //only here for testing
        //a new List with the same name with all actions
        //should be queried from the database
        ArrayList<Action> listOfActions = new ArrayList<Action>();
        Action firstAction = new Action("bike instead of car", "transport", 100);
        Action secondAction = new Action("train instead of car", "transport", 20);
        Action thirdAction = new Action("vegetarian meal", "food", 20);
        Action fourthAction = new Action("lights out", "energy", 30);
        Action fifthAction = new Action("heating off", "energy", 50);
        listOfActions.add(thirdAction);
        listOfActions.add(firstAction);
        listOfActions.add(fifthAction);
        listOfActions.add(secondAction);
        listOfActions.add(fourthAction);
        // end of only here for testing part

        //gets all available actions and display's dem in the right category
        for (int i = 0; i < listOfActions.size(); i++) {
            if (listOfActions.get(i).getCategory().equals("transport")) {
                CheckBox newCheckBox = new CheckBox(listOfActions.get(i).getActionName());
                GridPane.setConstraints(newCheckBox, 1,i);
                listCheckboxes.add(newCheckBox);
                String strPoints = Integer.toString(listOfActions.get(i).getPoints());
                Label  newLabelPoints = new Label(strPoints);
                GridPane.setConstraints(newLabelPoints,2,i);
                gridTransport.getChildren().addAll(newCheckBox, newLabelPoints);
            }
        }
        transportCategory.setContent(gridTransport);

        ///here the drop down menu Food is created///////////////////////////////////////
        TitledPane foodCategory = new TitledPane();
        foodCategory.setText("Food");
        GridPane.setConstraints(foodCategory, 1,2,2,1);
        foodCategory.setExpanded(false);

        //Set the layout for the TiteldPane's contents
        GridPane gridFood = new GridPane();
        gridFood.setPadding(new Insets(10, 10, 10, 10));
        gridFood.setVgap(8);
        gridFood.setHgap(10);

        //gets all available actions and display's dem in the right category
        for (int i = 0; i < listOfActions.size(); i++) {
            if (listOfActions.get(i).getCategory().equals("food")) {
                CheckBox newCheckBox = new CheckBox(listOfActions.get(i).getActionName());
                GridPane.setConstraints(newCheckBox, 1, i);
                listCheckboxes.add(newCheckBox);
                String strPoints = Integer.toString(listOfActions.get(i).getPoints());
                Label newLabelPoints = new Label(strPoints);
                GridPane.setConstraints(newLabelPoints,2, i);
                gridFood.getChildren().addAll(newCheckBox, newLabelPoints);
            }
        }
        foodCategory.setContent(gridFood);

        ///////here the drop down menu Energy is created/////////////////////////////////
        TitledPane energyCategory = new TitledPane();
        energyCategory.setText("Energy");
        GridPane.setConstraints(energyCategory, 1,3,2,1);
        energyCategory.setExpanded(false);

        //Set the layout for the TiteldPane's contents
        GridPane gridEnergy = new GridPane();
        gridEnergy.setPadding(new Insets(10, 10, 10, 10));
        gridEnergy.setVgap(8);
        gridEnergy.setHgap(10);

        //gets all available actions and display's dem in the right category
        for (int i = 0; i < listOfActions.size(); i++) {
            if (listOfActions.get(i).getCategory().equals("energy")) {
                CheckBox newCheckBox = new CheckBox(listOfActions.get(i).getActionName());
                GridPane.setConstraints(newCheckBox, 1,i);
                listCheckboxes.add(newCheckBox);
                String strPoints = Integer.toString(listOfActions.get(i).getPoints());
                Label newLabelPoints = new Label(strPoints);
                GridPane.setConstraints(newLabelPoints,2,i);
                gridEnergy.getChildren().addAll(newCheckBox, newLabelPoints);
            }
        }
        energyCategory.setContent(gridEnergy);
        ////////////////////////////////////////////////////////////////////////////////////////
        ////central page layout/////////////////////////////////////////////////////////////////

        BorderPane centralPageLayout = new BorderPane();
        gridCenter.getChildren().addAll(backButton, saveAsButton,submitButton, transportCategory,
                                                                    foodCategory, energyCategory);
        centralPageLayout.setCenter(gridCenter);

        //here the create view is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1000, 600);
        return scene;
    }
}
