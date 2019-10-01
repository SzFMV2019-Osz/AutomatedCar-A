package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

import java.awt.geom.Ellipse2D;

/**
 * Tábla alaposztály.
 */
public class Sign extends WorldObject implements IStatic, ICrashable {

    public double getWeight() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape(){
        //TODO: fix magic numbers
        this.polygon = new Ellipse2D.Float(-5, -5, 10, 10);
    }
}
