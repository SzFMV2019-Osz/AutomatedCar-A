package hu.oe.nik.szfmv.automatedcar.visualization.debug;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.visualization.Utils.DrawingInfo;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;


/**
 * Displays debugging-related information on the screen
 */
public class DebugViewer {

    private Graphics2D graphics2D;
    private DrawingInfo info = new DrawingInfo(Color.RED, 2);
    private Color sensorColor = Color.RED;
    private Position sensorPosition;
    private static final int SENSOR_DIMENSION = 8;

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


    public void DrawSensorTriangle(int aX, int aY, int bX, int bY, int cX, int cY, Color color, AffineTransform t){
        if (debuggerSwitchedOn){
            graphics2D.setColor(color);
            graphics2D.drawLine(aX, aY, bX, bY);
            graphics2D.drawLine(aX, aY, cX, cY);
            graphics2D.drawLine(bX, bY, cX, cY);
        }
    }



    private void updateSensorPosition(AutomatedCar car){
        // the center of the car bumper is the same x as the car refX and hal the car refY
        // the sensor is going to be on the same layer (z) as the car
        sensorPosition = new Position(car.getReferenceX(), car.getReferenceY() - car.getHeight()/2);
    }


    public void operateSensor(Graphics2D drawer, AutomatedCar car,  AffineTransform t){
        updateSensorPosition(car);
        Shape sensor = new Ellipse2D.Double(sensorPosition.getX(), sensorPosition.getY(), SENSOR_DIMENSION, SENSOR_DIMENSION);
        drawer.fill(t.createTransformedShape(sensor));
    }
}
