package uml.xml;

import javafx.scene.control.Alert;
import uml.dialogs.AlertBox;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Amory Hoste
 * Klasse die dient om een xml bestand in te lezen
 */

public class XMLReader {

    /**
     * Leest het XML-bestand op de gegeven locatie in en geeft een lijst terug met alle elementen
     */
    public ArrayList<Box> readfile(String location) {
        Path path = Paths.get(location);

        ArrayList<Box> boxes = new ArrayList<>();

        try (InputStream stream = Files.newInputStream(path)) {
            JAXBContext jc = JAXBContext.newInstance(Diagram.class);
            Diagram diagram = (Diagram)jc.createUnmarshaller().unmarshal(
                    stream);

            if (diagram.getItems() != null) {
                for (Box box : diagram.getItems()) {
                    boxes.add(box);
                }
            }

        } catch (JAXBException ex) {
            new AlertBox(Alert.AlertType.ERROR, "Couldn't read file", ex.getMessage()).show();
        } catch (IOException b) {
            new AlertBox(Alert.AlertType.ERROR, "File not found", b.getMessage()).show();
        }
        return boxes;
    }

}
