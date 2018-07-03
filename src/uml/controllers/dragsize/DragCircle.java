package uml.controllers.dragsize;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * Amory Hoste
 * Cirkeltje dat toegevoegd wordt aan bijde kanten van het vierkant
 * boven de umlbox waarmee de breedte kan bewerkt worden
 */

public class DragCircle extends Circle {
    public DragCircle( DragSizeHandler dragSizeHandler) {
        super(0, 0, 5);
        Color color = Color.GOLD;
        setStroke(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);
        setFill(color.deriveColor(1, 1, 1, 0.5));
        enableDrag(dragSizeHandler);
    }

    /**
     * Maak cirkel versleepbaar
     */
    private void enableDrag(DragSizeHandler dragSizeHandler) {

        // Houdt het verschil bij tussen het begin en het einde van de sleepoperatie
        Verschil verschil = new Verschil();

        // Drukken op cirkel
        this.setOnMousePressed(ev1 -> {
            verschil.x = this.getCenterX() - ev1.getX();
            this.getScene().setCursor(Cursor.H_RESIZE);
        });

        // Cirkel loslaten
        this.setOnMouseReleased(ev1 -> {
            if (!ev1.isPrimaryButtonDown()) {
                this.getScene().setCursor(Cursor.DEFAULT);
            }

            double newX = this.getCenterX();

            if (dragSizeHandler != null ) {
                dragSizeHandler.handle(newX, this.getCenterY(), newX, this.getCenterY(), true);
            }

        });

        // Cirkel bewegen
        this.setOnMouseDragged(ev1 -> {
            double oldX = this.getCenterX();

            double newX = ev1.getX() + verschil.x;
            if ( newX > 0 && newX < this.getScene().getWidth()) {
                this.setCenterX(newX);
            }

            newX = this.getCenterX();

            if (dragSizeHandler != null && (newX != oldX)) {
                dragSizeHandler.handle(oldX, this.getCenterY(), newX, this.getCenterY(), false);
            }
        });

        // Over cirkel gaan met muis
        this.setOnMouseEntered(ev1 -> {
            if (!ev1.isPrimaryButtonDown()) {
                this.getScene().setCursor(Cursor.H_RESIZE);
            }
        });

        // van cirkel gaan met muis
        this.setOnMouseExited(ev1 -> {
            if (!ev1.isPrimaryButtonDown()) {
                this.getScene().setCursor(Cursor.DEFAULT);
            }
        });

    }

    // Verschil x coordinaat
    private static class Verschil {
        double x;
    }
}
