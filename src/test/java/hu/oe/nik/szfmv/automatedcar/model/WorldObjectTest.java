package hu.oe.nik.szfmv.automatedcar.model;

import org.junit.Before;

public class WorldObjectTest {

    WorldObject worldObject;

    @Before
    public void init() {
        worldObject = new WorldObject(100, 100, "car_2_white.png");
    }
}
