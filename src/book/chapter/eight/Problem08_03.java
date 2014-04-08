package book.chapter.eight;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import book.common.BSTNode;

/**
 * Problem 8.3
 * Inorder traversal without recursion.
 * 
 * Basically the solution here is to "fake" what we would get in recursion
 * by using a Stack. However, we have to push in reverse order and maintain
 * a visited array.
 * 
 * My solution inorderIterative1 uses O(n) time and O(n) space.
 * 
 * The book solution (inorderIterative2) uses O(n) time and O(n) space also,
 * but manages to get around the need for a visited field by more closely
 * replicating the recursive solution (always going left until can't, then visiting, and only then going right).
 * 
 * @author rob
 *
 */
public class Problem08_03 {
	public static void inorderIterative1(BSTNode root) {
		Map<BSTNode, Boolean> visited = new HashMap<BSTNode, Boolean>();
		LinkedList<BSTNode> stack = new LinkedList<BSTNode>();
		stack.push(root);
		while (!stack.isEmpty()) {
			BSTNode n = stack.pop();
			if (n == null) continue;
			// This is the "visit" step we would normally do in between two recursive calls
			Boolean nVisited = visited.get(n);
			if (nVisited != null && nVisited.equals(true)) {
				System.out.print(n.getData() + " ");
			} else {
				// If not already visited before, add children and self to stack in reverse order.
				// (It must be reversed because pushing several things and popping will reverse it.)
				stack.push(n.getRight());
				stack.push(n);
				stack.push(n.getLeft());
				// Set n as visited so we don't loop forever and actually "visit" it.
				visited.put(n, true);
			}
		}
		System.out.println();
	}
	
	public static void inorderIterative2(BSTNode root) {
		LinkedList<BSTNode> stack = new LinkedList<BSTNode>();
		BSTNode current = root; 
		while (!stack.isEmpty() || current != null) {
			if (current != null) {
				// In usual case we go left, but remember previous in stack.
				stack.push(current);
				current = current.getLeft();
			} else {
				// current == null
				// equivalent to base case in recursion
				// where we would return, print last element, and go right.
				// here we replicate that behavior.
				current = stack.pop(); // get last element
				System.out.print(current.getData() + " "); // print last element
				current = current.getRight(); // go right
			}
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		BSTNode root = new BSTNode( // root = 50
				new BSTNode( // 30
						new BSTNode(20), new BSTNode( // 40
								new BSTNode(new BSTNode(new BSTNode(31), null, 33), null, 35), null, 40), 30),
				new BSTNode( // 60
						new BSTNode(55), new BSTNode( // 70
								null, new BSTNode(80), 70), 60),
				50);
		
		// Recursive inorder
		System.out.print("Recursive : ");
		BSTNode.inorder(root);
		System.out.println();
		
		// Iterative
		System.out.print("Iterative1: ");
		inorderIterative1(root);
		System.out.print("Iterative2: ");
		inorderIterative2(root);
	}
}
