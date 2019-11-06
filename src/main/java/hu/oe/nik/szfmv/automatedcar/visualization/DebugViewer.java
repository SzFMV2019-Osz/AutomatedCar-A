package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.visualization.Utils.DrawingInfo;
import hu.oe.nik.szfmv.automatedcar.visualization.interfaces.IDebugColorable;
import hu.oe.nik.szfmv.automatedcar.visualization.interfaces.IDetectedObjects;
import hu.oe.nik.szfmv.automatedcar.visualization.interfaces.ISensorAreaInterface;
import hu.oe.nik.szfmv.automatedcar.visualization.interfaces.ISwitchableDebugViewer;

import java.awt.*;
import java.awt.geom.*;
import java.util.List;


/**
 * Displays debugging-related information on the screen
 */
public class DebugViewer implements IDebugColorable, ISwitchableDebugViewer, ISensorAreaInterface, IDetectedObjects {

    private static Color BASE_COLOR = Color.BLUE;
    private static Color SENSOR_TRIANGLE_BASE_COLOR = Color.RED;
    private Graphics2D graphics2D;
    private DrawingInfo info = new DrawingInfo(BASE_COLOR, 2);
    private boolean sensorTriangleDisplayed = false;
    private Color sensorTriangleColor = SENSOR_TRIANGLE_BASE_COLOR;


    // sensor
    private Color sensorColor = Color.RED;
    private Position sensorPosition;
    private static final int SENSOR_DIMENSION = 8;
    private Position sensorTriangleLeftTip;
    private Position sensorTriangleRightTip;

    //"seen" object
    public List<IObject> SeenObjects;

    /**
     * @return The information represening the color and borderline width of the object
     */
    DrawingInfo getInfo() {
        return info;
    }

    /**
     * @param info The information represening the color and borderline width of the object
     */
    void setInfo(DrawingInfo info) {
        this.info = info;
    }

    private boolean debuggerSwitchedOn = true;

    DebugViewer(Graphics2D graphics) {
        this.graphics2D = graphics;
    }

    public DebugViewer() {

    }

    /**
     * @param x The x of coordinate of the center of the object
     * @param y The y of coordinate of the center of the object
     * @param width The width of the object
     * @param height The height of the object
     */
    public void DrawPolygon(int x, int y, int width, int height, AffineTransform t, int[] offset, Shape s){
        if (debuggerSwitchedOn){
            graphics2D.setColor(info.getColor());
            graphics2D.setStroke(info.getThickness());

            // create a rectangle with the original data and draw the result of applying the transformation
            Rectangle rect = new Rectangle(x, y, width, height);
            graphics2D.draw(t.createTransformedShape(rect));
        }
    }

    public void DrawPolygon(int x, int y, int width, int height, Color color, AffineTransform t){
        if (debuggerSwitchedOn){
            graphics2D.setColor(color);
            graphics2D.setStroke(info.getThickness());
            graphics2D.drawRect(x, y, width, height);
        }
    }


    public void drawSensorTriangle(Color color, AffineTransform t, Position centerEdge, Position leftEdge, Position rightEdge){
        if (debuggerSwitchedOn){
            updateSensorTrianglePosition();
            graphics2D.setColor(color);

            Shape leftLine = new Line2D.Double(centerEdge.getX(), centerEdge.getY(), leftEdge.getX(), leftEdge.getY());
            Shape baseLine = new Line2D.Double(leftEdge.getX(), leftEdge.getY(), rightEdge.getX(), rightEdge.getY());
            Shape rightLine = new Line2D.Double(centerEdge.getX(), centerEdge.getY(), rightEdge.getX(), rightEdge.getY());

            graphics2D.draw(t.createTransformedShape(leftLine));
            graphics2D.draw(t.createTransformedShape(baseLine));
            graphics2D.draw(t.createTransformedShape(rightLine));
        }
    }



    private void updateSensorPosition(AutomatedCar car){
        // the center of the car bumper is the same x as the car refX and hal the car refY
        // the sensor is going to be on the same layer (z) as the car
        sensorPosition = new Position(car.getReferenceX(), car.getReferenceY() - car.getHeight()/2);
    }

    private void updateSensorTrianglePosition(){
        /* Calculating the positions of the sensor triangle using ANAL1 trigonometrikus szögfüggvény: tan(alpha) = a/b
           The edge of the triangle is always the exact position of the sensor body.
           Since the sensor area is 2 right-angled triangles and we know that the sensor sees in 60° (30° each),
           we know that alpha = 60 °. We also know the sensor position and the length of the central edge = 200.
           So, since tan(60) = 200/b, from here b = 200/tan(60).
         */
        int sensorLength = 400;
        int baseAngle = 60;

        Position sensorTriangleBasePoint = new Position(sensorPosition.getX(), sensorPosition.getY()-sensorLength);
        int sensorTriangleBaseHalfLength = (int)(sensorLength/Math.tan(Math.toRadians(baseAngle)));
        sensorTriangleLeftTip = new Position(sensorTriangleBasePoint.getX()-sensorTriangleBaseHalfLength,sensorTriangleBasePoint.getY());
        sensorTriangleRightTip = new Position(sensorTriangleBasePoint.getX() + sensorTriangleBaseHalfLength, sensorTriangleBasePoint.getY());
    }


    public void operateFrontalRadarSensor(Graphics2D drawer, AutomatedCar car, AffineTransform t){
        if(debuggerSwitchedOn){
            updateSensorPosition(car);
            drawSensorBody(drawer, t);
            updateSensorTrianglePosition();

            Position centerPosition = new Position(sensorPosition.getX(), sensorPosition.getY());
            Position leftPosition = new Position(sensorTriangleLeftTip.getX(), sensorTriangleLeftTip.getY());
            Position rightPosition = new Position(sensorTriangleRightTip.getX(), sensorTriangleRightTip.getY());

            drawSensorTriangle(sensorTriangleColor, t, centerPosition, leftPosition, rightPosition);
        }
    }

    public void detectObjects(List<IObject> objects){
        Polygon sensor = new Polygon();
        sensor.addPoint(sensorPosition.getX(), sensorPosition.getY());
        sensor.addPoint(sensorTriangleLeftTip.getX(), sensorTriangleLeftTip.getY());
        sensor.addPoint(sensorTriangleRightTip.getX(), sensorTriangleRightTip.getY());

        for (IObject object : objects){
            if (sensor.contains(object.getPosX(), object.getPosY())){
                SeenObjects.add(object);
            }
        }
    }

    private void drawSensorBody(Graphics2D drawer, AffineTransform t) {
        Shape sensor = new Ellipse2D.Double(sensorPosition.getX(), sensorPosition.getY(), SENSOR_DIMENSION, SENSOR_DIMENSION);
        drawer.fill(t.createTransformedShape(sensor));
    }

    @Override
    public Color getDebugColor() {
        return sensorColor;
    }

    @Override
    public void setColor(Color debugColor) {
        sensorColor = debugColor;
    }

    @Override
    public boolean getState() {
        return this.debuggerSwitchedOn;
    }

    @Override
    public void setState(boolean desiredState) {
        this.debuggerSwitchedOn = desiredState;

    }

    @Override
    public void setSelected(boolean state) {
        this.sensorTriangleDisplayed = state;
    }

    @Override
    public boolean getSelected() {
        return this.sensorTriangleDisplayed;
    }

    @Override
    public void setStatus(boolean switchedOn) {
        debuggerSwitchedOn = switchedOn;
    }

    @Override
    public void setSensorTriangleColor(Color color) {
        this.sensorTriangleColor = color;
    }
}
