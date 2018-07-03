package uml.dialogs;

import javafx.scene.control.Alert;

/**
 * Amory Hoste
 * Error popup
 */

public class AlertBox extends Alert {

    public AlertBox(AlertType type, String header, String message) {
        super(type);
        super.setHeaderText(header);
        super.setContentText(message);
    }
}
