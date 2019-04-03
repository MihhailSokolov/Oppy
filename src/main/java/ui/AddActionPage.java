package ui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import server.model.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        //setting title of the window and creating the BorderPane, the central layout for the window
        Stage window = primaryStage;
        window.setMaximized(true);
        window.setTitle("AddActionPage");
        final BorderPane centralPageLayout = new BorderPane();

        //here variables for the hamburger menu's and the top menu and the central page are initialized
        final GridPane gridHamburgerLeft = MainPage.gridHamburgerLeft(window);
        final GridPane gridHamburgerRight = MainPage.gridHamburgerRight(window);
        final GridPane gridTop = MainPage.gridTop(centralPageLayout, gridHamburgerLeft,
                gridHamburgerRight, "Actions Page", new Label(), window);
        final GridPane gridCenter = centralGrid(window);

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
        scene.getStylesheets().add("addActionStyle.css");
        scene.getStylesheets().add("topHamburgerStyle.css");

        //here Key_events are added to the scene
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                submitButton.fire();
                ke.consume();
            }
            if (ke.getCode() == KeyCode.ESCAPE) {
                window.setScene(MainPage.mainScene(window));
                ke.consume();
            }
        });
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
        column1.setMinWidth(350);
        column1.setMaxWidth(350);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMinWidth(50);
        column2.setMaxWidth(50);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setMinWidth(100);
        column3.setMaxWidth(100);
        ArrayList<ColumnConstraints> columns = new ArrayList<ColumnConstraints>();
        columns.add(column0);
        columns.add(column1);
        columns.add(column2);
        columns.add(column3);
        return columns;
    }

    /**
     * Method for the central Grid.
     *
     *
     * @return GridPane , the centralGrid
     */
    public static GridPane centralGrid(Stage primaryStage) {
        //create the grid for the center of the page
        final Stage window = primaryStage;
        final GridPane gridCenter = new GridPane();
        gridCenter.setId("gridCenter");

        //initialize some variables an update some stats
        final ArrayList<Action> listOfActions = new ArrayList<Action>();
        final ArrayList<ActionMenuObject> listCheckboxes = new ArrayList<ActionMenuObject>();
        Main.clientController.updateActionList();
        List<Action> actionList = Main.clientController.getActionList();
        for (Action act : actionList) {
            listOfActions.add(act);
        }

        //here the Save as button is created
        Button saveAsButton = new Button("Save as preset...");
        GridPane.setConstraints(saveAsButton, 1, 10);
        ArrayList<String> listForPresets = new ArrayList<String>();
        saveAsButton.setOnAction(e -> {
            if (validInput(listCheckboxes)) {
                saveAS(listOfActions, listCheckboxes, listForPresets, window);
            }
        });

        //here the submit button is created
        Button submitButton = new Button("submit");
        GridPane.setConstraints(submitButton, 3, 10);
        submitButton.setOnAction(e -> {
            if (validInput(listCheckboxes)) {
                submit(listOfActions, listCheckboxes, listForPresets, window);
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
            TextField textField = new TextField();
            GridPane.setConstraints(textField, 2, i);
            textField.setId("input");
            textField.setPromptText("km");
            listCheckboxes.add(new ActionMenuObject(newCheckBox, textField));
            String strPoints = Integer.toString(transportList.get(i).getPoints());
            Label newLabelPoints = new Label(strPoints);
            GridPane.setConstraints(newLabelPoints, 3, i);
            gridTransport.getChildren().addAll(newCheckBox, newLabelPoints, textField);
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
            listCheckboxes.add(new ActionMenuObject(newCheckBox, null));
            String strPoints = Integer.toString(foodList.get(i).getPoints());
            Label newLabelPoints = new Label(strPoints);
            GridPane.setConstraints(newLabelPoints, 3, i);
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
            listCheckboxes.add(new ActionMenuObject(newCheckBox, null));
            String strPoints = Integer.toString(energyList.get(i).getPoints());
            Label newLabelPoints = new Label(strPoints);
            GridPane.setConstraints(newLabelPoints, 3, i);
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
            listCheckboxes.add(new ActionMenuObject(newCheckBox, null));
            String strPoints = Integer.toString(miscList.get(i).getPoints());
            Label newLabelPoints = new Label(strPoints);
            GridPane.setConstraints(newLabelPoints, 3, i);
            gridMisc.getChildren().addAll(newCheckBox, newLabelPoints);
        }
        miscCategory.setContent(gridMisc);

        //setting so that only one category can be open at any time
        onlyOneOpen(transportCategory, foodCategory, energyCategory, miscCategory);

        //here all objects created above are placed in the central grid
        gridCenter.getChildren().addAll(saveAsButton, submitButton, transportCategory,
                miscCategory, foodCategory, energyCategory);
        return gridCenter;
    }

    /**
     * Method for setting the save as preset function.
     *
     * @param listOfActions list of all actions in the database
     * @param listCheckboxes list of all checkboxes on the page
     * @param listForPresets list that's send to the server to save as preset
     * @param window the window that needs to be modified
     *
     */
    public static void saveAS( ArrayList<Action> listOfActions, ArrayList<ActionMenuObject> listCheckboxes,
                               ArrayList<String> listForPresets, Stage window) {
        for (int i = 0; i < listCheckboxes.size(); i++) {
            if (listCheckboxes.get(i).getCheckBox().isSelected()) {
                if (listCheckboxes.get(i).getTextField() != null) {
                    for (int j = 0; j < Integer.parseInt(listCheckboxes.get(i).getTextField().getText()); j++) {
                        listForPresets.add(listCheckboxes.get(i).getCheckBox().getText());
                    }
                } else {
                    listForPresets.add(listCheckboxes.get(i).getCheckBox().getText());
                }
            }
        }
        window.setScene(NamePresetPage.namePresetScene(window, listForPresets));
    }

    /**
     * Method for submitting your actions.
     *
     * @param listOfActions list of all actions in the database
     * @param listCheckboxes list of all checkboxes on the page
     * @param listForPresets list that's send to the server to save as preset
     * @param window the window that needs to be modified
     *
     */
    public static void submit(ArrayList<Action> listOfActions, ArrayList<ActionMenuObject> listCheckboxes,
                              ArrayList<String> listForPresets, Stage window) {
        for (int i = 0; i < listCheckboxes.size(); i++) {
            if (listCheckboxes.get(i).getCheckBox().isSelected()) {
                if (listCheckboxes.get(i).getTextField() != null) {
                    for (int j = 0; j < Integer.parseInt(listCheckboxes.get(i).getTextField().getText()); j++) {
                        Main.clientController.takeAction(listCheckboxes.get(i).getCheckBox().getText());
                    }
                } else {
                    Main.clientController.takeAction(listCheckboxes.get(i).getCheckBox().getText());
                }
            }
        }
        window.setScene(MainPage.mainScene(window));
    }

    /**
     * Method that check if input in textField is valid.
     *
     * @param listCheckboxes list of all checkboxes on the page
     *
     */
    public static boolean validInput(ArrayList<ActionMenuObject> listCheckboxes) {
        for (int i = 0; i < listCheckboxes.size(); i++) {
            if (listCheckboxes.get(i).getCheckBox().isSelected()) {
                if (listCheckboxes.get(i).getTextField() != null ) {
                    if (! checkIfInt(listCheckboxes.get(i).getTextField().getText())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("ALERT!");
                        alert.setHeaderText("Enter number of km traveled");
                        alert.showAndWait();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method that checks if a String  could be an Integer.
     *
     * @param string string to check
     *
     */
    public static boolean checkIfInt(String string) {
        Scanner sc = new Scanner(string);
        if (sc.hasNextInt()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that makes sure only one titledPane is opened at anny time.
     *
     * @param transportCategory the first titledPane
     * @param foodCategory the second titledPane
     * @param energyCategory the third titledPane
     * @param miscCategory the fourth titledPane
     *
     */
    public static void onlyOneOpen(TitledPane transportCategory, TitledPane foodCategory,
                                   TitledPane energyCategory, TitledPane miscCategory) {
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

    }
}
