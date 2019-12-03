package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;

import java.util.List;

public interface OutGoingUltraSoundPacket {

    List<List<IObject>> getUltraSoundObjects();
}
