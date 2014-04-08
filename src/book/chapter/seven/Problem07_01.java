package book.chapter.seven;

import book.common.List;
import book.common.Node;

/**
 * Given two sorted linked lists, combine them into a single sorted linked list.
 * Use O(1) space, i.e. re-use existing nodes.
 * 
 * My solution below works really well, and again I think it is more straight-forward than the books confusing answer.
 * The one downside of the below solution is that my method signature returns a new List. This is somewhat confusing
 * because the user of this method may think l1 and l2 will be untouched, when in reality they will be destroyed.
 *
 */
public class Problem07_01 {

	public static List mergeLists(List l1, List l2) {
		Node p1 = l1.getHead();
		Node p2 = l2.getHead();
		// Handle null cases
		if (p1 == null)
			return new List(p2);
		else if (p2 == null)
			return new List(p1);
		// Ensure p1 points to list containing min element, for convenience.
		if (p2.getData() < p1.getData()) {
			Node tmp = p1;
			p1 = p2;
			p2 = tmp;
		}
		// Setup return value and initial state for loop
		List retVal = new List(p1);
		Node prev = null;
		while (p1 != null && p2 != null) {
			if (p1.getData() > p2.getData()) {
				prev.setNext(p2);;
				prev = p2;
				p2 = p2.getNext();
				prev.setNext(p1);
			} else {
				prev = p1;
				p1 = p1.getNext();
			}
		}
		if (p1 == null)
			prev.setNext(p2);
		return retVal;
	}
	
	public static void runCase(int[] a1, int[] a2) {
		List l1 = List.fromArray(a1);
		List l2 = List.fromArray(a2);
		
		System.out.println("LIST1: " + l1);
		System.out.println("LIST2: " + l2);
		
		List m = mergeLists(l1, l2);
		System.out.println("MERGED: " + m);
	}
	
	public static void main(String[] args) {
		int[] a1 = {2, 5, 7};
		int[] a2 = {3, 11, 15, 17};
		runCase(a1, a2);
		
		int[] b1 = {1, 1, 1};
		int[] b2 = {2, 2, 2};
		runCase(b1, b2);
		
		int[] c1 = {1, 9};
		int[] c2 = {2, 3, 4};
		runCase(c1, c2);
		
		int[] d1 = {2, 4, 5};
		int[] d2 = {1, 3, 6, 7};
		runCase(d1, d2);
	}
}
