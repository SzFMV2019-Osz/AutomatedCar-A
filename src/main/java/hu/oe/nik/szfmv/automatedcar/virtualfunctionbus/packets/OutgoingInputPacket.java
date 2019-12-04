package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.GearShift.POS;

public interface OutgoingInputPacket {
    int getGasPedalValue();
    boolean getZoomIn();
    boolean getZoomOut();
    boolean getDebugOn();

    boolean getDebugCameraOn();

    boolean getDebugUltrasoundOn();

    int getBreakPedalValue();

    int getRightSteeringWheelValue();

    int getLeftSteeringWheelValue();

    int getSteeringWheelValue();

    POS getGearShiftValue();

    int getAccSpeed();

    boolean getAccState();

    boolean getParkingState();

    boolean getLaneKeepingState();

    double getAccTimeGap();

    boolean getLeftSignalValue();

    boolean getRightSignalValue();
}
