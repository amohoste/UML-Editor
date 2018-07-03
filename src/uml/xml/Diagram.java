package uml.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Amory Hoste
 * Klasse horende bij het xml bestand
 */

@XmlRootElement
public class Diagram {

    private List<Box> items;

    @XmlElement(name="box")
    public List<Box> getItems() {
        return items;
    }

    public void setItems(List<Box> items) {
        this.items = items;
    }

}
