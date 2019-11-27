package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UltraSoundSensor extends Sensor {
    @Override
    public List<IObject> getAllSensedRelevantObjects(WorldManager currentWorld, Shape triangle, int offsetX, int offsetY) {
        List<IObject> objectsInRange = currentWorld.getAllCrashableObjectsInTriangle(triangle, offsetX, offsetY);
        List<IObject> relevantObjects = new ArrayList<>();
        for (IObject obj : objectsInRange) {
            if (obj instanceof ICrashable) {
                relevantObjects.add(obj);
            }
        }
        return relevantObjects;
    }

    @Override
    public void handleSensedObjects(List<IObject> sensedObjects) {
        //todo: do something.
    }
}
