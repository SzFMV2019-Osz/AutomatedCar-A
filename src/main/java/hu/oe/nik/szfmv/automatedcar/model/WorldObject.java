package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Object")
@XmlType(name = "field")
public class WorldObject implements IObject {

    private static final Logger LOGGER = LogManager.getLogger();
    
    @XmlElement(name = "Position", required = true)
    protected Position position;
    
    @XmlElement(name = "Transform", required = true)
    protected Transform transform;
    
    @XmlTransient
    protected int width;
    
    @XmlTransient
    protected int height;
    
    @XmlAttribute(name = "type", required = true)
    protected String imageFileName;
    
    @XmlTransient
    protected BufferedImage image;

    public WorldObject() {
        // default konstruktor
    }

    public WorldObject(int x, int y, String imageFileName) {
        this.position = new Position(x, y);
        this.transform = new Transform();
        this.imageFileName = imageFileName;
        initImage();
    }

    public WorldObject(String type, int posX, int posY, double m11, double m12, double m21, double m22) {
        this.position = new Position(posX, posY);
        this.transform = new Transform(m11, m12, m21, m22);
        this.imageFileName = type;
        initImage();
    }

    /** {@inheritDoc}
     */
    @Override
    public int getPosX(){
        return this.position.getX();
    }

    /** {@inheritDoc}
     */
    public void setX(int x){
        this.position.setX(x);
    }

    /** {@inheritDoc}
     */
    @Override
    public int getPosY(){
        return this.position.getY();
    }

    /** {@inheritDoc}
     */
    public void setY(int y){
        this.position.setY(y);
    }

    /** {@inheritDoc}
     */
    @Override
    public int getPosZ() {
        return this.position.getZ();
    }

    public void setZ(int z) {
        this.position.setZ(z);
    }

    /** {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /** {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /** {@inheritDoc}
     */
    @Override
    public double getRotation(){
        return this.transform.getRotation();
    }

    /**
     * <b>Nem használható, csak Unit tesztelés miatt kell!<b/>
     * @param rotation rotation of the object
     */
    public void setRotation(double rotation) {
        this.transform.setRotation(rotation);
    }

    /**
     * <b>Nem használható, csak Unit tesztelés miatt kell!<b/>
     * @param position of the object
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    public void initImage() {
        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource(imageFileName).getFile()));
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * JAXB unmarshaller event listenerje.
     * Az objektum felépítése után hívódik meg, a kép betöltéséért felel.
     *
     * @param u unmarshaller
     * @param parent JAXBElement
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        try {
            image = ModelCommonUtil.loadObjectImage(getImageFileName());
        } catch (Exception e) {
            LOGGER.error("Nem sikerült a képfájl betöltése!", e);
        }
    }
}
