package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

import com.github.pyknic.vector.Vec2f;

public class PowertrainPacket {
    private int RPM;
    private Vec2f velocityVector;

    public PowertrainPacket() {
    }

    public int getRPM() {
        return RPM;
    }

    public void setRPM(int RPM) {
        this.RPM = RPM;
    }

    public Vec2f getVelocityVector() {
        return velocityVector;
    }

    public void setVelocityVector(Vec2f velocityVector) {
        this.velocityVector = velocityVector;
    }
}
