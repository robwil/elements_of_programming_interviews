package book.chapter.eight;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Create a stack with O(1) pop and push,
 * that also supports an O(1) max() operation that returns the max value stored in the stack so far.
 * You may use O(n) space.
 *
 */
public class Problem08_01_StackWithMax {
	private LinkedList<NodeWithMax> internalList;
	private Comparable max;
	
	public Problem08_01_StackWithMax() {
		internalList = new LinkedList<NodeWithMax>();
		max = null;
	}
	
	public void push(Comparable node) {
		if (max == null || max.compareTo(node) < 0)
			max = node;
		NodeWithMax n = new NodeWithMax(node, max);
		internalList.addFirst(n);
	}
	
	public Comparable pop() {
		return internalList.remove().getNode();
	}
	
	public Comparable max() {
		return internalList.getFirst().getMax();
	}
	
	public static void pushHelper(Problem08_01_StackWithMax stack, Comparable node) {
		System.out.println();
		System.out.println("PUSHing " + node);
		stack.push(node);
		System.out.println("MAX = " + stack.max());
	}
	
	public static void popHelper(Problem08_01_StackWithMax stack) {
		System.out.println();
		String operation = "POP";
		try {
			System.out.println("POPed " + stack.pop());
			operation = "MAX";
			System.out.println("MAX = " + stack.max());
		} catch (NoSuchElementException ex) {
			System.out.println("Caught NoSuchElementException during " + operation);
		}
	}
	
	public static void main(String[] args) {
		Problem08_01_StackWithMax stack = new Problem08_01_StackWithMax();
		
		Integer i = 5;
		pushHelper(stack, i);
		i = 10;
		pushHelper(stack, i);
		i = 1;
		pushHelper(stack, i);
		i = 17;
		pushHelper(stack, i);
		
		popHelper(stack);
		popHelper(stack);
		popHelper(stack);
		popHelper(stack);
		popHelper(stack);
	}
}
