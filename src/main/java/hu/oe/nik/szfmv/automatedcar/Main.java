package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.exceptions.CrashException;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.model.utility.Consts;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.InputReader;
import hu.oe.nik.szfmv.automatedcar.visualization.Gui;
import javax.swing.JOptionPane;

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
        this.init();
        this.loop();
    }

    private void init() {
        this.worldManager = new WorldManager("test_world", "reference_points");
        AutomatedCar car = new AutomatedCar(80, 80, "car_2_white.png");
        this.worldManager.setAutomatedCar(car);

        this.window = new Gui(this.worldManager);
        this.window.setVirtualFunctionBus(car.getVirtualFunctionBus());
        this.window.addKeyListener(new InputReader(car.getVirtualFunctionBus()));

    }

    private void loop() {
        while (true) {
            try {
                this.worldManager.getAutomatedCar().drive();
                // TODO IWorld-öt használjon a drawWorld
                this.window.getCourseDisplay().drawWorld((this.worldManager));
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
            car.setPosY(car.getPosY() - 100);
            LOGGER.info("Result: " + result + ". Y coordinate modified!");
        } else {
            car.setPosX(car.getPosX() - 100);
            LOGGER.info("Result: " + result + ". X coordinate modified!");
        }
        // key reset (hajlamosak beragadni a gombok a dialog felugrásakor)
        this.window.addKeyListener(new InputReader(car.getVirtualFunctionBus()));
    }
}
