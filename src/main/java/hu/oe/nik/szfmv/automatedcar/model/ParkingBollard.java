package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

import javax.xml.bind.Unmarshaller;

public class ParkingBollard extends WorldObject implements IStatic {

    /**
     * Ennél az objektumnál már az objektum neve az XML-ben, mint a hozzá tartozó kép neve.
     * {@inheritDoc}
     */
    @Override
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.setImageFileName("bollard");
        super.afterUnmarshal(u, parent);
    }
}
