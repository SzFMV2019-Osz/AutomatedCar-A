package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.Car;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Powertrain;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.bind.annotation.XmlTransient;
import java.awt.geom.AffineTransform;

public class AutomatedCar extends Car {

    private static final int REFRESH_RATE = 10;
    @XmlTransient
    private static final Logger LOGGER = LogManager.getLogger();
    private Powertrain pt;

    @XmlTransient
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);
        pt = new Powertrain(virtualFunctionBus, REFRESH_RATE, x, y, (float)getRotation(),getHeight(), getWidth());
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
        this.setPosX(this.getPosX() + (int)movingVector.getX());
        this.setPosY(this.getPosY() + (int)movingVector.getY());
        setRotation(pt.getCarRotation());
    }

    @Override
    public AffineTransform getTransform(int offsetX, int offsetY) {
        AffineTransform shapeTransform = new AffineTransform();

        shapeTransform.translate(this.getPosX() - this.getReferenceX() + offsetX, this.getPosY() - this.getReferenceY() + offsetY);
        shapeTransform.rotate(Math.toRadians(this.getRotation() + 90), this.getReferenceX(), this.getReferenceY());

        return shapeTransform;
    }
}
