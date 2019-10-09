package hu.oe.nik.szfmv.automatedcar.visualization;


import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.visualization.debug.DebugViewer;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private final int width = 770;
    private final int height = 700;
    private int backgroundColor = 0xEEEEEE;
    private Gui parent;
    private IWorld world;


    /**
     * Initialize the course display
     *
     * @param pt parent Gui
     */
    CourseDisplay(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        this.width = this.world.getWidth();
        this.height = this.world.getHeight();

        setDoubleBuffered(true);
        setLayout(null);
        setBounds(0, 0, this.width, this.height);
        parent = pt;
    }


    /**
     * Inherited method that can paint on the JPanel.
     *
     * @param g     {@link Graphics} object that can draw to the canvas
     * @param world {@link World} object that describes the virtual world
     */
    private void paintComponent(Graphics g, WorldManager world) {

        g.drawImage(renderDoubleBufferedScreen(world), 0, 0, this);
    }

    /**
     * Rendering method to avoid flickering
     *
     * @param world {@link World} object that describes the virtual world
     * @return the ready to render doubleBufferedScreen
     */
    private BufferedImage renderDoubleBufferedScreen(WorldManager world) {
        BufferedImage doubleBufferedScreen = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) doubleBufferedScreen.getGraphics();
        Rectangle r = new Rectangle(0, 0, this.width, this.height);
        g2d.setPaint(new Color(backgroundColor));
        g2d.fill(r);

        drawObjects(g2d, world);

        return doubleBufferedScreen;
    }

    /**
     * Draw objects from the world.
     * @param world World object.
     */
    public void drawWorld(WorldManager world) {
        this.world = world.getWorld();
        this.backgroundColor = Integer.valueOf(this.world.getColor().replace("#","").toUpperCase(),16);
        paintComponent(getGraphics(), world);

    }

    /**
     * Calculates the offset which will be used to move every object to virtualy center the AutomatedCar.
     * @param car Automated car which needs to be centered.
     * @return Returns an array. [xOffset, yOffset].
     */
    private int[] getCarOffset(AutomatedCar car) {
        int[] offset = new int[2];

        offset[0] = (this.width / 2) - (car.getX() + (car.getWidth() / 2));
        offset[1] = (this.height / 2) - (car.getY() + (car.getHeight() / 2));

        return offset;
    }

    /**
     * Selects the AutomatedCar object from the list and returns with it.
     * @param objects WorldObjects in an array.
     * @return Returns the AutomatedCar object from the list.
     */
    private AutomatedCar getCarObject(List<WorldObject> objects) {
        AutomatedCar findCar = null;

        for(WorldObject item : objects) {
            if (item instanceof AutomatedCar) {
                findCar = (AutomatedCar)item;
                break;
            }
        }

        return findCar;
    }

    /**
     * Draw every object to the world. Center the car object and offset the others according to this.
     * @param g2d Buffered image.
     * @param world World object which will be drawn.
     */
    private void drawObjects(Graphics2D g2d, WorldManager world) {
        int[] offsets = getCarOffset(world.getAutomatedCar());

        DebugViewer viewer = new DebugViewer(g2d);
        AutomatedCar car = world.getAutomatedCar();
        for (IObject object : world.getAllObjectsInRectangle(
                new Position(0,0),
                new Position(this.world.getWidth(),this.world.getHeight()))) {
            Point2D refPoint;
            try {
                refPoint = new Point(object.getReferenceX(), object.getReferenceY());
            } catch (NullPointerException e){
                refPoint = new Point(0,0);
            }
            AffineTransform t = new AffineTransform();
            t.translate(object.getPosX() + offsets[0], object.getPosY() + offsets[1]);
            t.rotate(Math.toRadians(-object.getRotation()),refPoint.getX() + offsets[0],refPoint.getY() + offsets[1]);
            g2d.drawImage(object.getImage(), t, this);

            // todo: decide on how model will signal colors
            viewer.DrawPolygon(object.getX()+ offsets[0], object.getY() + offsets[1], object.getWidth(), object.getHeight());
        }

        // Draw car

        AffineTransform t1 = new AffineTransform();
        t1.translate(car.getPosX()+ offsets[0], car.getPosY()+ offsets[1]);
        g2d.drawImage(car.getImage(), t1, this);
        t1.rotate(Math.toRadians(car.getRotation()),offsets[0],offsets[1]);
        viewer.DrawSensorTriangle(50, 50, 300, 300, 350, 50, Color.GREEN);

    }
}
