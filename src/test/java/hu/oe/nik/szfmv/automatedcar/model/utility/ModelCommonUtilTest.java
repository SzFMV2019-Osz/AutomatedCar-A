package hu.oe.nik.szfmv.automatedcar.model.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ModelCommonUtilTest {

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
}
