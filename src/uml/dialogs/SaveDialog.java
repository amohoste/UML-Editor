package uml.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uml.Main;
import uml.UmlViewerCompanion;

import java.util.Optional;

/**
 * Amory Hoste
 * Dialoog die wordt weergegeven bij sluiten als er wijzigingen zijn aangebracht die niet zijn opgeslaan
 */

public class SaveDialog extends Alert {

    private ButtonType save;
    private UmlViewerCompanion companion;

    public SaveDialog(UmlViewerCompanion companion) {
        super(AlertType.CONFIRMATION);
        this.companion = companion;

        // Tekst instellen
        super.setTitle("Save changes");
        super.setHeaderText("Do you want to save the changes you made?");
        super.setContentText("Your changes will be lost if you don't save them");

        // Knoppen aanmaken en toevoegen
        save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        super.getButtonTypes().setAll(save, close);
    }

    /**
     * Toont de dialoog en slaat het bestand op indien nodig
     */
    public void alert() {
        Optional<ButtonType> result = super.showAndWait();
        if (result.get() == save){
            if (!companion.isSave()) {
                companion.saveFile();
            } else {
                companion.saveAs();
            }
        }
    }
}
