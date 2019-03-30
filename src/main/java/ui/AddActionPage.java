package ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import server.model.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for adding the action page.
 */
public class AddActionPage {
    private static Button submitButton;
    private static Button saveAsButton;

    /**
     * Class for adding the action page.
     * @param primaryStage Primary stage
     * @return Scene
     */
    public static Scene addActionScene(Stage primaryStage) {
        Stage window = primaryStage;
        window.setMaximized(true);
        window.setTitle("AddActionPage");
        final BorderPane centralPageLayout = new BorderPane();

        //here the hamburger menu's and the top menu and the mainGrid are initialized
        final GridPane gridHamburgerLeft = MainPage.gridHamburgerLeft(window);
        final GridPane gridHamburgerRight = MainPage.gridHamburgerRight(window);
        final GridPane gridTop = MainPage.gridTop(centralPageLayout, gridHamburgerLeft,
                gridHamburgerRight, "Actions Page");
        final GridPane gridCenter = centralGrid(window);

        ////setting the sizes of the rows///////////////////////////////
        gridCenter.getRowConstraints().addAll(gridRowConstraints());
        gridCenter.getColumnConstraints().addAll(gridColumnConstraints());
        gridHamburgerLeft.getRowConstraints().addAll(MainPage.hamburgerRowConstraintsLeft());
        gridHamburgerLeft.getColumnConstraints().addAll(MainPage.hamburgerColumnConstraintsLeft());
        gridHamburgerRight.getRowConstraints().addAll(MainPage.hamburgerRowConstraintsRight());
        gridHamburgerRight.getColumnConstraints().addAll(MainPage.hamburgerColumnConstraintsRight());
        gridTop.getColumnConstraints().addAll(MainPage.girdTopColumnConstraints());

        centralPageLayout.setCenter(gridCenter);
        centralPageLayout.setTop(gridTop);
        //here the create view is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1920, 1080);
        scene.getStylesheets().add("addActionStyle.css");
        scene.getStylesheets().add("topHamburgerStyle.css");
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
            if (ke.getCode() == KeyCode.S) {
                saveAsButton.fire();
            }
            ke.consume();
        });
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
        row1.setMinHeight(50);
        row1.setMaxHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(40);
        row2.setMaxHeight(420);
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(10);
        row3.setMaxHeight(10);
        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(40);
        row4.setMaxHeight(420);
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(10);
        row5.setMaxHeight(10);
        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(40);
        row6.setMaxHeight(420);
        RowConstraints row7 = new RowConstraints();
        row7.setMinHeight(10);
        row7.setMaxHeight(10);
        RowConstraints row8 = new RowConstraints();
        row8.setMinHeight(40);
        row8.setMaxHeight(420);
        RowConstraints row9 = new RowConstraints();
        row9.setMinHeight(10);
        row9.setMaxHeight(10);
        RowConstraints row10 = new RowConstraints();
        row10.setMinHeight(50);
        row10.setMaxHeight(50);
        RowConstraints row11 = new RowConstraints();
        row11.setMinHeight(0);
        row11.setMaxHeight(380);

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
        column1.setMinWidth(237);
        column1.setMaxWidth(237);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(26);
        column2.setMaxWidth(26);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setMinWidth(237);
        column3.setMaxWidth(237);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setMinWidth(50);
        column4.setMaxWidth(50);

        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        columns.add(column3);
        columns.add(column4);
        return columns;
    }

    /**
     * Method for Column constraints of the grid inside the drop-down menu's.
     *
     *
     * @return ArrayList of ColumnConstraints
     */
    public static ArrayList<ColumnConstraints> menuGridColumnConstraints() {
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setMinWidth(0);
        column0.setMaxWidth(0);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(400);
        column1.setMaxWidth(400);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(100);
        column2.setMaxWidth(100);
        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        return columns;
    }

    /**
     * Method for the central Grid.
     *
     *
     * @return GridPane , the centralGrid
     */
    public static GridPane centralGrid(Stage primaryStage) {
        final Stage window = primaryStage;
        final ArrayList<Action> listOfActions = new ArrayList<Action>();
        final ArrayList<CheckBox> listCheckboxes = new ArrayList<CheckBox>();
        final GridPane gridCenter = new GridPane();
        gridCenter.setId("gridCenter");
        Main.clientController.updateActionList();
        List<Action> actionList = Main.clientController.getActionList();
        for (Action act : actionList) {
            listOfActions.add(act);
        }
        Button saveAsButton = new Button("Save as preset...");
        GridPane.setConstraints(saveAsButton, 1, 10);
        ArrayList<String> listForPresets = new ArrayList<String>();
        saveAsButton.setOnAction(e -> {
            for (int i = 0; i < listCheckboxes.size(); i++) {
                if (listCheckboxes.get(i).isSelected()) {
                    String actionName = listCheckboxes.get(i).getText();
                    for (int j = 0; j < listOfActions.size(); j++) {
                        if (actionName.equals(listOfActions.get(j).getActionName())) {
                            listForPresets.add(listOfActions.get(j).getActionName());
                        }
                    }
                }
            }
            window.setScene(NamePresetPage.namePresetScene(window, listForPresets));
        });
        Button submitButton = new Button("submit");
        GridPane.setConstraints(submitButton, 3, 10);
        submitButton.setOnAction(e -> {
            for (int i = 0; i < listCheckboxes.size(); i++) {
                if (listCheckboxes.get(i).isSelected()) {
                    Main.clientController.takeAction(listCheckboxes.get(i).getText());
                }
            }
        });
        //here the drop down menu transport is created
        TitledPane transportCategory = new TitledPane();
        transportCategory.setText("Transport");
        GridPane.setConstraints(transportCategory, 1, 2, 3, 1);
        transportCategory.setExpanded(false);
        //Set the layout for the TiteldPane's contents
        GridPane gridTransport = new GridPane();
        gridTransport.setId("menuGrid");
        gridTransport.getColumnConstraints().addAll(menuGridColumnConstraints());
        List<Action> transportList = Main.clientController.getCategoryList("transport");
        //gets all available actions and display's them in the right category
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
        ///here the drop down menu Food is created
        TitledPane foodCategory = new TitledPane();
        foodCategory.setText("Food");
        GridPane.setConstraints(foodCategory, 1, 4, 3, 1);
        foodCategory.setExpanded(false);
        //Set the layout for the TitledPane's contents
        GridPane gridFood = new GridPane();
        gridFood.setId("menuGrid");
        gridFood.getColumnConstraints().addAll(menuGridColumnConstraints());
        //gets all available actions and display's them in the right category
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
        //here the drop down menu Energy is created
        TitledPane energyCategory = new TitledPane();
        energyCategory.setText("Energy");
        GridPane.setConstraints(energyCategory, 1, 6, 3, 1);
        energyCategory.setExpanded(false);
        //Set the layout for the TitledPane's contents
        GridPane gridEnergy = new GridPane();
        gridEnergy.setId("menuGrid");
        gridEnergy.getColumnConstraints().addAll(menuGridColumnConstraints());
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
        //here the drop down menu misc is created
        TitledPane miscCategory = new TitledPane();
        miscCategory.setText("Misc.");
        GridPane.setConstraints(miscCategory, 1, 8, 3, 1);
        miscCategory.setExpanded(false);
        //Set the layout for the TiteldPane's contents
        GridPane gridMisc = new GridPane();
        gridMisc.setId("menuGrid");
        gridMisc.getColumnConstraints().addAll(menuGridColumnConstraints());
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
        //setting so that only one category can be open at any time
        transportCategory.setOnMouseClicked(e -> {
            foodCategory.setExpanded(false);
            energyCategory.setExpanded(false);
            miscCategory.setExpanded(false);
        });
        foodCategory.setOnMouseClicked(e -> {
            transportCategory.setExpanded(false);
            energyCategory.setExpanded(false);
            miscCategory.setExpanded(false);
        });
        energyCategory.setOnMouseClicked(e -> {
            transportCategory.setExpanded(false);
            foodCategory.setExpanded(false);
            miscCategory.setExpanded(false);
        });
        miscCategory.setOnMouseClicked(e -> {
            transportCategory.setExpanded(false);
            foodCategory.setExpanded(false);
            energyCategory.setExpanded(false);
        });
        ////central page layout/////////////////////////////////////////////////////////////////
        gridCenter.getChildren().addAll(saveAsButton, submitButton, transportCategory,
                miscCategory, foodCategory, energyCategory);
        return gridCenter;
    }

}
