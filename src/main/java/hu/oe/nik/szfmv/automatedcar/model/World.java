package hu.oe.nik.szfmv.automatedcar.model;

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
 * World object containing the Model of the world
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
        // default konstruktor
    }

    /**
     * Initializes a new instance of the {@link World} class
     * @param width Width of the {@link World}
     * @param height Height of t he {@link World}
     * @deprecated
     */
    public World(int width, int height){
        this.width = width;
        this.height = height;
        this.color = "#FFFFFF";
    }

    /**
     * Initializes a new instance of the {@link World} class
     * @param width Width of the {@link World} object
     * @param height Height of the {@link World} object
     * @param color Color of the {@link World} object
     */
    public World(int width, Integer height, String color) {
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
    @Override
    public int getHeight() {
        return this.height;
    }

    /** {@inheritDoc}
     */
    @Override
    public String getColor() { return this.color; }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    /**
     * Adds an object to the virtual world.
     *
     * @param o {@link WorldObject} to be added to the virtual world
     */
    public void addObjectToWorld(WorldObject o) {
        worldObjects.add(o);
    }

    /**
     * Adds an object to the virtual world.
     *
     * @param type the type of {@link WorldObject}
     * @param posX X position
     * @param posY Y position
     * @param m11 Distance from top left corner
     * @param m12 Distance from top right corner
     * @param m21 Distance from bottom left corner
     * @param m22 Distance from bottom right corner
     */
    public void addObject(String type, int posX, int posY, double m11, double m12, double m21, double m22){
        this.worldObjects.add(new WorldObject(type, posX, posY, m11, m12, m21, m22));
    }
}
