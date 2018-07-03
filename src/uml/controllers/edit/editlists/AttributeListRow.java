package uml.controllers.edit.editlists;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import uml.controllers.edit.comboboxes.ScopeCombo;
import uml.controllers.edit.comboboxes.VisibilityCombo;
import uml.xml.Attribute;

/**
 * Amory Hoste
 * Stelt een rij voor die aan de attributelist kan worden toegevoegd om de attributes te bewerken
 */

public class AttributeListRow extends HBox {

    private ScopeCombo scopebox;
    private VisibilityCombo visibilitybox;
    private TextField namefield;
    private TextField typefield;

    public AttributeListRow(Attribute attribute, AttributeList attributeList, String token) {
        // Componenten aanmaken
        scopebox = new ScopeCombo();
        visibilitybox = new VisibilityCombo();
        namefield = new TextField();
        typefield = new TextField();


        // Componenten invullen
        if (attribute != null) {
            scopebox.setValue(attribute.getScope());
            visibilitybox.setValue(attribute.getVisibility());
            namefield.setText(attribute.getName());
            typefield.setText(attribute.getType());
        }

        // Knop
        Button b = new Button();
        b.setText(token);
        b.setOnMouseClicked(e -> {
            if (b.getText().equals("+")) {
                AttributeListRow r = new AttributeListRow(null, attributeList, "+");
                attributeList.getChildren().add(r);
                attributeList.getRows().add(r);
                b.setText("x");
            } else if (b.getText().equals("x")) {
                attributeList.getChildren().remove(this);
                attributeList.getRows().remove(this);
            }
        });
        super.getChildren().addAll(scopebox, visibilitybox, namefield, typefield, b);

        // Layout
        scopebox.setPrefWidth(110);
        visibilitybox.setPrefWidth(110);
        namefield.setPrefWidth(140);
        typefield.setPrefWidth(140);
        setMargin(visibilitybox, new Insets(0, 0, 0, 20));
        setMargin(namefield, new Insets(0, 0, 0, 20));
        setMargin(typefield, new Insets(0, 0, 0, 20));
        setMargin(b, new Insets(0, 0, 0, 20));
        super.setPadding(new Insets(0, 0, 10, 10));
    }

    public String getScopeString() {
        if (scopebox.getValue() != null) {
            return scopebox.getValue();
        } else {
            return "";
        }
    }

    public String getVisibilityString() {
        if (visibilitybox.getValue() != null) {
            return visibilitybox.getValue();
        } else {
            return "";
        }
    }

    public String getNameString() {
        return namefield.getText();
    }

    public String getTypeString() {
        return typefield.getText();
    }

}
