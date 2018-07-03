package uml.controllers.edit.comboboxes;

import javafx.scene.control.ComboBox;

/**
 * Amory Hoste
 * Combobox waarmee de scope kan gekozen worden
 */

public class ScopeCombo extends ComboBox<String> {

    public ScopeCombo() {
        super.getItems().addAll("instance", "classifier");
    }

}
