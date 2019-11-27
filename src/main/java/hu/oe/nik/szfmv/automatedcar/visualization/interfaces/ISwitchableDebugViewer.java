package hu.oe.nik.szfmv.automatedcar.visualization.interfaces;

public interface ISwitchableDebugViewer {

    boolean getState();
    void setState(boolean desiredState);
}
