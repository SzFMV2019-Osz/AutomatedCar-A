package hu.oe.nik.szfmv.automatedcar.model;

/**
 * Provides an interface for the {@link World} object
 */
public interface IWorld {
    /**
     * Gets the Width of a {@link World} object
     * @return the width of the object
     */
    public int getWidth();

    /**
     * Gets the Height of a {@link World} object
     * @return the height of the object
     */
    public int getHeight();

    /**
     * Gets the color of a {@link World} object
     * @return the color of the object
     */
    public int getColor();
}
