package book.chapter.fifteen;

public class Building {
	private int left;
	private int right;
	private int height;
	
	public Building(int left, int right, int height) {
		super();
		this.left = left;
		this.right = right;
		this.height = height;
	}
	
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
