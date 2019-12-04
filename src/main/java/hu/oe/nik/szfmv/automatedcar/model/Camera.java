package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ISensor;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Camera {
    private static final int CAMERA_RANGE = 80;
    private static final int CAMERA_OFFSET = 40;

    public Position pos = new Position();
    private List<ISensor> sensorList;

    public Camera(int x, int y) {
        this.sensorList = new ArrayList<>();


        RoadSensor roadSensor = new RoadSensor();
        roadSensor.setPos(x, y - CAMERA_OFFSET);
        this.sensorList.add(roadSensor);

        SignSensor signSensor = new SignSensor();
        signSensor.setPos(x, y - CAMERA_OFFSET);
        this.sensorList.add(signSensor);

        this.pos = new Position(x, y - CAMERA_OFFSET);
    }

    public int getPosX() {
        return this.pos.getX();
    }

    public int getPosY() {
        return this.pos.getY();
    }

    public void setPos(int x, int y) {
        this.pos.setX(x);
        this.pos.setY(y);
    }

    public List<ISensor> getSensors() {
        return this.sensorList;
    }


    public List<IObject> loop(WorldManager manager, IObject parent, int offsetX, int offsetY, double carRotation) {
        Shape cameraTriangle = this.generateCameraTriangle(parent, offsetX, offsetY);

        List<IObject> sensedObjects = new ArrayList<>();

        for (ISensor sensor : this.sensorList) {
            List<IObject> list = sensor.getAllSensedRelevantObjects(manager, cameraTriangle, offsetX, offsetY);

            if (!list.isEmpty()) {
                sensor.handleSensedObjects(list);
                
                for (IObject object : list) {
                    if (object instanceof Sign) {
                        double carRealAngle = ModelCommonUtil.getRealAngle(carRotation);
                        double real = ModelCommonUtil.getRealAngle(carRealAngle - 90);
                        double diff = real - object.getRotation();
                        
                        if (diff >= -30 && diff <= 30) {
                            sensedObjects.add(object);
                        }
                    }
                }
            }
            String sensed = "";
            sensed = sensedObjects.stream()
                                  .map((sensedObject) -> sensedObject.getImageFileName() + " | ")
                                  .reduce(sensed, String::concat);

            System.out.println("Sensed signs: " + sensed);

        }

        return sensedObjects;
    }

    public Shape generateCameraTriangle(IObject parent, int offsetX, int offsetY) {
        Position pointA = new Position(this.getPosX() + offsetX, this.getPosY() + offsetY);
        Position pointB = this.generateTriangleLeftPoint(offsetX, offsetY);
        Position pointC = this.generateTriangleRightPoint(offsetX, offsetY);

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(parent.getRotation() + 90), parent.getPosX() + offsetX, parent.getPosY() + offsetY); //TODO: remove +90 after TeamA3 merge

        return transform.createTransformedShape(ModelCommonUtil.generateTriangle(pointA, pointB, pointC));
    }

    private Position generateTriangleLeftPoint(int offsetX, int offsetY) {
        int rangeModifier = this.calculateRangeModifier();
        Position point = new Position(this.getPosX() + offsetX - this.calculateTriangleSide(rangeModifier, 30), this.getPosY() + offsetY - rangeModifier);
        return point;
    }

    private Position generateTriangleRightPoint(int offsetX, int offsetY) {
        int rangeModifier = this.calculateRangeModifier();
        Position point = new Position(this.getPosX() + offsetX + this.calculateTriangleSide(rangeModifier, 30), this.getPosY() + offsetY - rangeModifier);
        return point;
    }

    private int calculateTriangleSide(int b, double angle) {
        double angleInRad = Math.toRadians(angle);
        return (int) (b * Math.tan(angleInRad));
    }

    private int calculateRangeModifier() {
        return this.CAMERA_RANGE * Consts.PIXEL_PER_METERS;
    }
}
