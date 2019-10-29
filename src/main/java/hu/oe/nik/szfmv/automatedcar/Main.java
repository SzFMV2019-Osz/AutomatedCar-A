package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.NPC;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.InputReader;
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
        AutomatedCar car = new AutomatedCar(225, 250, "car_2_white.png");
        NPC walkerNPC = new NPC(new Position(1000, 100), 90, "woman.png", "walker_npc_road_1");
        NPC carNPC = new NPC(new Position(220, 824), 180, "car_2_blue.png", "car_npc_road_1");
        worldManager = new WorldManager("test_world", "reference_points");
        worldManager.setAutomatedCar(car);
        worldManager.getNpcs().add(walkerNPC);
        worldManager.getNpcs().add(carNPC);
        worldManager.addObjectToWorld(walkerNPC);
        worldManager.addObjectToWorld(carNPC);
        window = new Gui(this.worldManager);
        window.setVirtualFunctionBus(car.getVirtualFunctionBus());
        window.addKeyListener(new InputReader(car.getVirtualFunctionBus()));

    }

    private void loop() {
        while (true) {
            try {
                worldManager.getAutomatedCar().drive();
                for (NPC npc : worldManager.getNpcs()) {
                    npc.move();
                }
                // TODO IWorld-öt használjon a drawWorld
                window.getCourseDisplay().drawWorld((this.worldManager));
                // TODO window.getCourseDisplay().refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}