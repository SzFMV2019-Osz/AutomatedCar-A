package hu.oe.nik.szfmv.automatedcar.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldObjectTest {

    WorldObject worldObject;

    @Before
    public void init() {
        worldObject = new WorldObject(100, 100, "car_2_white.png");
    }
/*
    @Test
    public void addition() {
        assertEquals(100, worldObject.getPosX());
    }
*/
}
