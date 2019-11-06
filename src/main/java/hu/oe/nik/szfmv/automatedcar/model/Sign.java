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
        int x = this.width / 2 - 5;
        int y = this.height / 2 - 5;
        this.polygons.add(new Ellipse2D.Float(x,y, 10, 10));
    }
}
