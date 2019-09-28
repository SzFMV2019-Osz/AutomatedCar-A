package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IDynamic;

public class Car extends WorldObject implements ICrashable, IDynamic {

    public Car(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    @Override
    public double getWeight() {
        return 0;
    }
}
