package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.exceptions.CrashException;
import hu.oe.nik.szfmv.automatedcar.model.Car;
import hu.oe.nik.szfmv.automatedcar.model.Camera;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.UltraSound;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Powertrain;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.bind.annotation.XmlTransient;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AutomatedCar extends Car {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int REFRESH_RATE = 10;
    private Powertrain pt;
    private IObject closestObject;

    @XmlTransient
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);
        pt = new Powertrain(virtualFunctionBus, REFRESH_RATE, x, y, (float)getRotation(),getHeight(), getWidth());
        this.camera = new Camera(x, y);
        this.ultraSounds.add( new UltraSound(x,y,-44,-104,90)); //front-left
        this.ultraSounds.add( new UltraSound(x,y,+44,-104,90)); //front-right
        this.ultraSounds.add( new UltraSound(x,y,-44,-104,-90)); //back-right
        this.ultraSounds.add( new UltraSound(x,y,+44,-104,-90)); //back left
        this.ultraSounds.add( new UltraSound(x,y,+104,-44,0)); //left side front
        this.ultraSounds.add( new UltraSound(x,y,-104,-44,0)); //left side back
        this.ultraSounds.add( new UltraSound(x,y,+104,-44,180)); //right side back
        this.ultraSounds.add( new UltraSound(x,y,-104,-44,180)); //right side front
    }

    public void drive() {
        this.virtualFunctionBus.loop();
        this.calculatePositionAndOrientation();
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
        return this.camera.loop(manager, this, offsetX, offsetY, getRotation()).stream().map(o -> o.getPolygons(offsetX, offsetY)).collect(Collectors.toList());
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
        System.out.println(closestObject);
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
