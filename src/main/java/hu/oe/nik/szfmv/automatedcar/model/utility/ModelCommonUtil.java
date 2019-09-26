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
     * Gets a rotation value from 2x2 matrix values
     * @param m11 Mx top left value
     * @param m12 Mx top left value
     * @param m21 Mx bottom left point
     * @param m22 Mx bottom right point
     * @return Double between [0,360[
     */
    public static double getRotationValue(double m11, double m12, double m21, double m22) {
        // A Math.PI / 2.0 az elvileg a 0 pontot északra teszi, mert alapból kelet felé néz
        double angle = Math.atan2(m11, m21) + (Math.PI / 2.0E00);
        angle = Math.toDegrees(angle);
        if (angle < MIN_DEGREE) {
            return (angle + MAX_DEGREE);
        }
        return angle;
    }
}
