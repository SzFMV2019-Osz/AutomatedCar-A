package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.Car;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.bind.annotation.XmlTransient;

public class AutomatedCar extends Car {

    @XmlTransient
    private static final Logger LOGGER = LogManager.getLogger();

    @XmlTransient
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
                this.setPosY(this.getPosY() - 5);
                break;
            case 1:
                this.setPosX(this.getPosX() + 5);
                break;
            case 2:
                this.setPosY(this.getPosY() + 5);
                break;
            case 3:
                this.setPosX(this.getPosX() - 5);
                break;
            default:
                break;
        }
    }
}
