package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.ICrashable;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

/**
 * Fa objektum alaposztály.
 */
public class Tree extends WorldObject implements IStatic, ICrashable {

    public double getWeight() {
        //TODO
        return 0;
    }
}
