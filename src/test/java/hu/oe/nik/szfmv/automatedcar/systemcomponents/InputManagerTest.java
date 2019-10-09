package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import org.junit.jupiter.api.Test;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import java.awt.event.KeyEvent;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.GearShift.POS;

public class InputManagerTest {
    VirtualFunctionBus bus;
    InputManager inputManager;
    final int step = 4;
    int elapsedCycles;
    int expectedControlValue;

    private void simulateElapsedCycles() {
        for (int i = 0; i < elapsedCycles; i++) {
            inputManager.loop();
        }
    }

    private void SetUpControlTest() {
        expectedControlValue = ReturnExpectedValue();
        bus = new VirtualFunctionBus();
        inputManager = new InputManager(bus);
        inputManager.HandleSyncKeys(KeyEvent.VK_UP, true);
    }

    private int ReturnExpectedValue() {
        int expectedValue = elapsedCycles * step;
        if(100 < expectedValue) {
            return 100;
        } else {
            return expectedValue;
        }
    }

    /* HandlingSyncKeys */

    @Test
    void SyncKeyPressed_pressedControlInRange() {
        int gasPedalValue;
        elapsedCycles = 5;
        SetUpControlTest();
        inputManager.HandleSyncKeys(KeyEvent.VK_UP, true);

        simulateElapsedCycles();
        gasPedalValue = bus.inputPacket.getGasPedalValue();

        assertEquals(expectedControlValue, gasPedalValue);
    }

    @Test
    void SyncKeyPressed_pressedControlAboveRange() {
        int gasPedalValue;
        elapsedCycles = 30;
        SetUpControlTest();
        inputManager.HandleSyncKeys(KeyEvent.VK_UP, true);

        simulateElapsedCycles();
        gasPedalValue = bus.inputPacket.getGasPedalValue();

        assertEquals(expectedControlValue, gasPedalValue);
    }

    @Test
    void SyncKeyReleased_releasedControlBelowRange() {
        int gasPedalValue;
        elapsedCycles = 5;
        expectedControlValue = 0;
        bus = new VirtualFunctionBus();
        inputManager = new InputManager(bus);
        inputManager.HandleSyncKeys(KeyEvent.VK_UP, false);

        simulateElapsedCycles();
        gasPedalValue = bus.inputPacket.getGasPedalValue();

        assertEquals(expectedControlValue, gasPedalValue);
    }

    @Test
    void SyncKeyPressedAndReleased_PressedControl5CyclesReleased3Cycles() {
        int gasPedalValue;
        elapsedCycles = 5;
        SetUpControlTest();

        inputManager.HandleSyncKeys(KeyEvent.VK_UP, true);
        simulateElapsedCycles();

        gasPedalValue = bus.inputPacket.getGasPedalValue();
        assertEquals(expectedControlValue, gasPedalValue);

        expectedControlValue = (elapsedCycles - 3) * step;
        elapsedCycles = 3;

        inputManager.HandleSyncKeys(KeyEvent.VK_UP, false);
        simulateElapsedCycles();

        gasPedalValue = bus.inputPacket.getGasPedalValue();
        assertEquals(expectedControlValue, gasPedalValue);
    }

    /* HandlingAsyncKeys */

    @Test
    void TIsPressed_HandleAsyncKeyIsCalled_SetAccTimeGap() {
        double accTimegap = 0;
        double expectedAccTimegap = 1.0;
        bus = new VirtualFunctionBus();
        inputManager = new InputManager(bus);

        inputManager.HandleAsyncKeys(KeyEvent.VK_T);

        accTimegap = bus.inputPacket.getAccTimeGap();

        assertEquals(expectedAccTimegap, accTimegap);

        inputManager.HandleAsyncKeys(KeyEvent.VK_T);
        inputManager.HandleAsyncKeys(KeyEvent.VK_T);

        expectedAccTimegap = 1.4;
        accTimegap = bus.inputPacket.getAccTimeGap();

        assertEquals(expectedAccTimegap, accTimegap);

        inputManager.HandleAsyncKeys(KeyEvent.VK_T);

        expectedAccTimegap = 0.8;
        accTimegap = bus.inputPacket.getAccTimeGap();

        assertEquals(expectedAccTimegap, accTimegap);
    }

    @Test
    void PlusIsPressed_AsyncKeyHandlerIsCalled_IncrementAccReferenceSpeed() {
        int accSpeed;
        int expectedAccSpeed = 60;
        bus = new VirtualFunctionBus();
        inputManager = new InputManager(bus);

        inputManager.HandleAsyncKeys(KeyEvent.VK_ADD);

        accSpeed = bus.inputPacket.getAccSpeed();

        assertEquals(expectedAccSpeed, accSpeed);
    }

    @Test
    void MinusIsPressed_AsyncKeyHandlerIsCalled_DecrementAccReferenceSpeed() {
        int accSpeed;
        int expectedAccSpeed = 40;
        bus = new VirtualFunctionBus();
        inputManager = new InputManager(bus);

        inputManager.HandleAsyncKeys(KeyEvent.VK_SUBTRACT);

        accSpeed = bus.inputPacket.getAccSpeed();

        assertEquals(expectedAccSpeed, accSpeed);
    }

    @Test
    void NumPad1IsPressed_AsyncKeyHandlerIsCalled_SwitchLeftSign() {

        boolean leftsign;
        boolean expectedLeftSign;
        bus = new VirtualFunctionBus();
        inputManager = new InputManager(bus);

        expectedLeftSign = !bus.inputPacket.getLeftSignalValue();

        inputManager.HandleAsyncKeys(KeyEvent.VK_NUMPAD1);
        leftsign = bus.inputPacket.getLeftSignalValue();

        assertEquals(expectedLeftSign, leftsign);
    }

    @Test
    void NumPad2IsPressed_AsyncKeyHandlerIsCalled_SwitchRightSign() {
        boolean rightSign;
        boolean expectedRightSign;
        bus = new VirtualFunctionBus();
        inputManager = new InputManager(bus);

        expectedRightSign = !bus.inputPacket.getRightSignalValue();

        inputManager.HandleAsyncKeys(KeyEvent.VK_NUMPAD2);
        rightSign = bus.inputPacket.getRightSignalValue();

        assertEquals(expectedRightSign, rightSign);
    }

    @Test
    void QIsPressed_AsyncKeyEventHandlerIsCalled_IncrementGearShift() {
        POS gearState;
        POS expectedGearState = POS.R;
        bus = new VirtualFunctionBus();
        inputManager = new InputManager(bus);

        inputManager.HandleAsyncKeys(KeyEvent.VK_Q);
        gearState = bus.inputPacket.getGearShiftValue();

        assertEquals(expectedGearState, gearState);
    }

    @Test
    void WIsPressed_AsyncKeyEventHandlerIsCalled_IncrementGearShift() {
        POS gearState;
        POS expectedGearState = POS.R;
        bus = new VirtualFunctionBus();
        inputManager = new InputManager(bus);

        inputManager.HandleAsyncKeys(KeyEvent.VK_Q);
        gearState = bus.inputPacket.getGearShiftValue();

        assertEquals(expectedGearState, gearState);
    }

}
