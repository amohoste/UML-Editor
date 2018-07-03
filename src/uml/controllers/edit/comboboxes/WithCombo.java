package uml.controllers.edit.comboboxes;

import javafx.scene.control.ComboBox;

import java.util.ArrayList;

/**
 * Amory Hoste
 * Combobox waarmee de klasse gekozen kan worden de te bewerken klasse een relatie heeft
 */

public class WithCombo extends ComboBox<String> {

    public WithCombo(ArrayList<String> names) {
        for (String name : names) {
            super.getItems().add(name);
        }
    }

}
