package hu.oe.nik.szfmv.automatedcar.visualization.dashboard;

import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LastRoadSign extends JPanel implements ILastRoadSign {

    BufferedImage image;
    String RoadSignName;
    Boolean isDetected = false;

    public LastRoadSign() {
        this.setBounds(120, 205, 100, 40);
        this.setVisible(true);
    }

    public void setIsDetected(Boolean value) {
        this.isDetected = value;
    }

    @Override
    public String getRoadSignName() {
        if (this.RoadSignName != null) {
            return this.RoadSignName;
        } else {
            return "No Road Sign";
        }
    }

    @Override
    public void setRoadSignName(String roadSignName) {
        this.RoadSignName = roadSignName;
        try {
            this.image = ModelCommonUtil.loadObjectImage(this.RoadSignName);
        } catch (Exception e) {
        }
        this.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 100, 100);

        if (this.image != null) {
            g.drawImage(this.image, 0, 0, null);
        }

    }
}
