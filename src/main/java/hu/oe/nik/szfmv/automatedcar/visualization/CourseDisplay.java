package hu.oe.nik.szfmv.automatedcar.visualization;


import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.exceptions.CrashException;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.visualization.Utils.DrawingInfo;
import hu.oe.nik.szfmv.automatedcar.visualization.debug.DebugViewer;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.InputPacket;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private int width = 770;
    private int height = 700;
    private int backgroundColor = 0xEEEEEE;
    private Gui parent;
    private IWorld world;
    private int renderDistance = 300;
    private boolean showDebugSensors;
    private InputPacket inputPacket;

    /**
     * Initialize the course display
     *
     * @param pt parent Gui
     */
    public CourseDisplay(Gui pt, IWorld world) {
        // Not using any layout manager, but fixed coordinates
        this.world = world;
        //this.width = this.world.getWidth();
        //this.height = this.world.getHeight();

        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.setBounds(0, 0, this.width, this.height);
        this.parent = pt;
        showDebugSensors=false;

    }


    /**
     * Inherited method that can paint on the JPanel.
     *
     * @param g     {@link Graphics} object that can draw to the canvas
     * @param world {@link World} object that describes the virtual world
     */
    private void paintComponent(Graphics g, WorldManager world) throws CrashException {

        g.drawImage(this.renderDoubleBufferedScreen(world), 0, 0, this);
    }

    /**
     * Rendering method to avoid flickering
     *
     * @param world {@link World} object that describes the virtual world
     * @return the ready to render doubleBufferedScreen
     */
    private BufferedImage renderDoubleBufferedScreen(WorldManager world) throws CrashException {
        BufferedImage doubleBufferedScreen = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) doubleBufferedScreen.getGraphics();
        Rectangle r = new Rectangle(0, 0, this.width, this.height);
        g2d.setPaint(new Color(this.backgroundColor));
        g2d.fill(r);

        this.drawObjects(g2d, world);

        return doubleBufferedScreen;
    }

    /**
     * Draw objects from the world.
     *
     * @param world World object.
     */
    public void drawWorld(WorldManager world, InputPacket inputPacket) throws CrashException {
        this.world = world.getWorld();
        this.backgroundColor = Integer.valueOf(this.world.getColor().replace("#", "").toUpperCase(),
                16);
        this.inputPacket=inputPacket;
        this.paintComponent(this.getGraphics(), world);

    }

    /**
     * Calculates the offset which will be used to move every object to virtualy center the AutomatedCar.
     *
     * @param car Automated car which needs to be centered.
     * @return Returns an array. [xOffset, yOffset].
     */
    private int[] getCarOffset(AutomatedCar car) {
        int[] offset = new int[2];

        offset[0] = (this.width / 2) - (car.getPosX() - car.getReferenceX() + (car.getWidth() / 2));
        offset[1] = (this.height / 2) - (car.getPosY() - car.getReferenceY() + (car.getHeight() / 2));

        return offset;
    }

    /**
     * Selects the AutomatedCar object from the list and returns with it.
     *
     * @param objects WorldObjects in an array.
     * @return Returns the AutomatedCar object from the list.
     */
    private AutomatedCar getCarObject(List<WorldObject> objects) {
        AutomatedCar findCar = null;

        for (WorldObject item : objects) {
            if (item instanceof AutomatedCar) {
                findCar = (AutomatedCar) item;
                break;
            }
        }

        return findCar;
    }

    /**
     * Draw every object to the world. Center the car object and offset the others according to this.
     *
     * @param g2d   Buffered image.
     * @param world World object which will be drawn.
     */
    private void drawObjects(Graphics2D g2d, WorldManager world) throws CrashException {
        int[] offsets = getCarOffset(world.getAutomatedCar());

        DebugViewer viewer = new DebugViewer(g2d);
        AutomatedCar car = world.getAutomatedCar();
        List<List<Shape>> sensedObjects = car.checkCamera(world, offsets[0], offsets[1]);
        List<List<Shape>> soundObjects = car.checkUltraSound(world, offsets[0], offsets[1]);
        viewer.setDebuggerSwitchedOn(this.inputPacket.getDebugOn());
        //draw world
        for (IObject object : world.getAllObjectsInRectangle(
                new Position(0 - this.renderDistance, 0 - this.renderDistance),
                new Position(this.width + this.renderDistance, this.height + this.renderDistance),
                offsets[0], offsets[1])) {
            AffineTransform t = object.getTransform(offsets[0], offsets[1]);

            g2d.drawImage(object.getImage(), t, this);

            // todo: decide on how model will signal colors

            viewer.DrawPolygon(object.getPolygons(offsets[0], offsets[1]));
        }

        // Draw car
        AffineTransform t1 = car.getTransform(offsets[0], offsets[1]);
        viewer.DrawPolygon(car.getPolygons(offsets[0], offsets[1]));
        viewer.setDebuggerSwitchedOn(this.inputPacket.getDebugCameraOn());
        g2d.drawImage(car.getImage(), t1, this);
        viewer.setInfo(new DrawingInfo(Color.BLUE, 4));
        viewer.DrawPolygon(car.getCameraTriangle(offsets[0], offsets[1]));
        for (List<Shape> shape : sensedObjects) {
            viewer.DrawPolygon(shape);
        }
        viewer.setDebuggerSwitchedOn(this.inputPacket.getDebugUltrasoundOn());
        //TODO: UltraSound shapes, get ultrasound offset
        viewer.setInfo(new DrawingInfo(Color.GREEN, 4));
        viewer.DrawPolygon(car.getUltraSoundTriangle(offsets[0], offsets[1]));

        // Set debug viewer
        viewer.operateFrontalRadarSensor(g2d, car, t1);
        viewer.detectObjects(world.getAllObjectsInRectangle(new hu.oe.nik.szfmv.automatedcar.model.Position(0,0),
                new hu.oe.nik.szfmv.automatedcar.model.Position(this.world.getWidth(),this.world.getHeight())));
        for (List<Shape> shape : soundObjects) {
            viewer.DrawPolygon(shape);
        }
        //viewer.DrawSensorTriangle(50, 50, 300, 300, 350, 50, Color.GREEN);

        car.checkCollisions(world, offsets[0], offsets[1]);
    }
}