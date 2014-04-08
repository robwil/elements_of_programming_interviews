package book.common;

public class BSTNode {
	private BSTNode left;
	private BSTNode right;
	private BSTNode parent;
	private Object data;
	public BSTNode() {
		super();
		this.left = null;
		this.right = null;
		this.parent = null;
		this.data = null;
	}
	public BSTNode(Object data) {
		super();
		this.left = null;
		this.right = null;
		this.parent = null;
		this.data = data;
	}
	public BSTNode(BSTNode left, BSTNode right, Object data) {
		super();
		this.left = left;
		this.right = right;
		this.parent = null;
		this.data = data;
	}
	public BSTNode(BSTNode left, BSTNode right, BSTNode parent, Object data) {
		super();
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.data = data;
	}
	public BSTNode getLeft() {
		return left;
	}
	public void setLeft(BSTNode left) {
		this.left = left;
	}
	public BSTNode getRight() {
		return right;
	}
	public void setRight(BSTNode right) {
		this.right = right;
	}
	public BSTNode getParent() {
		return parent;
	}
	public void setParent(BSTNode parent) {
		this.parent = parent;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public static void inorder(BSTNode current) {
		if (current == null)
			return;
		inorder(current.getLeft());
		System.out.print(current.getData() + " ");
		inorder(current.getRight());
	}
	
	public static void preorder(BSTNode current) {
		if (current == null)
			return;
		System.out.print(current.getData() + " ");
		preorder(current.getLeft());
		preorder(current.getRight());
	}
	
	public String originalToString() {
		return super.toString();
	}
	
	public String toString() {
		return data.toString();
	}
	
}
