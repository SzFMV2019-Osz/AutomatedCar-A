package hu.oe.nik.szfmv.automatedcar.model.utility;

import hu.oe.nik.szfmv.automatedcar.model.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

/**
 * Modelben használt általános funkciók utility osztálya.
 */
public final class ModelCommonUtil {

    /**
     * Betölt egy képet a nyersanyagok közül.
     * @param name Kép neve kiterjesztés nélkül (pl. bycicle).
     * @return Az adott kép {@link BufferedImage}-ként.
     * @throws IOException Ha nem olvasható a fájl/mappa, pl. jogosultságok hiányában.
     */
    public static BufferedImage loadObjectImage(String name) throws IOException {
        // @TODO: Fájl betöltést kiemelni külön függvénybe
        if ( !StringUtils.endsWith(name, Consts.SUFFIX_IMAGE)) {
            name += Consts.SUFFIX_IMAGE;
        }
        return ImageIO.read(new File(ClassLoader.getSystemResource(name).getFile()));
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

        if (angle < Consts.MIN_DEGREE) {
            return (angle + Consts.MAX_DEGREE);
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

    /**
     * Visszaadja, hogy az adott pont a háromszögön belül van-e.
     * @param triangleA Háromszög első pontja.
     * @param triangleB Háromszög második pontja.
     * @param triangleC Háromszög harmadik pontja.
     * @param point Lekérdezett pont.
     * @return Benne van? logikai kifejezés értéke.
     */
    public static boolean isPointInTriangle (Position triangleA, Position triangleB, Position triangleC,
                                             Position point) {
        double a = calculateAreaOfTriangle(triangleA, triangleB, triangleC);
        double b = calculateAreaOfTriangle(point, triangleB, triangleC);
        double c = calculateAreaOfTriangle(triangleA, point, triangleC);
        double d = calculateAreaOfTriangle(triangleA, triangleB, point);

        return (a == (b + c + d));
    }

    /**
     * Egy háromszög területét adja vissza.
     * @param a Háromszög első pontja.
     * @param b Háromszög második pontja.
     * @param c Háromszög harmadik pontja.
     * @return A háromszög területe.
     */
    private static double calculateAreaOfTriangle(Position a, Position b, Position c) {
        return Math.abs((a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY())
                + c.getX() * (a.getY() - b.getY())) / 2.0);
    }

    /**
     * Visszaadja, hogy a pontok közül melyik a bal felső pont.
     * @param points A pontok amik közül a bal felsőt keressük.
     * @return A bal felső pont.
     */
    public static Position getTopLeftPoint(Position... points) {
        Position p = new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (Position point : points) {
            if (point.getX() <= p.getX() && point.getY() <= p.getY()) {
                p = point;
            }
        }

        return p;
    }

    /**
     * Visszaadja, hogy a pontok közül melyik a jobb alsó pont.
     * @param points A pontok amik közül a jobb alsót keressük.
     * @return A jobb alsó pont.
     */
    public static Position getBottomRightPoint(Position... points) {
        Position p = new Position(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (Position point : points) {
            if (point.getX() >= p.getX() && point.getY() >= p.getY()) {
                p = point;
            }
        }

        return p;
    }
}
