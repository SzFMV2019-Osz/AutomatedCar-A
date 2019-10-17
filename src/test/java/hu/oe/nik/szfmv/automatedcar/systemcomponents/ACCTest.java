package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ACCTest extends ACC {

    ACC accTest = new ACC();

    @Test
    void set() {
        int speed = 40;
        accTest.Set(speed);
        assertEquals(accTest.referenceSpeed, speed);
    }

    @Test
    void resume() {
        accTest.Resume();
        assertTrue(accTest.isOn);
    }

    @Test
    void plus() {

        accTest.Plus();
        assertEquals(accTest.referenceSpeed, 60);
    }

    @Test
    void plusaroundMaxValue() {
        accTest.Set(160);
        accTest.Plus();
        assertEquals(accTest.referenceSpeed, 160);
    }

    @Test
    void minus() {
        accTest.Minus();
        assertEquals(accTest.referenceSpeed, 40);
    }

    @Test
    void minuaroundMinValue() {
        accTest.Set(30);
        accTest.Minus();
        assertEquals(accTest.referenceSpeed, 30);
    }

    @Test
    void timeGapSetter() {
        accTest.TimeGapSetter();
        assertEquals(accTest.ReturnTimeGap(), 1.0);
    }

    @Test
    void timeGapSetterReset() {
        accTest.TimeGapSetter();
        accTest.TimeGapSetter();
        accTest.TimeGapSetter();
        accTest.TimeGapSetter();
        assertEquals(accTest.ReturnTimeGap(), 0.8);
    }

    @Test
    void returnTimeGap() {
        double returned = accTest.ReturnTimeGap();
        assertEquals(returned, 0.8);
    }
}