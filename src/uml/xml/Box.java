package uml.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Amory Hoste
 * Klasse horende bij het xml bestand
 */

public class Box {

    // Name
    private String name;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // Row
    private double row;

    @XmlAttribute
    public double getRow() {
        return row;
    }

    public void setRow(double row) {
        this.row = row;
    }


    // Column
    private double col;

    @XmlAttribute
    public double getCol() {
        return col;
    }

    public void setCol(double col) {
        this.col = col;
    }


    // Width
    private double width;

    @XmlAttribute
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }


    // Relations
    private List<Relation> relations;

    @XmlElement(name="relation")
    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }


    // Attributes
    private List<Attribute> attributes;

    @XmlElement(name="attribute")
    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }


    // Operations
    private List<Operation> operations;

    @XmlElement(name="operation")
    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

}
