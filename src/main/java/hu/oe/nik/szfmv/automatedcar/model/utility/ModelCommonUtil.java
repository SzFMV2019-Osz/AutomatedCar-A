package hu.oe.nik.szfmv.automatedcar.model.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Common utility functions in model
 */
public class ModelCommonUtil {

    private static final double MAX_DEGREE = 360;

    /**
     * Loads a png image from resources
     * @param name Name of the image file
     * @return A BufferedImage of the requested image
     * @throws IOException If the IO operation cannot be processed, perhaps accessing rights
     */
    public static BufferedImage loadObjectImage(String name) throws IOException {
        return ImageIO.read(Objects.requireNonNull(ModelCommonUtil.class.getClassLoader().getResourceAsStream(
                "/" + name + ".png")));
    }

    /**
     * Gets a rotation value from 4 points
     * @param m11 Top left point
     * @param m12 Top right point
     * @param m21 Bottom left point
     * @param m22 Bottom right point
     * @return Double between [0,360[
     */
    public static double getRotationValue(double m11, double m12, double m21, double m22) {
        // A Math.PI / 2.0 az elvileg a 0 pontot északra teszi, mert alapból kelet, én se értem
        double angle = Math.atan2(m11 - m21, m12 - m22) + (Math.PI / 2.0E00);
        angle = Math.toDegrees(angle);
        if (angle < 0) {
            return (angle + MAX_DEGREE);
        }
        return angle;
    }
}
