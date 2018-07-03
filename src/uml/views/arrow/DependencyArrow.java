package uml.views.arrow;

import uml.views.umlbox.UMLBox;

/**
 * Amory Hoste
 */

public class DependencyArrow extends AssociationArrow {

    public DependencyArrow(UMLBox from, UMLBox to) {
        super(from , to);

        // Ervoor zorgen dat de lijn gestreept is
        super.getLine().getStrokeDashArray().addAll(10d, 8d);
    }

    public void move() {
        super.move();
    }

}
