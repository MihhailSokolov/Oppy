package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class namePresetPage {

    private static String presetName;

    public static Scene namePresetScene(Stage primaryStage, ArrayList<String> listForPresets){
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
            presetName = nameTextField.getText();
            server.model.Preset presetToSend = new server.model.Preset(presetName, listForPresets);
            listForPresets.clear();
            String result = Main.clientController.addPreset(presetToSend);
            if (result.equals("true")) {
                window.setScene(AddActionPage.addActionScene(window));
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
