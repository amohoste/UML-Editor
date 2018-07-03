package uml.controllers.edit.editlists;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import uml.xml.Attribute;

/**
 * Amory Hoste
 * Stelt een rij voor die aan de attributepopuplist kan worden toegevoegd om de attributes te bewerken
 */

public class AttributePopupListRow extends HBox {

    private TextField namefield;
    private TextField typefield;

    public AttributePopupListRow(Attribute attribute, AttributePopupList popupList, String token) {
        // Componenten aanmaken
        namefield = new TextField();
        typefield = new TextField();

        // Componenten invullen
        if (attribute != null) {
            namefield.setText(attribute.getName());
            typefield.setText(attribute.getType());
        }

        // Knop
        Button b = new Button();
        b.setText(token);

        b.setOnMouseClicked(e -> {
            if (b.getText().equals("+")) {
                AttributePopupListRow a = new AttributePopupListRow(null, popupList, "+");
                popupList.getChildren().add(a);
                popupList.getRows().add(a);
                b.setText("x");
            } else if (b.getText().equals("x")) {
                popupList.getChildren().remove(this);
                popupList.getRows().remove(this);
            }
        });
        super.getChildren().addAll(namefield, typefield, b);

        // Layout
        namefield.setPrefWidth(110);
        typefield.setPrefWidth(110);
        setMargin(namefield, new Insets(0, 0, 0, 10));
        setMargin(typefield, new Insets(0, 0, 0, 20));
        setMargin(b, new Insets(0, 20, 0, 20));
        super.setPadding(new Insets(0, 0, 10, 10));
    }

    public String getNameString() {
        return namefield.getText();
    }

    public String getTypeString() {
        return typefield.getText();
    }

}
