package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import server.model.Action;

import java.util.ArrayList;

public class AddActionPage {

    public static Scene addActionScene(Stage primaryStage) {
        ArrayList<CheckBox> listCheckboxes = new ArrayList<CheckBox>();
        Stage window = primaryStage;
        window.setTitle("AddActionPage");
        BorderPane centralPageLayout = new BorderPane();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //CentralGrid////////////////////////////////////////////////////////////////////////////////////////////////////////////
        GridPane gridCenter = new GridPane();
        gridCenter.setPadding(new Insets(10, 10, 10, 10));
        gridCenter.setVgap(8);
        gridCenter.setHgap(10);

        //adding the back, submit and save as buttons
        ToggleGroup addActionGroup = new ToggleGroup();

        ToggleButton backButton = new ToggleButton("Back");
        GridPane.setConstraints(backButton, 0, 0);
        backButton.setOnAction(e->{
            window.setScene(MainPage.MainScene(window));
        });
        backButton.setToggleGroup(addActionGroup);

        ToggleButton saveAsButton = new ToggleButton("Save as ...");
        GridPane.setConstraints(saveAsButton, 1,4);
        saveAsButton.setOnAction(e->{
            //inplement a save as method
        });
        saveAsButton.setToggleGroup(addActionGroup);

        ToggleButton submitButton = new ToggleButton("submit");
        GridPane.setConstraints(submitButton, 2,4);
        submitButton.setOnAction(e->{
            for(int i=0; i<listCheckboxes.size(); i++){
               if(listCheckboxes.get(i).isSelected()){
                   System.out.println(listCheckboxes.get(i).getText());
               }
            }
        });

        submitButton.setToggleGroup(addActionGroup);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //here the drop down menu transport is created ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        for(int i=0; i<listOfActions.size(); i++){
            if(listOfActions.get(i).getCategory() == "transport"){
                CheckBox newCheckBox = new CheckBox(listOfActions.get(i).getActionName());
                GridPane.setConstraints(newCheckBox, 1,i);
                listCheckboxes.add(newCheckBox);
                Label  newLabelPoints = new Label(Integer.toString(listOfActions.get(i).getPoints()));
                GridPane.setConstraints(newLabelPoints,2,i);
                gridTransport.getChildren().addAll(newCheckBox, newLabelPoints);
            }
        }
        transportCategory.setContent(gridTransport);




        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///here the drop down menu Food is created/////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        for(int i=0; i<listOfActions.size(); i++){
            if(listOfActions.get(i).getCategory() == "food"){
                CheckBox newCheckBox = new CheckBox(listOfActions.get(i).getActionName());
                GridPane.setConstraints(newCheckBox, 1,i);
                listCheckboxes.add(newCheckBox);
                Label  newLabelPoints = new Label(Integer.toString(listOfActions.get(i).getPoints()));
                GridPane.setConstraints(newLabelPoints,2,i);
                gridFood.getChildren().addAll(newCheckBox, newLabelPoints);
            }
        }
        foodCategory.setContent(gridFood);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////here the drop down menu Energy is created//////////////////////////////////////////////////////////////////////////////////////////////////////////
        TitledPane energyCategory = new TitledPane();
        energyCategory.setText("Energy");
        GridPane.setConstraints(energyCategory, 1,3,2,1);
        energyCategory.setExpanded(false);

        //Set the layout for the TiteldPane's contents
        GridPane gridEnergy= new GridPane();
        gridEnergy.setPadding(new Insets(10, 10, 10, 10));
        gridEnergy.setVgap(8);
        gridEnergy.setHgap(10);

        //gets all available actions and display's dem in the right category
        for(int i=0; i<listOfActions.size(); i++){
            if(listOfActions.get(i).getCategory() == "energy"){
                CheckBox newCheckBox = new CheckBox(listOfActions.get(i).getActionName());
                GridPane.setConstraints(newCheckBox, 1,i);
                listCheckboxes.add(newCheckBox);
                Label  newLabelPoints = new Label(Integer.toString(listOfActions.get(i).getPoints()));
                GridPane.setConstraints(newLabelPoints,2,i);
                gridEnergy.getChildren().addAll(newCheckBox, newLabelPoints);
            }
        }
        energyCategory.setContent(gridEnergy);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////central page layout////////////////////////////////////////////////////////////////////////////////////////////////////////

        gridCenter.getChildren().addAll(backButton, saveAsButton,submitButton, transportCategory, foodCategory, energyCategory);
        centralPageLayout.setCenter(gridCenter);

        //here the create vieuw is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1000, 600);
        return scene;
}
}
