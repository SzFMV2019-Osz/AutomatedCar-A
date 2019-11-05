package hu.oe.nik.szfmv.automatedcar.model.utility;

import hu.oe.nik.szfmv.automatedcar.model.Position;

import org.apache.commons.lang3.StringUtils;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Modelben használt általános funkciók utility osztálya.
 */
public final class ModelCommonUtil {

    private static final Random RND = new Random();

    /**
     * Betölt egy képet a nyersanyagok közül.
     * @param name Kép neve kiterjesztés nélkül (pl. bycicle).
     * @return Az adott kép {@link BufferedImage}-ként.
     * @throws IOException Ha nem olvasható a fájl/mappa, pl. jogosultságok hiányában.
     */
    public static BufferedImage loadObjectImage(String name) throws IOException {
        if (!StringUtils.endsWith(name, Consts.SUFFIX_IMAGE)) {
            name += Consts.SUFFIX_IMAGE;
        }
        return ImageIO.read(getFileFromName(name));
    }

    /**
     * @param fileName - kiterjesztéssel
     * @return betöltött fájl
     */
    public static File getFileFromName(String fileName) {
        return new File(ClassLoader.getSystemResource(fileName).getFile());
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
        Area areaA = new Area(whereIn);
        areaA.intersect(new Area(shape));
        return !areaA.isEmpty();
    }

    public static boolean isShapeInPolygonRoad(Shape shape, Shape whereIn) {
        // Line2D-knek 0 a widthje vagy heightja ezért nem adná vissza őket az intersect, ezért kerül rá correction
        return whereIn.getBounds2D().intersects(shape.getBounds2D().getX(),
                shape.getBounds2D().getY(), shape.getBounds2D().getWidth() + 1, shape.getBounds2D().getHeight() + 1)
                || whereIn.getBounds2D().contains(shape.getBounds2D());
    }

    /**
     * Visszaadja, hogy a pont a shapere belül esik-e.
     *
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

    /**
     * @param topLeftX bal felső sarok X koordinátája
     * @param topLeftY bal felső sarok Y koordinátája
     * @param width négyszög szélessége
     * @param height négyszög magasssága
     * @return generált négyszög
     */
    public static Rectangle createRectangle(int topLeftX, int topLeftY, int width, int height) {
        return new Rectangle(topLeftX, topLeftY, width, height);
    }

    /**
     * @return 1 és maxNumber között ad vissza számokat
     * @throws IllegalArgumentException, ha maxNumber < 1
     */
    public static int getRandom(int maxNumber) {
        if (maxNumber < 1) {
            throw new IllegalArgumentException(
                    MessageFormat.format(Consts.ERROR_GREATER_THAN_ONE, "Maximum"));
        }
        return RND.nextInt(maxNumber) + 1;
    }
}
