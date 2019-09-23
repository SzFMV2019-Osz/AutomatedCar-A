package hu.oe.nik.szfmv.automatedcar.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WorldObject {

    private static final Logger LOGGER = LogManager.getLogger();
    private double m11;
    private double m12;
    private double m21;
    private double m22;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected double rotation = 0;
    protected String imageFileName;
    protected BufferedImage image;

    public WorldObject(int x, int y, String imageFileName) {
        this.x = x;
        this.y = y;
        this.imageFileName = imageFileName;
        initImage();
    }

    public WorldObject(String type, int posX, int posY, double m11, double m12, double m21, double m22){
        this.x = posX;
        this.y = posY;
        this.m11 = m11;
        this.m12 = m12;
        this.m21 = m21;
        this.m22 = m22;
        this.rotation = this.getRotationValue(m11, m12, m21, m22);
        this.imageFileName = type;
        initImage();
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the rotation of the {@link WorldObject} object
     * @return rotation of the object
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Calculates and gets the rotation value from the distance of the corners
     * @param m11 Distance from top left corner
     * @param m12 Distance from top right corner
     * @param m21 Distance from bottom left corner
     * @param m22 Distance from bottom right corner
     * @return calculated rotation
     */
    public double getRotationValue(double m11, double m12, double m21, double m22){
        // TODO calculate rotation
        return 0;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
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
