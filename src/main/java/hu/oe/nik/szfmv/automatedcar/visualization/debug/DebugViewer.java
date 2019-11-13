package hu.oe.nik.szfmv.automatedcar.visualization.debug;

import hu.oe.nik.szfmv.automatedcar.visualization.Utils.DrawingInfo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.List;


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



}
