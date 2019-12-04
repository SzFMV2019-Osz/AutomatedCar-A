package hu.oe.nik.szfmv.automatedcar.visualization;


import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.PowertrainPacket;
import hu.oe.nik.szfmv.automatedcar.model.Sign;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IObject;
import hu.oe.nik.szfmv.automatedcar.model.managers.WorldManager;
import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;
import hu.oe.nik.szfmv.automatedcar.visualization.dashboard.OMeter;
import hu.oe.nik.szfmv.automatedcar.visualization.dashboard.StatusIndicator;
import hu.oe.nik.szfmv.automatedcar.visualization.dashboard.Turn_Signal;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.InputPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


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

    private JLabel currentSpeedText = new JLabel("0 KM/h");
    private JLabel currentRpmText = new JLabel("0");
    private JLabel gearShiftText = new JLabel("Gear:");
    private JLabel gasPedalText = new JLabel("Gas Pedal");
    private JLabel accMenuText = new JLabel("ACC opts:");
    private JLabel breakPedalText = new JLabel("Break Pedal");
    private JLabel xCoordText = new JLabel("X: ");
    private JLabel yCoordText = new JLabel("Y: ");
    private JLabel steeringWheelText = new JLabel("Steering Wheel:");
    private JLabel speedLimitText = new JLabel("Speed Limit:");
    private JLabel controlStartExplainerText=new JLabel("Controls");
    private JLabel pedalExplainerText=new JLabel("W/S : Throttle/Break  ");
    private JLabel steeringExplainerText=new JLabel(" A/D :Turn Left/Right");
    private JLabel gearChangeExplainerText=new JLabel("Q/E : Gear Up/Down");
    private JLabel indicatorExplainerText=new JLabel("L/R Arrow: Indicator L/R");
    private JLabel parkingIndicatorExplainerText=new JLabel("P : Parking mode");
    private JLabel accIndicatorExplainerText=new JLabel("O : Automated Cruise Control");
    private JLabel laneKeepingIndicatorExplainerText=new JLabel("L : LaneKeeping");
    private JLabel timeGapExplainerText=new JLabel("T: Set time Gap");
    private JLabel referenceSpeedExplainer=new JLabel("U/D Arrow : Change ACC speed");

    private JLabel currentGearText = new JLabel("P");
    private JLabel speedLimitValueText = new JLabel("No limit");
    private JLabel steeringWheelValueText = new JLabel("0");
    private JLabel xCoordValueText = new JLabel("0");
    private JLabel yCoordValueText = new JLabel("0");
    
    private JProgressBar gasProgressBar = new JProgressBar(0, 100);
    private JProgressBar breakProgressBar = new JProgressBar(0, 100);

    private OMeter speedoMeter;
    private OMeter RPMmeter;
    private StatusIndicator AccIndicator;
    private StatusIndicator PPIndicator;
    private StatusIndicator LKAIndicator;
    private StatusIndicator LKWARNIndicator;
    private StatusIndicator AEBWARNIndicator;
    private StatusIndicator RRWARNIndicator;
    private StatusIndicator lastRoadSignIndicator;
    private StatusIndicator TimeGapIndicator;
    private StatusIndicator ReferenceSpeedIndicator;
    private JPanel sensedSign;

    private Sign roadSign = null;

    private void CreateSpeedometer() {
        speedoMeter = new OMeter();
        speedoMeter.setPosition(new Point(0, 0));
        speedoMeter.setSize(new Point(100, 100));
        speedoMeter.setPerf_Percentage(0);
        speedoMeter.setBounds(10, 15, 110, 100);
    }

    private void CreateRPMmeter() {
        RPMmeter = new OMeter();
        RPMmeter.setPosition(new Point(0, 0));
        RPMmeter.setSize(new Point(80, 80));
        RPMmeter.setPerf_Percentage(0);
        RPMmeter.setBounds(120, 25, 110, 100);
    }

    private void OMeterPlacing() {
        CreateRPMmeter();
        CreateSpeedometer();

        add(speedoMeter);
        add(RPMmeter);
    }

    private void IndicatorPlacing() {
        ReferenceSpeedIndicator = new StatusIndicator(10, 205, 50, 40, "0.0");
        TimeGapIndicator = new StatusIndicator(60, 205, 50, 40, "0.8");
        AccIndicator = new StatusIndicator(10, 250, 50, 40, "ACC");
        PPIndicator = new StatusIndicator(60, 250, 50, 40, "PP");
        LKAIndicator = new StatusIndicator(10, 300, 50, 40, "LKA");
        LKWARNIndicator = new StatusIndicator(10, 350, 100, 40, "LKA WARN");
        lastRoadSignIndicator = new StatusIndicator(120, 205, 100, 40, roadSign != null ?
                                                                       roadSign.getImageFileName() : "No Sign");
        sensedSign = new JPanel();
        sensedSign.setBounds(120, 205, 100, 40);
        sensedSign.setVisible(false);
        AEBWARNIndicator = new StatusIndicator(120, 310, 100, 40, "AEB WARN");
        RRWARNIndicator = new StatusIndicator(120, 350, 100, 40, "RR WARN");

        add(AccIndicator);
        add(PPIndicator);
        add(LKAIndicator);
        add(LKWARNIndicator);
        add(AEBWARNIndicator);
        add(RRWARNIndicator);
        add(lastRoadSignIndicator);
        add(sensedSign);
        add(TimeGapIndicator);
        add(ReferenceSpeedIndicator);
    }

    private void TextPlacing() {
        gearShiftText.setBounds(100, 150, 40, 15);
        currentGearText.setBounds(135, 150, 10, 15);
        accMenuText.setBounds(10, 190, 80, 15);
        gasPedalText.setBounds(10, 390, 100, 15);
        breakPedalText.setBounds(10, 420, 100, 15);
        speedLimitText.setBounds(10, 450, 120, 15);
        speedLimitValueText.setBounds(90, 450, 60, 15);
        steeringWheelText.setBounds(10, 620, 120, 15);
        steeringWheelValueText.setBounds(130, 620, 40, 15);
        xCoordText.setBounds(10, 635, 30, 15);
        xCoordValueText.setBounds(40, 635, 40, 15);
        yCoordText.setBounds(80, 635, 30, 15);
        yCoordValueText.setBounds(110, 635, 40, 15);
        currentSpeedText.setBounds(50, 125, 50, 15);
        currentRpmText.setBounds(150, 125, 50,15);
        controlStartExplainerText.setBounds(10,470,200,15);
        pedalExplainerText.setBounds(10,485,200,15);
        steeringExplainerText.setBounds(10,500,200,15);
        gearChangeExplainerText.setBounds(10,515,200,15);
        indicatorExplainerText.setBounds(10,530,200,15);
        laneKeepingIndicatorExplainerText.setBounds(10,560,200,15);
        parkingIndicatorExplainerText.setBounds(10,545,200,15);
        accIndicatorExplainerText.setBounds(10,575,220,15);
        timeGapExplainerText.setBounds(10,590,220,15);
        referenceSpeedExplainer.setBounds(10,605,220,15);




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
        add(currentSpeedText);
        add(currentRpmText);
        add(controlStartExplainerText);
        add(pedalExplainerText);
        add(steeringExplainerText);
        add(gearChangeExplainerText);
        add(indicatorExplainerText);
        add(accIndicatorExplainerText);
        add(parkingIndicatorExplainerText);
        add(laneKeepingIndicatorExplainerText);
        add(timeGapExplainerText);
        add(referenceSpeedExplainer);
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
        IndicatorPlacing();
    }

    private Thread timer = new Thread() {
        int difference;

        public void run() {
            while (true) {

                try {
                    EventHandling();
                    Thread.sleep(40);
                } catch (InterruptedException ex) {
                }

            }
        }
    };

    private void inputEventHandling(InputPacket inputPacket) {
        gasProgressBar.setValue(inputPacket.getGasPedalValue());
        breakProgressBar.setValue(inputPacket.getBreakPedalValue());
        steeringWheelValueText.setText(String.valueOf(inputPacket.getSteeringWheelValue()));
        left_Turn_Signal.setOn(inputPacket.getLeftSignalValue());
        right_Turn_Signal.setOn(inputPacket.getRightSignalValue());
        TimeGapIndicator.setText(String.valueOf(inputPacket.getAccTimeGap()));
        ReferenceSpeedIndicator.setText(String.valueOf(inputPacket.getAccSpeed()));
        currentGearText.setText(String.valueOf(inputPacket.getGearShiftValue()));
        AccIndicator.switchIt(inputPacket.getAccState());
        PPIndicator.switchIt(inputPacket.getParkingState());
        LKAIndicator.switchIt(inputPacket.getLaneKeepingState());


    }


    private void OtherEventHandling(PowertrainPacket packet) {
        speedoMeter.setPerf_Percentage(packet.getVelocity());
        RPMmeter.setPerf_Percentage(packet.getRPM());
        AutomatedCar car = parent.getAutomatedCar();
        
        xCoordValueText.setText(String.valueOf(car.getPosX()));
        yCoordValueText.setText(String.valueOf(car.getPosY()));
        currentSpeedText.setText(String.valueOf(packet.getVelocity()) + " KM/H");
        currentRpmText.setText(String.valueOf(packet.getRPM()));
    }

    public void RoadSignEventHandling() {
        AutomatedCar car = parent.getAutomatedCar();
        WorldManager manager = parent.getManager(); 
        
        int width = parent.getCourseDisplay().getWidth();
        int height = parent.getCourseDisplay().getHeight();
        int offsetX = (width / 2) - (car.getPosX() - car.getReferenceX() + (car.getWidth() / 2));
        int offsetY = (height / 2) - (car.getPosY() - car.getReferenceY() + (car.getHeight() / 2));
        
        List<IObject> sensedObjects = car.getCamera().loop(manager, car, offsetX, offsetY, car.getRotation());
        
        if (sensedObjects.isEmpty()) {
            roadSign = null;
        } else {
            for (IObject object : sensedObjects) {
                if (object instanceof Sign) {
                    roadSign = (Sign)object;
                    break;
                }
            }
        }
        
        if (roadSign != null) {
            speedLimitValueText.setText(roadSign.getSpeedLimit());
            lastRoadSignIndicator.setText(roadSign.getImageFileName());
        } else {
            sensedSign.setVisible(false);
            lastRoadSignIndicator.setVisible(true);
            lastRoadSignIndicator.setText("No Sign");
        }
    }

    private void EventHandling() {
        VirtualFunctionBus virtualFunctionBus = parent.getVirtualFunctionBus();
        if (virtualFunctionBus != null) {
            if(virtualFunctionBus.inputPacket != null)
                inputEventHandling(virtualFunctionBus.inputPacket);
            if(virtualFunctionBus.powertrainPacket != null)
                OtherEventHandling(virtualFunctionBus.powertrainPacket);
            if(virtualFunctionBus.samplePacket != null)
                RoadSignEventHandling();
        }


    }

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
