package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IBackground;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

import javax.xml.bind.Unmarshaller;
import java.awt.Rectangle;

/**
 * Gyalogos átkelőhely alaposztály.
 */
public class Crosswalk extends WorldObject implements IStatic, IBackground {

    private final int WIDTH_BORDER = 4;
    private final int HEIGHT_BORDER = 4;

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
    public void initShape(){
        int x = 0 - (width / 2 - WIDTH_BORDER);
        int y = 0 - (height / 2 - HEIGHT_BORDER);
        this.polygon = new Rectangle(x, y, this.width - WIDTH_BORDER * 2, this.height - HEIGHT_BORDER * 2);
    }
}
