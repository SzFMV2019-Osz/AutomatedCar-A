package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IBackground;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IStatic;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;

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
        this.polygons.add(new Line2D.Float(this.BORDER, 0, this.BORDER , this.height));
        this.polygons.add(new Line2D.Float(this.width / 2, 0, this.width/ 2, this.height));
        this.polygons.add(new Line2D.Float(this.width - this.BORDER, 0, this.width - this.BORDER , this.height));
    }

    private void roadShape90Left() {
        Path2D.Float smallArc = this.createSmall90Arc();
        Path2D.Float middleArc = this.createMiddle90Arc();
        Path2D.Float bigArc = this.createBig90Arc();

        this.polygons.add(bigArc);
        this.polygons.add(middleArc);
        this.polygons.add(smallArc);
    }

    private void roadShape90Right() {
        this.roadShape90Left();
        this.polygons = this.mirrorList(this.width, this.polygons);
    }

    private void roadShape45Left() {
        Path2D.Float smallArc = this.createSmall45Arc();
        Path2D.Float middleArc = this.createMiddle45Arc();
        Path2D.Float bigArc = this.createBig45Arc();

        this.polygons.add(bigArc);
        this.polygons.add(middleArc);
        this.polygons.add(smallArc);
    }

    private void roadShape45Right() {
        this.roadShape45Left();
        this.polygons = this.mirrorList(this.width, this.polygons);
    }

    private Path2D.Float createSmall90Arc() {
        return this.createArc(new int[]{185, 155, 100, 0}, new int[]{525, 420, 368, 340}, 4);
    }

    private Path2D.Float createMiddle90Arc() {
        return this.createArc(new int[]{350, 323, 255, 144, 0}, new int[]{525, 393, 287, 207, 175}, 5);
    }

    private Path2D.Float createBig90Arc() {
        return this.createArc(new int[]{510, 474, 422, 288, 169, 0}, new int[]{525, 329, 236, 102, 42, 15}, 6);
    }

    private Path2D.Float createSmall45Arc() {
        return this.createArc(new int[]{63, 51, 10}, new int[]{371, 303, 240}, 3);
    }

    private Path2D.Float createMiddle45Arc() {
        return this.createArc(new int[]{225, 212, 183, 122}, new int[]{371, 275, 202, 124}, 4);
    }

    private Path2D.Float createBig45Arc() {
        return this.createArc(new int[]{388, 371, 321, 239}, new int[]{371, 238, 120, 0}, 4);
    }

    private void roadShapeTJunctionRight() {
        this.polygons.add(new Line2D.Float(this.BORDER, 0, this.BORDER, this.height));
        this.polygons.add(new Line2D.Float(this.BORDER + this.ROAD_WIDTH / 2, 0, this.BORDER + this.ROAD_WIDTH / 2, this.height));

        this.polygons.add(new Line2D.Float(this.BORDER * 2 + this.ROAD_WIDTH, 0, this.BORDER * 2 + this.ROAD_WIDTH, 350));
        this.polygons.add(new Line2D.Float(this.BORDER * 2 + this.ROAD_WIDTH, 1050, this.BORDER * 2 + this.ROAD_WIDTH, this.height));

        this.polygons.add(new Line2D.Float(525, 540, 875, 540));
        this.polygons.add(new Line2D.Float(525, 700, 875, 700));
        this.polygons.add(new Line2D.Float(525, 860, 875, 860));

        this.polygons.add(this.createArc(new int[]{340, 420, 524}, new int[]{350, 450, 536}, 3));
        this.polygons.add(this.createArc(new int[]{340, 420, 524}, new int[]{1050, 946, 868}, 3));
    }

    private void roadShapeTJunctionLeft() {
        this.roadShapeTJunctionRight();
        this.polygons = mirrorList(this.width, this.polygons);
    }

    private void roadShapeCrossroad() {
        int x = 535;
        this.polygons.add(new Line2D.Float(x, 0, x, this.height));
        this.polygons.add(new Line2D.Float(x + this.ROAD_WIDTH / 2, 0, x + this.ROAD_WIDTH / 2, this.height));

        this.polygons.add(new Line2D.Float(x * 2 + this.ROAD_WIDTH, 0, x * 2 + this.ROAD_WIDTH, 350));
        this.polygons.add(new Line2D.Float(x * 2 + this.ROAD_WIDTH, 1050, x * 2 + this.ROAD_WIDTH, this.height));

        this.polygons.add(new Line2D.Float(x + 525, 540, x + 875, 540));
        this.polygons.add(new Line2D.Float(x + 525, 700, x + 875, 700));
        this.polygons.add(new Line2D.Float(x + 525, 860, x + 875, 860));

        this.polygons.add(new Line2D.Float(0, 540, 345, 540));
        this.polygons.add(new Line2D.Float(0, 700, 345, 700));
        this.polygons.add(new Line2D.Float(0, 860, 345, 860));

        this.polygons.add(this.createArc(new int[]{x + 340, x + 420, x + 524}, new int[]{350, 450, 536}, 3));
        this.polygons.add(this.createArc(new int[]{x + 340, x + 420, x + 524}, new int[]{1050, 946, 868}, 3));

        this.polygons.add(this.createArc(new int[]{348, 450, 537}, new int[]{350, 450, 536}, 3));
        this.polygons.add(this.createArc(new int[]{348, 450, 537}, new int[]{1050, 946, 868}, 3));
    }

    private void roadShapeRotary() {
        this.polygons.add(new Rectangle(0, -this.BORDER, this.width, -this.ROAD_WIDTH));

        this.polygons.add(this.createCrossroadRoad());
        this.polygons.add(this.createEllipses());
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

    private Path2D.Float createArc(int[] x, int[] y, int n){
        Path2D.Float arc = new Path2D.Float();
        arc.moveTo(x[0], y[0]);
        for (int i = 1; i < n; i++){
            arc.lineTo(x[i], y[i]);
        }

        return arc;
    }

    private static List<Shape> mirrorList(double x, List<Shape> objects){
        List<Shape> mirroredObjects = new ArrayList<>();

        for(Shape object : objects){
            mirroredObjects.add(mirrorAlongX(x, object));
        }

        return mirroredObjects;
    }

    private static Shape mirrorAlongX(double x, Shape shape) {
        AffineTransform at = new AffineTransform();
        at.translate(x, 0);
        at.scale(-1, 1);
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
