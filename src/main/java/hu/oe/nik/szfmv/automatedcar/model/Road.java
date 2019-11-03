package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IBackground;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;

import javax.xml.bind.Unmarshaller;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * Út alaposztály.
 */
public class Road extends WorldObject implements IStatic, IBackground {

    private final int BORDER = 10;
    private final int ROAD_WIDTH = 325;
    private final int SMALL_CIRCLE_DIAMETER = 350;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initShape() {
        switch (this.imageFileName) {
            case Consts.RES_IDENTIFIER_ROAD_STRAIGHT:
                this.roadShapeStraight();
                break;
            case Consts.RES_IDENTIFIER_ROAD_90_LEFT:
                this.roadShape90Left();
                break;
            case Consts.RES_IDENTIFIER_ROAD_90_RIGHT:
                this.roadShape90Right();
                break;
            case Consts.RES_IDENTIFIER_ROAD_45_LEFT:
                this.roadShape45Left();
                break;
            case Consts.RES_IDENTIFIER_ROAD_45_RIGHT:
                this.roadShape45Right();
                break;
            case Consts.RES_IDENTIFIER_ROAD_T_JUNCTION_LEFT:
                this.roadShapeTJunctionLeft();
                break;
            case Consts.RES_IDENTIFIER_ROAD_T_JUNCTION_RIGHT:
                this.roadShapeTJunctionRight();
                break;
            case Consts.RES_IDENTIFIER_ROAD_CROSSROAD_1:
            case Consts.RES_IDENTIFIER_ROAD_CROSSROAD_2:
                this.roadShapeCrossroad();
                break;
            case Consts.RES_IDENTIFIER_ROAD_ROTARY:
                this.roadShapeRotary();
                break;
            default:
                this.roadShapeStraight();
                break;
        }
    }

    private void roadShapeStraight() {
        this.polygon = new Rectangle(this.BORDER, 0, this.width - (this.BORDER * 2), this.height);
        Rectangle line = new Rectangle(this.width / 2 - 1, 0, 2, this.height);
        ((Rectangle) this.polygon).add(line);
    }

    private void roadShape90Left() {
        Polygon smallArc = this.createSmall90Arc();
        Polygon middleArc = this.createMiddle90Arc();
        Polygon bigArc = this.createBig90Arc();

        this.polygon = new Area(bigArc);
        ((Area) this.polygon).add(new Area(middleArc));
        ((Area) this.polygon).add(new Area(smallArc));
    }

    private void roadShape90Right() {
        this.roadShape90Left();
        this.polygon = mirrorAlongX(525, this.polygon);
    }

    private void roadShape45Left() {
        Polygon smallArc = this.createSmall45Arc();
        Polygon middleArc = this.createMiddle45Arc();
        Polygon bigArc = this.createBig45Arc();

        this.polygon = new Area(bigArc);
        ((Area) this.polygon).add(new Area(middleArc));
        ((Area) this.polygon).add(new Area(smallArc));
    }

    private void roadShape45Right() {
        this.roadShape45Left();
        this.polygon = mirrorAlongX(401, this.polygon);
    }

    private Polygon createSmall90Arc() {
        return new Polygon(new int[]{0, -27, -80, -187}, new int[]{0, -100, -150, -187}, 4);
    }

    private Polygon createMiddle90Arc() {
        return new Polygon(new int[]{163, 138, 68, -41, -187}, new int[]{0, -135, -240, -320, -350}, 5);
    }

    private Polygon createBig90Arc() {
        return new Polygon(new int[]{323, 303, 233, 126, -26, -187}, new int[]{0, -157, -293, -407, -487, -510}, 6);
    }

    private Polygon createSmall45Arc() {
        return new Polygon(new int[]{12, 0, -41}, new int[]{0, -73, -131}, 3);
    }

    private Polygon createMiddle45Arc() {
        return new Polygon(new int[]{176, 162, 132, 74}, new int[]{0, -97, -169, -247}, 4);
    }

    private Polygon createBig45Arc() {
        return new Polygon(new int[]{339, 321, 275, 189}, new int[]{0, -131, -247, -360}, 4);
    }

    private void roadShapeTJunctionRight() {
        this.polygon = new Rectangle(this.BORDER, 0, this.ROAD_WIDTH, this.height);
        Rectangle line = new Rectangle(this.width / 2 - 1, 0, 2, this.height);
        int x = this.BORDER + this.ROAD_WIDTH;
        Rectangle secondRoad = new Rectangle(x, (this.height / 2) - (this.ROAD_WIDTH / 2), this.width - x, this.ROAD_WIDTH);
        ((Rectangle) this.polygon).add(line);
        ((Rectangle) this.polygon).add((secondRoad));
    }

    private void roadShapeTJunctionLeft() {
        this.roadShapeTJunctionRight();
        this.polygon = mirrorAlongX(875, this.polygon);
    }

    private void roadShapeCrossroad() {
        this.polygon = new Rectangle(0, -this.BORDER, this.width, -this.ROAD_WIDTH);
        ((Rectangle) this.polygon).add(this.createCrossroadRoad());
    }

    private void roadShapeRotary() {
        this.polygon = new Rectangle(0, -this.BORDER, this.width, -this.ROAD_WIDTH);

        ((Rectangle) this.polygon).add(this.createCrossroadRoad());
        ((Area) this.polygon).add(this.createEllipses());
    }

    private Rectangle createCrossroadRoad() {
        int x = (this.width + this.ROAD_WIDTH) / 2 - this.ROAD_WIDTH;
        int y = (this.height + this.ROAD_WIDTH) / 2;

        return new Rectangle(x, -y, this.ROAD_WIDTH, this.height);
    }

    private Area createEllipses() {
        Ellipse2D.Float bigEllipse = this.createBigEllipse();
        Ellipse2D.Float smallEllipse = this.createSmallEllipse();

        Area shape = new Area(bigEllipse);
        shape.subtract(new Area(smallEllipse));

        return shape;
    }

    private Ellipse2D.Float createBigEllipse() {
        int x = this.width / 2 - this.SMALL_CIRCLE_DIAMETER;
        int y = 0 - this.ROAD_WIDTH / 2 - this.SMALL_CIRCLE_DIAMETER;

        return new Ellipse2D.Float(x, y, this.SMALL_CIRCLE_DIAMETER * 2, this.SMALL_CIRCLE_DIAMETER * 2);
    }

    private Ellipse2D.Float createSmallEllipse() {
        int x = this.width / 2 - this.SMALL_CIRCLE_DIAMETER / 2;
        int y = 0 - this.ROAD_WIDTH / 2 - this.SMALL_CIRCLE_DIAMETER / 2;

        return new Ellipse2D.Float(x, y, this.SMALL_CIRCLE_DIAMETER, this.SMALL_CIRCLE_DIAMETER);
    }


    private static Shape mirrorAlongX(double x, Shape shape) {
        AffineTransform at = new AffineTransform();
        at.translate(x, 0);
        at.scale(-1, 1);
        at.translate(-x, 0);
        return at.createTransformedShape(shape);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        super.afterUnmarshal(u, parent);
        this.position.setZ(Consts.Z_ROAD);
    }
}