package uml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import uml.dialogs.AlertBox;
import uml.dialogs.SaveDialog;

import java.io.IOException;
import java.util.List;

/**
 * Amory Hoste
 * Hoofdklasse - maakt de scene aan en behandelt parameters uit de commandline
 */

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        List<String> args = getParameters().getRaw();

        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("files/fxml/UmlViewer.fxml")
        );

        BorderPane borderpane = null;

        try {
            borderpane = loader.load();
        } catch (IOException ex) {
            new AlertBox(Alert.AlertType.ERROR, "Couldn't load fxml","Please contact the program maker").showAndWait();
        }

        UmlViewerCompanion companion = loader.getController();

        Scene scene = new Scene(borderpane);
        stage.setScene(scene);
        stage.setTitle("UML Viewer");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("files/icon.png")));
        stage.show();

        // Zorgt ervoor dat er een waarschuwing gegeven wordt als er wijzigingen zijn aangebracht
        // die niet zijn opgeslaan
        stage.setOnCloseRequest(we -> {
            if (companion.getModel().isChanged()) {
                new SaveDialog(companion).alert();
            }
        });

        if (args.size() >= 1) {
            companion.loadFile(args.get(0));
        }

        if (args.size() == 2) {
            companion.makeSnapshot(args.get(1));
            companion.exitProgram();
        }

        // Extra parameters worden genegeerd, maar gebruiker wordt hiervan wel op de hoogte gesteld.
        if (args.size() > 2) {
            String str = "Usage: 'program-name' [file-location] [screenshot-location].\nExtra parameters will be ignored.";
            new AlertBox(Alert.AlertType.ERROR, "Invalid arguments", str).showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

