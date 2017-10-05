
public class BTNode<N> implements BTPos<N> {

	protected N elem;
	protected BTPos<N> parent, left, right;
	
	public BTNode (N elem, BTPos<N> parent, BTPos<N> left, BTPos<N> right ){
		this.elem = elem;
		this.parent = parent;
		this.left = left;
		this.right = right;
	}
	
	public N elem() {
		return elem;	
	}

	public BTPos<N> parent() {
		return parent;
	}

	public BTPos<N> left() {
		return left;
	}

	public BTPos<N> right() {
		return right;
	}

	public void setElem(N elem) {
		this.elem = elem;
	}

	public void setParent(BTPos<N> parent) {
		this.parent = parent;
	}

	public void setLeft(BTPos<N> left) {
		this.left = left;
	}

	public void setRight(BTPos<N> right) {
		this.right = right;
	}
}
