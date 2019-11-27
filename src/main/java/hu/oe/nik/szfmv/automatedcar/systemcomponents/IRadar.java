package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.model.Position;

public interface IRadar extends IDetectedObjects, IRadarClosestObject {
    Position getSensorPosition();
    Position getRadarAreaLeftTip();
    Position getRadarAreaRightTip();
}
