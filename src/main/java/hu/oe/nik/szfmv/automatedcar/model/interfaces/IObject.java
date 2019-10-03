package hu.oe.nik.szfmv.automatedcar.model.interfaces;

import java.awt.image.BufferedImage;

/**
 * A világban megtalálható objektumok közös tulajdonságait írja le.
 */
public interface IObject {

    /**
     * @return X koordináta.
     */
    int getPosX();

    /**
     * @return Y koordináta.
     */
    int getPosY();

    /**
     * Megmondja, hogy az objektum melyik layer-en helyezkedik el.
     * Grafikai szempontból fontos, milyen "sorrendben" jelennek meg az elemek.
     *
     * @return Z koordináta.
     */
    int getPosZ();

    /**
     * @return Objektum szélessége.
     */
    int getWidth();

    /**
     * @return Objektum hosszúsága.
     */
    int getHeight();

    /**
     * Az objektum elfordulását adja vissza fokban! A 0 fok észak felé néz.
     *
     * @return elfordulás szögben megadva. Tartomány: 0 - 359
     */
    double getRotation();

    /**
     * @return megjelenítendő kép
     */
    BufferedImage getImage();
}
