package hu.oe.nik.szfmv.automatedcar.model.managers;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.References;
import hu.oe.nik.szfmv.automatedcar.model.RoadSensor;
import hu.oe.nik.szfmv.automatedcar.model.SignSensor;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ISensor;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;
import hu.oe.nik.szfmv.automatedcar.xml.XmlParser;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@link World}-el dolgozó manager, ezen keresztül lehet lekérdezni az abban levő objektumokat.
 */
public class WorldManager {

    private World currentWorld;
    private AutomatedCar automatedCar;

    /**
     * Inicializálja a világot a kapott file-ok alapján.
     *
     * @param worldFileName     Feldolgozandó world fájl neve, kiterjesztés nélkül is megadható.
     * @param referenceFileName Feldolgozandó referencia fájl neve, kiterjesztés nélkül is megadható.
     * @throws NullPointerException Ha valamelyik fájl nem található.
     */
    public WorldManager(String worldFileName, String referenceFileName) {
        this.currentWorld = XmlParser.parseWorldObjects(worldFileName);
        References refs = XmlParser.parseReferences(referenceFileName);

        if (this.currentWorld != null && this.currentWorld.getWorldObjects() != null && refs != null) {
            for (IObject iObject : this.currentWorld.getWorldObjects()) {
                WorldObject wo = ((WorldObject) iObject);
                Position pos = refs.getReference(wo.getImageFileName());
                wo.setReferencePosition(pos);
            }
        }
    }

    /**
     * Hozzáad egy objektumot a világhoz.
     *
     * @param object Akármilyen objektum ami implementálja az {@link IObject}et.
     */
    public void addObjectToWorld(IObject object) {
        this.currentWorld.addObject(object);
    }

    /**
     * Visszaadja a jelenlegi világot.
     *
     * @return Jelenlegi világ {@link IWorld}-ként.
     */
    public IWorld getWorld() {
        return this.currentWorld;
    }

    /**
     * Visszaadja az összes objektumot a három pont között.
     *
     * @param offsetX X irányú eltolás.
     * @param offsetY Y irányú eltolás.
     * @return {@link IObject} lista amiben benne vannak a szűrt objectek amik a háromszögön belülre esnek.
     */
    public List<IObject> getAllObjectsInTriangle(Shape triangle, int offsetX, int offsetY) {
        List<IObject> inTriangle = new ArrayList<>();
        for (IObject obj : this.currentWorld.getWorldObjects()) {
            if (this.isObjectInShape(obj.getPolygons(offsetX, offsetY), triangle)) {
                inTriangle.add(obj);
            }
        }
        return inTriangle;
    }

    /**
     * Visszaadja az összes objektumot a ponton.
     *
     * @param point   A pont ahol keressük az objektumokat.
     * @param offsetX X irányú eltolás.
     * @param offsetY Y irányú eltolás.
     * @return {@link IObject} lista amiben benne vannak a szűrt objectek amik a ponton vannak.
     */
    public List<IObject> getAllObjectsOnPoint(Position point, int offsetX, int offsetY) {
        Point pointShape = new Point(point.getX(), point.getY());

        List<IObject> onPoint = new ArrayList<>();
        for (IObject obj : this.currentWorld.getWorldObjects()) {
            if (this.isObjectOnPoint(obj.getPolygons(offsetX, offsetY), pointShape)) {
                onPoint.add(obj);
            }
        }
        return onPoint;
    }

    /**
     * Visszaadja az összes objektumot a négyzeten belül. Ha két víszintes vagy függőleges pontra eső vonalat kapunk
     * akkor pedig a vonalon levőket adja vissza helyesen elvileg!
     *
     * @param pointA  A négyzet egyik pontja.
     * @param pointB  A négyzet másik pontja.
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

        for (IObject obj : this.currentWorld.getWorldObjects()) {
            if (this.isObjectInShape(obj.getPolygons(offsetX, offsetY), rect)) {
                inRectangle.add(obj);
            }
        }

        return inRectangle;
    }

    /**
     * Visszaadja az összes objektumot a négyzeten belül. Ha két víszintes vagy függőleges pontra eső vonalat kapunk
     * akkor pedig a vonalon levőket adja vissza helyesen elvileg!
     *
     * @param pointA  A négyzet egyik pontja.
     * @param pointB  A négyzet másik pontja.
     * @param offsetX X irányú eltolás.
     * @param offsetY Y irányú eltolás.
     * @return {@link IObject} lista amiben benne vannak a szűrt objectek amik a négyzeten belül vannak.
     */
    public List<ICrashable> getAllCrashableObjectsInRectangle(Position pointA, Position pointB, int offsetX, int offsetY) {
        List<IObject> results = this.getAllObjectsInRectangle(pointA, pointB, offsetX, offsetY);
        return results.stream().filter(o -> o instanceof ICrashable).map(o -> (ICrashable) o).collect(Collectors.toList());
    }

    /**
     * Visszaadja az irányított autót
     *
     * @return {@link AutomatedCar} referencia az irányíott autóval
     */
    public AutomatedCar getAutomatedCar() {
        return this.automatedCar;
    }

    /**
     * Beállítja az {@link AutomatedCar} referenciáját
     *
     * @param car Irányított autó objektuma
     */
    public void setAutomatedCar(AutomatedCar car) {
        this.automatedCar = car;
    }

    /**
     * Használható például arra, hogy az elején beállítsuk a szenzorok alaphelyzetét.
     *
     * @return az {@link AutomatedCar}-ban levő szenzorok.
     */
    public List<ISensor> getAllCarSensors() {
        return this.automatedCar.getSensors();
    }

    /**
     * Visszaadja az összes érzékelt utat.
     *
     * @param trianglePointB szenzor háromszög 2. pontja.
     * @param trianglePointC szenzor háromszög 3. pontja.
     * @param offsetX        x offset.
     * @param offsetY        y offset.
     * @return minden sávérzékelő által érzékelt út.
     */
    public List<IObject> getAllSensedRoads(Shape triangle, int offsetX, int offsetY) {
        Optional<ISensor> sensor = this.automatedCar.getSensors().stream().filter(x -> x instanceof RoadSensor).findFirst();
        if (sensor.isPresent()) {
            return sensor.get().getAllSensedRelevantObjects(
                    this, triangle, offsetX, offsetY);
        }
        return Collections.emptyList();
    }

    /**
     * Vissza az összes érzékelt táblát.
     *
     * @param trianglePointB szenzor háromszög 2. pontja.
     * @param trianglePointC szenzor háromszög 3. pontja.
     * @param offsetX        x offset.
     * @param offsetY        y offset.
     * @return minden táblaérzékelő által érzékelt tábla.
     */
    public List<IObject> getAllSensedSigns(Shape triangle, int offsetX, int offsetY) {
        Optional<ISensor> sensor = this.automatedCar.getSensors().stream().filter(x -> x instanceof SignSensor).findFirst();
        if (sensor.isPresent()) {
            return sensor.get().getAllSensedRelevantObjects(
                    this, triangle, offsetX, offsetY);
        }
        return Collections.emptyList();
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
