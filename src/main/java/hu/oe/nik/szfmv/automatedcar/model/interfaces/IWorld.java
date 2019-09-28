package hu.oe.nik.szfmv.automatedcar.model.interfaces;

import hu.oe.nik.szfmv.automatedcar.model.World;

/**
 * Provides an interfaces for the {@link World} object
 */
public interface IWorld {
    /**
     * Gets the Width of a {@link World} object
     * @return the width of the object
     */
    int getWidth();

    /**
     * Gets the Height of a {@link World} object
     * @return the height of the object
     */
    int getHeight();

    /**
     * Gets the color of a {@link World} object
     * @return the color of the object
     */
    String getColor();
}
