package book.chapter.fifteen;

import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Problem 15.1, p. 118
 * 
 * Drawing the skyline. You are given a set of building, each with left-position, right-position, and height, which
 * represents what their visibility is from a vantage point. Buildings can overlap one another. Buildings are all
 * rectangles, with their bottom on some fixed line. Determine an "efficient" algorithm for computing the skyline.
 * 
 * --- Before looking at book solution ---
 * 
 * I figured a reasonable output would be a set of points, which I will represent via an array.
 * Example, arr[0] = 5 would mean the point 0,5. arr[1] = 2 would mean the point 1,2. These should be connected
 * as the system draws.
 * 
 * My algorithm is likely not what the book has in mind by "efficient", since it is basically brute force.
 * I simply compute the min and max x-coordinates, then for each x-coordinate determine the max height skyscraper there.
 * 
 * I figured this was slightly boring without actually drawing the skyline, so I whipped together some quick drawing code
 * with a Swing component.
 * 
 * The time complexity of my approach is O(PN), where P is the amount of x-coordinates in the final skyline and N is number of buildings.
 * 
 * --- After looking at book solution ---
 * 
 * The book solution talks about a similar approach to what I did, but mention that it is O(N^2). A better approach involves divide-and-conquer,
 * with a "merge" step. This winds up being a O(N) algorithm since the merge logic time of O(N) dominates the simple dividing logic. 
 *  
 * @author rob
 *
 */
public class Problem15_01 {
	public static int[] computeSkyline(Building[] buildings) {
		if (buildings == null || buildings.length <= 0)
			return null;
		int minLeft = buildings[0].getLeft();
		int maxRight = buildings[0].getRight();
		for (int i = 1; i < buildings.length; i++) {
			if (buildings[i].getLeft() < minLeft)
				minLeft = buildings[i].getLeft();
			if (buildings[i].getRight() > maxRight)
				maxRight = buildings[i].getRight();
		}
		int[] skyline = new int[maxRight - minLeft + 1];
		for (int i = minLeft; i <= maxRight; i++) {
			// Find maximum height of any building at x coordinate i.
			int maxHeight = 0;
			for (Building building : buildings) {
				// If current i value is within building's left/right range, compare its height to max.
				if (i >= building.getLeft() && i <= building.getRight() && building.getHeight() > maxHeight)
					maxHeight = building.getHeight();
			}
			skyline[i - minLeft] = maxHeight;
		}
		return skyline;
	}
	
	public static void main(String[] args) {
		Building b1 = new Building(0, 3, 1);
		Building b2 = new Building(1, 6, 3);
		Building b3 = new Building(4, 8, 4);
		Building b4 = new Building(5, 9, 2);
		Building b5 = new Building(7, 14, 3);
		Building b6 = new Building(10, 12, 6);
		Building b7 = new Building(11, 17, 1);
		Building b8 = new Building(13, 16, 2);
		
		Building[] buildings = {b1, b2, b3, b4, b5, b6, b7, b8};
		
		final int[] skyline = computeSkyline(buildings);
		System.out.println(Arrays.toString(skyline));
		
		Runnable r = new Runnable() {
            public void run() {
                LineComponent lineComponent = new LineComponent(400,400);
                for (int i=0; i < skyline.length; i++) {
                    lineComponent.addLine(i*10, skyline[i]*10);
                }
                JOptionPane.showMessageDialog(null, lineComponent);
            }
        };
        SwingUtilities.invokeLater(r);
	}
}
