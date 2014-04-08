package book.chapter.seven;

import book.common.List;
import book.common.Node;

/**
 * Problem 7.4, p. 67
 * Given two single-linked acyclic lists, return the first
 * element reachable by both lists, if any. Else return null.
 * Must use O(1) additional memory at most.
 *  
 * Initially confused by trying to use fast/slow pointers, I
 * settled on an O(N*M) time solution presented below as findOverlapping.
 * 
 * The book solution is slightly better, with O(N+M) time. It involves
 * first calculating the length of each list, and while doing so
 * checking if both tails are the same (meaning they do overlap). It finds
 * the beginning of the overlap by doing a second iteration through both lists,
 * this time starting the longer list out at position |L1| - |L2|.
 * I implemented this below as findOverlapping2.
 * 
 * @author rob
 *
 */
public class Problem07_04 {
	public static Node findOverlapping(List list1, List list2) {
		if (list1 == null || list2 == null) {
			return null;
		}
		Node current1 = list1.getHead();
		Node current2;
		while (current1 != null) {
			current2 = list2.getHead();
			while (current2 != null) {
				if (current1 == current2) {
					return current1;
				}
				current2 = current2.getNext();
			}
			current1 = current1.getNext();
		}
		return null;
	}
	
	public static Node findOverlapping2(List list1, List list2) {
		if (list1 == null || list2 == null) {
			return null;
		}
		Node current1 = list1.getHead();
		Node current2 = list2.getHead();
		int length1 = 0;
		int length2 = 0;
		while (current1 != null) {
			length1++;
			if (current1.getNext() != null)
				current1 = current1.getNext();
			else
				break; // so that current1 will be tail of list1
		}
		while (current2 != null) {
			length2++;
			if (current2.getNext() != null)
				current2 = current2.getNext();
			else
				break; // so that current1 will be tail of list1
		}
		if (current1 != current2) {
			return null; // no overlap
		}
		// We know there is overlap, so iterate through both, starting longer list ahead by their size difference.
		// For convenience, set current1 to head of larger list and length1 to the difference of the two.
		if (length2 > length1) {
			current1 = list2.getHead();
			current2 = list1.getHead();
			length1 = length2 - length1;
		} else {
			current1 = list1.getHead();
			current2 = list2.getHead();
			length1 = length1 - length2;
		}
		// Move current1 ahead by size difference of two lists
		while (length1 > 0) {
			current1 = current1.getNext();
			length1--;
		}
		// Iterate through both together until they are equal.
		while (current1 != current2) {
			current1 = current1.getNext();
			current2 = current2.getNext();
		}
		return current1;
	}
	
	public static void main(String[] args) {
		int[] arr = {1, 7, 9, 2};
		int[] arr2 = {7, 11, 8, 13};
		List list1 = List.fromArray(arr);
		List list2 = List.fromArray(arr2);
		
		System.out.println("No overlap = " + findOverlapping(list1, list2));
		System.out.println("No overlap 2 = " + findOverlapping2(list1, list2));
		
		// create overlap, pointing list1's 2 --> list2's 8
		list1.getHead().getNext().getNext().getNext().setNext(list2.getHead().getNext().getNext());
		System.out.println("Overlap = " + findOverlapping(list1, list2).getData());
		System.out.println("Overlap 2 = " + findOverlapping2(list1, list2).getData());
	}
}
