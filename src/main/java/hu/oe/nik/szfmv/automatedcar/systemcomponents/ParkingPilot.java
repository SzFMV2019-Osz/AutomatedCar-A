package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import java.util.ArrayList;
import java.util.List;

public class ParkingPilot {
    private boolean leftIndicator = false;
    private boolean rightIndicator = false;
    int distance;
    int carSize;
    boolean isOn = false;
    VirtualFunctionBus functionBus;
    public ParkingPilot(int carSize, VirtualFunctionBus functionBus ){
        this.carSize = carSize;
        this.functionBus = functionBus;
    }

    public void ParkingPilotManagement(){
        leftIndicator= functionBus.inputPacket.getLeftSignalValue();
        rightIndicator = functionBus.inputPacket.getRightSignalValue();
        SetParkingPilotOn();
        CheckingForParkingSpace(leftIndicator, rightIndicator);
        if(isOn && distance > carSize * 1.5){
            System.out.println("Parking place is sufficient");
        }
    }

    public void SetParkingPilotOn(){
        isOn = functionBus.inputPacket.getParkingState();
    }

    public void CheckingForParkingSpace(boolean leftIndicatorOn, boolean rightIndicatorOn){
        if(functionBus.ultraSoundPacket.getUltraSoundObjects() != null){
            if(leftIndicatorOn){
                CalculateDistance(functionBus.ultraSoundPacket.getUltraSoundObjects().get(4), functionBus.ultraSoundPacket.getUltraSoundObjects().get(5));
            }
            else if(rightIndicator){
                CalculateDistance(functionBus.ultraSoundPacket.getUltraSoundObjects().get(6), functionBus.ultraSoundPacket.getUltraSoundObjects().get(7));
            }
        }
    }

    public void CalculateDistance(List<IObject> objList1, List<IObject> objList2){
        ArrayList<IObject> pointList = new ArrayList();
        pointList.addAll(objList2);
        for (int i = 0; i < objList1.size() ; i++) {
            if(!pointList.contains(objList1.get(i))){
                pointList.add(objList1.get(i));
            }
        }
        //add carWidth/2 to distance
        if(pointList.size() == 2){
            distance = Math.abs(pointList.get(0).getPosY() - pointList.get(1).getPosY() - carSize) ;
        }
        else{
            distance = 0;
        }

        System.out.print(distance);
    }

    public void AutomaticParking(){

    }
}
