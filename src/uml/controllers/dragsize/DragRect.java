package uml.controllers.dragsize;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.shape.Rectangle;

/**
 * Amory Hoste
 * Vierkant dat toegevoegd wordt bovenop de umlbox zodat duidelijk is dat deze geselecteerd is
 * Aan dit vierkant worden in dragsizecontrol twee cirkels toegevoegd om de breedte te herschalen
 * de wijzigingen in de positie van dit vierkant worden in dragsizecontrol ook doorgevoerd op de
 * verbonden umlbox
 */

public class DragRect extends Rectangle {

    public DragRect(Bounds bounds, DragSizeHandler dragSizeHandler) {
        super(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
        super.setArcHeight(20);
        super.setArcWidth(20);
        
        enableDrag(dragSizeHandler);
    }

    /**
     * Maak rectangle versleepbaar
     */
    private void enableDrag(DragSizeHandler dragSizeHandler) {
        Coordinaat verschil = new Coordinaat();

        // Over vierkant gaan ev2t muis
        this.setOnMouseEntered(ev2 -> {
            if (!ev2.isPrimaryButtonDown()) {
                this.getScene().setCursor(Cursor.HAND);
            }
        });

        this.setOnMouseExited(me -> {
            if (!me.isPrimaryButtonDown()) {
                this.getScene().setCursor(Cursor.DEFAULT);
            }
        });

        // Op vierkant drukken
        this.setOnMousePressed(ev2 -> {
            if (ev2.isPrimaryButtonDown()) {
                this.getScene().setCursor(Cursor.DEFAULT);
            }
            verschil.x = ev2.getX() - this.getX();
            verschil.y = ev2.getY() - this.getY();
            this.getScene().setCursor(Cursor.OPEN_HAND);
        });

        // Muis loslaten
        this.setOnMouseReleased(ev2 -> {
            if (!ev2.isPrimaryButtonDown()) {
                this.getScene().setCursor(Cursor.HAND);
            }

            double newX = this.getX();
            double newY = this.getY();
            if (dragSizeHandler != null ) {
                dragSizeHandler.handle(newX, newX, newX, newY, true);
            }

        });

        // Muis verplaatsen
        this.setOnMouseDragged(ev2 -> {
            double oldX = this.getX();
            double oldY = this.getY();

            if (ev2.getY() - verschil.y >= 0 && ev2.getX() - verschil.x >= 0) {

                this.setX(ev2.getX() - verschil.x);
                this.setY(ev2.getY() - verschil.y);

                double newX = this.getX();
                double newY = this.getY();

                if (dragSizeHandler != null && (newX != oldX || newY != oldY)) {
                    dragSizeHandler.handle(oldX, oldY, newX, newY, false);
                }
            }
        });
    }

    // Coordinaatvoorstelling
    private static class Coordinaat {
        double x, y;
    }

}
