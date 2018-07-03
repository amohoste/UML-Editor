package uml.views.arrow;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import uml.views.umlbox.UMLBox;

import java.awt.geom.RectangularShape;
import java.util.ArrayList;


/**
 * Amory Hoste
 * Bovenklasse - hierin worden de algemene berekeningen gedaan voor de pijl
 */

public abstract class Arrow extends Group {

    private double length;

    private double angle;

    private double x1;
    private double x2;
    private double y1;
    private double y2;

    public Arrow(UMLBox from, UMLBox to) {
        calculate(from, to);
    }

    public void calculate(UMLBox from, UMLBox to) {
        calculatePoints(from.getBoundsInParent(), to.getBoundsInParent());
        double dx = x1 - x2;
        double dy = y1 - y2;
        length = Math.sqrt(dx * dx + dy * dy);
        angle = Math.toDegrees(Math.atan(dy/dx)) + (dx < 0 ? 90 : -90);
    }

    /**
     * Berekent via de methode zoekpunt de x en y coordinaten van de pijl
     */
    private void calculatePoints(Bounds vertrekafm, Bounds aankomstafm) {

        // Data om eerste punt te berekenen
        double x1 = (vertrekafm.getMinX() + vertrekafm.getMaxX())/2;
        double y1 = (vertrekafm.getMinY() + vertrekafm.getMaxY())/2;
        double minX1 = aankomstafm.getMinX();
        double minY1 = aankomstafm.getMinY();
        double maxX1 = aankomstafm.getMaxX();
        double maxY1 = aankomstafm.getMaxY();

        // Data om tweede punt te berekenen
        double x2 = (aankomstafm.getMinX() + aankomstafm.getMaxX())/2;
        double y2 = (aankomstafm.getMinY() + aankomstafm.getMaxY())/2;
        double minX2 = vertrekafm.getMinX();
        double minY2 = vertrekafm.getMinY();
        double maxX2 = vertrekafm.getMaxX();
        double maxY2 = vertrekafm.getMaxY();

        // Coordinaatn punt 1 en 2 berekenen
        Coordinaat coordinaat1 = zoekPunt(x1, y1, minX1, minY1, maxX1, maxY1);
        Coordinaat coordinaat2 = zoekPunt(x2, y2, minX2, minY2, maxX2, maxY2);

        // X en Y Coordinaten pijl instellen
        this.x1 = coordinaat2.x;
        this.x2 = coordinaat1.x;
        this.y1 = coordinaat2.y;
        this.y2 = coordinaat1.y;
    }

    /**
     * Hulpmethode die gegeven een vertrekpunt en de afmetingen van een vierkant het punt op het vierkant
     * waar de lijn uit het vertrekpunt moet aankomen om naar het midden van het vierkant te wijzen berekent
     */
    private Coordinaat zoekPunt(double x, double y, double minX, double minY, double maxX, double maxY) {

        Coordinaat coordinaat = null;

        // Midden vierkant berekenen
        double midX = (minX + maxX) / 2;
        double midY = (minY + maxY) / 2;

        // Breedte en hoogte vierkant berekenen
        double height = (maxY - minY);
        double width = (maxX - minX);

        // Richtingscoëfficiënt berekenen
        double m = (midY - y) / (midX - x);

        // Coordinaat punt berekenen
        if (-height / 2 <= m * width / 2 && m * width / 2 <= height / 2) {
            if (x <= midX) {
                // Punt aan linkerzijde
                double minXy = m * (minX - x) + y;
                coordinaat = new Coordinaat(minX, minXy);
            } else if (x >= midX) {
                // Punt aan rechterzijde
                double maxXy = (m * (maxX - x) + y);
                coordinaat = new Coordinaat(maxX, maxXy);
            }
        } else if (-width / 2 <= (height / 2) / m && (height / 2) / m <= width / 2) {
            if (y <= midY) {
                // Punt aan bovenzijde
                double minYx = (minY - y) / m + x;
                coordinaat = new Coordinaat(minYx, minY);
            } else if (y >= midY) {
                // Punt aan onderzijde
                double maxYx = (maxY - y) / m + x;
                coordinaat = new Coordinaat(maxYx, maxY);
            }
        }

        return coordinaat;
    }

    protected double getLength() {
        return length;
    }

    protected void setLength(double length) {
        this.length = length;
    }

    protected void setAngle(double angle) {
        this.angle = angle;
    }

    protected double getAngle() {
        return angle;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public abstract void draw();

    public abstract void move();

    /**
     * Statische binnenklasse die een coordinaat voorsteld
     */
    public static class Coordinaat {

        public double x;
        public double y;

        Coordinaat(double x, double y) {
            this.x = x;
            this.y = y;
        }

    }
}
