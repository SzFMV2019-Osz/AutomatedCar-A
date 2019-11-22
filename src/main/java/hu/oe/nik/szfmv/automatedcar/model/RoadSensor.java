package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class RoadSensor extends Sensor {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IObject> getAllSensedRelevantObjects(WorldManager currentWorld, Shape triangle,
                                                     int offsetX, int offsetY) {
        List<IObject> objectsInRange = currentWorld.getAllObjectsInTriangle(triangle, offsetX, offsetY);
        List<IObject> relevantObjects = new ArrayList<>();
        for (IObject obj : objectsInRange) {
            if (obj instanceof Road) {
                relevantObjects.add(obj);
            }
        }
        return relevantObjects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSensedObjects(List<IObject> sensedObjects) {
        for (IObject sensedObject : sensedObjects) {
            // TODO feldolgozás
        }
    }
}
