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
            // TODO: implement other road shapes
            default:
                this.roadShapeStraight();
                break;
        }
    }

    private void roadShapeStraight() {
        this.polygon = new Rectangle(this.BORDER, 0, this.width - this.BORDER, this.height);
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
        return new Polygon(new int[]{323, 303, 233, 126, -26, -187}, new int[]{0, -157, -293, -407, -487, -512}, 6);
    }

    private Polygon createSmall45Arc() {
        return new Polygon(new int[]{12, 0, -41}, new int[]{0, -73, -131}, 3);
    }

    private Polygon createMiddle45Arc() {
        return new Polygon(new int[]{176, 162, 132, 74}, new int[]{0, -97, -169, -247}, 4);
    }

    private Polygon createBig45Arc() {
        return new Polygon(new int[]{338, 321, 275, 189}, new int[]{0, -131, -247, -360}, 4);
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
