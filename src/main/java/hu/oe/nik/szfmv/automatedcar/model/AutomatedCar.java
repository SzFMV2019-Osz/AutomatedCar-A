package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IDynamic;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutomatedCar extends WorldObject implements ICrashable, IDynamic {

    private static final Logger LOGGER = LogManager.getLogger();

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);
    }

    public void drive() {
        virtualFunctionBus.loop();

        calculatePositionAndOrientation();
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    private void calculatePositionAndOrientation() {
        //TODO it is just a fake implementation

        switch (virtualFunctionBus.samplePacket.getKey()) {
            case 0:
                this.setY(this.getPosY() - 5);
                break;
            case 1:
                this.setX(this.getPosX() + 5);
                break;
            case 2:
                this.setY(this.getPosY() + 5);
                break;
            case 3:
                this.setX(this.getPosX() - 5);
                break;
            default:
                break;
        }
    }

    @Override
    public double getWeight() {
        return 0;
    }
}