package ui;

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
        ArrayList<Action> listOfActions = new ArrayList<Action>();
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
        GridPane.setConstraints(saveAsButton, 1, 5);
        saveAsButton.setOnAction(e -> {
            //implement a save as method
        });


        Main.clientController.updateActionList();
        List<Action> actionList = Main.clientController.getActionList();
//        List<Action> actionList = actionHandler.getActionList();
        for (Action act : actionList) {
            listOfActions.add(act);
        }

        Button submitButton = new Button("submit");
        GridPane.setConstraints(submitButton, 2, 5);
        submitButton.setOnAction(e -> {
            for (int i = 0; i < listCheckboxes.size(); i++) {
                if (listCheckboxes.get(i).isSelected()) {
                    Main.clientController.takeAction(listCheckboxes.get(i).getText());
                }
            }
        });

        //here the drop down menu transport is created //////////////////////////////////
        TitledPane transportCategory = new TitledPane();
        transportCategory.setText("Transport");
        GridPane.setConstraints(transportCategory, 1, 1, 2, 1);
        transportCategory.setExpanded(false);

        //Set the layout for the TiteldPane's contents
        GridPane gridTransport = new GridPane();
        gridTransport.setPadding(new Insets(10, 10, 10, 10));
        gridTransport.setVgap(8);
        gridTransport.setHgap(10);

        List<Action> transportList = Main.clientController.getCategoryList("transport");
        //gets all available actions and display's dem in the right category
        for (int i = 0; i < transportList.size(); i++) {
            CheckBox newCheckBox = new CheckBox(transportList.get(i).getActionName());
            GridPane.setConstraints(newCheckBox, 1, i);
            listCheckboxes.add(newCheckBox);
            String strPoints = Integer.toString(transportList.get(i).getPoints());
            Label newLabelPoints = new Label(strPoints);
            GridPane.setConstraints(newLabelPoints, 2, i);
            gridTransport.getChildren().addAll(newCheckBox, newLabelPoints);

        }
        transportCategory.setContent(gridTransport);

        ///here the drop down menu Food is created///////////////////////////////////////
        TitledPane foodCategory = new TitledPane();
        foodCategory.setText("Food");
        GridPane.setConstraints(foodCategory, 1, 2, 2, 1);
        foodCategory.setExpanded(false);

        //Set the layout for the TiteldPane's contents
        GridPane gridFood = new GridPane();
        gridFood.setPadding(new Insets(10, 10, 10, 10));
        gridFood.setVgap(8);
        gridFood.setHgap(10);

        //gets all available actions and display's dem in the right category
        List<Action> foodList = Main.clientController.getCategoryList("food");
        for (int i = 0; i < foodList.size(); i++) {
            CheckBox newCheckBox = new CheckBox(foodList.get(i).getActionName());
            GridPane.setConstraints(newCheckBox, 1, i);
            listCheckboxes.add(newCheckBox);
            String strPoints = Integer.toString(foodList.get(i).getPoints());
            Label newLabelPoints = new Label(strPoints);
            GridPane.setConstraints(newLabelPoints, 2, i);
            gridFood.getChildren().addAll(newCheckBox, newLabelPoints);
        }
        foodCategory.setContent(gridFood);

        ///////here the drop down menu Energy is created/////////////////////////////////
        TitledPane energyCategory = new TitledPane();
        energyCategory.setText("Energy");
        GridPane.setConstraints(energyCategory, 1, 3, 2, 1);
        energyCategory.setExpanded(false);

        //Set the layout for the TiteldPane's contents
        GridPane gridEnergy = new GridPane();
        gridEnergy.setPadding(new Insets(10, 10, 10, 10));
        gridEnergy.setVgap(8);
        gridEnergy.setHgap(10);

        //gets all available actions and display's dem in the right category
        List<Action> energyList = Main.clientController.getCategoryList("energy");
        for (int i = 0; i < energyList.size(); i++) {
            CheckBox newCheckBox = new CheckBox(energyList.get(i).getActionName());
            GridPane.setConstraints(newCheckBox, 1, i);
            listCheckboxes.add(newCheckBox);
            String strPoints = Integer.toString(energyList.get(i).getPoints());
            Label newLabelPoints = new Label(strPoints);
            GridPane.setConstraints(newLabelPoints, 2, i);
            gridEnergy.getChildren().addAll(newCheckBox, newLabelPoints);
        }
        energyCategory.setContent(gridEnergy);

        ///here the drop down menu misc is created///////////////////////////////////////
        TitledPane miscCategory = new TitledPane();
        miscCategory.setText("Misc.");
        GridPane.setConstraints(miscCategory, 1, 4, 2, 1);
        miscCategory.setExpanded(false);

        //Set the layout for the TiteldPane's contents
        GridPane gridMisc = new GridPane();
        gridMisc.setPadding(new Insets(10, 10, 10, 10));
        gridMisc.setVgap(8);
        gridMisc.setHgap(10);

        //gets all available actions and display's dem in the right category
        List<Action> miscList = Main.clientController.getCategoryList("misc");
        for (int i = 0; i < miscList.size(); i++) {
            CheckBox newCheckBox = new CheckBox(miscList.get(i).getActionName());
            GridPane.setConstraints(newCheckBox, 1, i);
            listCheckboxes.add(newCheckBox);
            String strPoints = Integer.toString(miscList.get(i).getPoints());
            Label newLabelPoints = new Label(strPoints);
            GridPane.setConstraints(newLabelPoints, 2, i);
            gridMisc.getChildren().addAll(newCheckBox, newLabelPoints);

        }
        miscCategory.setContent(gridMisc);
        ////central page layout/////////////////////////////////////////////////////////////////
        BorderPane centralPageLayout = new BorderPane();
        gridCenter.getChildren().addAll(backButton, saveAsButton, submitButton, transportCategory,
                miscCategory, foodCategory, energyCategory);
        centralPageLayout.setCenter(gridCenter);
        //here the create view is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1000, 600);
        return scene;
    }
}
