package uml.controllers;

import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uml.model.UmlModel;
import uml.views.UmlView;
import uml.views.umlbox.UMLBox;
import uml.xml.Box;

/**
 * Amory Hoste
 * Klasse die ervoor zorgt dat er boxes kunnen getekent worden door de gebruiker
 */

public class BoxDrawer {

    private UmlView view;
    private UMLBox box = null;
    private UmlModel model;

    private double startx, starty ;

    public BoxDrawer(UmlView view, UmlModel model) {
        this.view = view;
        this.model = model;

    }

    public void draw(String name) {
        view.getScene().setCursor(Cursor.CROSSHAIR);

        // Rectangle aanmaken die het scherm vult (tonen dat je kan tekenen)
        double width = view.getBoundsInParent().getWidth();
        double height = view.getBoundsInParent().getHeight();
        Rectangle rect = new Rectangle(width, height);
        rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));
        view.getChildren().add(rect);

        // Rectangle updaten naargelang scherm geresized wordt
        view.boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
            rect.setHeight(newValue.getHeight());
            rect.setWidth(newValue.getWidth());
        });

        // Wanneer er op de rectangle gedrukt wordt box beginnen tekenen en rectangle verwijderen
        rect.setOnMousePressed((MouseEvent event) -> {
            rect.getScene().setCursor(Cursor.E_RESIZE);
            startx = event.getSceneX() ;
            starty = event.getSceneY() - 70;
            box = new UMLBox(name, 50, null, null, startx, starty) ;
            view.getChildren().remove(rect);
            view.getChildren().add(box) ;
        });

        rect.setOnMouseDragged((MouseEvent event) -> {
            double endx = event.getSceneX();
            if (endx - startx > 50) {
                changebox(startx, starty, endx, box);
            }
        });

        rect.setOnMouseReleased((MouseEvent event) -> {
            box.getScene().setCursor(Cursor.DEFAULT);
            addbox(box);
            view.getChildren().remove(box);
            box = null;
        });
    }

    /**
     * Past getekende box aan
     */
    public void changebox(double startx, double starty, double endx, UMLBox b) {
        b.setLayoutX(startx) ;
        b.setLayoutY(starty) ;
        b.setBoxWidth(endx - startx);

        if (b.getWidth() < 0) {
            b.setBoxWidth(- b.getWidth());
            b.setLayoutX( b.getLayoutX() - b.getWidth());
        }

        if (b.getHeight() < 0) {
            b.setLayoutY( b.getLayoutY() - b.getHeight());
        }
    }

    /**
     * Voegt getekende box toe aan het model
     */
    public void addbox(UMLBox box) {
        Box b = new Box();
        b.setName(box.getName());
        b.setWidth(box.getWidth());
        b.setCol(box.getRow());
        b.setRow(box.getCol());
        model.addBox(b);
    }
}
