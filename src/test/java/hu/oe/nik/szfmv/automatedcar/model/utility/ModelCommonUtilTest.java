package hu.oe.nik.szfmv.automatedcar.model.utility;

import static org.junit.jupiter.api.Assertions.assertFalse;

import hu.oe.nik.szfmv.automatedcar.model.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class ModelCommonUtilTest {

    private List<Pair<Double, Double>> angleCalculationParams;
    Position pointA;
    Position pointB;
    Position pointC;
    Position pointD;
    Polygon triangle;
    Rectangle rectangle;
    Polygon smallLine;
    Rectangle rectangleFarAway;
    Point point;
    int[] invalidNumbersForRandom = new int[] {0, -1, -235, -542};
    int[] properNumbersForRandom = new int[] {123, 1, 5, 86};

    @BeforeEach
    public void init() {
        this.pointA = new Position(-1, 0);
        this.pointB = new Position(1, -1);
        this.pointC = new Position(0, 1);
        this.pointD = new Position(0, 0);

        triangle = new Polygon();
        triangle.addPoint(-1, 2);
        triangle.addPoint(-1, -3);
        triangle.addPoint(2, 2);
        rectangle = new Rectangle(0, 0, 1, 2);
        smallLine = new Polygon();
        smallLine.addPoint(0,0);
        smallLine.addPoint(1,1);
        rectangleFarAway = new Rectangle(2, 2, 2 ,1);
        point = new Point(0,0);
        
        angleCalculationParams = new ArrayList<>();
        angleCalculationParams.add(new ImmutablePair(270d, 270d));
        angleCalculationParams.add(new ImmutablePair(85d, 85d));
        angleCalculationParams.add(new ImmutablePair(310d, -50d));
        angleCalculationParams.add(new ImmutablePair(222d, -138d));
    }

    @Test
    public void matrixShouldRotate0Degrees() {
        // https://en.wikipedia.org/wiki/Rotation_matrix#Common_rotations
        double angle = ModelCommonUtil.getRotationValue(0, 0, 0, 0);

        // assert statements
        assertEquals(0, angle, 0.1);
    }

    @Test
    public void matrixShouldRotate90Degrees() {
        // https://en.wikipedia.org/wiki/Rotation_matrix#Common_rotations
        double angle = ModelCommonUtil.getRotationValue(0, -1, 1, 0);

        // assert statements
        assertEquals(90, angle, 0.1);
    }

    @Test
    public void matrixShouldRotate180egrees() {
        // https://en.wikipedia.org/wiki/Rotation_matrix#Common_rotations
        double angle = ModelCommonUtil.getRotationValue(-1, 0, 0, -1);

        // assert statements
        assertEquals(180, angle, 0.1);
    }

    @Test
    public void matrixShouldRotate270egrees() {
        // https://en.wikipedia.org/wiki/Rotation_matrix#Common_rotations
        double angle = ModelCommonUtil.getRotationValue(0, 1, -1, 0);

        // assert statements
        assertEquals(270, angle, 0.1);
    }

    @Test
    public void loadExistingImage() throws IOException {
        BufferedImage image = ModelCommonUtil.loadObjectImage("car_2_red");
        assertNotNull(image);
    }

    @Test
    public void loadNotExistingImage() {
        assertThrows(NullPointerException.class, () -> ModelCommonUtil.loadObjectImage("car_2"));
    }

    @Test
    public void existingTopLeftPoint() {
        Position point = ModelCommonUtil.getTopLeftPoint(this.pointC, this.pointD);
        assertEquals(this.pointD.getX(), point.getX());
        assertEquals(this.pointD.getY(), point.getY());
    }

    @Test
    public void newTopLeftPoint() {
        Position point = ModelCommonUtil.getTopLeftPoint(this.pointA, this.pointB);
        assertEquals(-1, point.getX());
        assertEquals(-1, point.getY());
    }

    @Test
    public void existingBottomRightPoint() {
        Position point = ModelCommonUtil.getBottomRightPoint(this.pointC, this.pointA);
        assertEquals(this.pointC.getX(), point.getX());
        assertEquals(this.pointC.getY(), point.getY());
    }

    @Test
    public void newBottomRightPoint() {
        Position point = ModelCommonUtil.getBottomRightPoint(this.pointB, this.pointC);
        assertEquals(1, point.getX());
        assertEquals(1, point.getY());
    }

    @Test
    public void shapeInPolygon() {
        assertTrue(ModelCommonUtil.isShapeInPolygon(smallLine, triangle));
    }

    @Test
    public void shapeOnEdgeOfPolygon() {
        assertFalse(ModelCommonUtil.isShapeInPolygon(rectangleFarAway, rectangle));
    }

    @Test
    public void shapeIntersectsWithPolygon() {
        assertTrue(ModelCommonUtil.isShapeInPolygon(triangle, rectangle));
    }

    @Test
    public void shapeOutsidePolygon() {
        assertFalse(ModelCommonUtil.isShapeInPolygon(rectangle, rectangleFarAway));
    }

    @Test
    public void pointInPolygon() {
        assertTrue(ModelCommonUtil.isShapeOnPoint(triangle, point));
    }

    @Test
    public void pointOutsideOfPolygon() {
        assertFalse(ModelCommonUtil.isShapeOnPoint(rectangleFarAway, point));
    }

    @Test
    public void getRandom_ThrowsIllegalArgumentException_WhenCalledWith_NumbersLowerThanOne() {
        for (int num : invalidNumbersForRandom) {
            assertThrows(IllegalArgumentException.class, () -> ModelCommonUtil.getRandom(num));
        }
    }

    @Test
    public void getRandom_ReturnProperNumber_WhenCalledWith_NumbersGreaterOrEqualsOne() {
        for (int num : properNumbersForRandom) {
            int random = ModelCommonUtil.getRandom(num);
            assertTrue(random > 0);
            assertTrue(random <= num);
        }
    }
    
    @Test
    public void getRealAngle_ReturnProperAngle() {
        for (Pair<Double, Double> angles : angleCalculationParams) {
            assertEquals(angles.getKey(), ModelCommonUtil.getRealAngle(angles.getValue()));
        }
    }
}
