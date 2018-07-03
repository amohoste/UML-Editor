package uml.controllers.edit.editlists;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uml.xml.Attribute;
import uml.xml.Operation;

import java.util.ArrayList;

/**
 * Amory Hoste
 * Stelt de lijst voor waaraan verschillende rijen kunnen toegevoegd worden om attributes van operations te bewerken
 */

public class AttributePopupList extends VBox {

    private ArrayList<AttributePopupListRow> rows;

    // Geeft aan of bij alle rijen waar minstens één iets ingevuld is alle andere velden ook ingevuld zijn
    private boolean allFilled;

    public AttributePopupList() {
        // Hoofding
        HBox header = new HBox();
        Label name = new Label("Name");
        Label type = new Label("Type");
        header.getChildren().addAll(name, type);

        // Layout
        name.setPadding(new Insets(0, 0, 0, 22));
        type.setPadding(new Insets(0, 0, 0, 93));
        header.setPadding(new Insets(10, 0, 10, 0));

        super.getChildren().add(header);
        rows = new ArrayList<>();
    }

    /**
     * Voegt alle bewerkingsrijen toe + één extra lege rij
     */
    public void initialize(Operation operation) {
        if (operation != null && operation.getAttributes() != null) {
            for (Attribute attribute : operation.getAttributes()) {
                AttributePopupListRow a = new AttributePopupListRow(attribute, this, "x");
                getChildren().add(a);
                rows.add(a);
            }
        }
        AttributePopupListRow a = new AttributePopupListRow(null, this, "+");
        getChildren().add(a);
        rows.add(a);
    }

    /**
     * Geeft alle nieuwe gemaakte geldige attributen terug
     */
    public ArrayList<Attribute> getAttributes() {
        allFilled = true;
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (Object o : getChildren()) {
            if (o instanceof AttributePopupListRow) {
                Attribute a = new Attribute();
                String name = ((AttributePopupListRow) o).getNameString();
                String type = ((AttributePopupListRow) o).getTypeString();
                if (!name.equals("") && !type.equals("")) {
                    a.setName(name);
                    a.setType(type);
                    attributes.add(a);
                } else if (!name.equals("") || !type.equals("")) {
                    // Er is een rij waarbij minstens één iets is ingevuld, maar die ook lege velden heeft
                    allFilled = false;
                }
            }
        }
        return attributes;
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
    public ArrayList<AttributePopupListRow> getRows() {
        return rows;
    }

}
