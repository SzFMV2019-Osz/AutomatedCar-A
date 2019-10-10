package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class HMI {
    //We need to know the current speed of the car. Maybe input?
    private boolean PA = false;
    private boolean laneKeeping = false;
    private boolean leftIndex = false;
    private boolean rightIndex = false;

    private GearShift shifter;
    private ACC aCc;
    private Control gasPedal;
    private Control brakePedal;
    private Control leftSteering;
    private Control rightSteering;

    public HMI() {

    }
}
