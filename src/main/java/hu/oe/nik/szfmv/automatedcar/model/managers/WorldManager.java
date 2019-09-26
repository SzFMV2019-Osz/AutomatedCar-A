package hu.oe.nik.szfmv.automatedcar.model.managers;

import hu.oe.nik.szfmv.automatedcar.model.Position;
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
     * @param pointA Első pont
     * @param pointB Második pont
     * @param pointC Harmadik pont
     * @return IObject lista amiben benne vannak a szűrt objectek amik a háromszögen belül
     */
    public List<IObject> getAllObjectsInTriangle(Position pointA, Position pointB, Position pointC) {
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
