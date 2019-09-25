package hu.oe.nik.szfmv.automatedcar;

import com.github.pyknic.vector.Vec2f;
import com.github.pyknic.vector.Vec3f;

public interface IPowertrain {
    int calculateTurningCircle(int leftSteering, int rightSteering);
    Vec2f calculateMovementDirection(GearShift gearShift);
    Vec2f calculateAcceleration(GearShift gearShift, int throttle, int brake);
    Vec2f calculateMotionVector(int turningCircle, Vec2f movementDirection, Vec2f motionVector);

}
