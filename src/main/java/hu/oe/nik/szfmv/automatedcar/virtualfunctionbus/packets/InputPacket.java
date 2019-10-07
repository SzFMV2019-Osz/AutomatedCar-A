package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.GearShift.POS;

public class InputPacket implements OutgoingInputPacket {

    private int gasPedalValue;
    private int breakPedalValue;
    private int leftSteeringWheelValue;
    private int rightSteeringWheelValue;
    private POS gearShift;
    private boolean rightSignValue = false;
    private boolean leftSignValue = false;

    public void setGasPedalValue(int value) {
        gasPedalValue = value;
    }

    @Override
    public int getGasPedalValue() {
        return gasPedalValue;
    }

    public void setBreakPedalValue(int value) {
        breakPedalValue = value;
    }

    @Override
    public int getBreakPedalValue() {
        return breakPedalValue;
    }

    public void setRightSteeringWheelValue(int value) {
        rightSteeringWheelValue = value;
    }

    @Override
    public int getRightSteeringWheelValue() {
        return rightSteeringWheelValue;
    }

    public void setLeftSteeringWheelValue(int value) {
        leftSteeringWheelValue = value;
    }

    @Override
    public int getLeftSteeringWheelValue() {
        return leftSteeringWheelValue;
    }

    public void setGearShiftValue(POS value) {
        gearShift = value;
    }

    @Override
    public POS getGearShiftValue() {
        return gearShift;
    }

    public void setLeftSignValue(boolean value) {
        leftSignValue = value;
    }

    @Override
    public boolean getLeftSignalValue() {
        return leftSignValue;
    }

    public void setRightSignValue(boolean value) {
        rightSignValue = value;
    }

    @Override
    public boolean getRightSignalValue() {
        return rightSignValue;
    }
}
