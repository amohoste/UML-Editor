package uml.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Amory Hoste
 * Klasse horende bij het xml bestand
 */

public class Relation {

    // Type
    private String type;

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    // With
    private String with;

    @XmlAttribute
    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

}
