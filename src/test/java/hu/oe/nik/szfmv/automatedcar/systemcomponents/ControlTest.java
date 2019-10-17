package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlTest {
    int step = 10;
    Control pedal = new Control(step);

    @Test
    void increment() {
        int oldValue = pedal.currentValue;
        pedal.Increment();
        assertEquals(pedal.currentValue, oldValue + step);
    }

    @Test
    void decrement() {
        int oldValue = pedal.currentValue;
        pedal.Increment();
        pedal.Increment();
        pedal.Decrement();
        assertEquals(pedal.currentValue, oldValue + step);
    }

    @Test
    public void Increment_toLimit() {
        for (int i = 0; i < 11; i++) {
            pedal.Increment();
        }
        assertEquals(pedal.currentValue, 100);
    }

    @Test
    public void Decrement_toLimit() {
        int oldValue = pedal.currentValue;
        pedal.Decrement();
        assertEquals(pedal.currentValue, oldValue);
    }
}
