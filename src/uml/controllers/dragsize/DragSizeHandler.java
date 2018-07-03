package uml.controllers.dragsize;

/**
 * Amory Hoste
 * Interface voor actie die moet gebeuren nadat een cirkel / vierkant versleept is
 */

public interface DragSizeHandler {

    void handle(double oldX, double oldY, double newX, double newY, boolean release);

}
