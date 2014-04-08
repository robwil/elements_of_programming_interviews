package book.chapter.fourteen;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import book.common.BSTNode;

/**
 * Problem 14.4, p. 108
 * Find the first BST node with key equal to a given value k.
 * "First" is defined as first to appear in the in-order traversal.
 * Return null if no element has key equal to k.
 * 
 * --- Before looking at book solution ---
 * 
 * The problem asked for both a recursive and iterative solution.
 * Like many recursive-to-iterative conversions, I used a Stack and a Visited map.
 * Both solutions are O(N) time. The recursive one is O(logN) space and iterative is O(N) space.
 * 
 * --- After looking at book solution ---
 * 
 * It turns out that I did this wrong, by thinking of this more as an in-order walk than a search.
 * The book uses a modified version of the binary search, which of course is O(logN) time and O(logN) space. 
 * I implemented those below as findFirstK2 and findFirstK2Iterative, without referencing the book code.
 * Since this version doesn't depend on backtracing, the iterative code does not need a Stack.
 * Therefore the iterative2 version is the best, using O(logN) time and O(1) space.
 * 
 * @author rob
 *
 */
public class Problem14_04 {
	
	// O(N) time and O(logN) space - because max stack depth = tree height = log N
	public static BSTNode findFirstK(BSTNode root, Object k) {
		if (root == null)
			return null;
		// "Go left" step
		BSTNode leftResult = findFirstK(root.getLeft(), k);
		if (leftResult != null)
			return leftResult;
		// "Visit" step
		if (root.getData().equals(k))
			return root;
		// "Go right step"
		return findFirstK(root.getRight(), k);
	}
	
	// O(logN) time and O(logN) space
	public static BSTNode findFirstK2(BSTNode root, Object k) {
		if (root == null)
			return null;
		Object data = root.getData();
		if (data.equals(k)) {
			// Root is equal to k.
			// However, there may still be an element equal to k in the left subtree,
			// which would be first in an inorder walk.
			BSTNode leftResult = findFirstK2(root.getLeft(), k);
			if (leftResult != null)
				return leftResult;
			return root;
		}
		else if (((Comparable)data).compareTo(k) < 0) {
			// go right if current data is less than k
			return findFirstK2(root.getRight(), k);
		} else {
			// otherwise go left
			return findFirstK2(root.getLeft(), k);
		}
	}
	
	// O(N) time and O(N) space
	public static BSTNode findFirstKIterative(BSTNode root, Object k) {
		Deque<BSTNode> nodes = new LinkedList<BSTNode>();
		Map<BSTNode, Boolean> visited = new HashMap<BSTNode, Boolean>();
		nodes.push(root);
		while (!nodes.isEmpty()) {
			BSTNode node = nodes.pop();
			if (node == null)
				continue;
			// Node was seen before, which means we have seen all in its left subtree.
			// So now we can compare it to K
			if (visited.get(node) != null && visited.get(node).equals(true)) {
				if (node.getData().equals(k))
					return node;
			} else {
				visited.put(node, true);
				// Push RIGHT, node, LEFT in reverse order we want to process them.
				nodes.push(node.getRight());
				nodes.push(node);
				nodes.push(node.getLeft());
			}
		}
		// Found no element == k, so return null.
		return null;
	}
	
	// O(logN) time and O(1) space
	public static BSTNode findFirstKIterative2(BSTNode root, Object k) {
		BSTNode result = null;
		while (root != null) {
			Comparable data = (Comparable)(root.getData());
			if (data.equals(k)) {
				result = root; // set result to root; may be overridden by further iterations of loop
				root = root.getLeft(); // go left to check left subtree
			} else if (data.compareTo(k) < 0) {
				root = root.getRight(); // current value is less than k so go right
			} else {
				root = root.getLeft();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		// BST from Figure 14.2 in book
		//
		//                 108
		//         108                  285
		//    -10       108         243      285
		//  -14  2                                401
		
		BSTNode root = new BSTNode(
				new BSTNode(
						new BSTNode(
								new BSTNode(-14),
								new BSTNode(2),
								-10),
						new BSTNode(108),
						108),
				new BSTNode(
						new BSTNode(243),
						new BSTNode(null, new BSTNode(401), 285),
						285),
				108);
		
		System.out.println("k = 108");
		System.out.println("Expected = " + root.getLeft().originalToString());
		System.out.println("recursive = " + findFirstK(root, 108).originalToString());
		System.out.println("recursive2 = " + findFirstK2(root, 108).originalToString());
		System.out.println("iterative = " + findFirstKIterative(root, 108).originalToString());
		System.out.println("iterative2 = " + findFirstKIterative2(root, 108).originalToString());
		
		System.out.println("k = 285");
		System.out.println("Expected = " + root.getRight().originalToString());
		System.out.println("recursive = " + findFirstK(root, 285).originalToString());
		System.out.println("recursive2 = " + findFirstK2(root, 285).originalToString());
		System.out.println("iterative = " + findFirstKIterative(root, 285).originalToString());
		System.out.println("iterative2 = " + findFirstKIterative2(root, 285).originalToString());
		
		System.out.println("k = 143");
		System.out.println("Expected = null");
		System.out.println("recursive = " + findFirstK(root, 143));
		System.out.println("recursive2 = " + findFirstK2(root, 143));
		System.out.println("iterative = " + findFirstKIterative(root, 143));
		System.out.println("iterative2 = " + findFirstKIterative2(root, 143));
	}
}
