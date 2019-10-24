package hu.oe.nik.szfmv.automatedcar.visualization.debug;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.visualization.Utils.DrawingInfo;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;


/**
 * Displays debugging-related information on the screen
 */
public class DebugViewer {


    private Graphics2D graphics2D;
    private DrawingInfo info = new DrawingInfo(Color.RED, 2);


    // sensor
    private Color sensorColor = Color.RED;
    private Position sensorPosition;
    private static final int SENSOR_DIMENSION = 8;
    private Position sensorTriangleUpperTip;
    private Position sensorTriangleLowerTip;


    /**
     * @return The information represening the color and borderline width of the object
     */
    public DrawingInfo getInfo() {
        return info;
    }

    /**
     * @param info The information represening the color and borderline width of the object
     */
    public void setInfo(DrawingInfo info) {
        this.info = info;
    }

    private boolean debuggerSwitchedOn = true;

    /**
     * @return If debugger is switched on or not
     */
    public boolean isDebuggerSwitchedOn() {
        return this.debuggerSwitchedOn;
    }

    /**
     * @param debuggerSwitchedOn Flag indicating if debugger is switched on or not
     */
    public void setDebuggerSwitchedOn(boolean debuggerSwitchedOn) {
        this.debuggerSwitchedOn = debuggerSwitchedOn;
    }


    public DebugViewer(Graphics2D graphics) {
        this.graphics2D = graphics;
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


    public void DrawSensorTriangle(Color color, AffineTransform t){
        if (debuggerSwitchedOn){
            updateSensorTrianglePosition();
            //graphics2D.setColor(color);
            Shape upperLine = new Line2D.Double(sensorPosition.getX(), sensorPosition.getY(), sensorTriangleUpperTip.getX(), sensorTriangleUpperTip.getY());
            Shape height = new Line2D.Double(sensorPosition.getX(), sensorPosition.getY(), sensorPosition.getX(), sensorPosition.getY()-200);
            Shape centralLine = new Line2D.Double(sensorTriangleUpperTip.getX(), sensorTriangleUpperTip.getY(), sensorTriangleLowerTip.getX(), sensorTriangleLowerTip.getY());
            Shape lowerLine = new Line2D.Double(sensorPosition.getX(), sensorPosition.getY(), sensorTriangleLowerTip.getX(), sensorTriangleLowerTip.getY());
            graphics2D.draw(t.createTransformedShape(upperLine));
            graphics2D.draw(t.createTransformedShape(centralLine));
            graphics2D.draw(t.createTransformedShape(lowerLine));
            graphics2D.draw(t.createTransformedShape(height));
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
           So, since tan(30) = 200/b, from here b = 200/tan(30).
         */
        int sensorLength = 100;
        int baseAngle = 60;

        Position sensorTriangleBasePoint = new Position(sensorPosition.getX(), sensorPosition.getY()-sensorLength);
        int sensorTriangleBaseHalfLength = (int)(sensorLength/Math.tan(baseAngle));
        sensorTriangleUpperTip = new Position(sensorTriangleBasePoint.getX()-sensorTriangleBaseHalfLength,sensorTriangleBasePoint.getY());
        sensorTriangleLowerTip = new Position(sensorTriangleBasePoint.getX() + sensorTriangleBaseHalfLength, sensorTriangleBasePoint.getY());
    }


    public void operateSensor(Graphics2D drawer, AutomatedCar car,  AffineTransform t){
        if(debuggerSwitchedOn){
            updateSensorPosition(car);
            Shape sensor = new Ellipse2D.Double(sensorPosition.getX(), sensorPosition.getY(), SENSOR_DIMENSION, SENSOR_DIMENSION);
            drawer.fill(t.createTransformedShape(sensor));
        }
    }
}
