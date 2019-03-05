package UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddActionPage {

    public static Scene addActionScene(Stage primaryStage) {
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
        submitButton.setToggleGroup(addActionGroup);

        //here the drop down manu's are created
        TitledPane transportCategory = new TitledPane();
        transportCategory.setText("Transport");
        GridPane.setConstraints(transportCategory, 1,1);
        transportCategory.setExpanded(false);

        TitledPane foodCategory = new TitledPane();
        foodCategory.setText("Food");
        GridPane.setConstraints(foodCategory, 1,2);
        foodCategory.setExpanded(false);

        TitledPane enegryCategory = new TitledPane();
        enegryCategory.setText("energy");
        GridPane.setConstraints(enegryCategory, 1,3);
        enegryCategory.setExpanded(false);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////








        gridCenter.getChildren().addAll(backButton, saveAsButton,submitButton, transportCategory, foodCategory, enegryCategory);
        centralPageLayout.setCenter(gridCenter);
        //here the create vieuw is made into a scene and returned when the method is called
        Scene scene = new Scene(centralPageLayout, 1000, 600);
        return scene;
    }
}
