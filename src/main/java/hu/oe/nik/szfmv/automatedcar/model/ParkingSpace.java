package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IBackground;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

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
    public void initShape(){
        switch (this.imageFileName) {
            case "parking_space_parallel":
                int parkingSpaceWidth = this.width - LEFT_BORDER - RIGHT_BORDER;
                int parkingSpaceHeight = (this.height - TOP_BORDER - BOTTOM_BORDER) / 2;
                this.polygon = new Rectangle(LEFT_BORDER, TOP_BORDER, parkingSpaceWidth, parkingSpaceHeight);
                ((Rectangle) this.polygon).add(new Rectangle(parkingSpaceHeight + TOP_BORDER, LEFT_BORDER, parkingSpaceWidth, parkingSpaceHeight));
                break;
            case "parking_90":
                break;
        }
    }
}
