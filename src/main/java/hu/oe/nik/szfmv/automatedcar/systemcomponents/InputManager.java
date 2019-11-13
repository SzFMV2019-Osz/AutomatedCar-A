package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.InputPacket;

import java.awt.event.KeyEvent;

public class InputManager extends SystemComponent {

    private Control gasPedal;
    private Control breakPedal;
    private Control rightSteering;
    private Control leftSteering;
    private GearShift gearShift;
    private ACC acc;
    private int step = 4;
    private int brakeStep = step * 2;
    private InputPacket inputPacket;

    public InputManager(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        inputPacket = new InputPacket();
        virtualFunctionBus.inputPacket = inputPacket;
        gasPedal = new Control(step);
        breakPedal = new Control(brakeStep);
        rightSteering = new Control(step);
        leftSteering = new Control(step);
        gearShift = new GearShift();
        acc = new ACC(virtualFunctionBus);
    }

    @Override
    public void loop() {
        gasPedal.Listen();
        breakPedal.Listen();
        rightSteering.Listen();
        leftSteering.Listen();

        inputPacket.setGasPedalValue(gasPedal.getValue());
        inputPacket.setBreakPedalValue(breakPedal.getValue());
        inputPacket.setRightSteeringWheelValue(rightSteering.getValue());
        inputPacket.setLeftSteeringWheelValue(leftSteering.getValue());
    }

    public void HandleSyncKeys(int pressedKey, boolean trigger) {
        switch (pressedKey) {
            case KeyEvent.VK_W:
                gasPedal.Trigger(trigger);
                break;

            case KeyEvent.VK_S:
                breakPedal.Trigger(trigger);
                acc.turnOff();
                break;

            case KeyEvent.VK_D:
                rightSteering.Trigger(trigger);
                break;

            case KeyEvent.VK_A:
                leftSteering.Trigger(trigger);
                break;
        }
    }

    public void HandleAsyncKeys(int releasedKey) {
        switch (releasedKey) {
            case KeyEvent.VK_T:
                acc.TimeGapSetter();
                inputPacket.setAccTimegap(acc.ReturnTimeGap());
                break;

            case KeyEvent.VK_UP:
                acc.Plus();
                inputPacket.setAccSpeed(acc.getReferenceSpeed());
                break;

            case KeyEvent.VK_DOWN:
                acc.Minus();
                inputPacket.setAccSpeed(acc.getReferenceSpeed());
                break;

            case KeyEvent.VK_P:
                /*Todo: set parking pilot*/
                inputPacket.setParkingState();
                break;

            case KeyEvent.VK_L:
                /*Todo: set lane keeping*/
                inputPacket.setLaneKeepingState();
                break;

            case KeyEvent.VK_LEFT:
                inputPacket.setLeftSignValue(!inputPacket.getLeftSignalValue());
                break;

            case KeyEvent.VK_RIGHT:
                inputPacket.setRightSignValue(!inputPacket.getRightSignalValue());
                break;

            case KeyEvent.VK_Q:
                gearShift.Increment();
                inputPacket.setGearShiftValue(gearShift.GetCurrentState());
                break;

            case KeyEvent.VK_E:
                gearShift.Decrement();
                inputPacket.setGearShiftValue(gearShift.GetCurrentState());
                break;

            case KeyEvent.VK_O:
                acc.Resume();
                inputPacket.setAccState(acc.isOn);
                break;
        }
    }
}
