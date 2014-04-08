package book.common;

public class MutableInt {
	private int data;
	public MutableInt() {
		this.data = 0;
	}
	public MutableInt(int data) {
		this.data = data;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	/*
	 * Increment and return the new value.
	 */
	public int increment() {
		return ++this.data;
	}
	/* Decrement and return the new value */
	public int decrement() {
		return --this.data;
	}
}
