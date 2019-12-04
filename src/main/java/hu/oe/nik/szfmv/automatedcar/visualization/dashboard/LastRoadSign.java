package hu.oe.nik.szfmv.automatedcar.visualization.dashboard;

import hu.oe.nik.szfmv.automatedcar.model.utility.ModelCommonUtil;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class LastRoadSign extends JPanel implements ILastRoadSign {

    BufferedImage image;
    String RoadSignName;

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
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (this.image != null) {
            g.drawImage(this.image, 120, 205, null);
        }
    }
}
