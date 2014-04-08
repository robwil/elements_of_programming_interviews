package book.chapter.fourteen;

import book.common.BSTNode;

/**
 * Given the root node of a BST, return true iff the entire tree satisfies the BST properties.
 * i.e. every node is >= its left subtree's nodes, every node is <= it's right subtree's nodes
 * 
 * I initially coded the isBST1 solution, but dismissed it for the isBST2 solution.
 * It turns out that isBST2 is completely wrong because it only checks the left node, not the max from left subtree.
 * However, isBST1 is correct.
 * 
 * The problem with isBST1 is that it will return false if a valid BST contains Integer.MAX_VALUE, since it uses that as a special value.
 * A better and obvious solution is to combine isBST1 with isBST2 and instead of using MAX_VALUE, just throw an exception there.
 * 
 * The book has some crazier solutions. One is to compare max of left with min or right (which I think I remember from class).
 * But that is O(N^2) compares to the other O(N) solutions.
 * Their more exotic answer does a BFS to "fail fast".
 * 
 * @author rob
 *
 */
public class Problem14_01 {
	
	private static int maxNode(BSTNode root, int maxSoFar) {
		if (root == null) return maxSoFar;
		if (maxNode(root.getLeft(), maxSoFar) > ((Integer)root.getData())) {
			return Integer.MAX_VALUE;
		}
		maxSoFar = ((Integer)root.getData()).intValue();
		return maxNode(root.getRight(), maxSoFar);
	}
	
	public static boolean isBST1(BSTNode root) {
		if (maxNode(root, Integer.MIN_VALUE) == Integer.MAX_VALUE)
			return false;
		return true;
	}
	
	private static void isBSTE(BSTNode current) throws InvalidBSTException {
		if (current == null) return;
		if (current.getLeft() != null) {
			if (((Comparable)current.getLeft().getData()).compareTo(current.getData()) > 0) {
				throw new InvalidBSTException();
			}
			isBSTE(current.getLeft());
		}
		if (current.getRight() != null) {
			if (((Comparable)current.getRight().getData()).compareTo(current.getData()) < 0) {
				throw new InvalidBSTException();
			}
			isBSTE(current.getRight());
		}
	}
	public static boolean isBST2(BSTNode root) {
		try {
			isBSTE(root);
		} catch (InvalidBSTException ex) {
			return false;
		}
		return true;
	}
	
	private static int maxNode3(BSTNode root, int maxSoFar) throws InvalidBSTException {
		if (root == null) return maxSoFar;
		if (maxNode3(root.getLeft(), maxSoFar) > ((Integer)root.getData())) {
			throw new InvalidBSTException();
		}
		maxSoFar = ((Integer)root.getData()).intValue();
		return maxNode3(root.getRight(), maxSoFar);
	}
	
	public static boolean isBST3(BSTNode root) {
		try {
			if (maxNode3(root, Integer.MIN_VALUE) == Integer.MAX_VALUE)
				return false;
		} catch (InvalidBSTException ex) {
			return false;
		}
		return true;
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
		
		BSTNode.inorder(root);
		System.out.println();
		System.out.println("Expected = true");
		System.out.println("isBST1 = " + isBST1(root));
		System.out.println("isBST2 = " + isBST2(root));
		System.out.println("isBST3 = " + isBST3(root));
		
		// This tree has a problem in non-immediate node so it goes undetected by isBST2 solution.
		root = new BSTNode( // root = 50
				new BSTNode( // 30
						new BSTNode(20), new BSTNode( // 40
								new BSTNode(new BSTNode(new BSTNode(31), new BSTNode(80), 33), null, 35), null, 40), 30),
				new BSTNode( // 60
						new BSTNode(55), new BSTNode( // 70
								null, new BSTNode(80), 70), 60),
				50);
		
		System.out.println();
		System.out.println("Expected = false");
		System.out.println("isBST1 = " + isBST1(root));
		System.out.println("isBST2 = " + isBST2(root));
		System.out.println("isBST3 = " + isBST3(root));
		
		root = new BSTNode( // root = 50
				new BSTNode(25), new BSTNode(new BSTNode(15), null, 25), 50);
		
		System.out.println();
		System.out.println("Expected = false");
		System.out.println("isBST1 = " + isBST1(root));
		System.out.println("isBST2 = " + isBST2(root));
		System.out.println("isBST3 = " + isBST3(root));
	}
	
}
