package uml.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import uml.views.umlbox.UMLBox;
import uml.xml.Box;
import uml.xml.Relation;
import uml.xml.XMLReader;
import uml.xml.XMLWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Amory Hoste
 * Stelt het model van het diagram in het mvc. patroon
 */

public class UmlModel implements Observable {

    // Lijst van boxes die kan worden opgevraagd, aangepast ...
    private ArrayList<Box> boxes = new ArrayList<>();

    // Hashmap van de boxes zodat we deze sneller kunnen opzoeken
    private HashMap<String, Box> boxmap;

    // Houdt bij of model verandert is nadat er de laatste keer is opgeslaan
    private boolean changed;

    /**
     * Geeft de ingelezen boxes terug
     */
    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    /**
     * Geeft de box terug met een bepaalde naam
     */
    public Box getBox(String name) {
        return boxmap.get(name);
    }

    public UmlModel() {
        boxmap = new HashMap<>();
        changed = false;
    }

    public boolean isChanged() {
        return changed;
    }

    /**
     * Laad een nieuw bestand in en brengt de luisteraars hiervan op de hoogte
     */
    public void loadXML(String path) {
        // Bestand inlezen en boxes invullen met ingelezen data
        XMLReader reader = new XMLReader();
        boxes = reader.readfile(path);
        changed = false;

        // Map invullen
        for (Box box : boxes) {
            boxmap.put(box.getName(), box);
        }

        // Luisteraars op de hoogte brengen
        fireInvalidationEvent();
    }

    public void saveXML(String path) {
        XMLWriter writer = new XMLWriter();
        writer.writeFile(path, boxes);
        changed = false;
    }

    /**
     * Sluit het ingeladen bestand en brengt de lusiteraars hiervan op de hoogte
     */
    public void clear() {
        boxes.clear();
        fireInvalidationEvent();
        changed = false;
    }

    /**
     * Verandert de x- en y coordinaten van een box
     */
    public void setLocation(String name, double x, double y) {
        Box box = boxmap.get(name);
        box.setCol(x);
        box.setRow(y);
        changed = true;
    }

    /**
     * Verandert de breedte van een box
     */
    public void setWidth(String name, double width) {
        Box box = boxmap.get(name);
        box.setWidth(width);
        changed = true;
    }

    /**
     * Verandert de x coordinaat van een box
     */
    public void setX(String name, double x) {
        Box box = boxmap.get(name);
        box.setCol(x);
        changed = true;
    }

    /**
     * Geeft terug of het model een box bevat met een bepaalde naam
     */
    public boolean hasBox(String name) {
        return boxmap.containsKey(name);
    }

    /**
     * Update de inhoud en relaties van een box
     */
    public void updateBox(Box box, String oldname) {
        Box removebox = boxmap.get(oldname);
        boxes.remove(removebox);
        boxes.add(box);
        boxmap.remove(oldname);
        boxmap.put(box.getName(), box);
        for (Box b : boxes) {
            List<Relation> relations = b.getRelations();
            boolean changed = false;

            if (b.getRelations() != null) {

                for (Relation rel : b.getRelations()) {
                    if (rel.getWith().equals(oldname)) {
                        changed = true;
                        rel.setWith(box.getName());
                    }
                }

                if (changed) {
                    b.setRelations(relations);
                }
            }
        }
        changed = true;
        fireInvalidationEvent();

    }

    /**
     * Verwijdert een box
     */
    public void deleteBox(String name) {
        boxes.remove(boxmap.get(name));
        boxmap.remove(name);
        for (Box box : boxes) {
            List<Relation> relations = box.getRelations();
            boolean change = false;

            if (box.getRelations() != null) {
                Iterator<Relation> iter = box.getRelations().iterator();

                while (iter.hasNext()) {
                    Relation rel = iter.next();

                    if (rel.getWith().equals(name)) {
                        change = true;
                        iter.remove();
                    }
                }

                if (change) {
                    box.setRelations(relations);
                }
            }
        }
        changed = true;
        fireInvalidationEvent();
    }

    /**
     * Voegt een box toe
     */
    public void addBox(Box box) {
        boxes.add(box);
        boxmap.put(box.getName(), box);
        changed = true;
        fireInvalidationEvent();
    }

    /**
     * Lijst van geregistreerde luisteraars.
     */
    private List<InvalidationListener> listenerList = new ArrayList<> ();

    /**
     * Breng alle luisteraars op de hoogte van een verandering van het model.
     */
    private void fireInvalidationEvent () {
        for (InvalidationListener listener : listenerList) {
            listener.invalidated(this);
        }
    }

    /**
     * Voeg een nieuwe luisteraar toe
     */
    @Override
    public void addListener(InvalidationListener listener) {
        listenerList.add(listener);
    }

    /**
     * Verwijder een luisteraar
     */
    @Override
    public void removeListener(InvalidationListener listener) {
        listenerList.remove(listener);
    }

}
