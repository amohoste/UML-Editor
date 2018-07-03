package uml.views.umlbox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Amory Hoste
 * Zorgt voor de hoofding van een UMLBox
 */

public class HeaderCell extends Cell {

    public HeaderCell(double width, String name) {
        super(Pos.CENTER, new Insets(10, 0, 10, 0));
        Label header = new Label(name);
        header.setId("header");
        super.getChildren().add(header);
    }

}
