package hu.oe.nik.szfmv.automatedcar.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WorldObject {

    private static final Logger LOGGER = LogManager.getLogger();
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int rotation = 0;
    protected String imageFileName;
    protected BufferedImage image;

    public WorldObject(int x, int y, String imageFileName) {
        this.x = x;
        this.y = y;
        this.imageFileName = imageFileName;
        initImage();
    }

    public WorldObject(String type, int posX, int posY, int m11, int m12, int m21, int m22){
        this.x = posX;
        this.y = posY;
        this.rotation = this.getRotationValue(m11, m12, m21, m22);
        this.imageFileName = type;
        initImage();
    }

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

    public int getRotation() {
        return rotation;
    }

    public int getRotationValue(int m11, int m12, int m21, int m22){
        // TODO calculate rotation
        return 0;
    }

    public void setRotation(int rotation) {
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
