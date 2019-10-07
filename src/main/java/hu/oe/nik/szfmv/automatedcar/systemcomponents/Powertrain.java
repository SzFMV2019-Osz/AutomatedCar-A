package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import com.github.pyknic.vector.Vec2f;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;

public class Powertrain extends SystemComponent implements IPowertrain {
    private static final int RPM_CONSTANT = 60;
    private static final int BRAKE_CONSTANT = 60;
    private static final int RESIST_FORCE_CONSTANT = 100;
    private static final int CAR_MASS = 1000;
    private static final Vec2f NULL_VECTOR = Vec2f.constant(0, 0);
    private static final Vec2f FORWARD_VECTOR = Vec2f.constant(0, 1);
    private static final Vec2f BACKWARD_VECTOR = Vec2f.constant(0, -1);
    private static final List<Double> GEAR_RATIOS = Arrays.asList(2.66, 1.78, 1.3, 1.0, 0.74, 2.9);
    private static final List<AbstractMap.SimpleEntry> INSIDE_GEAR_SHIFT_LIMITS = Arrays.asList(
            new AbstractMap.SimpleEntry(0, 6000),
            new AbstractMap.SimpleEntry(3385, 6000),
            new AbstractMap.SimpleEntry(4010, 6000),
            new AbstractMap.SimpleEntry(4356, 6000),
            new AbstractMap.SimpleEntry(4224, Integer.MAX_VALUE)
    );

    private int currentInsideGearShift = 0;

    protected Powertrain(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void calculateMovingVector() {
    }

    @Override
    public void loop() {

    }

    private Vec2f getDirectionUnitVector(GearShift gearShift) {
        switch (gearShift) {
            case R:
                return BACKWARD_VECTOR;
            case D:
                return FORWARD_VECTOR;
            default:
                return NULL_VECTOR;
        }
    }

    private void getCurrentInsideGearShift(GearShift gearShift) {
        if (gearShift == GearShift.R) {
            currentInsideGearShift = 5;
        } else {
            if ((double) INSIDE_GEAR_SHIFT_LIMITS.get(currentInsideGearShift).getKey() > virtualFunctionBus.powertrainPacket.getRPM()) {
                currentInsideGearShift--;
            } else if ((double) INSIDE_GEAR_SHIFT_LIMITS.get(currentInsideGearShift).getValue() <= virtualFunctionBus.powertrainPacket.getRPM()) {
                currentInsideGearShift++;
                virtualFunctionBus.powertrainPacket.setRPM((int) INSIDE_GEAR_SHIFT_LIMITS.get(currentInsideGearShift).getKey());
            }
        }
    }

    private void calculateRPM(int throttle, GearShift gearShift) {
        getCurrentInsideGearShift(gearShift);
        virtualFunctionBus.powertrainPacket.setRPM((int) (throttle * RPM_CONSTANT * GEAR_RATIOS.get(currentInsideGearShift)));
        getCurrentInsideGearShift(gearShift);
    }

    private Vec2f calculateBrakeForceVector(int brake, GearShift gearShift) {
        return getDirectionUnitVector(gearShift).scale(-1).scale(brake).scale(BRAKE_CONSTANT);
    }

    private Vec2f calculateTractionForceVector(int throttle, GearShift gearShift) {
        calculateRPM(throttle, gearShift);
        return getDirectionUnitVector(gearShift).scale(virtualFunctionBus.powertrainPacket.getRPM());
    }

    private Vec2f calculateSummaryForceVector(int throttle, int brake, GearShift gearShift) {
        return calculateTractionForceVector(throttle, gearShift).plus(calculateBrakeForceVector(brake, gearShift)).plus(calculateResistForceVector(gearShift));
    }

    private Vec2f calculateResistForceVector(GearShift gearShift) {
        return (getDirectionUnitVector(gearShift).scale(-1).scale(RESIST_FORCE_CONSTANT)).scale(getDirectionUnitVector(gearShift).scale(virtualFunctionBus.powertrainPacket.getVelocityVector()));
    }

    private Vec2f calculateAccelerationVector(int throttle, int brake, GearShift gearShift) {
        Vec2f summaryForce = calculateSummaryForceVector(throttle, brake, gearShift);
        return Vec2f.of(summaryForce.getX() * CAR_MASS, summaryForce.getY() * CAR_MASS);
    }
}
