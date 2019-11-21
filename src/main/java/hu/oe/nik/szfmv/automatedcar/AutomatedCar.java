package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.Car;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.IRadar;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Powertrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Radar;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

public class AutomatedCar extends Car {

    private static final int REFRESH_RATE = 10;
    @XmlTransient
    private static final Logger LOGGER = LogManager.getLogger();
    private Powertrain pt;
    private Radar radar;

    @XmlTransient
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);
        pt = new Powertrain(virtualFunctionBus, REFRESH_RATE, x, y, (float)getRotation(),getHeight());
        radar = new Radar(virtualFunctionBus);
    }

    public void drive() {
        virtualFunctionBus.loop();
        calculatePositionAndOrientation();


    }

    public void operateSensors(WorldManager manager){
        // set radar

        radar.updateSensorPosition(this);

        // the radar is a proxy connecting the model functionality with any other component
        radar.setDetectedObjects(manager.getAllObjectsInTriangle(radar.getSensorPosition(),
                radar.getRadarAreaLeftTip(),
                radar.getRadarAreaRightTip()));
    }

    public IRadar getRadar(){
        return this.radar;
    }


    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    private void calculatePositionAndOrientation() {
        var movingVector = virtualFunctionBus.powertrainPacket.getMovingVector();
        this.setPosX(this.getPosX() + (int)movingVector.getX());
        this.setPosY(this.getPosY() + (int)movingVector.getY());
        setRotation(pt.getAutoSzoge());
    }
}
