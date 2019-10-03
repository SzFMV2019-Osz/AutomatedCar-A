package hu.oe.nik.szfmv.automatedcar.model.managers;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;
import java.util.ArrayList;

import java.util.List;

/**
 * {@link World}-el dolgozó manager, ezen keresztül lehet lekérdezni az abban levő objektumokat.
 */
public class WorldManager {

    private World currentWorld;

    private AutomatedCar automatedCar;

    /**
     * Inicializálja a jelenlegi világot egy xml file alapján.
     * @param filepath Neve a világ fájlnak (pl.: test_world.xml).
     */
    public WorldManager(String filepath) {
        // TODO
    }

    /**
     * Visszaadja a jelenlegi világot.
     * @return Jelenlegi világ {@link IWorld}-ként.
     */
    public IWorld getWorld() {
        return currentWorld;
    }

    /**
     * Visszaadja a világban levő összes objektumot.
     * @return {@link IObject} lista amiben minden a világban levő object benne van.
     */
    public List<IObject> getAllObjects() {
        return new ArrayList<>(currentWorld.getWorldObjects());
    }

    /**
     * Visszaadja az összes objektumot a három pont között.
     * @param pointA Első pont
     * @param pointB Második pont
     * @param pointC Harmadik pont
     * @return {@link IObject} lista amiben benne vannak a szűrt objectek amik a háromszögön belülre esnek.
     */
    public List<IObject> getAllObjectsInTriangle(Position pointA, Position pointB, Position pointC) {
        // TODO
        return null;
    }

    /**
     * Visszaadja az irányított autót
     * @return {@link AutomatedCar} referencia az irányíott autóval
     */
    public AutomatedCar getAutomatedCar(){
        return this.automatedCar;
    }

    /**
     *  Beállítja az {@link AutomatedCar} referenciáját
     * @param car Irányított autó objektuma
     */
    public void setAutomatedCar(AutomatedCar car){
        this.automatedCar = car;
    }
}
