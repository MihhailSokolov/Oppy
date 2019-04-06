package ui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ActionMenuObject {
    private CheckBox checkBox;
    private TextField textField;

    public ActionMenuObject(CheckBox checkBox, TextField textField) {
        this.checkBox = checkBox;
        this.textField = textField;
    }
    public CheckBox getCheckBox() {
        return this.checkBox;
    }
    public TextField getTextField() {
        return this.textField;
    }
}
