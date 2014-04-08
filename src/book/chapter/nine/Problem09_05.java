package book.chapter.nine;

import java.util.LinkedList;

import book.common.BSTNode;

/**
 * Perform an inorder walk of a BST tree where each node has a parent pointer.
 * You must use only O(1) space (which prevents recursion which takes O(h) space via function call stack).
 * 
 * My solution below was correct in concept, but I had some bugs in my code related to what to do after we
 * find a suitable parent. I needed to add code to print the parent and then go right. The below is the fixed
 * version.
 *
 */
public class Problem09_05 {
	LinkedList<BSTNode> stack = new LinkedList<BSTNode>();
	
	public static void fancyInorder(BSTNode root) {
		char dir = 'L'; // represents whether 'current' is their parent's left child (L) or right child (R)
		BSTNode current = root;
		while (current != null) {
			if (current.getLeft() != null) {
				dir = 'L';
				current = current.getLeft();
				continue;
			}
			System.out.print(current.getData() + " "); // "traverse"
			if (current.getRight() != null) {
				dir = 'R';
				current = current.getRight();
				continue;
			}
			if (dir == 'L') {
				// Traverse up ladder of parents until we find a node with right subtree that is not 'current' or null.
				while (current != root && (current.getParent().getRight() == current || current.getParent().getRight() == null)) {
					current = current.getParent();
				}
				// If current is equal to root, and it has no right subtree, then we're done
				if (current == root && current.getRight() == null)
					return;
				// Otherwise, print that node and proceed with traversal
				current = current.getParent();
				System.out.print(current.getData() + " ");
				dir = 'R';
				current = current.getRight();
				continue;
			} else {
				// dir == 'R' case
				// Traverse up ladder of parents until we find a right subtree that is not 'current' or null.
				while (current != root && (current.getParent().getRight() == current || current.getParent().getRight() == null)) {
					current = current.getParent();
				}
				// If current is root we just finished right subtree of root, so we're done.
				if (current == root)
					return;
				// Otherwise, print that node and proceed with traversal
				current = current.getParent();
				System.out.print(current.getData() + " ");
				dir = 'R';
				current = current.getRight();
				continue;
			}
		}
	}
	
	public static void setParentPointers(BSTNode current, BSTNode parent) {
		if (current == null)
			return;
		current.setParent(parent);
		setParentPointers(current.getLeft(), current);
		setParentPointers(current.getRight(), current);
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
								 
		setParentPointers(root, null);
		//BSTNode.inorder(root);
		fancyInorder(root);
	}
}
