package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

import com.github.pyknic.vector.Vec2f;

public class PowertrainPacket {
    private int RPM;
    private int velocity;
    private Vec2f movingVector;

    public PowertrainPacket() {
        movingVector = Vec2f.of(0, 0);
    }

    public int getRPM() {
        return RPM;
    }

    public void setRPM(int RPM) {
        this.RPM = RPM;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Vec2f getMovingVector() {
        return movingVector;
    }

    public void setMovingVector(Vec2f movingVector) {
        this.movingVector = movingVector;
    }
}
