package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.SignSensor;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ISensor;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class UltraSound {
    public Position pos;
    private List<ISensor> sensorList;
    private int rotation;

    public UltraSound(int x, int y, int offsetX, int offsetY, int rotation) {
        this.sensorList = new ArrayList<>();
        this.rotation = rotation;

        UltraSoundSensor crashSensor = new UltraSoundSensor();
        crashSensor.setPos(x + offsetX, y + offsetY);
        this.sensorList.add(crashSensor);

        this.pos = new Position(x + offsetX, y + offsetY);
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

    public int getRotation() {
        return  rotation;
    }

    public List<ISensor> getSensors() {
        return this.sensorList;
    }

    public List<IObject> loop(WorldManager manager, IObject parent, int offsetX, int offsetY) {
        Shape ultraSound = this.generateCameraTriangle(parent, offsetX, offsetY);

        List<IObject> sensedObjects = new ArrayList<>();

        for (ISensor sensor : this.sensorList) {
            List<IObject> list = sensor.getAllSensedRelevantObjects(manager, ultraSound, offsetX, offsetY);

            if (!list.isEmpty()) {
                sensor.handleSensedObjects(list);
                System.out.println("UltraSound Triggered");

                sensedObjects.addAll(list);
            }
        }

        return sensedObjects;
    }

    //Todo: Create ultraSound
    public Shape generateCameraTriangle(IObject parent, int offsetX, int offsetY) {
        Position pointA = new Position(this.getPosX() + offsetX, this.getPosY() + offsetY);
        Position pointB = this.generateTriangleLeftPoint(offsetX, offsetY);
        Position pointC = this.generateTriangleRightPoint(offsetX, offsetY);

        AffineTransform transform = new AffineTransform();
        transform.translate(0, 0);
        transform.rotate(Math.toRadians(parent.getRotation() + rotation), parent.getPosX() + offsetX, parent.getPosY() + offsetY);

        return transform.createTransformedShape(ModelCommonUtil.generateTriangle(pointA, pointB, pointC));
    }

    private Position generateTriangleLeftPoint(int offsetX, int offsetY) {
        int rangeModifier = this.calculateRangeModifier();
        Position point = new Position(this.getPosX() + offsetX - this.calculateTriangleSide(rangeModifier, 50), this.getPosY() + offsetY - rangeModifier);
        return point;
    }

    private Position generateTriangleRightPoint(int offsetX, int offsetY) {
        int rangeModifier = this.calculateRangeModifier();
        Position point = new Position(this.getPosX() + offsetX + this.calculateTriangleSide(rangeModifier, 50), this.getPosY() + offsetY - rangeModifier);
        return point;
    }

    private int calculateTriangleSide(int b, double angle) {
        double angleInRad = Math.toRadians(angle);
        return (int) (b * Math.tan(angleInRad));
    }

    private int calculateRangeModifier() {
        return 30 * Consts.PIXEL_PER_METERS / 10; // TODO: remove range reduction;
    }
}
