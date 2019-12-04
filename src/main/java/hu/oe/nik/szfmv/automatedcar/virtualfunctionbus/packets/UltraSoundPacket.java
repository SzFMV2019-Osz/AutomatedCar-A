package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;

import java.util.List;

public class UltraSoundPacket implements OutGoingUltraSoundPacket {
    private List<List<IObject>> ultraSoundObjects;

    @Override
    public List<List<IObject>> getUltraSoundObjects() {
        return ultraSoundObjects;
    }

    public void setUltraSoundObjects(List<List<IObject>> ultraSoundObjects) {
        this.ultraSoundObjects = ultraSoundObjects;
    }
}
