package uml.controllers.dragsize;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.controllers.edit.EditorCompanion;
import uml.model.UmlModel;
import uml.dialogs.AlertBox;
import uml.views.umlbox.UMLBox;
import java.io.IOException;


/**
 * Amory Hoste
 * Zorgt ervoor dat een umlbox kan worden geresized en versleept
 */

public class DragSizeControl extends Group {

    // Geeft de minimale breedte aan waarnaar een box kan geresized worden
    private static final double MINWIDTH = 50;

    private UMLBox connectedBox;
    private Rectangle omhulsel;
    private UmlModel model;
    private Button button;

    /*
     * Rechtse cirkel om breedte aan te passen
     */
    private DragCircle right = new DragCircle((oldX, oldY, newX, newY, release) -> {
        double newWidth = omhulsel.getWidth() + (newX - oldX);
        if (newWidth > MINWIDTH) {
            omhulsel.setWidth(newWidth);
        }

        updateAttachmentPositions();
        resizeConnectedBox();

        if (release) {
            setModelWidth(newWidth);
        }
    });

    /*
     * Linkse cirkel om breedte aan te passen
     */
    private DragCircle left = new DragCircle((oldX, oldY, newX, newY, release) -> {
        double newWidth = omhulsel.getWidth() - (newX - oldX);
        if (newWidth > MINWIDTH) {
            omhulsel.setX(newX);
            omhulsel.setWidth(newWidth);
        }

        updateAttachmentPositions();
        resizeConnectedBox();

        if (release) {
            setModelX(newX);
            setModelWidth(newWidth);
        }
    });

    /*
     * Constructor
     */
    public DragSizeControl(UMLBox connectedBox, UmlModel model) {
        this.connectedBox = connectedBox;
        this.model = model;
        getStylesheets().add("uml/files/UMLBox.css");

        // Omhulsel aanmaken
        omhulsel = new DragRect(connectedBox.getBoundsInParent(), (oldX, oldY, newX, newY, release) -> {
            updateAttachmentPositions();
            moveConnectedBox(newX, newY);

            if (release) {
                setModelLocation(newX, newY);
            }
        });

        omhulsel.toBack();
        omhulsel.setId("selectbox");
        super.getChildren().add(omhulsel);

        // Edit knop aanmaken
        button = new Button();
        button.getStyleClass().add("icon-button");
        enableButtonClick();

        updateAttachmentPositions();
        super.getChildren().addAll(right, left, button);
    }

    /**
     * Verplaatst de verbonden umlbox naar de meegegeven locatie
     */
    private void moveConnectedBox(double newX, double newY) {
        UMLBox box = connectedBox;
        box.setRow(newY);
        box.setCol(newX);
    }

    /**
     * Past de breedte van de verbonden umlbox aan
     */
    private void resizeConnectedBox() {
        connectedBox.setBoxWidth(omhulsel.getWidth());
        moveConnectedBox(omhulsel.getX(), omhulsel.getY());
    }

    /**
     * Geeft de nieuwe locatie mee aan het model
     */
    private void setModelLocation(double newX, double newY) {
        model.setLocation(connectedBox.getName(), newX, newY);
    }

    /**
     * Geeft de nieuwe x-coordinaat
     */
    private void setModelX(double newX) {
        model.setX(connectedBox.getName(), newX);
    }

    /**
     * Geeft de nieuwe breedte mee aan het model
     */
    private void setModelWidth(double newWidth) {
        model.setWidth(connectedBox.getName(), newWidth);
    }

    /**
     * Zorgt ervoor dat de edit-knop en de twee breedte regelaars
     * op de juiste positie staan
     */
    private void updateAttachmentPositions() {
        right.setCenterX(omhulsel.getX() + omhulsel.getWidth());
        right.setCenterY(omhulsel.getY() + omhulsel.getHeight() / 2);
        left.setCenterX(omhulsel.getX());
        left.setCenterY(omhulsel.getY() + omhulsel.getHeight() / 2);
        button.setLayoutX(omhulsel.getX() + omhulsel.getWidth() + 10);
        button.setLayoutY(omhulsel.getY() + 5);
    }

    /*
     * Zorgt ervoor dat wanneer op de edit-knop gedrukt wordt een editor-venster wordt geopend
     */
    private void enableButtonClick() {
        button.setOnMouseClicked(e -> {

            // FXML inladen
            FXMLLoader loader = new FXMLLoader(
                    DragSizeControl.class.getResource("/uml/files/fxml/Editor.fxml")
            );

            BorderPane borderpane = null;

            try {
                borderpane = loader.load();
            } catch (IOException ex) {
                new AlertBox(Alert.AlertType.ERROR, "Couldn't load fxml", "Please contact the program maker").showAndWait();
            }

            // Dialog stage maken
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit " + connectedBox.getName());
            Scene scene = new Scene(borderpane);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Alles instellen
            EditorCompanion companion = loader.getController();
            companion.setModel(model);
            companion.setBox(model.getBox(connectedBox.getName()));
            companion.setDialogStage(dialogStage);
            dialogStage.initModality(Modality.APPLICATION_MODAL);


            // Dialoog tonen en wachten tot het gesloten wordt

            dialogStage.showAndWait();
        });
    }

}
