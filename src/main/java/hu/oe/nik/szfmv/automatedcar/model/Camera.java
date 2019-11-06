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


    public Shape loop(WorldManager manager, IObject parent, int offsetX, int offsetY, double rotation) {
        Shape cameraTriangle = this.generateCameraTriangle(parent, offsetX, offsetY, rotation);

        for (ISensor sensor : this.sensorList) {
            List<IObject> list = sensor.getAllSensedRelevantObjects(manager, cameraTriangle, offsetX, offsetY);

            if (!list.isEmpty()) {
                sensor.handleSensedObjects(list);
                System.out.println("Objects found");
            }
        }

        return cameraTriangle;
    }

    private Shape generateCameraTriangle(IObject parent, int offsetX, int offsetY, double rotation) {
        Position pointA = new Position(this.getPosX() + offsetX, this.getPosY() + offsetY);
        Position pointB = this.generateTriangleLeftPoint(offsetX, offsetY);
        Position pointC = this.generateTriangleRightPoint(offsetX, offsetY);

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation + 90), parent.getPosX() + offsetX, parent.getPosY() + offsetY); //TODO: remove +90 after TeamA3 merge

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
        return this.CAMERA_RANGE * Consts.PIXEL_PER_METERS / 10; // TODO: remove range reduction;
    }
}
