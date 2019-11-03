package hu.oe.nik.szfmv.automatedcar.visualization;


import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * CourseDisplay is for providing a viewport to the virtual world where the simulation happens.
 */
public class CourseDisplay extends JPanel {

    private int width = 770;
    private int height = 700;
    private int backgroundColor = 0xEEEEEE;
    private Gui parent;
    private IWorld world;

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

        offset[0] = (this.width / 2) - (car.getPosX() + (car.getWidth() / 2));
        offset[1] = (this.height / 2) - (car.getPosY() + (car.getHeight() / 2));

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
                new hu.oe.nik.szfmv.automatedcar.model.Position(0,0),
                new hu.oe.nik.szfmv.automatedcar.model.Position(this.world.getWidth(),this.world.getHeight()))) {
            Point2D refPoint;
            try {
                refPoint = new Point(object.getReferenceX(), object.getReferenceY());
            } catch (NullPointerException e){
                refPoint = new Point(0,0);
            }
            AffineTransform t = new AffineTransform();

            t.translate(object.getPosX() - refPoint.getX() + offsets[0], object.getPosY() - refPoint.getY() + offsets[1]);
            t.rotate(Math.toRadians(-object.getRotation()), refPoint.getX(), refPoint.getY());
            g2d.drawImage(object.getImage(), t, this);

            // todo: decide on how model will signal colors

            viewer.DrawPolygon(object.getReferenceX(), object.getReferenceY(), object.getWidth(), object.getHeight(), t, offsets,
                    object.getPolygon(0, 0));
        }

        // Draw car

        AffineTransform t1 = new AffineTransform();

        t1.translate(car.getPosX() - car.getReferenceX() + offsets[0], car.getPosY() - car.getReferenceY() + offsets[1]);
        t1.rotate(Math.toRadians(car.getRotation() + 90), car.getReferenceX(), car.getReferenceY());
        viewer.DrawPolygon(car.getReferenceX()-car.getWidth()/2, car.getReferenceY()-car.getHeight()/2, car.getWidth(), car.getHeight(), t1,
                offsets, car.getPolygon(0, 0));
        g2d.drawImage(car.getImage(), t1, this);

        // Set debug viewer
        viewer.operateSensor(g2d, car, t1);

        viewer.operateUltraSoundSensor(g2d, car, 1, t1);
        viewer.operateUltraSoundSensor(g2d, car, 2, t1);


        AffineTransform t2=new AffineTransform();
        t2.translate(car.getPosX()  - car.getReferenceX()+ offsets[0],  car.getPosY() - car.getReferenceY()+ offsets[1]);
        t2.rotate(Math.toRadians(car.getRotation()-90),car.getReferenceX(),car.getReferenceY());
        viewer.operateUltraSoundSensor(g2d, car, 1, t2);
        viewer.operateUltraSoundSensor(g2d, car, 2, t2);

        AffineTransform t3=new AffineTransform();
        t3.translate(car.getPosX()  - car.getReferenceX()*2+ offsets[0],  car.getPosY() - car.getReferenceY()/2+ offsets[1]);
        t3.rotate(Math.toRadians(car.getRotation()),car.getReferenceX()*2,car.getReferenceY()/2);

        viewer.operateUltraSoundSensor(g2d, car, 1, t3);


        AffineTransform t4=new AffineTransform();
        t4.translate(car.getPosX()  - car.getReferenceX()*2+ offsets[0],  car.getPosY() - car.getReferenceY()/2+ offsets[1]);
        t4.rotate(Math.toRadians(car.getRotation()-180),car.getReferenceX()*2,car.getReferenceY()/2);

        viewer.operateUltraSoundSensor(g2d, car, 1, t4);
        AffineTransform t5=new AffineTransform();
        t5.translate(car.getPosX()  - car.getReferenceX()*2+offsets[0],  car.getPosY() - car.getReferenceY()/2+ offsets[1]);
        t5.rotate(Math.toRadians(car.getRotation()),car.getReferenceX()*2,car.getReferenceY()/2);

        viewer.operateUltraSoundSensor(g2d, car, 4, t5);

        AffineTransform t6=new AffineTransform();
        t6.translate(car.getPosX()  - car.getReferenceX()*2+ offsets[0],  car.getPosY() - car.getReferenceY()/2+ offsets[1]);
        t6.rotate(Math.toRadians(car.getRotation()-180),car.getReferenceX()*2,car.getReferenceY()/2);

        viewer.operateUltraSoundSensor(g2d, car, 4, t6);



        viewer.detectObjects(world.getAllObjectsInRectangle(new hu.oe.nik.szfmv.automatedcar.model.Position(0,0),
                new hu.oe.nik.szfmv.automatedcar.model.Position(this.world.getWidth(),this.world.getHeight())));
    }
}
