package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Object")
@XmlType(name = "field")
public class WorldObject implements IObject {

    private static final Logger LOGGER = LogManager.getLogger();

    @XmlElement(name = "Position", required = true)
    protected Position position;

    /**
     * Forgatási pontot tárol, külön XML-ből jön, ezért tranziens.
     */
    @XmlTransient
    protected Position referencePosition;

    @XmlElement(name = "Transform", required = true)
    protected Transform transform;

    @XmlTransient
    protected int width;

    @XmlTransient
    protected int height;

    @XmlTransient
    protected BufferedImage image;

    @XmlAttribute(name = "type", required = true)
    protected String imageFileName;

    @XmlTransient
    protected Shape polygon;

    public WorldObject() {
        this.transform = new Transform();
    }

    /**
     * Konstruktor manuális létrehozáshoz.
     *
     * @param x             Objektum X pozíciója.
     * @param y             Objektum Y pozíciója.
     * @param imageFileName Objektum fájlneve.
     */
    public WorldObject(int x, int y, String imageFileName) {
        this.position = new Position(x, y);
        this.transform = new Transform();
        this.imageFileName = imageFileName;
        this.initImage();
        this.initShape();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPosX() {
        return this.position.getX();
    }

    /**
     * {@inheritDoc}
     */
    public void setPosX(int x) {
        this.position.setX(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPosY() {
        return this.position.getY();
    }

    /**
     * {@inheritDoc}
     */
    public void setPosY(int y) {
        this.position.setY(y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPosZ() {
        return this.position.getZ();
    }

    public void setZ(int z) {
        this.position.setZ(z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getRotation() {
        return this.transform.getRotation();
    }

    /**
     * <b>Nem használható, csak Unit tesztelés miatt kell!<b/>
     *
     * @param rotation rotation of the object
     */
    public void setRotation(double rotation) {
        this.transform.setRotation(rotation);
    }

    /**
     * <b>Nem használható, csak Unit tesztelés miatt kell!<b/>
     *
     * @param position of the object
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    public String getImageFileName() {
        return this.imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    @Override
    public int getReferenceX() {
        return this.referencePosition.getX();
    }

    @Override
    public int getReferenceY() {
        return this.referencePosition.getY();
    }

    public void setReferencePosition(Position referencePosition) {
        this.referencePosition = referencePosition;
    }

    public void initImage() {
        try {
            this.image = ModelCommonUtil.loadObjectImage(this.getImageFileName());
            this.width = this.image.getWidth();
            this.height = this.image.getHeight();
        } catch (Exception e) {
            LOGGER.error(MessageFormat.format(Consts.ERROR_COULDNT_LOAD_IMG_FILE, this.getImageFileName()));
        }
    }

    /**
     * JAXB unmarshaller event listenerje.
     * Az objektum felépítése után hívódik meg, a kép betöltéséért felel.
     *
     * @param u      unmarshaller
     * @param parent JAXBElement
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.initImage();
        this.initShape();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape getPolygon() {
        return this.getShapeTransfrom().createTransformedShape(this.polygon);
    }

    private AffineTransform getShapeTransfrom() {
        double theta = Math.toRadians(this.getRotation());
        AffineTransform shapeTransform = new AffineTransform();
        shapeTransform.rotate(theta);
        shapeTransform.translate(this.getPosX() + this.getReferenceX(), this.getPosX() + this.getReferenceY());

        return shapeTransform;
    }

    /**
     * {@inheritDoc}
     */
    public void initShape() {
        int x = 0 - (this.width / 2);
        int y = 0 - (this.height / 2);
        this.polygon = new Rectangle(x, y, this.width, this.height);
    }
}
