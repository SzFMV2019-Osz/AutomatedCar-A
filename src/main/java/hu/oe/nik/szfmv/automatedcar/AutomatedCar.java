package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.*;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;
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
    private static final int CAMERA_OFFSET = 40;

    private static final int REFRESH_RATE = 10;
    private Powertrain pt;

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(this.virtualFunctionBus);
        this.pt = new Powertrain(this.virtualFunctionBus, REFRESH_RATE, x, y, (float) this.getRotation(), this.getHeight());

        this.roadSensor = new RoadSensor();
        this.roadSensor.setPos(this.getPosX(), this.getPosY() - CAMERA_OFFSET);

        this.signSensor = new SignSensor();
        this.signSensor.setPos(this.getPosX(), this.getPosY() - CAMERA_OFFSET);
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

    public Shape checkCamera(WorldManager manager, int offsetX, int offsetY) {
        Position pointA = new Position(this.roadSensor.getPosX() + offsetX, this.roadSensor.getPosY() + offsetY);
        Position pointB = this.generateTriangleLeftPoint(this.roadSensor, offsetX, offsetY);
        Position pointC = this.generateTriangleRightPoint(this.roadSensor, offsetX, offsetY);

        AffineTransform transform = new AffineTransform();
        //transform.rotate(Math.toRadians(this.getRotation() + 90), pointA.getX(), pointA.getY()); //TODO: remove +90 after TeamA3 merge
        transform.rotate(Math.toRadians(this.getRotation() + 90), this.getPosX() + offsetX, this.getPosY() + offsetY); //TODO: remove +90 after TeamA3 merge
        Shape cameraTriangle = transform.createTransformedShape(ModelCommonUtil.generateTriangle(pointA, pointB, pointC));

        List<IObject> signs = this.signSensor.getAllSensedRelevantObjects(manager, cameraTriangle, offsetX, offsetY);
        List<IObject> roads = this.roadSensor.getAllSensedRelevantObjects(manager, cameraTriangle, offsetX, offsetY);

        if (!signs.isEmpty()) {
            System.out.println("Sings found");
        }

        if (!roads.isEmpty()) {
            System.out.println("Roads found");
        }

        return cameraTriangle;
    }

    private Position generateTriangleLeftPoint(Sensor sensor, int offsetX, int offsetY) {
        int rangeModifier = this.CAMERA_RANGE * Consts.PIXEL_PER_METERS / 10; // TODO: remove 100
        Position point = new Position(sensor.getPosX() + offsetX - this.calculateTriangleSide(rangeModifier, 30), sensor.getPosY() + offsetY - rangeModifier);
        //TODO: rotate by car rotation
        return point;
    }

    private Position generateTriangleRightPoint(Sensor sensor, int offsetX, int offsetY) {
        int rangeModifier = this.CAMERA_RANGE * Consts.PIXEL_PER_METERS / 10; // TODO: remove 100
        Position point = new Position(sensor.getPosX() + offsetX + this.calculateTriangleSide(rangeModifier, 30), sensor.getPosY() + offsetY - rangeModifier);
        //TODO: rotate by car rotation
        return point;
    }

    private int calculateTriangleSide(int b, double angle) {
        double angleInRad = Math.toRadians(angle);
        return (int) (b * Math.tan(angleInRad));
    }
}
