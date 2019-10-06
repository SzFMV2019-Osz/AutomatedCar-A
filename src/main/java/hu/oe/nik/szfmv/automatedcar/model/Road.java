package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IBackground;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;

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
    private final int ROAD_WIDTH = 325;
    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape() {
        switch (this.imageFileName) {
            case Consts.RES_IDENTIFIER_ROAD_STRAIGHT:
                this.RoadShapeStraight();
                break;
            case Consts.RES_IDENTIFIER_ROAD_90_LEFT:
                this.RoadShape90Left();
                break;
            case Consts.RES_IDENTIFIER_ROAD_90_RIGHT:
                this.RoadShape90Right();
                break;
            case Consts.RES_IDENTIFIER_ROAD_45_LEFT:
                this.RoadShape45Left();
                break;
            case Consts.RES_IDENTIFIER_ROAD_45_RIGHT:
                this.RoadShape45Right();
                break;
            case Consts.RES_IDENTIFIER_ROAD_T_JUNCTION_LEFT:
                this.RoadShapeTJunctionLeft();
                break;
            case Consts.RES_IDENTIFIER_ROAD_T_JUNCTION_RIGHT:
                this.RoadShapeTJunctionRight();
                break;
            default:
                this.RoadShapeStraight();
                break;
        }
    }

    private void RoadShapeStraight() {
        this.polygon = new Rectangle(BORDER, 0, this.width - BORDER, this.height);
        Rectangle line = new Rectangle(this.width / 2 - 1, 0, 2, this.height);
        ((Rectangle)this.polygon).add(line);
    }

    private void RoadShape90Left() {
        Polygon smallArc = this.CreateSmall90Arc();
        Polygon middleArc = this.CreateMiddle90Arc();
        Polygon bigArc = this.CreateBig90Arc();

        this.polygon = new Area(bigArc);
        ((Area) this.polygon).add(new Area(middleArc));
        ((Area) this.polygon).add(new Area(smallArc));
    }

    private void RoadShape90Right() {
        this.RoadShape90Left();
        this.polygon = MirrorAlongX(525, this.polygon);
    }

    private void RoadShape45Left() {
        Polygon smallArc = this.CreateSmall45Arc();
        Polygon middleArc = this.CreateMiddle45Arc();
        Polygon bigArc = this.CreateBig45Arc();

        this.polygon = new Area(bigArc);
        ((Area) this.polygon).add(new Area(middleArc));
        ((Area) this.polygon).add(new Area(smallArc));
    }

    private void RoadShape45Right() {
        this.RoadShape45Left();
        this.polygon = MirrorAlongX(401, this.polygon);
    }

    private Polygon CreateSmall90Arc() {
        return new Polygon(new int[]{0, -27, -80, -187}, new int[]{0, -100, -150, -187}, 4);
    }

    private Polygon CreateMiddle90Arc() {
        return new Polygon(new int[]{163, 138, 68, -41, -187}, new int[]{0, -135, -240, -320, -350}, 5);
    }

    private Polygon CreateBig90Arc() {
        return new Polygon(new int[]{323, 303, 233, 126, -26, -187}, new int[]{0, -157, -293, -407, -487, -512}, 6);
    }

    private Polygon CreateSmall45Arc() {
        return new Polygon(new int[]{12, 0, -41}, new int[]{0, -73, -131}, 3);
    }

    private Polygon CreateMiddle45Arc() {
        return new Polygon(new int[]{176, 162, 132, 74}, new int[]{0, -97, -169, -247}, 4);
    }

    private Polygon CreateBig45Arc() {
        return new Polygon(new int[]{338, 321, 275, 189}, new int[]{0, -131, -247, -360}, 4);
    }

    private void RoadShapeTJunctionRight() {
        this.polygon = new Rectangle(BORDER, 0, ROAD_WIDTH, this.height);
        Rectangle line = new Rectangle(this.width / 2 - 1, 0, 2, this.height);
        Rectangle secondRoad = new Rectangle(BORDER + ROAD_WIDTH, (this.height / 2) - (ROAD_WIDTH / 2), this.width - (BORDER + ROAD_WIDTH), ROAD_WIDTH);
        ((Rectangle)this.polygon).add(line);
        ((Rectangle)this.polygon).add((secondRoad));
    }

    private void RoadShapeTJunctionLeft() {
        this.RoadShapeTJunctionRight();
        this.polygon = MirrorAlongX(875, this.polygon);
    }

    private static Shape MirrorAlongX(double x, Shape shape) {
        AffineTransform at = new AffineTransform();
        at.translate(x, 0);
        at.scale(-1, 1);
        at.translate(-x, 0);
        return at.createTransformedShape(shape);
    }
}
