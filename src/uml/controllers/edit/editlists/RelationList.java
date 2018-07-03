package uml.controllers.edit.editlists;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uml.xml.Box;
import uml.xml.Relation;

import java.util.ArrayList;


/**
 * Amory Hoste
 * Stelt de lijst voor waaraan verschillende rijen kunnen toegevoegd worden om relations te bewerken
 */

public class RelationList extends VBox {

    private ArrayList<RelationListRow> rows;

    // Geeft aan of bij alle rijen waar minstens één iets ingevuld is alle andere velden ook ingevuld zijn
    private boolean allFilled;

    public RelationList() {
        // Hoofding
        HBox header = new HBox();
        Label type = new Label("Type");
        Label with = new Label("With");
        header.getChildren().addAll(type, with);

        // Layout
        type.setPadding(new Insets(0, 0, 0, 12));
        with.setPadding(new Insets(0, 0, 0, 255));
        header.setPadding(new Insets(10, 0, 10, 0));

        super.getChildren().add(header);
        rows = new ArrayList<>();
    }

    /**
     * Voegt alle bewerkingsrijen toe + één extra lege rij
     */
    public void initialize(Box box, ArrayList<String> boxnames) {
        if (box.getRelations() != null) {
            for (Relation relation : box.getRelations()) {
                RelationListRow r = new RelationListRow(relation, boxnames, this, "x");
                getChildren().add(r);
                rows.add(r);
            }
        }
        RelationListRow r = new RelationListRow(null, boxnames, this, "+");
        getChildren().add(r);
        rows.add(r);
    }

    /**
     * Geeft alle nieuwe gemaakte geldige relations terug
     */
    public ArrayList<Relation> getRelations() {
        allFilled = true;
        ArrayList<Relation> relations = new ArrayList<>();
        for (RelationListRow o : rows) {
                Relation r = new Relation();
                String with = o.getWithString();
                String type = o.getTypeString();
                if (!with.equals("") && !type.equals("")) {
                    r.setWith(with);
                    r.setType(type);
                    relations.add(r);
                } else if(!with.equals("") || !type.equals("")) {
                    // Er is een rij waarbij minstens één iets is ingevuld, maar die ook lege velden heeft
                    allFilled = false;
                }
        }
        return relations;
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
    public ArrayList<RelationListRow> getRows() {
        return rows;
    }
}





