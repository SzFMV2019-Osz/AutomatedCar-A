package hu.oe.nik.szfmv.automatedcar.visualization;


import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private final int width = 1200;
    private final int height = 700;
    private final int backgroundColor = 0xEEEEEE;
    private Gui parent;


    /**
     * Initialize the course display
     *
     * @param pt parent Gui
     */
    CourseDisplay(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setDoubleBuffered(true);
        setLayout(null);
        setBounds(0, 0, width, height);
        parent = pt;
    }


    /**
     * Inherited method that can paint on the JPanel.
     *
     * @param g     {@link Graphics} object that can draw to the canvas
     * @param world {@link World} object that describes the virtual world
     */
    private void paintComponent(Graphics g, IWorld world, List<IObject> objects) {

        g.drawImage(renderDoubleBufferedScreen(world, objects), 0, 0, this);
    }

    /**
     * Rendering method to avoid flickering
     *
     * @param world {@link World} object that describes the virtual world
     * @return the ready to render doubleBufferedScreen
     */
    private BufferedImage renderDoubleBufferedScreen(IWorld world, List<IObject> objects) {
        BufferedImage doubleBufferedScreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) doubleBufferedScreen.getGraphics();
        Rectangle r = new Rectangle(0, 0, world.getWidth(), world.getHeight());
        g2d.setPaint(new Color(backgroundColor));
        g2d.fill(r);

        drawObjects(g2d, objects);

        return doubleBufferedScreen;
    }


    public void drawWorld(IWorld world, List<IObject> objects) {
        paintComponent(getGraphics(), world, objects);
    }

    private void drawObjects(Graphics2D g2d, List<IObject> objects) {

        int takenupwidth = 0;
        int takenupheight = 0;

        for (IObject object : objects) {
            AffineTransform t = new AffineTransform();
            t.translate(takenupwidth, takenupheight);
            t.rotate(object.getRotation());
            g2d.drawImage(object.getImage(), t, this);

            takenupwidth += 20;
            if (takenupwidth >= width) {
                takenupwidth = 0;
                takenupheight += 20;
            }
        }
    }
}
