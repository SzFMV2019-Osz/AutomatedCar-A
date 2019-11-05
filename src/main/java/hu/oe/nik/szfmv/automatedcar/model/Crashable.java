package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;

public abstract class Crashable extends WorldObject implements ICrashable {

    public Crashable() {
        super();
    }

    public Crashable(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void crashed() {

    }
}
