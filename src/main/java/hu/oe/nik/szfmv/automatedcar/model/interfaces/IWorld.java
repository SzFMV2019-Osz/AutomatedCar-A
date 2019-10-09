package hu.oe.nik.szfmv.automatedcar.model.interfaces;

import hu.oe.nik.szfmv.automatedcar.model.World;

/**
 * {@link World} objektumnak készített interface.
 */
public interface IWorld {
    /**
     * {@link World} objektum szélességét adja vissza.
     * @return Pálya szélessége.
     */
    int getWidth();

    /**
     * {@link World} objektum magasságát adja vissza.
     * @return Pálya magassága.
     */
    int getHeight();

    /**
     * {@link World} objektum színét adja vissza.
     * @return Világ színe.
     */
    String getColor();
}
