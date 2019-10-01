package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IDynamic;

import java.awt.Shape;
import java.awt.Rectangle;

/**
 * Autó alaposztály.
 */
public class Car extends WorldObject implements ICrashable, IDynamic {

    public Car() { }

    /**
     * Konstruktor manuális létrehozáshoz.
     * @param x X pozíció.
     * @param y Y pozíció.
     * @param imageFileName Kép file neve.
     */
    public Car(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        this.position.setZ(1);
    }

    public double getWeight() {
        // TODO
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape(){
        int x = 0 - (width / 2 - 6);
        int y = 0 - (height / 2 - 1);
        this.polygon = new Rectangle(x, y, this.width - 12, this.height - 2);
    }
}
