package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.InputReader;
import hu.oe.nik.szfmv.automatedcar.exceptions.CrashException;
import hu.oe.nik.szfmv.automatedcar.model.NPC;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.NPC;
import hu.oe.nik.szfmv.automatedcar.model.Position;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.InputReader;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.ParkingPilot;
import hu.oe.nik.szfmv.automatedcar.visualization.Gui;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 40;

    private Gui window;
    private WorldManager worldManager;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        this.init();
        this.loop();
    }

    ParkingPilot pilot;
    private void init() {
        this.worldManager = new WorldManager("test_world", "reference_points");
        NPC walkerNPC = new NPC(new Position(1000, 100), 90, "woman.png", "walker_npc_road_1");
        //NPC carNPC = new NPC(new Position(220, 824), 180, "car_2_blue.png", "car_npc_road_1");
        AutomatedCar car = new AutomatedCar(80, 80, "car_2_white.png");
        this.worldManager.setAutomatedCar(car);
        pilot = new ParkingPilot(car.getHeight(), car.getVirtualFunctionBus());
        worldManager.getNpcs().add(walkerNPC);
        //worldManager.getNpcs().add(carNPC);
        worldManager.addObjectToWorld(walkerNPC);
        //worldManager.addObjectToWorld(carNPC);
        this.window = new Gui(this.worldManager);
        this.window.setVirtualFunctionBus(car.getVirtualFunctionBus());
        this.window.addKeyListener(new InputReader(car.getVirtualFunctionBus()));

    }

    private void loop() {
        while (true) {
            try {
                this.worldManager.getAutomatedCar().drive();
                for (NPC npc : worldManager.getNpcs()) {
                    npc.move();
                }
                pilot.ParkingPilotManagement();
                // TODO IWorld-öt használjon a drawWorld
                this.window.getCourseDisplay().drawWorld((this.worldManager),window.getVirtualFunctionBus().inputPacket);
                // TODO window.getCourseDisplay().refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            } catch (CrashException e) {
                showAndHandlePopupDialog(e);
            }
        }
    }

    private void showAndHandlePopupDialog(CrashException e) {
        String message = e.getMessage();
        message += "\n\n" + Consts.CRASH_POPUP_MESSAGE;

        int result = JOptionPane.showConfirmDialog(null, message, "Uh-oh...",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        AutomatedCar car = this.worldManager.getAutomatedCar();
        if (result == 1) {
            car.moveCarByPos(0, -100);
            LOGGER.info("Result: " + result + ". Y coordinate modified!");
        } else {
            car.moveCarByPos(-100, 0);
            LOGGER.info("Result: " + result + ". X coordinate modified!");
        }
        // key reset (hajlamosak beragadni a gombok a dialog felugrásakor)
        this.window.addKeyListener(new InputReader(car.getVirtualFunctionBus()));
    }
}