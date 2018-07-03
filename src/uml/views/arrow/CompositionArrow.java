package uml.views.arrow;

import javafx.scene.paint.Color;
import uml.views.umlbox.UMLBox;

/**
 * Amory Hoste
 */

public class CompositionArrow extends AggregationArrow {

    public CompositionArrow(UMLBox from, UMLBox to) {
        super(from , to);

        // De vulling van het kopje op zwart instellen
        super.getHead().setFill(Color.BLACK);
    }

    public void move() {
        super.move();
    }

}
