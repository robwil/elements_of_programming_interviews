package book.chapter.fourteen;

import book.common.BSTNode;

/**
 * Problem 14.5, p. 109
 * Given a BST and a key k, return the first element with a key greater than k,
 * that would appear in an in-order walk. Return null if an element with k does not exist
 * in the tree, or if there is no element with a greater key.
 * 
 * --- Commentary before looking at book answer ---
 * 
 * My solution findElemLargerK1(..) works in O(logN) time and O(1) space.
 * Initially when writing on paper I wrote a vastly over-simplified solution,
 * but corrected it as I stepped through an example tree. However, this has a
 * bug where it only finds the answer if the key greater than k is "lower"
 * on the tree than k itself. i.e. it doesn't retest when returning from recursion
 * 
 * I then implemented findElemLargerK2(..) which handles this through Exception throwing.
 * This is a fairly hacky and confusing way to do it, but it works. Really, I used the Exception
 * as a way to signal the parent (caller) that k was found.
 * 
 * If we don't have parent pointers, then this can't be done iteratively. Recursion is needed,
 * but there must be some way to return data to the caller. Some alternatives to my Exception
 * method is returning a Tuple or Pair, which would include the updated "found" value. Another
 * case would be to make a MutableBoolean value and check if it has been changed by calling.
 * 
 * It occurs to me that my code could be cleaned up a lot with the MutableBoolean because 
 * it could be structured as a true InOrder walk. What I'm doing now is kind of like a Pre-order walk,
 * since the "search code" happens before traversing, not after coming back. However, that would
 * change the time complexity to O(N) instead of O(logN).
 * 
 * --- Commentary after looking at book answer ---
 * 
 * The book does find a way to implement this iteratively. It turns out that I was mistaken,
 * thinking I would need a full parent traversal, but instead I only need to keep track of the first
 * element larger than k which I find.
 * 
 * @author rob
 *
 */
public class Problem14_05 {
	public static BSTNode findElemLargerK1(BSTNode root, int k) {
		return findElemLargerK1(root, k, false);
	}
	
	/**
	 * @param root BST to search through
	 * @param k the key we must find before looking for a greater element
	 * @param found whether we have visited k yet
	 * @return the first element ("first" defined in in-order walk context) with key greater than k.
	 */
	private static BSTNode findElemLargerK1(BSTNode root, int k, boolean found) {
		// Base case
		if (root == null)
			return null;
		int current = (Integer)root.getData(); // forcing integer key types here. In more generic solution we would use Comparable
		// Search code
		// We don't start looking for "answer" until found = true.
		// "first" elem with key greater than k requires no left subtree
		if (found && current > k && root.getLeft() == null)
			return root;
		else if (current == k) // if we find k, mark found=true
			found = true;
		// Traversal code
		if (current <= k)
			return findElemLargerK1(root.getRight(), k, found);
		return findElemLargerK1(root.getLeft(), k, found);
	}
	
	
	public static BSTNode findElemLargerK2(BSTNode root, int k) {
		try {
			return findElemLargerK2(root, k, false);
		} catch (FoundElemButNoneGreaterException ex) {
			return null;
		}
	}
	
	/**
	 * @param root BST to search through
	 * @param k the key we must find before looking for a greater element
	 * @param found whether we have visited k yet
	 * @return the first element ("first" defined in in-order walk context) with key greater than k.
	 * @throws FoundElemButNoneGreaterException 
	 */
	private static BSTNode findElemLargerK2(BSTNode root, int k, boolean found) throws FoundElemButNoneGreaterException {
		// Base case
		if (root == null) {
			// If we get to "bottom" of the BST, but found == true, then it is possible we have an element
			// greater than k. We throw this exception to parent, who will settle the search.
			if (found) {
				throw new FoundElemButNoneGreaterException();
			}
			return null;
		}
		int current = (Integer)root.getData(); // forcing integer key types here. In more generic solution we would use Comparable
		// Search code
		// We don't start looking for "answer" until found = true.
		// "first" elem with key greater than k requires no left subtree
		if (found && current > k && root.getLeft() == null)
			return root;
		else if (current == k) // if we find k, mark found=true
			found = true;
		// Traversal code
		try {
			if (current <= k)
				return findElemLargerK2(root.getRight(), k, found);
			return findElemLargerK2(root.getLeft(), k, found);
		} catch (FoundElemButNoneGreaterException ex) {
			// Receiving this exception means found == true but the element greater than k
			// wasn't found below. We pass the exception up to parent until we find an element
			// greater than k.
			if (current > k)
				return root;
			else
				throw ex;
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
		
		BSTNode.inorder(root);
		
		System.out.println();
		System.out.println("Expected = 70");
		System.out.println("findElemLargerK1 = " + findElemLargerK1(root, 60));
		System.out.println("findElemLargerK2 = " + findElemLargerK2(root, 60));
		
		System.out.println();
		System.out.println("Expected = 31");
		System.out.println("findElemLargerK1 = " + findElemLargerK1(root, 30));
		System.out.println("findElemLargerK2 = " + findElemLargerK2(root, 30));
		
		System.out.println();
		System.out.println("Expected = 50");
		System.out.println("findElemLargerK1 = " + findElemLargerK1(root, 40));
		System.out.println("findElemLargerK2 = " + findElemLargerK2(root, 40));
		
		System.out.println();
		System.out.println("Expected = null");
		System.out.println("findElemLargerK1 = " + findElemLargerK1(root, 10));
		System.out.println("findElemLargerK2 = " + findElemLargerK2(root, 10));
		
		System.out.println();
		System.out.println("Expected = null");
		System.out.println("findElemLargerK1 = " + findElemLargerK1(root, 80));
		System.out.println("findElemLargerK2 = " + findElemLargerK2(root, 80));
	}
}
