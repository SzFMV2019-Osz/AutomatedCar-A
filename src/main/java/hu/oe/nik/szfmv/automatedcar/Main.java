package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.InputReader;
import hu.oe.nik.szfmv.automatedcar.visualization.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 40;
    // The window handle
    private Gui window;
    private AutomatedCar car;
    private World world;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        init();
        loop();
    }

    private void init() {
        // create the world
        world = new World(5000, 3000);
        // create an automated car
        car = new AutomatedCar(100, 300,"kek.png");
        world.addObjectToWorld(car);
        window = new Gui(car);
        window.setVirtualFunctionBus(car.getVirtualFunctionBus());
        window.addKeyListener(new InputReader(car.getVirtualFunctionBus()));

    }

    private void loop() {
        while (true) {
            try {
                car.drive();
                window.getCourseDisplay().drawWorld(world);
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}