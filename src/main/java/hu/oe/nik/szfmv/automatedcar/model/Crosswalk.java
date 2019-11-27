package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IBackground;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

import java.awt.Rectangle;
import javax.xml.bind.Unmarshaller;

/**
 * Gyalogos átkelőhely alaposztály.
 */
public class Crosswalk extends WorldObject implements IStatic, IBackground {

    private static final int WIDTH_BORDER = 4;
    private static final int HEIGHT_BORDER = 4;

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        super.afterUnmarshal(u, parent);
        this.position.setZ(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape() {
        this.polygons.add(new Rectangle(WIDTH_BORDER, HEIGHT_BORDER, this.width - WIDTH_BORDER * 2, this.height - HEIGHT_BORDER * 2));
    }
}
