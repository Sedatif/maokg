package lab2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Skeleton extends JPanel implements ActionListener {
    public int maxWidth;
    public int maxHeight;
    Timer timer;
    private Graphics2D g2d;
    private double angle = 0;
    private double scale = 1;
    private double delta = 0.01;

    public Skeleton(Dimension size, Insets insets) {
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;
        timer = new Timer(20, this);
        timer.start();
    }

    public void paint(Graphics g) {
        g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        
        g2d.setBackground(new Color(85, 107, 47));
        g2d.clearRect(0, 0, maxWidth, maxHeight);
        
        BasicStroke bs = new BasicStroke(10, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
        g2d.setStroke(bs);
        GradientPaint gp = new GradientPaint(5, 5, Color.gray, 15, 15, Color.cyan, true);
        g2d.setPaint(gp);
        g2d.drawRect(5, 5, maxWidth - 23, maxHeight - 45);
        
        g2d.scale(scale, scale);
        drawClock();
    }

    private void drawClock() {
        drawMainCircles();
        drawSubCircles();
        g2d.rotate(angle, 500, 500);
        drawHourHand();
        g2d.rotate(angle * 12, 500, 500);
        drawMinuteHand();
    }

    private void drawMainCircles() {
        g2d.scale(0.5, 0.5);
        g2d.setColor(new Color(255, 215, 0));
        drawFilledCircle(500, 500, 400);
        g2d.setColor(new Color(245, 245, 245));
        drawFilledCircle(500, 500, 350);
    }

    private void drawSubCircles() {
        GradientPaint gp = new GradientPaint(5, 25, Color.blue, 20, 2, Color.black, true);
        g2d.setPaint(gp);
        final int diameter = 30;
        drawFilledCircle(500, 370, diameter);
        drawFilledCircle(370, 500, diameter);
        drawFilledCircle(630, 500, diameter);
        drawFilledCircle(500, 630, diameter);
    }

    private void drawMinuteHand() {
        g2d.setColor(Color.BLACK);
        int[] x = new int[]{500, 490, 500, 510};
        int[] y = new int[]{520, 500, 350, 500};
        g2d.fillPolygon(new Polygon(x, y, 4));
    }

    private void drawHourHand() {
        int[] x = new int[]{480, 500, 600, 500};
        int[] y = new int[]{500, 480, 500, 520};
        g2d.setColor(Color.BLACK);
        g2d.fillPolygon(new Polygon(x, y, 4));

        x = new int[]{485, 500, 590, 500};
        y = new int[]{500, 490, 500, 510};
        g2d.setColor(new Color(245, 245, 245));
        g2d.fillPolygon(new Polygon(x, y, 4));
    }

    private void drawFilledCircle(int x, int y, int diameter) {
        final int radius = diameter / 2;
        g2d.fillOval(x - radius, y - radius, diameter, diameter);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //scaling
        if (scale < 0.99 || scale > 2)
            delta = -delta;
        scale += delta;
        // turning
        angle += 0.01;
        repaint();
    }
}
