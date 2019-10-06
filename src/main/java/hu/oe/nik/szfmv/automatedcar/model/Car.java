package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IDynamic;

import java.awt.Rectangle;

/**
 * Autó alaposztály.
 */
public class Car extends WorldObject implements ICrashable, IDynamic {

    private final int WIDTH_BORDER = 6;
    private final int HEIGHT_BORDER = 1;

    public Car() { }

    /**
     * Konstruktor manuális létrehozáshoz.
     * @param x X pozíció.
     * @param y Y pozíció.
     * @param imageFileName Kép file neve.
     */
    public Car(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        this.position.setZ(2);
    }

    public double getWeight() {
        // TODO
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape() {
        int x = 0 - (this.width / 2 - WIDTH_BORDER);
        int y = 0 - (this.height / 2 - HEIGHT_BORDER);
        this.polygon = new Rectangle(x, y, this.width - WIDTH_BORDER * 2, this.height - HEIGHT_BORDER * 2);
    }
}
