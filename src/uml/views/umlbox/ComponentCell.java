package uml.views.umlbox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import uml.xml.Attribute;

import java.util.List;

/**
 * Amory Hoste
 * Zorgt voor de inhoud van de onderdelen die de attributen / operations bevatten
 */

public class ComponentCell extends Cell {

    public ComponentCell (List<? extends Attribute> components) {
        super(Pos.CENTER_LEFT, new Insets(10, 0, 10, 10));
        initializeComponents(components);
    }

    private void initializeComponents(List<? extends Attribute> components) {
        if (components != null) {
            for (Attribute attribute : components) {
                HBox h = new HBox();
                Label label = new Label(attribute.getName());
                if (attribute.getScope().equals("classifier")) {
                    label.setStyle("-fx-underline: true");
                }
                Label label1 = new Label(attribute.toString().substring(attribute.getName().length() + 1));

                h.getChildren().addAll(label, label1);
                super.getChildren().add(h);
            }
        }
    }

}
