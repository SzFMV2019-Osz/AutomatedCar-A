package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IBackground;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;

import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

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
                this.RoadShapeStraight();
                break;
            case "road_2lane_90left":
                this.RoadShape90Left();
                break;
            case "road_2lane_90right":
                RoadShape90Right();
                break;
            default:
                this.RoadShapeStraight();
                break;
        }
    }

    private void RoadShapeStraight(){
        this.polygon = new Rectangle(BORDER, 0, this.width - BORDER, this.height);
        Rectangle line = new Rectangle(this.width / 2 - 1, 0, 2, this.height);
        ((Rectangle)this.polygon).add(line);
    }

    private void RoadShape90Left(){
        Polygon smallArc = this.CreateSmall90Arc();
        Polygon middleArc = this.CreateMiddle90Arc();
        Polygon bigArc = this.CreateBig90Arc();

        this.polygon = new Area(bigArc);
        ((Area) this.polygon).add(new Area(middleArc));
        ((Area) this.polygon).add(new Area(smallArc));
    }

    private void RoadShape90Right(){
        this.RoadShape90Left();
        this.polygon = mirrorAlongX(525, this.polygon);
    }

    private Polygon CreateSmall90Arc(){
        return new Polygon(new int[]{0, -27, -80, -187}, new int[]{0, -100, -150, -187}, 4);
    }

    private Polygon CreateMiddle90Arc(){
        return new Polygon(new int[]{163, 138, 68, -41, -187}, new int[]{0, -135, -240, -320, -350}, 5);
    }

    private Polygon CreateBig90Arc(){
        return new Polygon(new int[]{323, 303, 233, 126, -26, -187}, new int[]{0, -157, -293, -407, -487, -512}, 6);
    }

    private static Shape mirrorAlongX(double x, Shape shape)
    {
        AffineTransform at = new AffineTransform();
        at.translate(x, 0);
        at.scale(-1, 1);
        at.translate(-x, 0);
        return at.createTransformedShape(shape);
    }
}
