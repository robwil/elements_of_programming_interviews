package book.chapter.eight;

public class NodeWithMax {
	private Comparable node;
	private Comparable max;

	public NodeWithMax() {
		setNode(null);
		setMax(null);
	}
	public NodeWithMax(Comparable node, Comparable max) {
		this.setNode(node);
		this.setMax(max);
	}
	public Comparable getNode() {
		return node;
	}
	public void setNode(Comparable node) {
		this.node = node;
	}
	public Comparable getMax() {
		return max;
	}
	public void setMax(Comparable max) {
		this.max = max;
	}
	
}
