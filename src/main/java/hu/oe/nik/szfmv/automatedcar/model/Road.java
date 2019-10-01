package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IBackground;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

import java.awt.Rectangle;

/**
 * Út alaposztály.
 */
public class Road extends WorldObject implements IStatic, IBackground {

    private final int BORDER = 10;
    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape(){
        switch (this.imageFileName) {
            case "road_2lane_straight":
                StraighRoadShape();
                break;
            case "road_2lane_90left":
                break;
            case "road_2lane_90right":
                break;
            default:
                StraighRoadShape();
                break;
        }
    }

    private void StraighRoadShape(){
        this.polygon = new Rectangle(BORDER, 0, this.width - BORDER, this.height);
        Rectangle line = new Rectangle(this.width / 2 - 1, 0, 2, this.height);
        ((Rectangle)this.polygon).add(line);
    }
}
