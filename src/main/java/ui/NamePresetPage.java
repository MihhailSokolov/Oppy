package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NamePresetPage {

    private static String presetName;

    /**
     * This page gives user the option to name the presets which they created.
     *
     * @param primaryStage The stage being passed along from the addActionPage
     * @param listForPresets The list of the selected presets
     * @return returns the addActionPage again so the user can create another preset
     */

    public static Scene namePresetScene(Stage primaryStage, ArrayList<String> listForPresets) {
        //setting title of the window and creating the BorderPane, the central layout for the window
        Stage window = primaryStage;
        window.setTitle("Reset Points");
        window.setMaximized(true);
        final BorderPane borderPane = new BorderPane();

        //create the grid for the center of the page
        GridPane grid = new GridPane();
        grid.setId("grid");

        //Here the name Label is created
        Label name = new Label("Name");
        GridPane.setConstraints(name, 0, 2);

        //Here the password textfield is created
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Name");
        GridPane.setConstraints(nameTextField, 0, 3, 2,  1);

        //Here the set preset button is created
        Button presetButton = new Button("Set Preset");
        presetButton.setId("loginRegisterButton");
        presetButton.setOnAction(e -> {
            System.out.println(listForPresets);
            Main.clientController.updateUser();
            presetName = nameTextField.getText();
            final Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("ALERT!");
            alert1.setHeaderText("You exceeded the number of presets allowed");
            final Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("ALERT!");
            alert2.setHeaderText("No name entered for the preset");
            if (Main.clientController.getUser().getPresets().size() > 7) {
                alert1.showAndWait();
                window.setScene(AddActionPage.addActionScene(window));
            } else if (presetName == null || presetName.equals("")) {
                alert2.showAndWait();
            } else {
                server.model.Preset presetToSend = new server.model.Preset(presetName, listForPresets);
                String result = Main.clientController.addPreset(presetToSend);
                if (result.equals("true")) {
                    window.setScene(MainPage.mainScene(window));
                }
            }
        });
        GridPane.setConstraints(presetButton, 0, 9, 2, 1);

        Button cancelButton = new Button("Cancel");
        cancelButton.setId("loginRegisterButton");
        cancelButton.setOnAction(e -> {
            window.setScene(AddActionPage.addActionScene(window));
        });
        GridPane.setConstraints(cancelButton, 0, 10, 2,1);

        //here all objects created above are placed in the central grid
        grid.getChildren().addAll(presetButton, name,
                nameTextField, cancelButton);
        grid.setAlignment(Pos.CENTER);

        //create the grid for the top of the page
        GridPane topGrid = new GridPane();
        topGrid.setPadding(new Insets(10, 10, 10, 10));
        topGrid.setVgap(8);
        topGrid.setHgap(10);
        topGrid.setId("topGrid");

        //here the imageView of the logo is created
        Image logo = new Image("oppy350x150.png");
        ImageView displayLogo = new ImageView(logo);
        GridPane.setConstraints(displayLogo, 0, 0, 3, 1);
        topGrid.getChildren().add(displayLogo);
        topGrid.setAlignment(Pos.CENTER);

        //Here the column and row constraints of all sections of the page are set
        grid.getRowConstraints().addAll(gridRowConstraints());

        //here the top and center regions of the BorderPane are initialized to the desired gridPanes.
        borderPane.setCenter(grid);
        borderPane.setTop(topGrid);

        //here the top and center regions of the BorderPane are initialized to the desired gridPanes.
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("ConformationStyle.css");

        //here Key_events are added to the scene
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                presetButton.fire();
                ke.consume();
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
        row7.setMinHeight(40);
        row7.setMaxHeight(40);
        RowConstraints row8 = new RowConstraints();
        row8.setMinHeight(10);
        RowConstraints row9 = new RowConstraints();
        row9.setMinHeight(65);
        row9.setMaxHeight(65);
        RowConstraints row10 = new RowConstraints();
        row10.setMinHeight(100);

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
}
