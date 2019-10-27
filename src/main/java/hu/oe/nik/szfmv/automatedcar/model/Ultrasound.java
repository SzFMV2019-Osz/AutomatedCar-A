package hu.oe.nik.szfmv.automatedcar.model;

import java.util.ArrayList;

public class Ultrasound {
    ArrayList<Triangle> ultraSoundSensors = new ArrayList<Triangle>();
    public Ultrasound(int height, int width, int rotationDegree){
        ultraSoundSensors.add(new Triangle(0, 0, rotationDegree));
        ultraSoundSensors.add(new Triangle(0, 0, rotationDegree + 90));
        ultraSoundSensors.add(new Triangle(0, height, rotationDegree + 180));
        ultraSoundSensors.add(new Triangle(0, height, rotationDegree + 90));
        ultraSoundSensors.add(new Triangle(width, 0, rotationDegree));
        ultraSoundSensors.add(new Triangle(width, 0, rotationDegree - 90));
        ultraSoundSensors.add(new Triangle(width, height, rotationDegree + 180));
        ultraSoundSensors.add(new Triangle(width, height, rotationDegree - 90));
    }
}
