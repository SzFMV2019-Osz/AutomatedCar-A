package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.exceptions.CrashException;
import hu.oe.nik.szfmv.automatedcar.model.Car;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;
import hu.oe.nik.szfmv.automatedcar.model.Camera;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.UltraSound;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.*;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.AEBState;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AutomatedCar extends Car {
    private static final Logger LOGGER = LogManager.getLogger();

    int[] carOffset; // since many sensors on the car use it, we might as well record it here
    private static final int REFRESH_RATE = 10;
    private Powertrain pt;
    private Radar radar;
    private IObject closestObject;
    private AutomatedEmergencyBrake emergencyBrake;

    @XmlTransient
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);
        pt = new Powertrain(virtualFunctionBus, REFRESH_RATE, x, y, (float)getRotation(),getHeight(),getWidth());
        radar = new Radar(virtualFunctionBus);
        this.camera = new Camera(x, y);
        this.emergencyBrake = new AutomatedEmergencyBrake(virtualFunctionBus);
    }

    public void setCarOffset(int x, int y){
        this.carOffset = new int[]{x, y};
    }

    public void drive() {

        virtualFunctionBus.loop();
        calculatePositionAndOrientation();
    }


    public void operateSensors(WorldManager manager, int xOffset, int yOffset){
        // Radar
        radar.updateSensorPosition(this);

        // the radar is a proxy connecting the model functionality with any other component
        Shape triangle = ModelCommonUtil.generateTriangle(radar.getSensorPosition(), radar.getRadarAreaLeftTip(),radar.getRadarAreaRightTip());
        radar.setDetectedObjects(manager.getAllObjectsInTriangle(triangle,xOffset,yOffset));

        // AEB
        ClosestObject closest = radar.getClosestObjectInLane();
        emergencyBrake.setClosest(closest);
        emergencyBrake.decideIfReachedSuboptimalBarrier(virtualFunctionBus.powertrainPacket.getVelocity());
        emergencyBrake.isVelocityRationalBreakingNeeded(virtualFunctionBus.powertrainPacket.getVelocity());

        //todo: detecting and signaling 70 km/h
        //System.out.println(virtualFunctionBus.emergencyBrakePacket.isAebNotOptimal() ? "AEB SUB" : "AEB OPTIMAL");
        // todo: emergency brake state
        //if(closest != null){
        //    System.out.println("CLOSEST: " + closest.getClosestObject() + " WITH DISTANCE " + radar.getClosestObjectInLane().getDistanceFromCar());
        //    System.out.println("AEB: " + virtualFunctionBus.emergencyBrakePacket.getState().toString());
        //}

    }

    public IRadar getRadar(){
        return this.radar;
    }


    public VirtualFunctionBus getVirtualFunctionBus() {
        return this.virtualFunctionBus;
    }

    private void calculatePositionAndOrientation() {
        var movingVector = virtualFunctionBus.powertrainPacket.getMovingVector();
        this.setPosX(this.getPosX() + (int)movingVector.getX());
        this.setPosY(this.getPosY() + (int)movingVector.getY());
        setRotation(pt.getCarRotation());
        this.camera.setPos(this.camera.getPosX() + (int) movingVector.getX(),
                this.camera.getPosY() + (int) movingVector.getY());

        for (int i = 0; i < ultraSounds.size(); i++) {
            ultraSounds.get(i).setPos(this.ultraSounds.get(i).getPosX() + (int) movingVector.getX(),
                    this.ultraSounds.get(i).getPosY() + (int) movingVector.getY());
        }
    }

    /**
     * Moves the car by the coordinates, DO NOT use outside error handling
     *
     * @param x
     * @param y
     */
    public void moveCarByPos(int x, int y) {
        this.setPosX(this.getPosX() + x);
        this.setPosY(this.getPosY() + y);
        this.camera.setPos(this.camera.getPosX() + x, this.camera.getPosY() + y);

        for (int i = 0; i < this.ultraSounds.size(); i++) {
            this.ultraSounds.get(i).setPos(this.ultraSounds.get(i).getPosX() + x,
                    this.ultraSounds.get(i).getPosY() + y);
        }
    }

    @Override
    public AffineTransform getTransform(int offsetX, int offsetY) {
        AffineTransform shapeTransform = new AffineTransform();

        shapeTransform.translate(this.getPosX() - this.getReferenceX() + offsetX, this.getPosY() - this.getReferenceY() + offsetY);
        shapeTransform.rotate(Math.toRadians(this.getRotation() + 90), this.getReferenceX(), this.getReferenceY());

        return shapeTransform;
    }

    public void checkCollisions(WorldManager manager, int offsetX, int offsetY) throws CrashException {
        Shape carShape = this.getPolygons(offsetX, offsetY).get(0);

        List<ICrashable> collidedObjects = manager.getAllCrashableObjectsInRectangle(carShape, offsetX, offsetY);

        if (!collidedObjects.isEmpty()) {
            throw new CrashException("Oh oh, you crashed :(");
        }
    }

    public List<List<Shape>> checkCamera(WorldManager manager, int offsetX, int offsetY) {
        return this.camera.loop(manager, this, offsetX, offsetY).stream().map(o -> o.getPolygons(offsetX, offsetY)).collect(Collectors.toList());
    }



    public Shape getCameraTriangle(int offsetX, int offetY) {
        return  this.camera.generateCameraTriangle(this, offsetX, offetY);
    }

    public List<List<Shape>> checkUltraSound(WorldManager manager, int offsetX, int offsetY) {
        List<List<Shape>> ultraSoundObjects = new ArrayList<>();
        for (int i = 0; i < ultraSounds.size(); i++) {
            List<IObject> objects = ultraSounds.get(i).loop(manager, this, offsetX, offsetY);
            ultraSoundObjects.addAll(objects.stream().map(o -> o.getPolygons(offsetX, offsetY)).collect(Collectors.toList()));
            for (int j = 0; j < objects.size(); j++) {
                Position objectPos = new Position(objects.get(j).getPosX(), objects.get(j).getPosY());
                Position carPos = new Position(this.getPosX(), this.getPosY());
                Position closestObjPos;
                if(closestObject != null) {
                    closestObjPos = new Position(closestObject.getPosX(), closestObject.getPosY());
                } else {
                    closestObjPos = new Position(objects.get(j).getPosX(), objects.get(j).getPosY());
                    closestObject = objects.get(j);
                }

                double distanceOfObject = Math.sqrt(Math.pow(carPos.getX() - objectPos.getX(),2) + Math.pow(carPos.getY() - objectPos.getY(),2));
                double distanceOfClosest = Math.sqrt(Math.pow(carPos.getX() - closestObjPos.getX(),2) + Math.pow(carPos.getY() - closestObjPos.getY(),2));

                if(distanceOfObject<distanceOfClosest) {
                    closestObject = objects.get(j);
                }
            }
        }
        //System.out.println(closestObject);
        return ultraSoundObjects;
    }

    public List<Shape> getUltraSoundTriangle(int offsetX, int offsetY) {
        List<Shape> ultraSoundShapes = new ArrayList<>();
        for (int i = 0; i < ultraSounds.size(); i++) {
            ultraSoundShapes.add(ultraSounds.get(i).generateCameraTriangle(this, offsetX, offsetY));
        }
        return ultraSoundShapes;
    }
}
