package hu.oe.nik.szfmv.automatedcar.model.utility;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.*;

public class ModelCommonUtilTest {

    @Test
    public void matrixShouldRotate0Degrees() {
        // https://en.wikipedia.org/wiki/Rotation_matrix#Common_rotations
        double angle = ModelCommonUtil.calculateRotationFromMatrix(0, 0, 0, 0);

        // assert statements
        assertEquals(0, angle, 0.1);
    }

    @Test
    public void matrixShouldRotate90Degrees() {
        // https://en.wikipedia.org/wiki/Rotation_matrix#Common_rotations
        double angle = ModelCommonUtil.calculateRotationFromMatrix(0, -1, 1, 0);

        // assert statements
        assertEquals(90, angle, 0.1);
    }

    @Test
    public void matrixShouldRotate180egrees() {
        // https://en.wikipedia.org/wiki/Rotation_matrix#Common_rotations
        double angle = ModelCommonUtil.calculateRotationFromMatrix(-1, 0, 0, -1);

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
    public void loadExistingImage() {
        try {
            BufferedImage image = ModelCommonUtil.loadObjectImage("car_2_red");
            assertNotNull(image);
        } catch (IOException e) {

        }
    }

    @Test
    public void loadNotExistingImage() {

            /*BufferedImage image;
                    = ModelCommonUtil.loadObjectImage("car_2_red");
        } catch (IOException e){

        }*/
    }
}
