package uml.controllers.edit.editlists;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import uml.controllers.edit.comboboxes.ScopeCombo;
import uml.controllers.edit.comboboxes.VisibilityCombo;
import uml.files.controlsfx.PopOver;
import uml.xml.Attribute;
import uml.xml.Operation;
import java.util.ArrayList;

/**
 * Amory Hoste
 * Stelt een rij voor die aan de operationlist kan worden toegevoegd om de operations te bewerken
 */

public class OperationListRow extends HBox {

    private ScopeCombo scopebox;
    private VisibilityCombo visibilitybox;
    private Button attributeButton;
    private TextField namefield;
    private TextField typefield;
    private PopOver popup;
    private AttributePopupList attributeList;

    public OperationListRow(Operation operation, OperationList operationList, String token) {
        // Componenten aanmaken  
        scopebox = new ScopeCombo();
        visibilitybox = new VisibilityCombo();
        namefield = new TextField();
        typefield = new TextField();

        // Componenten invullen  
        if (operation != null) {
            scopebox.setValue(operation.getScope());
            visibilitybox.setValue(operation.getVisibility());
            namefield.setText(operation.getName());
            typefield.setText(operation.getType());
        }

        // Attributenknop
        attributeButton = new Button("...");
        attributeList = new AttributePopupList();
        attributeList.initialize(operation);

        popup = new PopOver();
        popup.setTitle("Attributes");
        popup.setPrefWidth(310);
        popup.setContentNode(attributeList);
        attributeButton.setOnMouseClicked(e -> popup.show(attributeButton));

        // Knop  
        Button b = new Button();
        b.setText(token);

        b.setOnMouseClicked(e -> {
            if (b.getText().equals("+")) {
                OperationListRow o = new OperationListRow(null, operationList, "+");
                operationList.getChildren().add(o);
                operationList.getRows().add(o);
                b.setText("x");
            } else if (b.getText().equals("x")) {
                operationList.getChildren().remove(this);
                operationList.getRows().remove(this);
            }
        });
        super.getChildren().addAll(scopebox, visibilitybox, attributeButton, namefield, typefield, b);

        // Layout
        scopebox.setPrefWidth(110);
        visibilitybox.setPrefWidth(110);
        namefield.setPrefWidth(110);
        typefield.setPrefWidth(110);

        setMargin(visibilitybox, new Insets(0, 0, 0, 20));
        setMargin(attributeButton, new Insets(0, 0, 0, 26));
        setMargin(namefield, new Insets(0, 0, 0, 26));
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

    public ArrayList<Attribute> getAttributes() {
        return attributeList.getAttributes();
    }

    /**
     * Geeft aan of bij alle rijen waar minstens één iets ingevuld is alle andere velden ook ingevuld zijn
     */
    public boolean isAllFilled() {
        return attributeList.isAllFilled();
    }

}
