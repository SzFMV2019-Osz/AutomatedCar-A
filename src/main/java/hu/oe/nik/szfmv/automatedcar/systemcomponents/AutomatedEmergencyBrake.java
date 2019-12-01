package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.AEBState;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.AEBPacket;

public class AutomatedEmergencyBrake extends SystemComponent {

    private ClosestObject closest;
    private  int AVOIDABLE_COLLISION_THRESHOLD = 1000;
    private  int IMMINENT_COLLISION_THRESHOLD = 400;

    public void setClosest(ClosestObject closest) {
        this.closest = closest;
    }

    public AutomatedEmergencyBrake(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        this.virtualFunctionBus.registerComponent(this);
        this.virtualFunctionBus.emergencyBrakePacket = new AEBPacket();
        this.virtualFunctionBus.emergencyBrakePacket.setState(AEBState.NEUTRAL);
    }

    @Override
    public void loop() {
        if(closest != null){
            this.virtualFunctionBus.emergencyBrakePacket.setState(calculateCollsionState());
        }
    }

    private AEBState calculateCollsionState(){
        double distance = closest.getDistanceFromCar();
        if(distance > AVOIDABLE_COLLISION_THRESHOLD)
            return AEBState.NEUTRAL;

        if (distance < AVOIDABLE_COLLISION_THRESHOLD && distance > IMMINENT_COLLISION_THRESHOLD)
            return AEBState.COLLISION_AVOIDABLE;

        return AEBState.COLLISION_IMMINENT;
    }






}
