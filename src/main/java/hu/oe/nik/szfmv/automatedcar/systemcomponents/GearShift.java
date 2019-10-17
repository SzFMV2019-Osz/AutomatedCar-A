package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public class GearShift {
    public enum POS {P, R, N, D}

    private int currValue = 0;

    public void Increment() {
        POS[] arr = POS.values();
        if (currValue < arr.length - 1)
            ++currValue;
    }

    public void Decrement() {
        if (currValue > 0)
            --currValue;
    }

    public POS GetCurrentState() {
        POS[] arr = POS.values();
        return arr[currValue];
    }
}
