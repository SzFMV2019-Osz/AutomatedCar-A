package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.AEBState;

public class AEBPacket {
    AEBState state;
    boolean aebNotOptimal;

    public boolean isAebNotOptimal() {
        return aebNotOptimal;
    }

    public void setAebNotOptimal(boolean aebNotOptimal) {
        this.aebNotOptimal = aebNotOptimal;
    }

    public AEBState getState() {
        return state;
    }

    public void setState(AEBState state) {
        this.state = state;
    }
}
