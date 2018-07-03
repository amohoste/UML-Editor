package uml.views.umlbox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Amory Hoste
 * Bovenklasse van header- en componentcell
 */

public abstract class Cell extends VBox {

    public Cell(Pos pos, Insets padding) {
        super.setAlignment(pos);
        super.setPadding(padding);
    }

}
