package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

/**
 * Tábla alaposztály.
 */
public class Sign extends WorldObject implements IStatic, ICrashable {

    public double getWeight() {
        return 0;
    }
}
