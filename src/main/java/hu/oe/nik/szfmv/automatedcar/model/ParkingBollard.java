package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;

import java.awt.Rectangle;
import javax.xml.bind.Unmarshaller;

/**
 * Parkoló bólya alaposztály.
 */
public class ParkingBollard extends WorldObject implements IStatic, ICrashable {

    /**
     * Ennél az objektumnál más az objektum neve az XML-ben, mint a hozzá tartozó kép neve.
     * {@inheritDoc}
     */
    @Override
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.setImageFileName(Consts.RES_FILENAME_BOLLARD);
        super.afterUnmarshal(u, parent);
        this.position.setZ(2);
    }

    public double getWeight() {
        //TODO
        return 0;
    }

    @Override
    public void crashed() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape() {
        int y = (this.height / 2);
        this.polygons.add(new Rectangle(0, y, this.width, this.height / 2));
    }
}
