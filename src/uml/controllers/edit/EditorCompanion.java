package uml.controllers.edit;

import javafx.scene.control.*;
import javafx.stage.Stage;
import uml.controllers.edit.editlists.*;
import uml.model.UmlModel;
import uml.dialogs.AlertBox;
import uml.xml.Box;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Amory Hoste
 * Companionklasse hordende bij editor.xml, zorgt ervoor dat de boxes kunnen bewerkt worden
 */

public class EditorCompanion {

    public TextField nameField;

    public TitledPane relationPane;
    public TitledPane attributePane;
    public TitledPane operationPane;

    private ArrayList<Box> boxes;
    private RelationList relationlist;
    private AttributeList attributeList;
    private OperationList operationList;

    private UmlModel model;
    private Box box;
    private Stage dialogStage;
    private String oldname;

    /**
     * Sla de wijzigingen op in het model
     */
    public void confirm() {

        if (!model.hasBox(nameField.getText()) || nameField.getText().equals(oldname)) {
            if (!nameField.getText().equals("")) {

                // Naam
                box.setName(nameField.getText().substring(0, 1).toUpperCase() + nameField.getText().substring(1).toLowerCase());

                // Relaties
                box.setRelations(relationlist.getRelations());

                // Attributen
                box.setAttributes(attributeList.getAttributes());

                // Operations
                box.setOperations(operationList.getOperations());

                if (relationlist.isAllFilled() && attributeList.isAllFilled() && operationList.isAllFilled()) {
                    model.updateBox(box, oldname);
                    cancel();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Some rows were only partially filled.");
                    alert.setContentText("Do you want to continue?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        model.updateBox(box, oldname);
                        cancel();
                    }
                }
            } else {
                new AlertBox(Alert.AlertType.ERROR, "Ongeldige naam", "Gelieve een geldige naam in te geven.").show();
            }
        } else {
            new AlertBox(Alert.AlertType.ERROR, "Foute naam", "Er bestaat al een box met de naam '" + nameField.getText() + "'").show();
        }
    }

    /**
     * Sluit de editor
     */
    public void cancel() {
        dialogStage.close();
    }

    /**
     * Stel de box in die de editor moet bewerken
     */
    public void setBox(Box box) {
        this.box = box;
        this.oldname = box.getName();
        nameField.setText(box.getName());
        boxes = model.getBoxes();
        initializeList();
    }

    /**
     * Stel het model van de box in
     */
    public void setModel(UmlModel model) {
        this.model = model;
    }

    /**
     * Referentie naar de stage zodat we deze kunnen sluiten met een druk op de cancel knop
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Initializeer de lijst met alle soorten bewerkingen
     */
    public void initializeList() {
        // Gebruikt in withcombo om te weten welke andere boxes er zijn
        ArrayList<String> boxnames = new ArrayList<>();
        for (Box b : boxes) {
            if (!b.getName().equals(box.getName())) {
                boxnames.add(b.getName());
            }
        }

        // Relations
        relationlist = new RelationList();
        relationlist.initialize(box, boxnames);
        relationPane.setContent(relationlist);

        // Attributes
        attributeList = new AttributeList();
        attributeList.initialize(box);
        attributePane.setContent(attributeList);

        // Operations
        operationList = new OperationList();
        operationList.initialize(box);
        operationPane.setContent(operationList);
    }

}
