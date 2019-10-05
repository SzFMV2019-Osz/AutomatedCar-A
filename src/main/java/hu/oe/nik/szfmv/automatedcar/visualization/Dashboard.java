package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.visualization.dashboard.OMeter;
import hu.oe.nik.szfmv.automatedcar.visualization.dashboard.Turn_Signal;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private final int width = 250;
    private final int height = 700;
    private final int backgroundColor = 0x888888;

    Gui parent;

    private Turn_Signal left_Turn_Signal;
    private Turn_Signal right_Turn_Signal;

    private JLabel gearShiftText = new JLabel("Gear:");
    private JLabel gasPedalText = new JLabel("Gas Pedal");
    private JLabel accMenuText = new JLabel("ACC opts:");
    private JLabel breakPedalText = new JLabel("Break Pedal");
    private JLabel xCoordText = new JLabel("X: ");
    private JLabel yCoordText = new JLabel("Y: ");
    private JLabel steeringWheelText = new JLabel("Steering Wheel:");
    private JLabel speedLimitText = new JLabel("Speed Limit:");

    private JLabel currentGearText = new JLabel("P");
    private JLabel speedLimitValueText = new JLabel("30");
    private JLabel steeringWheelValueText = new JLabel("0");
    private JLabel xCoordValueText = new JLabel("0");
    private JLabel yCoordValueText = new JLabel("0");

    private JProgressBar gasProgressBar = new JProgressBar(0, 100);
    private JProgressBar breakProgressBar = new JProgressBar(0, 100);

    private OMeter speedoMeter;
    private OMeter RPMmeter;

    private void CreateSpeedometer() {

        speedoMeter = new OMeter();
        speedoMeter.setPosition(new Point(0, 0));
        speedoMeter.setSize(new Point(100, 100));
        speedoMeter.setPerf_Percentage(0);
        speedoMeter.setBounds(10, 15, 130, 130);
    }

    private void CreateRPMmeter() {
        RPMmeter = new OMeter();
        RPMmeter.setPosition(new Point(0, 0));
        RPMmeter.setSize(new Point(80, 80));
        RPMmeter.setPerf_Percentage(0);
        RPMmeter.setBounds(120, 25, 130, 130);
    }

    private void OMeterPlacing() {
        CreateRPMmeter();
        CreateSpeedometer();

        add(speedoMeter);
        add(RPMmeter);
    }

    private void TextPlacing() {

        gearShiftText.setBounds(100, 150, 40, 15);
        currentGearText.setBounds(135, 150, 10, 15);
        accMenuText.setBounds(10, 190, 60, 15);
        gasPedalText.setBounds(10, 390, 100, 15);
        breakPedalText.setBounds(10, 420, 100, 15);
        speedLimitText.setBounds(10, 450, 80, 15);
        speedLimitValueText.setBounds(90, 450, 30, 15);
        steeringWheelText.setBounds(10, 580, 100, 15);
        steeringWheelValueText.setBounds(110, 580, 20, 15);
        xCoordText.setBounds(10, 600, 20, 15);
        xCoordValueText.setBounds(30, 600, 10, 15);
        yCoordText.setBounds(70, 600, 20, 15);
        yCoordValueText.setBounds(90, 600, 10, 15);

        add(gearShiftText);
        add(currentGearText);
        add(accMenuText);
        add(gasPedalText);
        add(breakPedalText);
        add(speedLimitText);
        add(speedLimitValueText);
        add(steeringWheelText);
        add(steeringWheelValueText);
        add(xCoordText);
        add(xCoordValueText);
        add(yCoordText);
        add(yCoordValueText);

    }

    private void Turn_SignalPlacing() {
        left_Turn_Signal = new Turn_Signal(10, 140, true);
        right_Turn_Signal = new Turn_Signal(190, 140, false);

        add(left_Turn_Signal);
        add(right_Turn_Signal);
    }

    private void ProgressBarPlacing() {
        gasProgressBar.setBounds(10, 405, 200, 15);
        breakProgressBar.setBounds(10, 435, 200, 15);
        gasProgressBar.setStringPainted(true);
        breakProgressBar.setStringPainted(true);

        add(gasProgressBar);
        add(breakProgressBar);
    }


    private void placeElements() {
        Turn_SignalPlacing();
        ProgressBarPlacing();
        TextPlacing();
        OMeterPlacing();
    }

    private Thread timer = new Thread() {
        int difference;

        public void run() {
            while (true) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }

            }
        }
    };

    /**
     * Initialize the dashboard
     */
    public Dashboard(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);

        parent = pt;
        placeElements();
        timer.start();
    }

}
