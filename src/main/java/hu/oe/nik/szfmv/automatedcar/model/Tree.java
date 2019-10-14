package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

import javax.xml.bind.Unmarshaller;
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
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        super.afterUnmarshal(u, parent);
        this.position.setZ(4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape() {
        int x = this.width / 2 -(15 / 2);
        int y = this.height / 2 - (15 / 2);
        this.polygons.add(new Ellipse2D.Float(x, y, 15, 15));
    }
}
