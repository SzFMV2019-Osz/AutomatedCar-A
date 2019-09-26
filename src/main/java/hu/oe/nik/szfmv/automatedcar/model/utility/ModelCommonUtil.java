package hu.oe.nik.szfmv.automatedcar.model.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Common utility functions in model
 */
public final class ModelCommonUtil {

    private static final double MIN_DEGREE = 0;
    private static final double MAX_DEGREE = 360;

    /**
     * Loads a png image from resources
     * @param name Name of the image file
     * @return A BufferedImage of the requested image
     * @throws IOException If the IO operation cannot be processed, perhaps accessing rights
     */
    public static BufferedImage loadObjectImage(String name) throws IOException {
        return ImageIO.read(new File(ClassLoader.getSystemResource((name + ".png")).getFile()));
    }

    /**
     * Gets a rotation value from rotation matrix
     * @param m11 Matrix 1,1 position
     * @param m12 Matrix 1,2 position
     * @param m21 Matrix 2,1 position
     * @param m22 Matrix 2,2 position
     * @return Double between [0,360[
     */
    public static double getRotationValue(double m11, double m12, double m21, double m22) {
        double angle = calculateRotationFromMatrix(m11, m12, m21, m22);

        if (angle < MIN_DEGREE) {
            return (angle + MAX_DEGREE);
        }
        return angle;
    }

    /**
     * @param m11 Matrix 1,1 position
     * @param m12 Matrix 1,2 position
     * @param m21 Matrix 2,1 position
     * @param m22 Matrix 2,2 position
     * @return Rotation in degrees
     */
    private static double calculateRotationFromMatrix(double m11, double m12, double m21, double m22){
        double angle = Math.atan2(m21, m11); // http://nghiaho.com/?page_id=846
        return Math.toDegrees(angle);
    }
}
