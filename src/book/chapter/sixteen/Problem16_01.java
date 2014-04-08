package book.chapter.sixteen;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Problem 16.1
 * Given a maze (represented by 2D array) and a start/end point, find a path from the start to end.
 * No time or space complexity goals were given.
 * 
 * Assumption: the 2D array is square; it is a boolean[][] where true means a path and false means blocked.
 * 
 * My solution was identical to the book solution (although he shows a DFS in code, he mentions BFS is shortest path).
 * However, I did have some mistakes (as mentioned below)
 * @author rob
 *
 */
public class Problem16_01 {

	// When coding this on paper, I forgot the following elements:
	// 1) Initialize 'weights' to INFINITY
	// 2) Handle maze[x][y] == false case.
	// 3) Incorrectly used .getWeight() instead of weights[x][y] in final maze solve.
	// 4) Condition start.X != end.X && start.Y != end.Y was wrong. Should be logical OR
	
	public static ArrayList<Point> adjacentPoints(boolean[][] maze, Point p) {
		ArrayList<Point> points = new ArrayList<Point>();
		// validate p
		int pX = p.getX();
		int pY = p.getY();
		int mazeMin = 0;
		int mazeMax = maze.length - 1;
		if (pX <= mazeMax && pY <= mazeMax && pX >= mazeMin && pY >= mazeMin) {
			// check "up"
			if (pY - 1 >= mazeMin && maze[pX][pY-1])
				points.add(new Point(pX, pY - 1));
			// check "down"
			if (pY + 1 <= mazeMax && maze[pX][pY+1])
				points.add(new Point(pX, pY + 1));
			// check "left"
			if (pX - 1 >= mazeMin && maze[pX-1][pY])
				points.add(new Point(pX - 1, pY));
			// check "right"
			if (pX + 1 <= mazeMax && maze[pX+1][pY])
				points.add(new Point(pX + 1, pY));
		}
		return points;
	}
	
	public static void visit(boolean[][] visited, Point p) {
		visited[p.getX()][p.getY()] = true;
	}
	
	public static void recordWeight(int[][] weights, Point p) {
		weights[p.getX()][p.getY()] = p.getWeight();
	}
	
	public static void solveMaze(boolean[][] maze, Point start, Point end) {
		LinkedList<Point> queue = new LinkedList<Point>();
		boolean[][] visited = new boolean[maze.length][maze.length];
		int[][] weights = new int[maze.length][maze.length];
		// Initialize weights to INT_MAX
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				weights[i][j] = Integer.MAX_VALUE;
			}
		}
		// Start by setting up the "end" point for use
		Point starter = new Point(end.getX(), end.getY(), 0); // weight = 0
		visit(visited, starter);
		recordWeight(weights, starter);
		// Do a BFS to build out distances for each point
		queue.add(starter); // enqueue
		while(!queue.isEmpty()) {
			Point p = queue.remove(); // dequeue
			for (Point neighbor : adjacentPoints(maze, p)) {
				if (!visited[neighbor.getX()][neighbor.getY()]) {
					neighbor.setWeight(p.getWeight()+1);
					visit(visited, neighbor);
					recordWeight(weights, neighbor);
					queue.add(neighbor); // enqueue
				}
			}
		}
		// Must reset visited so we can use it again
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				visited[i][j] = false;
			}
		}
		// After BFS is done, weights[][] contains distances from end node.
		// We find path through maze by taking min distance neighbor at every iteration.
		while (start.getX() != end.getX() || start.getY() != end.getY()) {
			System.out.println("Visit " + start);
			visit(visited, start);
			int min = Integer.MAX_VALUE, mini = -1;
			ArrayList<Point> neighbors = adjacentPoints(maze, start);
			for (int i = 0; i < neighbors.size(); i++) {
				Point neighbor = neighbors.get(i);
				if (!visited[neighbor.getX()][neighbor.getY()]) {
					if (weights[neighbor.getX()][neighbor.getY()] < min) {
						min = weights[neighbor.getX()][neighbor.getY()];
						mini = i;
					}
				}
			}
			start = neighbors.get(mini);
			start.setWeight(min);
		}
		System.out.println("Visit " + start);
	}
	
	private static void printMaze(boolean[][] maze) {
		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[x].length; y++) {
				if (maze[x][y]) {
					System.out.print("[ ]");
				} else {
					System.out.print("[X]");
				}
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		boolean[][] maze = {{false, true, true, true, true, true, false, false, true, true},
							{true,  true, false, true, true, true, true, true, true, true},
							{false, true, false, true, true, false, false, true, false, false},
							{true, true, true, false, false, false, true, true, false, true},
							{true, false, false, true, true, true, true, true, true, true},
							{true, false, false, true, true, false, true, false, false, true},
							{true, true, true, true, false, true, true, true, true, true},
							{false, true, false, true, false, true, false, true, true, true},
							{false, true, false, false, true, true, true, false, false, false},
							{true, true, true, true, true, true, true, false, false, true}};
		
		printMaze(maze);
		
		solveMaze(maze, new Point(9, 0), new Point(0, 9));
	}
}
