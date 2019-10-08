package hu.oe.nik.szfmv.automatedcar.model.utility;

import hu.oe.nik.szfmv.automatedcar.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ModelCommonUtilTest {

    Position pointA;
    Position pointB;
    Position pointC;
    Position pointD;

    @BeforeEach
    public void init() {
        this.pointA = new Position(-1, 0);
        this.pointB = new Position(1, -1);
        this.pointC = new Position(0, 1);
        this.pointD = new Position(0, 0);

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
    public void pointInTriangle() {
        assertEquals(true, ModelCommonUtil.isPointInTriangle(this.pointA, this.pointB, this.pointC, this.pointD));
    }

    @Test
    public void pointNotInTriangle() {
        assertEquals(false, ModelCommonUtil.isPointInTriangle(this.pointA, this.pointC, this.pointD, this.pointB));
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
}
