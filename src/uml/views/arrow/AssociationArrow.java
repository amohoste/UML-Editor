package uml.views.arrow;

import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import uml.views.umlbox.UMLBox;

import java.util.ArrayList;

/**
 * Amory Hoste
 */

public class AssociationArrow extends Arrow {

    private Path line;

    private Polyline head;

    public AssociationArrow(UMLBox from, UMLBox to) {
        super(from, to);

        // Lijn aanmaken van punt 1 naar punt 2
        line = new Path();

        // Kopje aanmaken en zorgen dat dit juist staat
        head = new Polyline();
        move();
    }

    public Path getLine() {
        return line;
    }

    public Shape getHead() {
        return head;
    }

    public void draw() {
        super.getChildren().addAll(line, head);
    }

    public void move() {
        double x1 = super.getX1();
        double x2 = super.getX2();
        double y1 = super.getY1();
        double y2 = super.getY2();

        if (line.getElements() != null) {
            line.getElements().clear();
        }
        line.getElements().add(new MoveTo(x1, y1));
        line.getElements().add(new LineTo(x2, y2));

        if (head.getPoints() != null) {
            head.getPoints().clear();
        }
        head.getPoints().setAll(x2 - 10, y2 + 20, x2, y2, x2 + 10, y2 + 20);
        head.getTransforms().clear();
        head.getTransforms().add(new Rotate(super.getAngle(), x2, y2));
    }

}
