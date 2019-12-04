package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Radar extends SystemComponent implements IRadar {
    public Radar(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void loop() {
    }

    private Position sensorPosition;
    private Position sensorTriangleLeftTip;
    private Position sensorTriangleRightTip;
    int sensorLength = 400;
    int baseAngle = 60;

    //"seen" object
    public List<IObject> SeenObjects;

    @Override
    public Position getSensorPosition() {
        return sensorPosition;
    }

    @Override
    public Position getRadarAreaLeftTip(){
        return sensorTriangleLeftTip;
    }

    @Override
    public Position getRadarAreaRightTip() {
        return sensorTriangleRightTip;
    }

    public void updateSensorPosition(AutomatedCar car){
        // the center of the car bumper is the same x as the car refX and hal the car refY
        // the sensor is going to be on the same layer (z) as the car
        sensorPosition = new Position(car.getReferenceX(), car.getReferenceY() - car.getHeight()/2);
        updateSensorTrianglePosition();
    }


    private void updateSensorTrianglePosition(){
        /* Calculating the positions of the sensor triangle using ANAL1 trigonometrikus szögfüggvény: tan(alpha) = a/b
           The edge of the triangle is always the exact position of the sensor body.
           Since the sensor area is 2 right-angled triangles and we know that the sensor sees in 60° (30° each),
           we know that alpha = 60 °. We also know the sensor position and the length of the central edge = 200.
           So, since tan(60) = 200/b, from here b = 200/tan(60).
         */

        Position sensorTriangleBasePoint = new Position(sensorPosition.getX(), sensorPosition.getY()-sensorLength);
        int sensorTriangleBaseHalfLength = (int)(sensorLength/Math.tan(Math.toRadians(baseAngle)));
        sensorTriangleLeftTip = new Position(sensorTriangleBasePoint.getX()-sensorTriangleBaseHalfLength,sensorTriangleBasePoint.getY());
        sensorTriangleRightTip = new Position(sensorTriangleBasePoint.getX() + sensorTriangleBaseHalfLength, sensorTriangleBasePoint.getY());
    }

    @Override
    public void setDetectedObjects(List<IObject> objects) {
        SeenObjects = objects;
    }

    @Override
    public List<IObject> getDetectedObjects() {
       return SeenObjects;
    }

    @Override
    public ClosestObject getClosestObjectInLane() {

        //If we do not found any objects yet, return null.
        if (this.SeenObjects == null) {
            return null;
        }

        WorldObject closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (IObject object : this.SeenObjects) {
            double a = Math.pow(object.getPosX() - sensorPosition.getX(), 2);
            double b = Math.pow(object.getPosY() - sensorPosition.getY(), 2);
            double distance = Math.sqrt(a + b);

            if (distance < closestDistance) {
                closestDistance = distance;
                closest = (WorldObject)object;
            }

        }
        ClosestObject closestInfo = new ClosestObject();
        closestInfo.setDistanceFromCar(closestDistance);
        closestInfo.setClosestObject(closest);

        return closestInfo;
    }
}
