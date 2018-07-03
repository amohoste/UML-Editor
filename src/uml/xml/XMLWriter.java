package uml.xml;

import javafx.scene.control.Alert;
import uml.dialogs.AlertBox;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Amory Hoste
 * Klasse die dient om naar een xml-bestand te schrijven
 */

public class XMLWriter {

    /**
     * Leest het XML-bestand op de gegeven locatie in en geeft een lijst terug met alle elementen
     */
    public void writeFile(String location, ArrayList<Box> boxes) {
        Diagram diagram = new Diagram();
        diagram.setItems(boxes);

        Path path = Paths.get(location);

        try (OutputStream stream = Files.newOutputStream(path)) {
            JAXBContext jc = JAXBContext.newInstance(Diagram.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(diagram, stream);
        } catch (IOException | JAXBException ex) {
            new AlertBox(Alert.AlertType.ERROR, "Failed writing file", ex.getMessage()).show();
        }
    }

}
