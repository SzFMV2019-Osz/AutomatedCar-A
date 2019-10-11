package hu.oe.nik.szfmv.automatedcar.model.utility;

import hu.oe.nik.szfmv.automatedcar.model.Position;

import org.apache.commons.lang3.StringUtils;

import java.awt.Point;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
     * Visszaadja, hogy az adott shape benne van-e a másikban.
     * @param shape Adott shape.
     * @param whereIn Shape amiben keressük.
     * @return Igaz, ha teljesen vagy részletesen benne van, hamis ha nincs bent vagy csak a szélével érintkezik.
     */
    public static boolean isShapeInPolygon(Shape shape, Shape whereIn) {
        return (whereIn.intersects(shape.getBounds()) || whereIn.contains(shape.getBounds()));
    }

    /**
     * Visszaadja, hogy a pont a shapere belül esik-e.
     * @param shape Adott shape.
     * @param point Point amire nézünk.
     * @return Igaz ha a polygonon belül van a pont, hamis ha rajta kívül vagy a vonalán.
     */
    public static boolean isShapeOnPoint(Shape shape, Point point) {
        return shape.contains(point);
    }

    public static Position getTopLeftPoint(Position... points) {
        Position p = new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (Position point : points) {
            p.setX(Math.min(point.getX(), p.getX()));
            p.setY(Math.min(point.getY(), p.getY()));
        }

        return p;
    }

    public static Position getBottomRightPoint(Position... points) {
        Position p = new Position(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (Position point : points) {
            p.setX(Math.max(point.getX(), p.getX()));
            p.setY(Math.max(point.getY(), p.getY()));
        }

        return p;
    }
}
