package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;

import java.util.ArrayList;
import java.util.List;

public class Ultrasound {
    /*ArrayList<Triangle> ultraSoundSensors = new ArrayList<Triangle>();
    public Ultrasound(int height, int width, int rotationDegree){
        ultraSoundSensors.add(new Triangle(0, 0, rotationDegree));
        ultraSoundSensors.add(new Triangle(0, 0, rotationDegree + 90));
        ultraSoundSensors.add(new Triangle(0, height, rotationDegree + 180));
        ultraSoundSensors.add(new Triangle(0, height, rotationDegree + 90));
        ultraSoundSensors.add(new Triangle(width, 0, rotationDegree));
        ultraSoundSensors.add(new Triangle(width, 0, rotationDegree - 90));
        ultraSoundSensors.add(new Triangle(width, height, rotationDegree + 180));
        ultraSoundSensors.add(new Triangle(width, height, rotationDegree - 90));
    }*/

    public Triangle[] sensors;
    private int currentSensor = 0;
    private List<IObject> detectedObjects = new ArrayList<>();

    public Ultrasound() {
        sensors = new Triangle[8];
        for (int i = 0; i < sensors.length; i++) {
            sensors[i] = new Triangle();
        }
    }

    public void saveTriangle(Position base, Position left, Position right) {
        sensors[currentSensor].setBase(base);
        sensors[currentSensor].setLeft(left);
        sensors[currentSensor].setRight(right);

        if(currentSensor == 7) {
            currentSensor = 0;
        } else {
            currentSensor++;
        }
    }

    public void resetDetectedObjects() {
        detectedObjects = new ArrayList<>();
    }

    public void addDetectedObjects(List<IObject> worldObjects) {
        detectedObjects.addAll(worldObjects);
    }
}
