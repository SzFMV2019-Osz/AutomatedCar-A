package hu.oe.nik.szfmv.automatedcar.visualization.Utils;


import java.awt.*;


/**
 * Represents the color and linethinckness of a drawable object
 */
public class DrawingInfo {

    public DrawingInfo(Color color, int thickness) {
        this.color = color;
        this.thickness = thickness;
    }

    /**
     * @return the color of the object
     */
    public Color getColor() {
        return color;
    }


    /**
     * @param color the color to be used in displaying the object
     */
    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;

    /**
     * @return the thicnkess of an object borderline represented as a BasicStroke
     */
    public BasicStroke getThickness() {
        return new BasicStroke(thickness);
    }

    /**
     * @param thickness A unit of representing the thickness of the object
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    private int thickness;
}
