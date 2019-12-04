package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingPilot {
    private boolean leftIndicator = false;
    private boolean rightIndicator = false;
    int distance;
    int carSize;
    boolean isOn = false;
    VirtualFunctionBus functionBus;
    Point finish;
    Position pos;
    AutomatedCar car;
    Powertrain dummy;
    int otherY;
    double rotation;
    public ParkingPilot(AutomatedCar car){
        this.carSize = car.getHeight();
        this.functionBus = car.getVirtualFunctionBus();
        this.car=car;
        finish=new Point();
        pos=new Position();
        dummy=new Powertrain(functionBus, 10, car.getPosX(), car.getPosY(), (float)car.getRotation(),car.getHeight(), car.getWidth());

    }
    public VirtualFunctionBus getFunctionbus(){return functionBus;}
    public void ParkingPilotManagement(){

        leftIndicator= functionBus.inputPacket.getLeftSignalValue();
        rightIndicator = functionBus.inputPacket.getRightSignalValue();
        SetParkingPilotOn();
        CheckingForParkingSpace(leftIndicator, rightIndicator);
        if(distance > carSize * 1.5) {
            System.out.println("Parking place is sufficient");
        }
}


    public boolean GetParkingPilotOn(){return isOn;}
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
        if(pointList.size() == 2) {

            int currentdistance = Math.abs(pointList.get(0).getPosY() - pointList.get(1).getPosY()) - carSize;
            if (currentdistance >= distance) {
                pos.setX(pointList.get(0).getPosX());
                pos.setY(pointList.get(1).getPosY() - distance / 2);
                if (car.getRotation() > 0) {
                    finish.y = pointList.get(1).getPosY();
                    otherY = pointList.get(0).getPosY();
                } else {
                    otherY = pointList.get(0).getPosY();
                    finish.y = pointList.get(1).getPosY();
                }
                finish.x = pointList.get(0).getPosX() + car.getWidth() / 2;
                distance=currentdistance;
            }

        }
        else{
            if(!isOn) {
                    distance = 0;
            }
        }

        //System.out.print(distance);
    }
    public void AutomaticParkin2() {
        if(distance > carSize * 1.5) {
        if(car.getRotation()<=0) {
            if ((car.getPosY() - carSize / 2) > otherY) {
                functionBus.inputPacket.setGasPedalValue(10);
            }
            else {
                functionBus.inputPacket.setGearShiftValue(GearShift.POS.R);
                if(car.getPosY()>(otherY-carSize/2-distance/2)) {
                    functionBus.inputPacket.setRightSteeringWheelValue(100);
                    functionBus.inputPacket.setLeftSteeringWheelValue(0);
                    functionBus.inputPacket.setGasPedalValue(100);

                }
                else if(car.getPosY()<(otherY+carSize-distance/2)&&car.getPosY()<finish.y+carSize+20){
                    functionBus.inputPacket.setLeftSteeringWheelValue(100);
                    functionBus.inputPacket.setRightSteeringWheelValue(100);
                    functionBus.inputPacket.setGasPedalValue(50);
                }
            }
            System.out.println(car.getRotation());
           /* if (functionBus.inputPacket.getGearShiftValue() == GearShift.POS.D) {
                if (car.getPosY() > otherY + carSize + 20) {
                    functionBus.inputPacket.setGasPedalValue(10);
                } else {
                    functionBus.inputPacket.setGearShiftValue(GearShift.POS.R);
                }
            } else if (functionBus.inputPacket.getGearShiftValue() == GearShift.POS.R) {
                if (car.getPosY() < (otherY + distance / 2 + carSize / 2)) {
                    functionBus.inputPacket.setGasPedalValue(90);
                    functionBus.inputPacket.setRightSteeringWheelValue(40);

                } else if ((car.getPosY() < (finish.y - carSize + 20)) && car.getPosX() > (otherY + distance / 2 + carSize / 2)) {
                    functionBus.inputPacket.setGasPedalValue(90);
                    functionBus.inputPacket.setLeftSteeringWheelValue(40);
                } else {
                    functionBus.inputPacket.setGearShiftValue(GearShift.POS.D);
                }
            }*/
        }
         else   if (car.getRotation() > 0) {
                if ((car.getPosY() - carSize / 2) < finish.y) {
                    functionBus.inputPacket.setGasPedalValue(10);
                } else {
                    functionBus.inputPacket.setGearShiftValue(GearShift.POS.R);
                    if(car.getPosY() < (finish.y +carSize)) {
                        functionBus.inputPacket.setGasPedalValue(20);
                        functionBus.inputPacket.setLeftSteeringWheelValue(100);

                }

               /* if (functionBus.inputPacket.getGearShiftValue() == GearShift.POS.D) {
                    if (car.getPosY() < finish.y - carSize/2-20) {

                        dummy.calculateMovingVector(functionBus.inputPacket);

                    } else {
                        functionBus.inputPacket.setGearShiftValue(GearShift.POS.R);
                    }
                } */
            /*    if{
                    if (car.getPosY() < (finish.y +car)) {
                        functionBus.inputPacket.setGasPedalValue(20);
                        functionBus.inputPacket.setLeftSteeringWheelValue(100);
                }
            }else  if(functionBus.inputPacket.getGearShiftValue() == GearShift.POS.R) {
                if (car.getPosY() > (finish.y - distance / 2)) {
                    functionBus.inputPacket.setGasPedalValue(20);
                    functionBus.inputPacket.setLeftSteeringWheelValue(100);
                } else if ((finish.y - distance / 2) > car.getPosY() && car.getPosY() > (otherY + carSize * 1.8)) {
                    functionBus.inputPacket.setGasPedalValue(20);
                    functionBus.inputPacket.setRightSteeringWheelValue(100);
                } else {
                    functionBus.inputPacket.setGearShiftValue(GearShift.POS.D);
                }
            }*/
/*                    while (car.getPosY() > otherY+carSize+20) {
                        functionBus.inputPacket.setGasPedalValue(10);
                        dummy.calculateMovingVector(functionBus.inputPacket);
                        //car.moveCarByPos((int) functionBus.powertrainPacket.getMovingVector().getX(), (int) functionBus.powertrainPacket.getMovingVector().getY());
                        car.drive();*/
            }

        }

        }
}}

/*                functionBus.inputPacket.setGearShiftValue(GearShift.POS.D);
                functionBus.inputPacket.setGasPedalValue(10);
                functionBus.inputPacket.setLeftSteeringWheelValue(10);
                dummy.calculateMovingVector(functionBus.inputPacket);

                car.drive();*/
/*
            }
        }
    }
        else if(car.getRotation()<=0&&functionBus.inputPacket.getGearShiftValue()==GearShift.POS.D){

                functionBus.inputPacket.setGearShiftValue(GearShift.POS.D);
                functionBus.inputPacket.setGasPedalValue(0);
                while (car.getPosY() > otherY+carSize+20) {
                    functionBus.inputPacket.setGasPedalValue(10);
                    dummy.calculateMovingVector(functionBus.inputPacket);
                    //car.moveCarByPos((int) functionBus.powertrainPacket.getMovingVector().getX(), (int) functionBus.powertrainPacket.getMovingVector().getY());
                    car.drive();
                }

                functionBus.inputPacket.setGearShiftValue(GearShift.POS.R);
                while(car.getPosY()<(otherY+distance/2+carSize/2)){
                    functionBus.inputPacket.setGasPedalValue(90);
                    functionBus.inputPacket.setRightSteeringWheelValue(40);
                    car.drive();
                }
                while(car.getPosY()<(finish.y-carSize+20)){
                    functionBus.inputPacket.setGasPedalValue(90);
                    functionBus.inputPacket.setLeftSteeringWheelValue(40);
                    car.drive();
                }
                functionBus.inputPacket.setGasPedalValue(0);





    }}
    public void AutomaticParking(){
        rotation=car.getRotation();
        if(car.getRotation()>0&&functionBus.inputPacket.getGearShiftValue()==GearShift.POS.D) {
            do{
                functionBus.inputPacket.setGearShiftValue(GearShift.POS.D);
                functionBus.inputPacket.setGasPedalValue(0);
                while (car.getPosY() < finish.y-20) {
                    functionBus.inputPacket.setGasPedalValue(10);
                    dummy.calculateMovingVector(functionBus.inputPacket);
                    //car.moveCarByPos((int) functionBus.powertrainPacket.getMovingVector().getX(), (int) functionBus.powertrainPacket.getMovingVector().getY());
                    car.drive();
                }

            functionBus.inputPacket.setGearShiftValue(GearShift.POS.R);
            while(car.getPosY()>(finish.y-distance/2+carSize/3)){
                functionBus.inputPacket.setGasPedalValue(90);
                functionBus.inputPacket.setLeftSteeringWheelValue(40);
                car.drive();
            }
            while(car.getPosY()>(otherY+carSize*1.8)){
                functionBus.inputPacket.setGasPedalValue(90);
                functionBus.inputPacket.setRightSteeringWheelValue(40);
                car.drive();
            }
            functionBus.inputPacket.setGasPedalValue(0);
            car.setRotation(rotation);
        }while(Math.abs(car.getPosX()-finish.x)>10);
            while(car.getPosY()<(finish.y-distance/2)){
                functionBus.inputPacket.setGearShiftValue(GearShift.POS.D);
                functionBus.inputPacket.setGasPedalValue(10);
                functionBus.inputPacket.setLeftSteeringWheelValue(10);
                dummy.calculateMovingVector(functionBus.inputPacket);

                car.drive();
            }
        }
        else if(car.getRotation()<=0&&functionBus.inputPacket.getGearShiftValue()==GearShift.POS.D){
            do{
                functionBus.inputPacket.setGearShiftValue(GearShift.POS.D);
                functionBus.inputPacket.setGasPedalValue(0);
                while (car.getPosY() > otherY+carSize+20) {
                    functionBus.inputPacket.setGasPedalValue(10);
                    dummy.calculateMovingVector(functionBus.inputPacket);
                    //car.moveCarByPos((int) functionBus.powertrainPacket.getMovingVector().getX(), (int) functionBus.powertrainPacket.getMovingVector().getY());
                    car.drive();
                }

                functionBus.inputPacket.setGearShiftValue(GearShift.POS.R);
                while(car.getPosY()<(otherY+distance/2+carSize/2)){
                    functionBus.inputPacket.setGasPedalValue(90);
                    functionBus.inputPacket.setRightSteeringWheelValue(40);
                    car.drive();
                }
                while(car.getPosY()<(finish.y-carSize+20)){
                    functionBus.inputPacket.setGasPedalValue(90);
                    functionBus.inputPacket.setLeftSteeringWheelValue(40);
                    car.drive();
                }
                functionBus.inputPacket.setGasPedalValue(0);



        }while(Math.abs(car.getPosX()-finish.x)>10);

        }
    }

}
*/


