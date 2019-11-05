package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IDynamic;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.ISensor;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.Unmarshaller;

/**
 * Autó alaposztály.
 */
public class Car extends WorldObject implements ICrashable, IDynamic {

    private static final int WIDTH_BORDER = 6;
    private static final int HEIGHT_BORDER = 1;

    protected RoadSensor roadSensor;
    protected SignSensor signSensor;

    public Car() {
    }

    /**
     * Konstruktor manuális létrehozáshoz.
     *
     * @param x             X pozíció.
     * @param y             Y pozíció.
     * @param imageFileName Kép file neve.
     */
    public Car(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        this.position.setZ(2);
        this.referencePosition = new Position(this.width / 2, this.height / 2);
    }

    @Override
    public double getWeight() {
        // TODO
        return 0;
    }

    @Override
    public void crashed() {

    }

    public List<ISensor> getSensors() {
        return Arrays.asList(this.roadSensor, this.signSensor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape() {
        this.polygons.add(new Rectangle(
                WIDTH_BORDER, HEIGHT_BORDER, this.width - WIDTH_BORDER * 2, this.height - HEIGHT_BORDER * 2));
    }

    @Override
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        switch (this.imageFileName) {
            case (Consts.RES_IDENTIFIER_CAR_1): {
                this.imageFileName = this.generateFilename(1);
                break;
            }
            case (Consts.RES_IDENTIFIER_CAR_2): {
                this.imageFileName = this.generateFilename(2);
                break;
            }
            case (Consts.RES_IDENTIFIER_CAR_3): {
                this.imageFileName = Consts.RES_IDENTIFIER_CAR_BLACK;
                break;
            }
            default: {
                this.imageFileName = this.generateFilename(ModelCommonUtil.getRandom(2));
            }
        }
        super.afterUnmarshal(u, parent);
    }

    private String generateFilename(int number) {
        return Consts.RES_IDENTIFIER_CAR_PREFIX + number + this.getColor();
    }

    private String getColor() {
        switch (ModelCommonUtil.getRandom(3)) {
            case 1:
                return Consts.RES_IDENTIFIER_COLOR_RED_SUFFIX;
            case 2:
                return Consts.RES_IDENTIFIER_COLOR_BLUE_SUFFIX;
            default:
                return Consts.RES_IDENTIFIER_COLOR_WHITE_SUFFIX;
        }
    }
}
