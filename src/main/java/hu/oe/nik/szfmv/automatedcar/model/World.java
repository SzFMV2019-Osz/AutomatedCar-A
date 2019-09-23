package hu.oe.nik.szfmv.automatedcar.model;

import java.util.ArrayList;
import java.util.List;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;

/**
 * World object containing the Model of the world
 */
public class World implements IWorld {
    private int width = 0;
    private int height = 0;
    private List<WorldObject> worldObjects = new ArrayList<>();
    private int color;

    /**
     * Initializes a new instance of the {@link World} class
     * @param width Width of the {@link World}
     * @param height Height of t he {@link World}
     * @deprecated
     */
    public World(int width, int height){
        this.width = width;
        this.height = height;
        this.color = 0xEEEEEE;
    }

    /**
     * Initializes a new instance of the {@link World} class
     * @param width Width of the {@link World} object
     * @param height Height of the {@link World} object
     * @param color Color of the {@link World} object
     */
    public World(int width, int height, int color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /** {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /** {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /** {@inheritDoc}
     */
    @Override
    public int getColor() { return this.color; }

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
