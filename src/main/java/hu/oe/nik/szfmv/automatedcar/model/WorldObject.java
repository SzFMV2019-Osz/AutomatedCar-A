package hu.oe.nik.szfmv.automatedcar.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WorldObject {

    private static final Logger LOGGER = LogManager.getLogger();
    protected Position position;
    protected Transform transform;
    protected int width;
    protected int height;
    protected String imageFileName;
    protected BufferedImage image;

    public WorldObject(int x, int y, String imageFileName) {
        this.position = new Position(x, y);
        this.transform = new Transform();
        this.imageFileName = imageFileName;
        initImage();
    }

    public WorldObject(String type, int posX, int posY, double m11, double m12, double m21, double m22){
        this.position = new Position(posX, posY);
        this.transform = new Transform(m11, m12, m21, m22);
        this.imageFileName = type;
        initImage();
    }


    public Position getPosition(){
        return this.position;
    }

    public Transform getTransform(){
        return  this.transform;
    }

    /**
     * @deprecated
     */
    public int getX(){
        return this.position.getX();
    }

    /**
     * @deprecated
     */
    public int getY(){
        return this.position.getY();
    }

    /** {@inheritDoc}
     */
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /** {@inheritDoc}
     */
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

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

}
