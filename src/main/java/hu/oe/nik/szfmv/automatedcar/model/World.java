package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
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
 * World objektum ami tartalmazza a világ modeljét.
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
    private List<WorldObject> worldObjects = new ArrayList<>();
    
    @XmlAttribute(name = "color", required = false)
    private String color;

    public World() {
    }

    /**
     * @deprecated
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
     * Visszaadja a világban levő összes objektumot.
     * @return Egy lista ami tartalmazza az összes világban levő objektumot.
     */
    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    /**
     * Hozzáad egy objektumot a virtuális világhoz.
     * @param o {@link IObject} amit hozzá kell adni a világhoz.
     */
    public void addObject(WorldObject o) {
        worldObjects.add(o);
    }
}
