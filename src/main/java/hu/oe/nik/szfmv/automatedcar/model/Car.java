package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IDynamic;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;

import java.awt.Rectangle;
import javax.xml.bind.Unmarshaller;

/**
 * Autó alaposztály.
 */
public class Car extends WorldObject implements ICrashable, IDynamic {

    private final int WIDTH_BORDER = 6;
    private final int HEIGHT_BORDER = 1;

    public Car() { }

    /**
     * Konstruktor manuális létrehozáshoz.
     * @param x X pozíció.
     * @param y Y pozíció.
     * @param imageFileName Kép file neve.
     */
    public Car(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        this.position.setZ(2);
        this.referencePosition = new Position(this.width / 2,this.height / 2);
    }

    public double getWeight() {
        // TODO
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape() {
        this.polygons.add(new Rectangle(WIDTH_BORDER, HEIGHT_BORDER, this.width - WIDTH_BORDER * 2, this.height - HEIGHT_BORDER * 2));
    }

    @Override
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        switch (this.imageFileName) {
            case (Consts.RES_IDENTIFIER_CAR_1):  this.imageFileName = generateFilename(1); break;
            case (Consts.RES_IDENTIFIER_CAR_2):  this.imageFileName = generateFilename(2); break;
            case (Consts.RES_IDENTIFIER_CAR_3):  this.imageFileName = Consts.RES_IDENTIFIER_CAR_BLACK; break;
            default:                             this.imageFileName = generateFilename(ModelCommonUtil.getRandom(2));
        }
        super.afterUnmarshal(u, parent);
    }
    
    private String generateFilename(int number) {
        return Consts.RES_IDENTIFIER_CAR_PREFIX + number + getColor();
    }
    
    private String getColor() {
        switch (ModelCommonUtil.getRandom(3)) {
            case 1:  return Consts.RES_IDENTIFIER_COLOR_RED_SUFFIX;
            case 2:  return Consts.RES_IDENTIFIER_COLOR_BLUE_SUFFIX;
            default: return Consts.RES_IDENTIFIER_COLOR_WHITE_SUFFIX;
        }
    }
}
