package hu.oe.nik.szfmv.automatedcar.visualization.debug;

import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.visualization.Utils.DrawingInfo;

import java.awt.*;
import java.awt.geom.AffineTransform;


/**
 * Displays debugging-related information on the screen
 */
public class DebugViewer {

    private Graphics2D graphics2D;
    private DrawingInfo info = new DrawingInfo(Color.RED, 2);

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
     * @param x      The x of coordinate of the center of the object
     * @param y      The y of coordinate of the center of the object
     * @param width  The width of the object
     * @param height The height of the object
     */
    public void DrawPolygon(IObject object, AffineTransform t) {
        if (debuggerSwitchedOn) {
            graphics2D.setColor(info.getColor());
            graphics2D.setStroke(info.getThickness());

            // create a rectangle with the original data and draw the result of applying the transformation
            Shape newShape = t.createTransformedShape(object.getPolygon());
            graphics2D.draw(newShape);
            newShape = null;
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
}
