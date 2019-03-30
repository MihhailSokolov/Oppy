package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
        Stage window = primaryStage;
        window.setTitle("Name Preset");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label name = new Label("Name");
        GridPane.setConstraints(name, 0, 2);

        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Preset Name");
        GridPane.setConstraints(nameTextField, 1, 2);

        Button setName = new Button("Set name");
        setName.setOnAction(e -> {
            System.out.println(listForPresets);
            Main.clientController.updateUser();
            presetName = nameTextField.getText();
            final Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("ALERT!");
            alert1.setHeaderText("You exceeded the number of presets allowed");
            final Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("ALERT!");
            alert2.setHeaderText("No name entered for the preset");
            if(Main.clientController.getUser().getPresets().size() > 7){
                alert1.showAndWait();
                window.setScene(AddActionPage.addActionScene(window));
            }else if (presetName ==null || presetName.equals("")) {
                alert2.showAndWait();
            } else {
                server.model.Preset presetToSend = new server.model.Preset(presetName, listForPresets);
                String result = Main.clientController.addPreset(presetToSend);
                if (result.equals("true")) {
                    window.setScene(AddActionPage.addActionScene(window));
                }
            }
        });
        GridPane.setConstraints(setName, 2, 3);


        Button cancelButton = new Button("cancel");
        cancelButton.setOnAction(e -> {
            window.setScene(SettingsPage.settingsScene(window));
        });
        GridPane.setConstraints(cancelButton, 1, 3);

        grid.getChildren().addAll(name, nameTextField, setName, cancelButton);
        grid.setAlignment(Pos.CENTER);

        Scene scene = new Scene(grid, 500, 325);
        return scene;
    }
}
