package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;

public class ClosestObject {
    private double distanceFromCar;

    public IObject getClosestObject() {
        return closestObject;
    }

    public void setClosestObject(IObject closestObject) {
        this.closestObject = closestObject;
    }

    IObject closestObject;
    public double getDistanceFromCar() {
        return distanceFromCar;
    }

    public void setDistanceFromCar(double distanceFromCar) {
        this.distanceFromCar = distanceFromCar;
    }
}
