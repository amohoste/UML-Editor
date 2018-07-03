package uml.about;

import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Amory Hoste
 * Companionklasse die hoort bij het about venster
 */

public class AboutCompanion {

    public Label version;
    public Label email;
    public Label info;

    public void initialize() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Config.class);
        Config ac = (Config) jc.createUnmarshaller().unmarshal(
                        AboutCompanion.class.getResourceAsStream("/uml/files/config.xml")
                );
        info.setText(ac.getInfo());
        version.setText("Version " + ac.getVersion());
        email.setText("Made by " + ac.getEmail());
    }

}
