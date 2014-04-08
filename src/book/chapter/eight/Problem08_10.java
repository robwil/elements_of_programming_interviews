package book.chapter.eight;

/**
 * Implement a Circular Queue, using two integers and 1 array.
 * Dynamically resize it as needed such that inserts are amortized O(1).
 * Implement size(), enqueue(), dequeue(), and constructor.
 * 
 * I implemented with Java Generics, called it CircularQueue (separate file).
 * This is the test code.
 * 
 * My implementation was mostly correct, but I got confused and tried to do some weird
 * stuff where the just-initialized array had "first" = -1, which was not needed and
 * confused some things. I also forgot to reset first/last values after resizing array.
 * 
 * @author rob
 *
 */
public class Problem08_10 {
	public static void main(String[] args) {
		CircularQueue<Integer> q = new CircularQueue<Integer>(3);
		// Test basic inserts and removes
		q.enqueue(1);
		System.out.println("After inserting 1: " + q);
		q.enqueue(2);
		System.out.println("After inserting 2: " + q);
		System.out.println("After dequeuing " + q.dequeue() + ": " + q);
		// Test expanding array (and also looping around)
		q.enqueue(3); q.enqueue(3); q.enqueue(3);
		System.out.println("After adding 3 3's: " + q);
	}
}
