package book.chapter.twelve;

public class Node {
	private HTEntry data;
	private Node next;

	public Node(HTEntry data) {
		this.data = data;
		this.next = null;
	}

	public Node(HTEntry data, Node next) {
		this.data = data;
		this.next = next;
	}

	public HTEntry getData() {
		return data;
	}

	public void setData(HTEntry data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(data.toString());
		Node current = this;
		while (current.getNext() != null) {
			sb.append(", " + current.getNext().getData());
			current = current.getNext();
		}
		return sb.toString();
	}
}