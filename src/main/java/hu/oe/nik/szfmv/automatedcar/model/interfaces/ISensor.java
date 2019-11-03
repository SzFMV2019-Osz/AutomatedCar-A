package hu.oe.nik.szfmv.automatedcar.model.interfaces;

import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;

import java.util.List;

public interface ISensor {

    List<IObject> getAllSensedRelevantObjects(WorldManager currentWorld, Position pointB, Position pointC,
            int offsetX, int offsetY);

    int getPosX();

    int getPosY();

    void setPos(int x, int y);
}
