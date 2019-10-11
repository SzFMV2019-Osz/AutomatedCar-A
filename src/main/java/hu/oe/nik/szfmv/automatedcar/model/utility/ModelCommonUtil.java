package hu.oe.nik.szfmv.automatedcar.model.utility;

import hu.oe.nik.szfmv.automatedcar.model.Position;

import org.apache.commons.lang3.StringUtils;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
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
     * Visszaadja, hogy az adott shape a polygonon belül van-e.
     * @param shape Adott shape.
     * @param whereIn Polygon amiben keressük.
     * @return Benne van? logikai kifejezés értéke.
     */
    public static boolean isShapeInPolygon(Shape shape, Polygon whereIn) {
        return (whereIn.intersects(shape.getBounds()) || whereIn.contains(shape.getBounds()));
    }

    public static boolean isShapeInRectangle(Shape shape, Rectangle rect) {
        return (rect.intersects(shape.getBounds()) || rect.contains(shape.getBounds()));
    }

    public static boolean isShapeOnPoint(Shape shape, Point point) {
        return shape.contains(point);
    }

    public static Position getTopLeftPoint(Position... points) {
        Position p = new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (Position point : points) {
            if (point.getX() <= p.getX() && point.getY() <= p.getY()) {
                p = point;
            }
        }

        return p;
    }

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
