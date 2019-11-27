package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class ACC extends SystemComponent {
    protected int referenceSpeed = 50;

    protected boolean isOn = false;
    private static final int minValue = 30;
    private static final int maxValue = 160;
    private static final int step = 10;

    private double[] timeGap = {0.8, 1.0, 1.2, 1.4};
    private int index = 0;

    public ACC(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    public void Set(int setSpeed) {
        referenceSpeed = setSpeed;
        isOn = true;
    }

    public void Resume() {
        if (isOn == false) {
            isOn = true;
        } else {
            isOn = false;
        }
    }

    public void Plus() {
        if (referenceSpeed + step <= maxValue)
            referenceSpeed += step;
    }

    public void Minus() {
        if (referenceSpeed - step >= minValue)
            referenceSpeed -= step;
    }

    public void TimeGapSetter() {
        if (index < timeGap.length - 1) {
            ++index;
        } else {
            index = 0;
        }

    }

    public double ReturnTimeGap() {
        return timeGap[index];
    }

    public int getReferenceSpeed() {
        return referenceSpeed;
    }

    void turnOn() {
        virtualFunctionBus.inputPacket.setAccState(true);
        virtualFunctionBus.inputPacket.setAccSpeed(selectNewAccSpeed());
    }

    void turnOff() {
        virtualFunctionBus.inputPacket.setAccState(false);
    }

    private int selectNewAccSpeed() {
        var currentVelocity = virtualFunctionBus.powertrainPacket.getVelocity();
        if (currentVelocity >= 30 && currentVelocity <= 160) {
            return currentVelocity;
        } else {
            return virtualFunctionBus.inputPacket.getAccSpeed();
        }
    }

    @Override
    public void loop() {

    }
}
