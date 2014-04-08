package book.chapter.eight;

import java.util.LinkedList;

import book.common.BSTNode;

/**
 * Print the "level" order of a binary tree (I'm using Binary Search Tree because I'm lazy and already have BSTNode defined)
 * Level order is defined as printing each "level", defined as level(root) = 0 and level(n) = level(parent(n)) + 1.
 * 
 * This is easily accomplished by treating the tree as a graph and doing a BFS,
 * which is what my solution does. O(n) time and O(n) space.
 * 
 * @author rob
 *
 */
public class Problem08_09 {
	public static void levelOrder(BSTNode root) {
		LinkedList<BSTNode> queue = new LinkedList<BSTNode>();
		queue.add(root); // enqueue
		while (!queue.isEmpty()) {
			BSTNode current = queue.remove(); // dequeue
			if (current != null) {
				System.out.print(current.getData() + " ");
				queue.add(current.getLeft());
				queue.add(current.getRight());
			}
		}
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
		
		System.out.print("In    Order = ");
		BSTNode.inorder(root);
		System.out.print("\nLevel Order = ");
		levelOrder(root);
	}
}
