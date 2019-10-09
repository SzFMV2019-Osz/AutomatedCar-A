package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import com.github.pyknic.vector.Vec2f;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.InputPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.PowertrainPacket;

import java.util.Arrays;
import java.util.List;

public class Powertrain extends SystemComponent {
    private static final int RPM_CONSTANT = 60;
    private static final int BRAKE_CONSTANT = 60;
    private static final int RESIST_FORCE_CONSTANT = 100;
    private static final int WHEELBASE = 130;
    private static final int CAR_WIDTH = 90;
    private static final float CAR_MASS = 1000.0F;
    private static final Vec2f NULL_VECTOR = Vec2f.constant(0, 0);
    private static final Vec2f FORWARD_VECTOR = Vec2f.constant(0, 1);
    private static final Vec2f BACKWARD_VECTOR = Vec2f.constant(0, -1);
    private static final List<Double> GEAR_RATIOS = Arrays.asList(2.66, 1.78, 1.3, 1.0, 0.74, 2.9);
    private static final int[] LOWER_LIMITS = new int[]{0, 3385, 4010, 4356, 4224};
    private static final int[] UPPER_LIMITS = new int[]{6000, 6000, 6000, 6000, Integer.MAX_VALUE};

    private Vec2f currentVelocityVector = Vec2f.of(0, 0);
    private int currentInsideGearShift = 0;
    private int refreshRate;

    public Powertrain(VirtualFunctionBus virtualFunctionBus, int refreshRate) {
        super(virtualFunctionBus);

        this.refreshRate = refreshRate;
        virtualFunctionBus.powertrainPacket = new PowertrainPacket();
    }

    private void calculateMovingVector(InputPacket inputPacket) {
        calculateVelocityVector(inputPacket.getGasPedalValue(), inputPacket.getBreakPedalValue(), inputPacket.getGearShiftValue());
        virtualFunctionBus.powertrainPacket.setMovingVector(currentVelocityVector);
    }

    @Override
    public void loop() {
        calculateMovingVector(virtualFunctionBus.inputPacket);
    }

    private Vec2f getDirectionUnitVector(GearShift.POS gearShiftPos) {
        switch (gearShiftPos) {
            case R:
                return BACKWARD_VECTOR;
            case D:
                return FORWARD_VECTOR;
            default:
                return NULL_VECTOR;
        }
    }

    private void getCurrentInsideGearShift(GearShift.POS gearShiftPos) {
        if (gearShiftPos == GearShift.POS.R) {
            currentInsideGearShift = 5;
        } else {
            if ((double) LOWER_LIMITS[currentInsideGearShift] > virtualFunctionBus.powertrainPacket.getRPM()) {
                currentInsideGearShift--;
            } else if ((double) UPPER_LIMITS[currentInsideGearShift] <= virtualFunctionBus.powertrainPacket.getRPM()) {
                currentInsideGearShift++;
                virtualFunctionBus.powertrainPacket.setRPM(LOWER_LIMITS[currentInsideGearShift]);
            }
        }
    }

    private void calculateRPM(int throttle, GearShift.POS gearShiftPos) {
        getCurrentInsideGearShift(gearShiftPos);
        virtualFunctionBus.powertrainPacket.setRPM((int) (throttle * RPM_CONSTANT * GEAR_RATIOS.get(currentInsideGearShift)));
        getCurrentInsideGearShift(gearShiftPos);
    }

    private Vec2f calculateBrakeForceVector(int brake, GearShift.POS gearShiftPos) {
        return getDirectionUnitVector(gearShiftPos).scale(-1).scale(brake).scale(BRAKE_CONSTANT);
    }

    private Vec2f calculateTractionForceVector(int throttle, GearShift.POS gearShiftPos) {
        calculateRPM(throttle, gearShiftPos);
        return getDirectionUnitVector(gearShiftPos).scale(virtualFunctionBus.powertrainPacket.getRPM());
    }

    private Vec2f calculateSummaryForceVector(int throttle, int brake, GearShift.POS gearShiftPos) {
        return calculateTractionForceVector(throttle, gearShiftPos).plus(calculateBrakeForceVector(brake, gearShiftPos)).plus(calculateResistForceVector(gearShiftPos));
    }

    private Vec2f calculateResistForceVector(GearShift.POS gearShiftPos) {
        return (getDirectionUnitVector(gearShiftPos).scale(-1).scale(RESIST_FORCE_CONSTANT)).scale(getDirectionUnitVector(gearShiftPos).scale(currentVelocityVector));
    }

    private Vec2f calculateAccelerationVector(int throttle, int brake, GearShift.POS gearShiftPos) {
        Vec2f summaryForce = calculateSummaryForceVector(throttle, brake, gearShiftPos);
        return Vec2f.of(summaryForce.getX() / CAR_MASS, summaryForce.getY() / CAR_MASS);
    }

    private void calculateVelocityVector(int throttle, int brake, GearShift.POS gearShiftPos) {
        var accelerationVector = calculateAccelerationVector(throttle, brake, gearShiftPos);
        var velocityVector = currentVelocityVector.plus(Vec2f.of(accelerationVector.getX() * refreshRate, accelerationVector.getY() * refreshRate));
        if (velocityVector.getY() * getDirectionUnitVector(gearShiftPos).getY() <= 0) {
            velocityVector = NULL_VECTOR;
        }
        currentVelocityVector = velocityVector;
        virtualFunctionBus.powertrainPacket.setVelocity((int) currentVelocityVector.magn());
    }

    private double calculateSteeringLimitation(double steering) {
        return (steering / 100) * 60;
    }

    private int calculateTurningCircle(int steering) {
        if (steering == 0) {
            return 0;
        }
        var limitedSteering = calculateSteeringLimitation(steering);
        var radian = convertDegreeToRadian(limitedSteering);
        return (int) (WHEELBASE / Math.tan(radian) + CAR_WIDTH);
    }

    private double convertDegreeToRadian(double degree) {
        return (degree * Math.PI) / 180;
    }
}
