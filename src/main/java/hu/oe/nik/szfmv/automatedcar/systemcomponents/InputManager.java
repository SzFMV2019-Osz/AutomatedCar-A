package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.InputPacket;

import java.awt.event.KeyEvent;

public class InputManager extends SystemComponent{

    private Control gasPedal;
    private Control breakPedal;
    private Control rightSteering;
    private Control leftSteering;
    private GearShift gearShift;
    private ACC acc;
    private int step = 4;
    private InputPacket inputPacket;

    public InputManager(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        inputPacket = new InputPacket();
        virtualFunctionBus.inputPacket = inputPacket;
        gasPedal = new Control(step);
        breakPedal = new Control(step);
        rightSteering = new Control(step);
        leftSteering = new Control(step);
        gearShift = new GearShift();
        acc = new ACC();
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

    public void HandleSyncKeys(int pressedKey, boolean trigger)
    {
        switch(pressedKey)
        {
            case KeyEvent.VK_UP :
                gasPedal.Trigger(trigger);
                break;

            case KeyEvent.VK_DOWN :
                breakPedal.Trigger(trigger);
                break;

            case KeyEvent.VK_RIGHT :
                rightSteering.Trigger(trigger);
                break;

            case KeyEvent.VK_LEFT :
                leftSteering.Trigger(trigger);
                break;
        }
    }

    public void HandleAsyncKeys(int releasedKey) {
        switch(releasedKey)
        {
            case KeyEvent.VK_T :
                acc.TimeGapSetter();
                break;

            case KeyEvent.VK_PLUS:
                acc.Plus();
                break;

            case KeyEvent.VK_MINUS:
                acc.Minus();
                break;

            case KeyEvent.VK_P:
                /*Todo: set parking pilot*/
                break;

            case KeyEvent.VK_L:
                /*Todo: set lane keeping*/
                break;

            case KeyEvent.VK_NUMPAD1:
                inputPacket.setRightSignValue(!inputPacket.getRightSignalValue());
                break;

            case KeyEvent.VK_NUMPAD2:
                inputPacket.setLeftSignValue(!inputPacket.getLeftSignalValue());
                break;

            case KeyEvent.VK_Q:
                gearShift.Increment();
                inputPacket.setGearShiftValue(gearShift.GetCurrentState());
                break;

            case KeyEvent.VK_W:
                gearShift.Decrement();
                inputPacket.setGearShiftValue(gearShift.GetCurrentState());
                break;
        }
    }
}
