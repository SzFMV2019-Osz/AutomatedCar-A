package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.AEBState;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.AEBPacket;

public class AutomatedEmergencyBrake extends SystemComponent {

    private ClosestObject closest;
    private  int AVOIDABLE_COLLISION_THRESHOLD = 1000;
    private  int IMMINENT_COLLISION_THRESHOLD = 400;
    private  int AEB_OPTIMALITY_THRESHOLD = 70;
    private boolean subOptimalReached;

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
            this.virtualFunctionBus.emergencyBrakePacket.setAebNotOptimal(subOptimalReached);
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

    public void decideIfReachedSuboptimalBarrier(int speed){
        if(speed >= AEB_OPTIMALITY_THRESHOLD){
            subOptimalReached = true;
        }
        else{
            subOptimalReached = false;
        }
    }

    public AEBState isVelocityRationalBreakingNeeded(double speed){
        double maxDeceleration = -9; // m/s^-2
        double currentVelocity = speed;
        double timeToSlowDown = currentVelocity / maxDeceleration;
        double distanceNeeded = currentVelocity * timeToSlowDown;
        if (currentVelocity == 0){
            return AEBState.NEUTRAL;
        }
        else if (distanceNeeded > closest.getDistanceFromCar() + 1){
            return AEBState.COLLISION_IMMINENT;
        }
        else if (distanceNeeded > closest.getDistanceFromCar()){
            return AEBState.COLLISION_IMMINENT;
        }
        return AEBState.NEUTRAL;
    }






}
