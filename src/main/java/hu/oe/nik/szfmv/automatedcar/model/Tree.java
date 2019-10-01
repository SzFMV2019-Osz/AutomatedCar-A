package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

import java.awt.geom.Ellipse2D;

/**
 * Fa objektum alaposztály.
 */
public class Tree extends WorldObject implements IStatic, ICrashable {

    public double getWeight() {
        //TODO
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape(){
        //TODO: fix magic numbers
        int x = 0 - (15 / 2);
        int y = 0 - (15 / 2 - 1);
        this.polygon = new Ellipse2D.Float(x, y, 15, 15);
    }
}
