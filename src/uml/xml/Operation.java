package uml.xml;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Amory Hoste
 * Klasse horende bij het xml bestand
 */

public class Operation extends Attribute {

    // Attributes
    private List<Attribute> attributes;

    @XmlElement(name="attribute")
    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }


    // Stringvoorstelling
    @Override
    public String toString() {
        if (attributes != null && attributes.size() != 0) {
            // Als de operation attributen bevat moeten we deze nog bij de stringvoorstelling voegen

            // Eerste attribute inlezen en toevoegen aan de in te voegen string
            Attribute a = attributes.get(0);
            String atstring = "(" + a.getName() + " : " + a.getType();

            // Andere attributen inlezen indien er nog zijn
            for (int i = 1; i < attributes.size(); i++) {
                a = attributes.get(i);
                atstring += ", " + a.getName() + " : " + a.getType();
            }

            // In te voegen string afsluiten met een haakje
            atstring += ")";

            // De tostring van attribute oproepen en de attributen aan deze string toevoegen
            String tmp = super.toString();
            int index = tmp.indexOf(':') - 1;

            // String teruggeven
            return tmp.substring(0, index) + atstring + tmp.substring(index);

        } else {
            // Als de Operation geen attributen bevat volstaat het de tostring van attribute op te roepen
            return super.toString();
        }
    }

}
