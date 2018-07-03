package uml.controllers.edit.comboboxes;

import javafx.scene.control.ComboBox;

/**
 * Amory Hoste
 * Combobox waarmee de visibility kan gekozen worden
 */

public class VisibilityCombo extends ComboBox<String> {

    public VisibilityCombo() {
        super.getItems().addAll("public", "private", "protected", "package");
    }

}
