package uml.controllers.edit.editlists;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import uml.controllers.edit.comboboxes.TypeCombo;
import uml.controllers.edit.comboboxes.WithCombo;
import uml.xml.Relation;

import java.util.ArrayList;

/**
 * Amory Hoste
 * Stelt een rij voor die aan de relationlist kan worden toegevoegd om de relations te bewerken
 */

public class RelationListRow extends HBox {
    
    private TypeCombo typebox;
    private WithCombo withbox;

    public RelationListRow(Relation relation, ArrayList<String> boxnames, RelationList relationlist, String token) {
        // Componenten aanmaken
        typebox = new TypeCombo();
        withbox = new WithCombo(boxnames);

        // Componenten invullen  
        if (relation != null) {
            typebox.setValue(relation.getType());
            withbox.setValue(relation.getWith());
        }

        // Knop  
        Button b = new Button();
        b.setText(token);

        b.setOnMouseClicked(e -> {
            if (b.getText().equals("+")) {
                RelationListRow r = new RelationListRow(null, boxnames, relationlist, "+");
                relationlist.getChildren().add(r);
                relationlist.getRows().add(r);
                b.setText("x");
            } else if (b.getText().equals("x")) {
                relationlist.getChildren().remove(this);
                relationlist.getRows().remove(this);
            }
        });
        super.getChildren().addAll(typebox, withbox, b);

        // Layout
        typebox.setPrefWidth(270);
        withbox.setPrefWidth(270);
        setMargin(withbox, new Insets(0, 0, 0, 20));
        setMargin(b, new Insets(0, 0, 0, 20));
        super.setPadding(new Insets(0, 0, 10, 10));
    }

    public String getWithString() {
        if (withbox.getValue() != null) {
            return withbox.getValue();
        } else {
            return "";
        }
    }

    public String getTypeString() {
        if (typebox.getValue() != null) {
            return typebox.getValue();
        } else {
            return "";
        }
    }
}
