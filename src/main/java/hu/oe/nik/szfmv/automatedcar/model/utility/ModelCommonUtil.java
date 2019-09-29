package hu.oe.nik.szfmv.automatedcar.model.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Modelben használt általános funkciók utility osztálya.
 */
public final class ModelCommonUtil {

    private static final double MIN_DEGREE = 0;
    private static final double MAX_DEGREE = 360;

    /**
     * Betölt egy képet a nyersanyagok közül.
     * @param name Kép neve kiterjesztés nélkül (pl. bycicle).
     * @return Az adott kép {@link BufferedImage}-ként.
     * @throws IOException Ha nem olvasható a fájl/mappa, pl. jogosultságok hiányában.
     */
    public static BufferedImage loadObjectImage(String name) throws IOException {
        return ImageIO.read(new File(ClassLoader.getSystemResource((name + CommonMessages.SUFFIX_IMAGE))
                .getFile()));
    }

    /**
     * Visszaadja a rotáció értéket egy bemeneti mátrix alapján.
     * @param m11 Matrix 1,1 helyen levő értéke.
     * @param m12 Matrix 1,2 helyen levő értéke.
     * @param m21 Matrix 2,1 helyen levő értéke.
     * @param m22 Matrix 2,2 helyen levő értéke.
     * @return double [0,360[ között.
     */
    public static double getRotationValue(double m11, double m12, double m21, double m22) {
        double angle = calculateRotationFromMatrix(m11, m12, m21, m22);

        if (angle < MIN_DEGREE) {
            return (angle + MAX_DEGREE);
        }
        return angle;
    }

    /**
     * Belső függvény a mátrixból fok számolásnak.
     * @param m11 Matrix 1,1 helyen levő értéke.
     * @param m12 Matrix 1,2 helyen levő értéke.
     * @param m21 Matrix 2,1 helyen levő értéke.
     * @param m22 Matrix 2,2 helyen levő értéke.
     * @return Rotáció fokban.
     */
    private static double calculateRotationFromMatrix(double m11, double m12, double m21, double m22) {
        double angle = Math.atan2(m21, m11); // http://nghiaho.com/?page_id=846
        return Math.toDegrees(angle);
    }
}
