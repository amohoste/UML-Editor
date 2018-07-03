package uml.views.umlbox;

import javafx.scene.layout.VBox;
import uml.xml.Attribute;
import uml.xml.Operation;

import java.util.List;

/**
 * Amory Hoste
 * Deze klasse stelt een UMLBox voor en bevat een headercel en twee componentcells
 */

public class UMLBox extends VBox {

    // Componenten
    private HeaderCell top;
    private ComponentCell center;
    private ComponentCell bottom;

    // Eigenschappen
    private String name;
    private double row;
    private double col;
    private double width;

    public UMLBox(String name, double width, List<Attribute> attributes, List<Operation> operations, double row, double col) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.width = width;

        super.setId("maincontainer");
        super.setPrefWidth(width);

        // Header
        this.top = new HeaderCell(width, name);

        // Middenste container
        this.center = new ComponentCell(attributes);
        center.setId("innercontainer");

        // Onderste container
        this.bottom = new ComponentCell(operations);
        bottom.setId("innercontainer");

        super.getChildren().addAll(top, center, bottom);
        getStylesheets().add("uml/files/UMLBox.css");
        super.setLayoutX(row);
        super.setLayoutY(col);
    }

    public double getRow() {
        return row;
    }

    public double getCol() {
        return col;
    }

    public void setRow(double row) {
        this.row = row;
        super.setLayoutY(row);
    }

    public void setCol(double col) {
        this.col = col;
        super.setLayoutX(col);
    }

    public void setBoxWidth(double width) {
        super.setPrefWidth(width);
    }

    public String getName() {
        return name;
    }

}
