package book.common;

public class Node {
	private int data;
	private Node next;

	public Node() {
		this.data = 0;
		next = null;
	}

	public Node(int data) {
		this.data = data;
		this.next = null;
	}

	public Node(int data, Node next) {
		this.data = data;
		this.next = next;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(new Integer(data).toString());
		Node current = this;
		while (current.getNext() != null) {
			sb.append(", " + current.getNext().getData());
			current = current.getNext();
		}
		return sb.toString();
	}
}