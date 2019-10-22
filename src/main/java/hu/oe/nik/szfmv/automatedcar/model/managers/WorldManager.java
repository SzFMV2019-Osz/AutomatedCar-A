package hu.oe.nik.szfmv.automatedcar.model.managers;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.References;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;
import hu.oe.nik.szfmv.automatedcar.xml.XmlParser;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link World}-el dolgozó manager, ezen keresztül lehet lekérdezni az abban levő objektumokat.
 */
public class WorldManager {

    private World currentWorld;
    private AutomatedCar automatedCar;

    /**
     * Inicializálja a világot a kapott file-ok alapján.
     * 
     * @param worldFileName Feldolgozandó world fájl neve, kiterjesztés nélkül is megadható.
     * @param referenceFileName Feldolgozandó referencia fájl neve, kiterjesztés nélkül is megadható.
     * @throws NullPointerException Ha valamelyik fájl nem található.
     */
    public WorldManager(String worldFileName, String referenceFileName) {
        currentWorld = XmlParser.parseWorldObjects(worldFileName);
        References refs = XmlParser.parseReferences(referenceFileName);
        
        if (currentWorld != null && currentWorld.getWorldObjects() != null && refs != null) {
            for (IObject iObject : currentWorld.getWorldObjects()) {
                WorldObject wo = ((WorldObject)iObject);
                Position pos = refs.getReference(wo.getImageFileName());
                wo.setReferencePosition(pos);
            }
        }
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
     * Visszaadja az összes objektumot a három pont között.
     * @param pointA Első pont.
     * @param pointB Második pont.
     * @param pointC Harmadik pont.
     * @param offsetX X irányú eltolás.
     * @param offsetY Y irányú eltolás.
     * @return {@link IObject} lista amiben benne vannak a szűrt objectek amik a háromszögön belülre esnek.
     */
    public List<IObject> getAllObjectsInTriangle(Position pointA, Position pointB, Position pointC,
                                                 int offsetX, int offsetY) {
        Polygon triangle = new Polygon();
        triangle.addPoint(pointA.getX(), pointA.getY());
        triangle.addPoint(pointB.getX(), pointB.getY());
        triangle.addPoint(pointC.getX(), pointC.getY());

        List<IObject> inTriangle = new ArrayList<>();
        for (IObject obj : currentWorld.getWorldObjects()) {
            if (isObjectInShape(obj.getPolygons(offsetX, offsetY), triangle)) {
                inTriangle.add(obj);
            }
        }
        return inTriangle;
    }

    /**
     * Visszaadja az összes objektumot a ponton.
     * @param point A pont ahol keressük az objektumokat.
     * @param offsetX X irányú eltolás.
     * @param offsetY Y irányú eltolás.
     * @return {@link IObject} lista amiben benne vannak a szűrt objectek amik a ponton vannak.
     */
    public List<IObject> getAllObjectsOnPoint(Position point, int offsetX, int offsetY) {
        Point pointShape = new Point(point.getX(), point.getY());

        List<IObject> onPoint = new ArrayList<>();
        for (IObject obj : currentWorld.getWorldObjects()) {
            if (isObjectOnPoint(obj.getPolygons(offsetX, offsetY), pointShape)) {
                onPoint.add(obj);
            }
        }
        return onPoint;
    }

    /**
     * Visszaadja az összes objektumot a négyzeten belül. Ha két víszintes vagy függőleges pontra eső vonalat kapunk
     * akkor pedig a vonalon levőket adja vissza helyesen elvileg!
     * @param pointA A négyzet egyik pontja.
     * @param pointB A négyzet másik pontja.
     * @param offsetX X irányú eltolás.
     * @param offsetY Y irányú eltolás.
     * @return {@link IObject} lista amiben benne vannak a szűrt objectek amik a négyzeten belül vannak.
     */
    public List<IObject> getAllObjectsInRectangle(Position pointA, Position pointB, int offsetX, int offsetY) {
        Position pointC = new Position(pointA.getX(), pointB.getY());
        Position pointD = new Position(pointB.getX(), pointA.getY());

        Position topLeft = ModelCommonUtil.getTopLeftPoint(pointA, pointB, pointC, pointD);
        Position bottomRight = ModelCommonUtil.getBottomRightPoint(pointA, pointB, pointC, pointD);
        Rectangle rect = ModelCommonUtil.createRectangle(topLeft.getX(), topLeft.getY(),
                                                        (bottomRight.getX() - topLeft.getX()),
                                                        (bottomRight.getY() - topLeft.getX()));

        List<IObject> inRectangle = new ArrayList<>();

        for (IObject obj : currentWorld.getWorldObjects()) {
            if (isObjectInShape(obj.getPolygons(offsetX, offsetY), rect)) {
                inRectangle.add(obj);
            }
        }

        return inRectangle;
    }

    /**
     * Visszaadja az irányított autót
     * @return {@link AutomatedCar} referencia az irányíott autóval
     */
    public AutomatedCar getAutomatedCar() {
        return this.automatedCar;
    }

    /**
     * Beállítja az {@link AutomatedCar} referenciáját
     * @param car Irányított autó objektuma
     */
    public void setAutomatedCar(AutomatedCar car) {
        this.automatedCar = car;
    }

    private boolean isObjectInShape(List<Shape> polygonsOfObject, Shape shape) {
        for (Shape poly : polygonsOfObject) {
            if (ModelCommonUtil.isShapeInPolygon(poly, shape)) {
                return true;
            }
        }
        return false;
    }

    private boolean isObjectOnPoint(List<Shape> polygonsOfObject, Point point) {
        for (Shape poly : polygonsOfObject) {
            if (ModelCommonUtil.isShapeOnPoint(poly, point)) {
                return true;
            }
        }
        return false;
    }
}
