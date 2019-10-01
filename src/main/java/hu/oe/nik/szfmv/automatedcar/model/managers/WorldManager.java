package hu.oe.nik.szfmv.automatedcar.model.managers;

import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;
import hu.oe.nik.szfmv.automatedcar.xml.XmlParser;

import java.awt.Rectangle;
import java.util.ArrayList;

import java.util.List;

/**
 * {@link World}-el dolgozó manager, ezen keresztül lehet lekérdezni az abban levő objektumokat.
 */
public class WorldManager {

    private World currentWorld;

    /**
     * Inicializálja a jelenlegi világot egy xml file alapján.
     * @param filepath Neve a világ fájlnak (pl.: test_world.xml).
     */
    public WorldManager(String filepath) {
        currentWorld = XmlParser.parseWorldObjects(filepath);
    }

    /**
     * Hozzáad egy objektumot a világhoz.
     * @param object Akármilyen objektum ami implementálja az {@link IObject}et.
     */
    public void addObjectToWorld(IObject object) {
        currentWorld.addObject(object);
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

        return currentWorld.getWorldObjects();
    }

    /**
     * Visszaadja az összes objektumot a három pont között. TODO majd változni fog poligonok definiálása után
     * @param pointA Első pont.
     * @param pointB Második pont.
     * @param pointC Harmadik pont.
     * @return {@link IObject} lista amiben benne vannak a szűrt objectek amik a háromszögön belülre esnek.
     */
    public List<IObject> getAllObjectsInTriangle(Position pointA, Position pointB, Position pointC) {
        List<IObject> inTriangle = new ArrayList<>();

        Position pos = new Position();
        for (IObject obj : currentWorld.getWorldObjects()) {
            pos.setX(obj.getPosX());
            pos.setY(obj.getPosY());
            if (ModelCommonUtil.isPointInTriangle(pointA, pointB, pointC, pos)) {
                inTriangle.add(obj);
            }
        }

        return inTriangle;
    }

    /**
     * Visszaadja az összes objektumot a ponton. TODO majd változni fog poligonok definiálása után
     * @param point A pont ahol keressük az objektumokat.
     * @return {@link IObject} lista amiben benne vannak a szűrt objectek amik a ponton vannak.
     */
    public List<IObject> getAllObjectsOnPoint(Position point) {
        List<IObject> onPoint = new ArrayList<>();

        for (IObject obj : currentWorld.getWorldObjects()) {
            if (obj.getPosX() == point.getX() && obj.getPosY() == point.getY()) {
                onPoint.add(obj);
            }
        }

        return onPoint;
    }

    /**
     * Visszaadja az összes objektumot a négyzeten belül. TODO majd változni fog poligonok után (ez csak kicsit)
     * @param pointA A négyzet egyik pontja.
     * @param pointB A négyzet másik pontja.
     * @return {@link IObject} lista amiben benne vannak a szűrt objectek amik a négyzeten belül vannak.
     */
    public List<IObject> getAllObjectsInRectangle(Position pointA, Position pointB) {
        Position pointC = new Position(pointA.getX(), pointB.getY());
        Position pointD = new Position(pointB.getX(), pointA.getY());

        Position topLeft = ModelCommonUtil.getTopLeftPoint(pointA, pointB, pointC, pointD);
        Position bottomRight = ModelCommonUtil.getBottomRightPoint(pointA, pointB, pointC, pointD);
        Rectangle rect = new Rectangle(topLeft.getX(), topLeft.getY(), (bottomRight.getX() - topLeft.getX()),
                (bottomRight.getY() - topLeft.getX()));

        List<IObject> inRectangle = new ArrayList<>();

        for (IObject obj : currentWorld.getWorldObjects()) {
            if (rect.contains(obj.getPosX(), obj.getPosY())) {
                inRectangle.add(obj);
            }
        }

        return inRectangle;
    }
}
