package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;


import hu.oe.nik.szfmv.automatedcar.systemcomponents.GearShift.POS;

public class InputPacket implements OutgoingInputPacket {

    private int gasPedalValue;
    private int breakPedalValue;
    private int leftSteeringWheelValue;
    private int rightSteeringWheelValue;
    private POS gearShift = POS.P;
    private boolean rightSignValue = false;
    private boolean leftSignValue = false;
    private boolean accState = false;
    private boolean laneKeepingState = false;
    private boolean parkingState = false;
    private double accTimegap = 0.8;
    private int accSpeed = 50;

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

    @Override
    public int getSteeringWheelValue() {
        return rightSteeringWheelValue - leftSteeringWheelValue;
    }

    public void setGearShiftValue(POS value) {
        gearShift = value;
    }

    @Override
    public POS getGearShiftValue() {
        return gearShift;
    }

    public void setAccSpeed(int value) {
        accSpeed = value;
    }

    @Override
    public int getAccSpeed() {
        return accSpeed;
    }

    public void setAccTimegap(double value) {
        accTimegap = value;
    }

    @Override
    public boolean getLaneKeepingState() {
        return laneKeepingState;
    }


    public void setLaneKeepingState() {
        laneKeepingState = !laneKeepingState;
    }

    @Override
    public boolean getParkingState() {
        return parkingState;
    }


    public void setParkingState() {
        parkingState = !parkingState;
    }

    @Override
    public boolean getAccState() {
        return accState;
    }

    public void setAccState(boolean value) {
        accState = value;
    }


    @Override
    public double getAccTimeGap() {
        return accTimegap;
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
