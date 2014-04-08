package book.chapter.nine;

import java.util.Arrays;

import book.common.BSTNode;

/**
 * Problem 9.7, p. 80
 * Given an in-order tree traversal result, and either a pre- or post-order traversal,
 * reconstruct the binary tree.
 * 
 * I chose to use pre-order + in-order because I am not insane and pre-order is much simpler to reason about.
 * I stared blankly at this problem for a while, trying to do weird stuff involving comparing the two traversals.
 * Eventually I realized that the root is always determined by the first in the preorder, and then by finding
 * the root in the in-order we can figure out what the left and right subtrees are made up of. Recursing with those
 * subtrees lets them be reconstructed also (progressively finding the sub-roots of sub-trees).
 * 
 * My solution worked perfectly on first attempt!
 * 
 * Note: Again here I use BSTNode because I have it, but this tree does not have to be a binary search tree.
 * @author rob
 *
 */
public class Problem09_07 {

	public static BSTNode reconstructTree(Object[] preorder, Object[] inorder) {
		return reconstructTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
	}
	
	/**
	 * Find item in arr, between the ranges [startIndex, endIndex].
	 * @param arr array to search through
	 * @param startIndex
	 * @param endIndex
	 * @param item
	 * @return index where the item was found
	 * @throws RuntimeException if item is not found in the array/range
	 */
	private static int find(Object[] arr, int startIndex, int endIndex, Object item) {
		for (int i = startIndex; i <= endIndex; i++) {
			if (arr[i].equals(item))
				return i;
		}
		throw new RuntimeException("Item not found in array");
	}
	
	private static BSTNode reconstructTree(Object[] preorder, int preorderStart, int preorderEnd,
										   Object[] inorder, int inorderStart, int inorderEnd) {
		
		int N = (inorderEnd - inorderStart + 1);
		if (N <= 0) return null; // empty subtree base case
		if (N == 1) return new BSTNode(preorder[preorderStart]); // leaf node base case
		
		// Normal case
		Object root = preorder[preorderStart];
		int index = find(inorder, inorderStart, inorderEnd, root); // find index of root node's occurence in inorder array
		int leftSize = (index - inorderStart);
		int rightSize = (inorderEnd - index);
		
		// construct tree recursively
		return new BSTNode(reconstructTree(preorder, preorderStart+1, preorderStart+leftSize, inorder, inorderStart, index-1), // left subtree
				reconstructTree(preorder, preorderStart+leftSize+1, preorderStart+leftSize+rightSize, inorder, index+1, inorderEnd), // right subtree
				root); // data for this node
	}
	
	public static void main(String[] args) {
		// book example
		String[] inorder = {"F", "B", "A", "E", "H", "C", "D", "I", "G"};
		String[] preorder = {"H", "B", "F", "E", "A", "C", "D", "G", "I"};
		
		BSTNode tree = reconstructTree(preorder, inorder);
		System.out.println("Expected inorder = " + Arrays.toString(inorder));
		System.out.println("Actual inorder = ");
		BSTNode.inorder(tree);
		System.out.println("\nExpected preorder = " + Arrays.toString(preorder));
		System.out.println("Actual preorder = ");
		BSTNode.preorder(tree);
		
		// more "empty" tree example
		String[] inorder2 = {"A", "B", "H", "C", "Z"};
		String[] preorder2 = {"H", "B", "A", "C", "Z"};
		
		BSTNode tree2 = reconstructTree(preorder2, inorder2);
		System.out.println("Expected inorder = " + Arrays.toString(inorder2));
		System.out.println("Actual inorder = ");
		BSTNode.inorder(tree2);
		System.out.println("\nExpected preorder = " + Arrays.toString(preorder2));
		System.out.println("Actual preorder = ");
		BSTNode.preorder(tree2);
	}
}
