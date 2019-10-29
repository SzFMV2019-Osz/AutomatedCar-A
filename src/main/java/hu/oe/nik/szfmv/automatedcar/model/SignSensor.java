package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;

import java.util.ArrayList;
import java.util.List;

public class SignSensor extends Sensor {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IObject> getAllSensedRelevantObjects(WorldManager currentWorld, Position pointB, Position pointC,
            int offsetX, int offsetY) {
        List<IObject> objectsInRange = currentWorld.getAllObjectsInTriangle(pos, pointB, pointC, offsetX, offsetY);
        List<IObject> relevantObjects = new ArrayList<>();
        for (IObject obj : objectsInRange) {
            if (obj instanceof Sign) {
                relevantObjects.add(obj);
            }
        }
        return relevantObjects;
    }
}
