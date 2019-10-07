package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public interface IPowertrain {
    void calculateMovingVector(int throttle, int brake, GearShift gearShift);
}
