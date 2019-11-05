package hu.oe.nik.szfmv.automatedcar.model.interfaces;

import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;

import java.awt.Shape;
import java.util.List;

public interface ISensor {

    List<IObject> getAllSensedRelevantObjects(WorldManager currentWorld, Shape triangle, int offsetX, int offsetY);

    int getPosX();

    int getPosY();

    void setPos(int x, int y);
}
