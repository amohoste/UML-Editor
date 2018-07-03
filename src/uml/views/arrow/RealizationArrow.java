package uml.views.arrow;

import uml.views.umlbox.UMLBox;

/**
 * Amory Hoste
 */

public class RealizationArrow extends InheritanceArrow {

    public RealizationArrow(UMLBox from, UMLBox to) {
        super(from , to);

        // Zorgen dat de lijn gestreept is
        super.getLine().getStrokeDashArray().addAll(10d, 8d);
    }

    public void move() {
        super.move();
    }

}
