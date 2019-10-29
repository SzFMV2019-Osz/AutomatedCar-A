package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ISensor;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;

import java.util.List;

public abstract class Sensor implements ISensor {

    public int range = 10000;
    public Position pos = new Position();

    /**
     * Visszaad egy objektum listát ami elemeit a szenzor érzékelte és számára relevánsak.
     * A háromszög egyik pontja a szenzor önmaga.
     * @param currentWorld manager.
     * @param pointB háromszög második pontja.
     * @param pointC háromszög harmadik pontja.
     * @param offsetX x offset.
     * @param offsetY y offset.
     * @return releváns IObjectek listája.
     */
    public abstract List<IObject> getAllSensedRelevantObjects(WorldManager currentWorld, Position pointB,
            Position pointC, int offsetX, int offsetY);

    public void setRange(int newRange) {
        range = newRange;
    }

    public int getPosX() {
        return pos.getX();
    }

    public int getPosY() {
        return pos.getY();
    }

    public void setPos(int x, int y) {
        pos.setX(x);
        pos.setY(y);
    }
}
