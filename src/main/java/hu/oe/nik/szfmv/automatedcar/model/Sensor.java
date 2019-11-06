package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ISensor;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;

import java.awt.Shape;
import java.util.List;

public abstract class Sensor implements ISensor {

    public Position pos = new Position();

    /**
     * Visszaad egy objektum listát ami elemeit a szenzor érzékelte és számára relevánsak.
     * A háromszög egyik pontja a szenzor önmaga.
     *
     * @param currentWorld manager.
     * @param offsetX      x offset.
     * @param offsetY      y offset.
     * @return releváns IObjectek listája.
     */
    public abstract List<IObject> getAllSensedRelevantObjects(WorldManager currentWorld, Shape triangle, int offsetX, int offsetY);

    /**
     * Lekezeli az érzékelt objektumat.
     * 
     * @param sensedObjects érzékelt objektumok
     */
    public abstract void handleSensedObjects(List<IObject> sensedObjects);

    @Override
    public int getPosX() {
        return this.pos.getX();
    }

    @Override
    public int getPosY() {
        return this.pos.getY();
    }

    @Override
    public void setPos(int x, int y) {
        this.pos.setX(x);
        this.pos.setY(y);
    }
}
