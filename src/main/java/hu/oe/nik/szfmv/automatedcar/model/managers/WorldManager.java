package hu.oe.nik.szfmv.automatedcar.model.managers;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;

import java.util.List;

public class WorldManager {

    private World currentWorld;


    /**
     * Inicializálja a jelenlegi világot egy xml file alapján
     * @param filepath Neve a világ fájlnak (pl.: test_world.xml)
     * @return Egy IWorld-el tér vissza
     */
    public IWorld initWorld(String filepath) {
        // TODO
        return null;
    }

    /**
     * Visszaadja a jelenlegi világot, ha már inicializálva lett
     * @return Jelenlegi világ IWorld-ként
     */
    public IWorld getWorld() {
        // TODO
        validate();
        return null;
    }

    /**
     * Visszaadja a világban levő összes objektumot
     * @return IObject lista amiben minden a világban levő object benne van
     */
    public List<IObject> getAllObjects() {
        // TODO
        validate();
        return null;
    }

    /**
     * Visszaadja az összes objektumot a három pont között
     * @param x1 Első pont X
     * @param y1 Első pont Y
     * @param x2 Második pont X
     * @param y2 Második pont Y
     * @param x3 Harmadik pont X
     * @param y3 Harmadik pont Y
     * @return IObject lista amiben benne vannak a szűrt objectek amik a háromszögen belül
     */
    public List<IObject> getAllObjectsInTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        // TODO
        validate();
        return null;
    }

    /**
     * Hibát dob, ha az initWorld nem volt még meghívva.
     */
    private void validate() {
        // TODO
    }
}
