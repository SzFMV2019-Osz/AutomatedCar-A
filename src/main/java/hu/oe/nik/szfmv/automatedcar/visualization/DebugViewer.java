package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.visualization.Utils.DrawingInfo;
import hu.oe.nik.szfmv.automatedcar.visualization.interfaces.IDebugColorable;
import hu.oe.nik.szfmv.automatedcar.visualization.interfaces.ISensorAreaInterface;
import hu.oe.nik.szfmv.automatedcar.visualization.interfaces.ISwitchableDebugViewer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;


/**
 * Displays debugging-related information on the screen
 */
public class DebugViewer implements IDebugColorable, ISwitchableDebugViewer, ISensorAreaInterface {

    private static Color BASE_COLOR = Color.BLUE;
    private Graphics2D graphics2D;
    private DrawingInfo info = new DrawingInfo(BASE_COLOR, 2);
    private boolean sensorTriangleDisplayed = false;
    private Color sensorTriangleColor = BASE_COLOR;

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
    void DrawPolygon(int x, int y, int width, int height, AffineTransform t, int[] offset, Shape s){
        if (debuggerSwitchedOn){
            graphics2D.setColor(getDebugColor());
            graphics2D.setStroke(info.getThickness());

            // create a rectangle with the original data and draw the result of applying the transformation
            Rectangle rect = new Rectangle(x, y, width, height);
            graphics2D.draw(t.createTransformedShape(rect));
        }
    }

    void DrawPolygon(int x, int y, int width, int height, AffineTransform t){
        if (debuggerSwitchedOn){
            graphics2D.setColor(getDebugColor());
            graphics2D.setStroke(info.getThickness());
            graphics2D.drawRect(x, y, width, height);
        }
    }


    void DrawSensorTriangle(int aX, int aY, int bX, int bY, int cX, int cY, Color color, AffineTransform t){
        if (debuggerSwitchedOn && sensorTriangleDisplayed){
            graphics2D.setColor(color);
            graphics2D.drawLine(aX, aY, bX, bY);
            graphics2D.drawLine(aX, aY, cX, cY);
            graphics2D.drawLine(bX, bY, cX, cY);
        }
    }

    @Override
    public Color getDebugColor() {
        return info.getColor();
    }

    @Override
    public void setColor(Color debugColor) {
        info.setColor(debugColor);
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
    public void setSensorTriangleColor(Color color) {
        this.sensorTriangleColor = color;
    }
}
