package uml.controllers.edit.editlists;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uml.xml.Attribute;
import uml.xml.Box;
import uml.xml.Operation;

import java.util.ArrayList;

/**
 * Amory Hoste
 * Stelt de lijst voor waaraan verschillende rijen kunnen toegevoegd worden om operations te bewerken
 */

public class OperationList extends VBox {

    private ArrayList<OperationListRow> rows;

    // Geeft aan of bij alle rijen waar minstens één iets ingevuld is alle andere velden ook ingevuld zijn
    private boolean allFilled;

    public OperationList() {
        // Hoofding
        HBox header = new HBox();
        Label scope = new Label("Scope");
        Label visibility = new Label("Visibility");
        Label attributes = new Label("Attributes");
        Label name = new Label("Name");
        Label type = new Label("Type");
        header.getChildren().addAll(scope, visibility, attributes, name, type);

        // Layout
        scope.setPadding(new Insets(0, 0, 0, 12));
        visibility.setPadding(new Insets(0, 0, 0, 92));
        attributes.setPadding(new Insets(0, 0, 0, 65));
        name.setPadding(new Insets(0, 0, 0, 13));
        type.setPadding(new Insets(0, 0, 0, 93));
        header.setPadding(new Insets(10, 0, 10, 0));

        super.getChildren().add(header);
        rows = new ArrayList<>();
    }

    /**
     * Voegt alle bewerkingsrijen toe + één extra lege rij
     */
    public void initialize(Box box) {
        if (box.getOperations() != null) {
            for (Operation operation : box.getOperations()) {
                OperationListRow o = new OperationListRow(operation, this, "x");
                getChildren().add(o);
                rows.add(o);
            }
        }
        OperationListRow o = new OperationListRow(null, this, "+");
        getChildren().add(o);
        rows.add(o);
    }

    /**
     * Geeft alle nieuwe gemaakte geldige operations terug
     */
    public ArrayList<Operation> getOperations() {
        allFilled = true;
        ArrayList<Operation> operations = new ArrayList<>();
        for (OperationListRow o : rows) {
            Operation op = new Operation();
            String scope = o.getScopeString();
            String visibility = o.getVisibilityString();
            String name = o.getNameString();
            String type = o.getTypeString();
            ArrayList<Attribute> attrs = o.getAttributes();
            // Als er in de attributeslist iets niet volledig ingevuld is
            if (! o.isAllFilled()) {
                allFilled = false;
            }
            if (!scope.equals("") && !visibility.equals("") && !name.equals("") && !type.equals("")) {
                op.setScope(scope);
                op.setVisibility(visibility);
                op.setName(name);
                op.setType(type);
                op.setAttributes(attrs);
                operations.add(op);
            } else if (!scope.equals("") || !visibility.equals("") || !name.equals("") || !type.equals("")) {
                // Er is een rij waarbij minstens één iets is ingevuld, maar die ook lege velden heeft
                allFilled = false;
            }
        }
        return operations;
    }

    /**
     * Geeft aan of bij alle rijen waar minstens één iets ingevuld is alle andere velden ook ingevuld zijn
     */
    public boolean isAllFilled() {
        return allFilled;
    }

    /**
     * Geeft een lijst met alle bewerkingsrijen terug
     */
    public ArrayList<OperationListRow> getRows() {
        return rows;
    }

}
