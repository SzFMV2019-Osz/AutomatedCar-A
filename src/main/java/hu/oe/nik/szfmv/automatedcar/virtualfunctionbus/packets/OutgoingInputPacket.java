package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;
import  hu.oe.nik.szfmv.automatedcar.systemcomponents.GearShift.POS;

public interface OutgoingInputPacket {
    int getGasPedalValue();

    int getBreakPedalValue();

    int getRightSteeringWheelValue();

    int getLeftSteeringWheelValue();

    POS getGearShiftValue();

    boolean getLeftSignalValue();

    boolean getRightSignalValue();
}
