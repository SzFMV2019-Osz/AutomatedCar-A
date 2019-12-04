package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;

import java.util.List;

public interface IDetectedObjects {

    void setDetectedObjects(List<IObject> objects);
    List<IObject> getDetectedObjects();
}
