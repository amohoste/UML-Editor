package uml.controllers.edit.editlists;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uml.xml.Attribute;
import uml.xml.Box;
import uml.xml.Relation;

import java.util.ArrayList;

/**
 * Amory Hoste
 * Stelt de lijst voor waaraan verschillende rijen kunnen toegevoegd worden om attributes te bewerken
 */

public class AttributeList extends VBox {

    private ArrayList<AttributeListRow> rows;

    // Geeft aan of bij alle rijen waar minstens één iets ingevuld is alle andere velden ook ingevuld zijn
    private boolean allFilled;

    public AttributeList() {
        // Hoofding
        HBox header = new HBox();
        Label scope = new Label("Scope");
        Label visibility = new Label("Visibility");
        Label name = new Label("Name");
        Label type = new Label("Type");
        header.getChildren().addAll(scope, visibility, name, type);

        // Layout
        scope.setPadding(new Insets(0, 0, 0, 12));
        visibility.setPadding(new Insets(0, 0, 0, 92));
        name.setPadding(new Insets(0, 0, 0, 66));
        type.setPadding(new Insets(0, 0, 0, 122));
        header.setPadding(new Insets(10, 0, 10, 0));

        super.getChildren().add(header);
        rows = new ArrayList<>();
    }

    /**
     * Voegt alle bewerkingsrijen toe + één extra lege rij
     */
    public void initialize(Box box) {
        if (box.getAttributes() != null) {
            for (Attribute attribute : box.getAttributes()) {
                AttributeListRow r = new AttributeListRow(attribute, this, "x");
                rows.add(r);
                getChildren().add(r);
            }
        }

        AttributeListRow r = new AttributeListRow(null, this, "+");
        rows.add(r);
        getChildren().add(r);

    }

    /**
     * Geeft alle nieuwe gemaakte geldige attributen terug
     */
    public ArrayList<Attribute> getAttributes() {
        allFilled = true;
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (AttributeListRow r : rows) {
            Attribute a = new Attribute();
            String scope = r.getScopeString();
            String visibility = r.getVisibilityString();
            String name = r.getNameString();
            String type = r.getTypeString();
            if (!scope.equals("") && !visibility.equals("") && !name.equals("") && !type.equals("")) {
                a.setScope(scope);
                a.setVisibility(visibility);
                a.setName(name);
                a.setType(type);
                attributes.add(a);
            } else if (!scope.equals("") || !visibility.equals("") || !name.equals("") || !type.equals("")) {
                // Er is een rij waarbij minstens één iets is ingevuld, maar die ook lege velden heeft
                allFilled = false;
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
    public ArrayList<AttributeListRow> getRows() {
        return rows;
    }

}
