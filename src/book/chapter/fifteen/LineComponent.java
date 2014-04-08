package book.chapter.fifteen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;

public class LineComponent extends JComponent {
	ArrayList<Line2D.Double> lines;
	Point previousPoint;

    LineComponent(int width, int height) {
        super();
        setPreferredSize(new Dimension(width,height));
        lines = new ArrayList<Line2D.Double>();
        previousPoint = null;
    }

    public void addLine(int x, int y) {
    	// If we are changing heights, we need to draw intermediate line (so we don't get diagonal skyline)
    	// If our height is increasing, we need horizontal line to move us to the right x position.
    	if (previousPoint != null && previousPoint.getY() < y) {
    		Line2D.Double intermediate = new Line2D.Double(previousPoint.x, previousPoint.y, x, previousPoint.y);
    		previousPoint = new Point(x, previousPoint.y);
    		lines.add(intermediate);
    	}
    	// If our height is decreasing, we need vertical line to move us to right y position.
    	if (previousPoint != null && previousPoint.getY() > y) {
    		Line2D.Double intermediate = new Line2D.Double(previousPoint.x, previousPoint.y, previousPoint.x, y);
    		previousPoint = new Point(previousPoint.x, y);
    		lines.add(intermediate);
    	}
    	if (previousPoint == null) {
    		previousPoint = new Point(0, 0);
    	}
        Line2D.Double line = new Line2D.Double(previousPoint.x, previousPoint.y, x, y);
        previousPoint = new Point(x, y);
        lines.add(line);
        repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        Dimension d = getPreferredSize();
        g.setColor(Color.black);
        int w = getWidth();
        int h = getHeight();
        // while drawing, transform from bottom-left 0,0 to top-left 0,0 system
        for (Line2D.Double line : lines) {
            g.drawLine(
                (int)line.getX1(),
                h - (int)line.getY1(),
                (int)line.getX2(),
                h - (int)line.getY2()
                );
        }
    }
}
