package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShapeTest {

    @Test
    public void roadStraightShapeHasCorrectWidthAndHeight() {
        Road road = this.createRoad("road_2lane_straight");

        assertNotNull(road.getImage());
        assertNotNull(road.getPolygons());
        assertNotNull(road.getPolygons().get(0));
    }

    @Test
    public void road90ShapeHasCorrectWidthAndHeight() {
        Road road = this.createRoad("road_2lane_90right");

        assertNotNull(road.getImage());
        assertNotNull(road.getPolygons());
        assertNotNull(road.getPolygons().get(0));
    }

    @Test
    public void road45ShapeHasCorrectWidthAndHegith() {
        Road road = this.createRoad("road_2lane_45right");

        assertNotNull(road.getImage());
        assertNotNull(road.getPolygons());
        assertNotNull(road.getPolygons().get(0));
    }

    @Test
    public void roadTJunctionHasCorrectWidthAndHegith() {
        Road road = this.createRoad("road_2lane_tjunctionleft");

        assertNotNull(road.getImage());
        assertNotNull(road.getPolygons());
        assertNotNull(road.getPolygons().get(0));
    }

    @Test
    public void parking90ShapeThrowsException() {
        ParkingSpace space = new ParkingSpace();
        space.setImageFileName("parking_90");

        assertDoesNotThrow(() -> space.initShape());
    }

    @Test
    public void carShapeHasCorrectWidthAndHeight() {
        AutomatedCar car = new AutomatedCar(0, 0, "car_2_red");
        car.setReferencePosition(new Position(0, 0));

        assertNotNull(car.getImage());
        assertNotNull(car.getPolygons());
        assertNotNull(car.getPolygons().get(0));
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
