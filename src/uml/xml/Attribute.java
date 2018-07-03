package uml.xml;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.HashMap;

/**
 * Amory Hoste
 * Klasse horende bij het xml bestand
 */

public class Attribute {

    // Wordt gebruikt in de tostring methode om een type te mappen op het juiste teken
    private static final HashMap<String, String> visibilitytokens;
    static {
        visibilitytokens = new HashMap<>();
        visibilitytokens.put("public", "+");
        visibilitytokens.put("private", "-");
        visibilitytokens.put("protected", "#");
        visibilitytokens.put("package", "~");
    }


    // Scope
    private String scope;

    @XmlAttribute
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }


    // Visibility
    private String visibility;

    @XmlAttribute
    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }


    // Name
    private String name;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // Type
    private String type;

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Stringvoorstelling
    @Override
    public String toString() {
        String token;
        token = visibilitytokens.get(visibility);
        return token + name + " : " + type;
    }

}
