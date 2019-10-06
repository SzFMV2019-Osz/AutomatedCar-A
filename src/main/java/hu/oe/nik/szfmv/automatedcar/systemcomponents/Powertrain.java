package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class Powertrain extends SystemComponent implements IPowertrain {
    protected Powertrain(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void CalculateMovingVector() {
    }

    @Override
    public void loop() {

    }
}
