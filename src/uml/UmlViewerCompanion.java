package uml;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.controllers.BoxDrawer;
import uml.model.UmlModel;
import uml.about.AboutCompanion;
import uml.dialogs.AlertBox;
import uml.views.UmlView;
import javafx.scene.control.MenuItem;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Amory Hoste
 * Companionklasse van UmlViewer.fxml
 */

public class UmlViewerCompanion {

    // Fxml
    public UmlView umlView;

    public MenuItem saveButton;

    public MenuItem saveAsButton;

    public MenuItem exportButton;

    // Variabelen
    private UmlModel model;

    private String path;

    private BoxDrawer drawer;

    private boolean save;

    public void initialize() {
        model = new UmlModel();
        umlView.setModel(model);
        setItemsClick(false);
        drawer = new BoxDrawer(umlView, model);
    }

    /**
     * Geeft terug of er kan gesaved worden (true) of save-as (false)
     */
    public boolean isSave() {
        return save;
    }

    /**
     * Geeft het model terug (gebruikt in de main methode om bij het sluiten van de stage
     * te kunnen nagaan of er veranderingen zijn doorgevoerd)
     */
    public UmlModel getModel() {
        return model;
    }

    /**
     * Open een nieuw bestand via de filechooser
     */
    public void openFile() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = chooser.showOpenDialog(umlView.getScene().getWindow());
        if (file != null) {
            loadFile(file.getAbsolutePath());
        }
    }

    /**
     * Laadt een nieuw bestand in
     */
    public void loadFile(String path) {
        this.path = path;
        setItemsClick(true);
        if (path != null) {
            model.loadXML(path);
        }
    }

    /**
     * Slaat bestand op
     */
    public void saveFile() {
        model.saveXML(path);
    }

    /**
     * Slaat schema bestand op in nieuw bestand
     */
    public void saveAs() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = chooser.showSaveDialog(umlView.getScene().getWindow());
        if (file != null) {
            this.path = file.getAbsolutePath();
            model.saveXML(path);
            setItemsClick(true);
        }
    }

    /**
     * Slaat schema op als een png
     */
    public void export() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("png", "*.png"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = chooser.showSaveDialog(umlView.getScene().getWindow());
        if (file != null) {
            makeSnapshot(file.getAbsolutePath());
        }
    }

    /**
     * Sluit het geopende bestand
     */
    public void closeFile() {
        model.clear();
        setItemsClick(false);
    }

    /**
     * Sluit het programma af
     */
    public void exitProgram() {
        Platform.exit();
    }

    /**
     * Maakt een screenshot van het huidige venster
     */
    public void makeSnapshot(String outpath) {

        File outFile = Paths.get(outpath).toFile();
        Scene scene = umlView.getScene();
        WritableImage writableImage = scene.snapshot(null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png",
                    outFile);
        } catch (IOException d) {
            new AlertBox(Alert.AlertType.ERROR , "Screenshot Failed","Failed to make screenshot\n" + d.getMessage()).show();
        }
    }

    /**
     * Nieuwe box toevoegen
     */
    public void newBox() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create box");
        dialog.setHeaderText("Create new box");
        dialog.setContentText("Box name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if (!result.get().equals("")) {
                String name = result.get().substring(0, 1).toUpperCase() + result.get().substring(1).toLowerCase();
                if (!model.hasBox(name)) {
                    drawer.draw(name);
                } else {
                    new AlertBox(Alert.AlertType.ERROR, "Name Error", "A box with name '" + name + "' already exists.").show();
                }
            } else {
                new AlertBox(Alert.AlertType.ERROR, "Name Error", "Please enter a valid name").show();
            }
        }
    }

    /**
     * Toont dialoogvenster met informatie over het programma
     */
    public void about() {
        // FXML inladen
        FXMLLoader loader = new FXMLLoader(
                UmlViewerCompanion.class.getResource("files/fxml/About.fxml")
        );

        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (IOException ex) {
            System.out.println(ex);
            new AlertBox(Alert.AlertType.ERROR, "Couldn't load fxml", "Please contact the program maker\n" + ex.getMessage()).showAndWait();
        }

        // Dialog stage maken
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About");

        aboutStage.setScene(scene);
        aboutStage.setResizable(false);
        aboutStage.initModality(Modality.APPLICATION_MODAL);

        // Dialoog tonen en wachten tot het gesloten wordt
        aboutStage.showAndWait();

    }

    /**
     * Toont dialoogvenster met hulp om het programma te gebruiken
     */
    public void help() {
        Stage helpStage = new Stage();
        URL url = this.getClass().getResource("/uml/files/help/index.html");
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        engine.load(url.toString());
        helpStage.setScene(new Scene(webView));
        helpStage.setMinWidth(980);
        helpStage.show();
    }

    /**
     * Zorgt ervoor dat er al dan niet op de menu-items kan gedrukt worden
     */
    private void setItemsClick(boolean bool) {
        saveButton.setDisable(!bool);
        exportButton.setDisable(!bool);
        save = !bool;
    }
}
