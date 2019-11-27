package hu.oe.nik.szfmv.automatedcar.visualization.interfaces;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;

import java.awt.*;

public interface ISensorAreaInterface {
    void setSelected(boolean state);
    boolean getSelected();
    void setStatus(boolean switchedOn);
    void setSensorTriangleColor(Color color);
}
