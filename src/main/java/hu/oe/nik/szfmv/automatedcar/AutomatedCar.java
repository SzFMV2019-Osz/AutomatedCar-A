package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutomatedCar extends WorldObject {

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
                this.position.setY(this.position.getY() - 5);
                break;
            case 1:
                this.position.setX(this.position.getX() + 5);
                break;
            case 2:
                this.position.setY(this.position.getY() + 5);
                break;
            case 3:
                this.position.setX(this.position.getX() - 5);
                break;
            default:
                break;
        }
    }
}
