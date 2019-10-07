package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public class Control {
    private static final int rangeMax = 100;
    private static final int rangeMin = 0;
    private int step;
    private boolean isTriggered = false;
    public int currentValue = 0;

    public Control(int step) {
        this.step = step;
    }

    public void Increment() {
        if (currentValue + step <= rangeMax)
            currentValue += step;
    }

    public void Decrement() {
        if (currentValue - step >= rangeMin)
            currentValue -= step;
    }

    public void Listen() {
        if(isTriggered) {
            Increment();
        } else {
            Decrement();
        }
    }

    public int getValue() {
        return currentValue;
    }

    public void Trigger(boolean isTriggered) {
        this.isTriggered = isTriggered;
    }
}
