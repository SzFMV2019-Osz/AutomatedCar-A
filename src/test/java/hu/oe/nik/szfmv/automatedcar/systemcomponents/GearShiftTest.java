package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.GearShift;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GearShiftTest {

    GearShift gearShift;


    @Test
    void IncrementOnce() {
        gearShift = new GearShift();
        gearShift.Increment();
        assertEquals(GearShift.POS.R, gearShift.GetCurrentState());
    }

    @Test
    void Decrement() {
        gearShift = new GearShift();
        gearShift.Decrement();
        assertEquals(GearShift.POS.P, gearShift.GetCurrentState());
    }

    @Test
    void IncrementAtMax() {
        gearShift = new GearShift();
        gearShift.Increment();
        gearShift.Increment();
        gearShift.Increment();
        gearShift.Increment();
        gearShift.Increment();

        assertEquals(GearShift.POS.D, gearShift.GetCurrentState());
    }

}
