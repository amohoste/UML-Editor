package uml.controllers.edit.comboboxes;

import javafx.scene.control.ComboBox;

/**
 * Amory Hoste
 * Combobox waarmee het type van een relatie kan gekozen worden
 */

public class TypeCombo extends ComboBox<String> {

    public TypeCombo() {
        super.getItems().addAll("association", "inheritance", "realization", "dependency", "aggregation", "composition");
    }

}
