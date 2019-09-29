package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;
import hu.oe.nik.szfmv.automatedcar.model.utility.CommonMessages;

import javax.xml.bind.Unmarshaller;

/**
 * Parkoló bólya alaposztály.
 */
public class ParkingBollard extends WorldObject implements IStatic, ICrashable {

    /**
     * Ennél az objektumnál már az objektum neve az XML-ben, mint a hozzá tartozó kép neve.
     * {@inheritDoc}
     */
    @Override
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.setImageFileName(CommonMessages.RES_FILENAME_BOLLARD);
        super.afterUnmarshal(u, parent);
    }

    public double getWeight() {
        //TODO
        return 0;
    }
}
