package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.xml.converter.IntegerConverter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Osztály ami tartalmazza az objektum pozíciójával kapcsolatos információkat.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Position")
public class Position {

    @XmlAttribute(name = "x")
    @XmlJavaTypeAdapter(value = IntegerConverter.class, type = int.class)
    private int x;

    @XmlAttribute(name = "y")
    @XmlJavaTypeAdapter(value = IntegerConverter.class, type = int.class)
    private int y;

    @XmlTransient
    private int z = 0;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Visszaadja az X pozícióját az objektumnak (vízszintes elhelyezkedés, balra jobbra növekvő).
     * @return X pozíció int-ként.
     */
    public int getX() {
        return  this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * Visszaadja az Y pozícióját az objektumnak (függőleges elhelyezkedés, fentről lefele növekvő).
     * @return Y pozíció int-ként.
     */
    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Visszaadja a Z pozícióját az objektumnak (minél magasabb az érték, annál később/fentebbre kell rajzolni).
     * @return Z pozíció int-ként.
     */
    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
