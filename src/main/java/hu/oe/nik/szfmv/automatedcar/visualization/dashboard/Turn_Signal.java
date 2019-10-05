package hu.oe.nik.szfmv.automatedcar.visualization.dashboard;

import javax.swing.*;
import java.awt.*;

public class Turn_Signal extends JPanel {

    private static final Color Signal_OFF = Color.white;
    private static final Color Signal_ON = Color.green;
    private static final Color Background = new Color(0x888888);
    private int[] x;
    private int[] y;
    private Color currentColor = Signal_OFF;

    public Turn_Signal(int x_pos, int y_pos, boolean isLeft) {
       setBounds(x_pos, y_pos, 40, 40);
        if (isLeft) {
            x = new int[]{0, 15, 15, 30, 30, 15, 15};
            y = new int[]{15, 0, 10, 10, 20, 20, 30};
        } else {
            x = new int[]{0, 15, 15, 30, 15, 15, 0};
            y = new int[]{10, 10, 0, 15, 30, 20, 20};
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Background);
        g.fillRect(0, 0, 40, 40);
        g.setColor(currentColor);
        g.fillPolygon(x, y, 7);
    }
}

