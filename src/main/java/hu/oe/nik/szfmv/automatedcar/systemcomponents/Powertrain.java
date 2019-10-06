package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import com.github.pyknic.vector.Vec2f;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class Powertrain extends SystemComponent implements IPowertrain {
    private static final Vec2f NULL_VECTOR = Vec2f.constant(0, 0);
    private static final Vec2f FORWARD_VECTOR = Vec2f.constant(0, 1);
    private static final Vec2f BACKWARD_VECTOR = Vec2f.constant(0, -1);

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
}
