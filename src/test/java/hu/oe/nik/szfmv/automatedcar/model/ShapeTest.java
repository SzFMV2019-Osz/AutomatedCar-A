package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShapeTest {

    @Test
    public void roadStraightShapeHasCorrectWidthAndHeight() {
        Road road = this.createRoad("road_2lane_straight");

        assertNotNull(road.getImage());
        assertNotNull(road.getPolygon());
        assertEquals(330, road.getPolygon().getBounds().width);
        assertEquals(350, road.getPolygon().getBounds().height);
    }

    @Test
    public void road90ShapeHasCorrectWidthAndHeight() {
        Road road = this.createRoad("road_2lane_90right");

        assertNotNull(road.getImage());
        assertNotNull(road.getPolygon());
        assertEquals(510, road.getPolygon().getBounds().width);
        assertEquals(510, road.getPolygon().getBounds().height);
    }

    @Test
    public void road45ShapeHasCorrectWidthAndHegith() {
        Road road = this.createRoad("road_2lane_45right");

        assertNotNull(road.getImage());
        assertNotNull(road.getPolygon());
        assertEquals(380, road.getPolygon().getBounds().width);
        assertEquals(360, road.getPolygon().getBounds().height);
    }

    @Test
    public void roadTJunctionHasCorrectWidthAndHegith() {
        Road road = this.createRoad("road_2lane_tjunctionleft");

        assertNotNull(road.getImage());
        assertNotNull(road.getPolygon());
        assertEquals(865, road.getPolygon().getBounds().width);
        assertEquals(1400, road.getPolygon().getBounds().height);
    }

    @Test
    public void parking90ShapeThrowsException() {
        ParkingSpace space = new ParkingSpace();
        space.setImageFileName("parking_90");

        assertThrows(UnsupportedOperationException.class, () -> space.initShape());
    }

    @Test
    public void carShapeHasCorrectWidthAndHeight() {
        AutomatedCar car = new AutomatedCar(0, 0, "car_2_red");
        car.setReferencePosition(new Position(0, 0));

        assertNotNull(car.getImage());
        assertNotNull(car.getPolygon());
        assertEquals(90, car.getPolygon().getBounds().width);
        assertEquals(206, car.getPolygon().getBounds().height);
        assertEquals(2, car.getPosZ());
    }


    private Road createRoad(String fileName) {
        Road road = new Road();
        road.setPosition(new Position(0, 0));
        road.setZ(0);
        road.setRotation(0);
        road.setImageFileName(fileName);
        road.setReferencePosition(new Position(0, 0));
        road.initImage();
        road.initShape();

        return road;
    }
}
