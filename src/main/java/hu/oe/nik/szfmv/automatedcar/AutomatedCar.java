package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.Car;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.RoadSensor;
import hu.oe.nik.szfmv.automatedcar.model.SignSensor;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Powertrain;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.geom.AffineTransform;
import java.util.List;

public class AutomatedCar extends Car {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int REFRESH_RATE = 10;
    private Powertrain pt;

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(this.virtualFunctionBus);
        this.pt = new Powertrain(this.virtualFunctionBus, REFRESH_RATE, x, y, (float) this.getRotation(), this.getHeight());

        this.roadSensor = new RoadSensor();
        this.roadSensor.setPos(this.getPosX(), this.getPosY());

        this.signSensor = new SignSensor();
        this.signSensor.setPos(this.getPosX(), this.getPosY());
    }

    public void drive() {
        this.virtualFunctionBus.loop();
        this.calculatePositionAndOrientation();
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return this.virtualFunctionBus;
    }

    private void calculatePositionAndOrientation() {
        var movingVector = this.virtualFunctionBus.powertrainPacket.getMovingVector();
        this.setPosX(this.getPosX() + (int) movingVector.getX());
        this.setPosY(this.getPosY() + (int) movingVector.getY());
        this.setRotation(this.pt.getAutoSzoge());

        this.roadSensor.setPos(this.roadSensor.getPosX() + (int) movingVector.getX(),
                this.roadSensor.getPosY() + (int) movingVector.getY());
        this.signSensor.setPos(this.signSensor.getPosX() + (int) movingVector.getX(),
                this.signSensor.getPosY() + (int) movingVector.getY());
    }

    @Override
    public AffineTransform getTransform(int offsetX, int offsetY) {
        AffineTransform shapeTransform = new AffineTransform();

        shapeTransform.translate(this.getPosX() - this.getReferenceX() + offsetX, this.getPosY() - this.getReferenceY() + offsetY);
        shapeTransform.rotate(Math.toRadians(this.getRotation() + 90), this.getReferenceX(), this.getReferenceY());

        return shapeTransform;
    }

    public void checkCollisions(WorldManager manager, int offsetX, int offsetY) {
        Position pointA = new Position(this.getPosX() - this.getReferenceX(), this.getPosY() - this.getReferenceY());
        Position pointB = new Position(pointA.getX() + this.getWidth(), pointA.getY() + this.getHeight());

        List<IObject> collidedObjects = manager.getAllCrashableObjectsInRectangle(pointA, pointB, offsetX, offsetY);

        if (!collidedObjects.isEmpty()) {
            for (IObject collidedObject : collidedObjects) {
                Class c = collidedObject.getClass();
                // (ICrashable) collidedObject.crashed();
            }
        }
    }
}
