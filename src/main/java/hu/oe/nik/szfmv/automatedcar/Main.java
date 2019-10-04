package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.visualization.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 40;

    private Gui window;
    private WorldManager worldManager;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        init();
        loop();
    }

    private void init() {
        AutomatedCar car = new AutomatedCar(20, 20, "car_2_white.png");
        worldManager = new WorldManager("test_world");
        worldManager.setAutomatedCar(car);

        window = new Gui();
        window.setVirtualFunctionBus(car.getVirtualFunctionBus());
    }

    private void loop() {
        while (true) {
            try {
                worldManager.getAutomatedCar().drive();
                // TODO IWorld-öt használjon a drawWorld
                window.getCourseDisplay().drawWorld((World)worldManager.getWorld());
                // TODO window.getCourseDisplay().refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
