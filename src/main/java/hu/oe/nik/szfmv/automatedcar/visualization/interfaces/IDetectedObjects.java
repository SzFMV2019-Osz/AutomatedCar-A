package hu.oe.nik.szfmv.automatedcar.visualization.interfaces;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;

import java.util.List;

public interface IDetectedObjects {
    List<IObject> SeenObjects = null;

    void detectObjects(List<IObject> objects);
}
