package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

import javax.xml.bind.Unmarshaller;
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
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        super.afterUnmarshal(u, parent);
        this.position.setZ(3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape() {
        this.polygon = new Ellipse2D.Float(-5, -5, 10, 10);
    }
}