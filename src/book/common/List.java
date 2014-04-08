package book.common;

public class List {
	private Node head;

	public List() {
		head = null;
	}

	public List(Node head) {
		this.head = head;
	}

	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}
	
	public String toString() {
		return head.toString();
	}
	
	public static List fromArray(int[] a) {
		Node next = null;
		for (int i = a.length - 1; i >= 0; i--) {
			next = new Node(a[i], next);
		}
		return new List(next);
	}
}