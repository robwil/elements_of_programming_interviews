package book.chapter.seven;

import book.common.List;
import book.common.Node;

/**
 * Problem 7.6, Even-Odd Merge
 * Let L be a singly linked list, say L0 -> L1 -> L2 -> L3 -> L4.
 * Write a function evenOdd that will take L and make it into L0 -> L2 -> L4 -> L1 -> L3.
 * That is, all the even numbered indices (starting at 0) will be first, followed by odd-numbered indices.
 * Use constant memory O(1) and only modify next pointer of list elements.
 * 
 * I solved this using an iterative move through the list that maintains three pointers.
 * The only extra dimension I needed to add was the distinction between ending on an odd or even,
 * which I discovered while stepping through my original solution on paper.
 * 
 * The book solution does it in a slightly different way,
 * but both answers are O(N) time and O(1) space.
 * 
 * @author rob
 *
 */
public class Problem07_06 {
	public static void evenOdd(Node head) {
		if (head == null || head.getNext() == null)
			return; // already meets conditions
		boolean currentEven = true;
		Node originalNext = head.getNext();
		Node current = head;
		Node next = head.getNext();
		Node nextnext = head.getNext().getNext();
		while (nextnext != null) {
			current.setNext(nextnext);
			current = next;
			next = nextnext;
			nextnext = nextnext.getNext();
			currentEven = !currentEven; // toggle currentEven
		}
		if (currentEven) {
			current.setNext(originalNext);
			next.setNext(null);
		} else {
			current.setNext(null);
			next.setNext(originalNext);
		}
	}
	
	private static void runCase(int[] arr) {
		List list = List.fromArray(arr);
		
		System.out.println("BEFORE: " + list);
		evenOdd(list.getHead());
		System.out.println("AFTER: " + list);
	}
	
	public static void main(String[] args) {
		int[] a1 = {0, 1, 2, 3, 4};
		runCase(a1);
		int[] a2 = {0, 1, 2, 3, 4, 5};
		runCase(a2);
	}
}
