package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.*;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Powertrain;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class AutomatedCar extends Car {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int CAMERA_RANGE = 80;

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
        Shape carShape = this.getPolygons(offsetX, offsetY).get(0);

        Position pointA = new Position((int) carShape.getBounds2D().getX(), (int) carShape.getBounds2D().getY());
        Position pointB = new Position((int) (carShape.getBounds2D().getX() + carShape.getBounds2D().getWidth()), (int) (carShape.getBounds2D().getY() + carShape.getBounds2D().getY()));
        List<ICrashable> collidedObjects = manager.getAllCrashableObjectsInRectangle(pointA, pointB, offsetX, offsetY);

        if (!collidedObjects.isEmpty()) {
            for (ICrashable collidedObject : collidedObjects) {
                collidedObject.crashed();
            }
        }
    }

    public Polygon checkCamera(WorldManager manager, int offsetX, int offsetY) {
        Position pointA = this.generateTriangleLeftPoint(this.roadSensor);
        Position pointB = this.generateTriangleRightPoint(this.roadSensor);
        List<IObject> roads = this.roadSensor.getAllSensedRelevantObjects(manager, pointA, pointB, offsetX, offsetY);

        return manager.generateTriangle(this.position, pointA, pointB);
    }

    private Position generateTriangleLeftPoint(Sensor sensor) {
        int rangeModifier = this.CAMERA_RANGE * Consts.PIXEL_PER_METERS / 25; // TODO: remove 100
        Position point = new Position(sensor.getPosX() - this.calculateTriangleSide(rangeModifier, 30), sensor.getPosY() - rangeModifier);
        //TODO: rotate by car rotation
        return point;
    }

    private Position generateTriangleRightPoint(Sensor sensor) {
        int rangeModifier = this.CAMERA_RANGE * Consts.PIXEL_PER_METERS / 25; // TODO: remove 100
        Position point = new Position(sensor.getPosX() + this.calculateTriangleSide(rangeModifier, 30), sensor.getPosY() - rangeModifier);
        //TODO: rotate by car rotation
        return point;
    }

    private Position rotagePositionByAngle(Position point, double angle) {
        double angleInRad = Math.toRadians(angle);
        double newX = point.getX() * Math.cos(angleInRad) - point.getY() * Math.sin(angleInRad);
        double newY = point.getX() * Math.sin(angleInRad) + point.getY() * Math.cos(angleInRad);
        return new Position((int) newX, (int) newY);
    }

    private int calculateTriangleSide(int b, double angle) {
        double angleInRad = Math.toRadians(angle);
        return (int) (b * Math.tan(angleInRad));
    }
}
