package uml.about;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Amory Hoste
 * Houdt informatie bij over de applicatie (ingelezen mbv JAXB uit config.xml)
 */

@XmlRootElement
public class Config {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
