package uml.views;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import uml.controllers.dragsize.DragSizeControl;
import uml.views.arrow.*;
import uml.model.UmlModel;
import uml.views.umlbox.UMLBox;
import uml.xml.Box;
import uml.xml.Relation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Amory Hoste
 * Het venster (Pane) waarop alle boxes en pijlen getekend worden
 */

public class UmlView extends Pane implements InvalidationListener {

    private UmlModel model;

    private ArrayList<Box> umlboxes;

    // Een hashmap zodat we makkelijk kunnen terugvinden bij welke naam welke umlbox hoort
    private HashMap<String, UMLBox> umlmap;

    // houdt bij welke box geslecteerd is
    private UMLBox selectedBox;

    public UmlView() {
        // zorgt ervoor dat we boxes kunnen unselecteren door op de achtergrond te klikken
        setOnMouseClicked(event -> {
            if (selectedBox != null && !(((Node) event.getTarget()).getParent() instanceof DragSizeControl)) {
                super.getChildren().removeIf(dragsize -> dragsize instanceof DragSizeControl);
                selectedBox = null;
            }
        });

        // Zorgt ervoor dat we een geselecteerde box kunnen verwijderen met de backspace toets
        super.setOnKeyPressed(e -> {
            if (selectedBox != null && e.getCode() == KeyCode.BACK_SPACE) {
                model.deleteBox(selectedBox.getName());
            }
        });
    }

    /**
     * Stel het model in waar de view naar moet luisteren
     */
    public void setModel(UmlModel model) {
        this.model = model;
        model.addListener(this);
    }

    /**
     * Laadt de gegevens van het model opnieuw in en hertekent de pijlen en boxes
     */
    @Override
    public void invalidated(Observable o) {
        umlmap = new HashMap<>();
        umlboxes = model.getBoxes();

        super.getChildren().clear();

        if (!umlboxes.isEmpty()) {
            // Boxes tekenen
            addBoxes();

            // Pijlen tekenen
            addArrows();
        }
    }

    /*
     * Voegt de boxes toe en vult ondertussen de umlmap op
     */
    private void addBoxes() {
        for (Box box : umlboxes) {
            UMLBox uml = new UMLBox(box.getName(), box.getWidth(), box.getAttributes(), box.getOperations(), box.getCol(), box.getRow());
            umlmap.put(box.getName(), uml);
            super.getChildren().add(uml);
            makeSelectable(uml);
        }
    }

    /*
     * Voegt de arrows toe
     */
    private void addArrows() {
        for (Box box : umlboxes) {
            if (box.getRelations() != null) {
                for (Relation r : box.getRelations()) {
                    UMLBox frombox = umlmap.get((box.getName()));
                    UMLBox tobox = umlmap.get(r.getWith());

                    Arrow a = ArrowTypes.valueOf(r.getType().toUpperCase()).create(frombox, tobox);
                    a.draw();

                    // Pijl laten hertekenen wanneer een van de twee verbonden boxes verandert
                    tobox.boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
                        a.calculate(frombox, tobox);
                        a.move();
                    });
                    frombox.boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
                        a.calculate(frombox, tobox);
                        a.move();
                    });

                    super.getChildren().add(a);
                }
            }
        }
        front();
    }

    /*
     * Zorgt ervoor dat een box kan worden geselcteerd
     */
    private void makeSelectable(UMLBox box) {
        box.setOnMouseClicked(event -> {
            if (selectedBox != box) {
                selectedBox = box;
                selectedBox.toFront();
                super.getChildren().removeIf(candidate -> candidate instanceof DragSizeControl);
                DragSizeControl control = new DragSizeControl(box, model);
                super.getChildren().add(control);
            }
            event.consume();
        });

    }

    /**
     * Brengt alle boxes naar voren zodat de pijlen op de achtergrond staan
     */
    private void front() {
        for (UMLBox b : umlmap.values()) {
            b.toFront();
        }
    }

    /**
     * Opsomtype dat gebruikt wordt om de juiste pijl te kunnen maken aan de hand van een ingegeven string
     */
    private enum ArrowTypes {

        ASSOCIATION {
            Arrow create(UMLBox from, UMLBox to) {
                return new AssociationArrow(from, to);
            }
        },
        INHERITANCE {
            Arrow create(UMLBox from, UMLBox to) {
                return new InheritanceArrow(from, to);
            }
        },
        REALIZATION {
            Arrow create(UMLBox from, UMLBox to) {
                return new RealizationArrow(from, to);
            }
        },
        AGGREGATION {
            Arrow create(UMLBox from, UMLBox to) {
                return new AggregationArrow(from, to);
            }
        },
        COMPOSITION {
            Arrow create(UMLBox from, UMLBox to) {
                return new CompositionArrow(from, to);
            }
        },
        DEPENDENCY {
            Arrow create(UMLBox from, UMLBox to) {
                return new DependencyArrow(from, to);
            }
        };

        abstract Arrow create(UMLBox from, UMLBox to);
    }
}
