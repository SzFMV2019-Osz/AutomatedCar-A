package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;
import hu.oe.nik.szfmv.automatedcar.xml.converter.IntegerConverter;
import hu.oe.nik.szfmv.automatedcar.xml.converter.ObjectConverter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * World objektum, ami tartalmazza a világ modelljét.
 */
@XmlRootElement(name = "Scene")
@XmlAccessorType(XmlAccessType.FIELD)
public class World implements IWorld {
    
    @XmlAttribute(name = "width", required = true)
    @XmlJavaTypeAdapter(value = IntegerConverter.class, type = int.class)
    private int width = 0;
    
    @XmlAttribute(name = "height", required = true)
    @XmlJavaTypeAdapter(value = IntegerConverter.class, type = int.class)
    private int height = 0;
    
    @XmlElementWrapper(name = "Objects", required = true)
    @XmlElement(name = "Object")
    @XmlJavaTypeAdapter(ObjectConverter.class)
    private List<IObject> worldObjects = new ArrayList<>();
    
    @XmlAttribute(name = "color", required = false)
    private String color;

    public World() {
        // default konstruktor
    }

    /**
     * @deprecated XML beolvasás miatt nem kell
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.color = "#FFFFFF";
    }

    /**
     * @param width Világ szélessége.
     * @param height Világ magassága.
     * @param color Világ alapszíne.
     * @deprecated teszteléshez kell
     */
    public World(int width, int height, String color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /** {@inheritDoc}
     */
    public int getWidth() {
        return this.width;
    }

    /** {@inheritDoc}
     */
    public int getHeight() {
        return this.height;
    }

    /** {@inheritDoc}
     */
    public String getColor() {
        return this.color;
    }

    /**
     * @return Visszaadja a világban található elemeket.
     */
    public List<IObject> getWorldObjects() {
        return worldObjects;
    }

    /**
     * Hozzáadja a világ elemeihez az objektumot.
     */
    public void addObject(IObject o) {
        worldObjects.add(o);
    }
}
