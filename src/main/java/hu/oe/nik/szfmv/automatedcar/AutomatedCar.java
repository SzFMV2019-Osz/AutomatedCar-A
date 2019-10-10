package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Powertrain;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutomatedCar extends WorldObject {

    private static final int REFRESH_RATE = 10;
    private static final Logger LOGGER = LogManager.getLogger();
    private Powertrain pt;

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);
        pt = new Powertrain(virtualFunctionBus, REFRESH_RATE, x, y, getRotation(),getHeight());
    }

    public void drive() {
        virtualFunctionBus.loop();
        calculatePositionAndOrientation();
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    private void calculatePositionAndOrientation() {
        var movingVector = virtualFunctionBus.powertrainPacket.getMovingVector();
        x += movingVector.getX();
        y += movingVector.getY();
        setRotation(pt.getAutoSzoge());
    }
}
