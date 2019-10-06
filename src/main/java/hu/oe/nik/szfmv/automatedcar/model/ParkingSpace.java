package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IBackground;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;

import javax.xml.bind.Unmarshaller;
import java.awt.Rectangle;

/**
 * Parkolóhely alaposztály.
 */
public class ParkingSpace extends WorldObject implements IStatic, IBackground {
    private final int LEFT_BORDER = 4;
    private final int RIGHT_BORDER = 14;
    private final int TOP_BORDER = 10;
    private final short BOTTOM_BORDER = 4;

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
        switch (this.imageFileName) {
            case Consts.RES_IDENTIFIER_PARKINGSPACE_PARALLEL:
                this.initParallelShape();
                break;
            case Consts.RES_IDENTIFIER_PARKINGSPACE_90:
                // TODO: implement parking space 90
                throw new UnsupportedOperationException("Not implemented yet");
                //break;
        }
    }

    private void initParallelShape() {
        int parkingSpaceWidth = this.width - LEFT_BORDER - RIGHT_BORDER;
        int parkingSpaceHeight = (this.height - TOP_BORDER - BOTTOM_BORDER) / 2;
        this.polygon = new Rectangle(LEFT_BORDER, TOP_BORDER, parkingSpaceWidth, parkingSpaceHeight);
        ((Rectangle) this.polygon).add(new Rectangle(parkingSpaceHeight + TOP_BORDER, LEFT_BORDER, parkingSpaceWidth, parkingSpaceHeight));
    }
}
