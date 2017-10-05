
public class DNode<N> implements Pos<N> {
	private N elem;
	private DNode<N> prev;
	private DNode<N> next;
	
	public DNode ( N elem, DNode<N> prev, DNode<N> next ){
		this.elem = elem;
		this.prev = prev;
		this.next = next;
	}
	
	public N elem(){
		return elem;
	}
	
	public DNode<N> prev(){
		return prev;
	}
	
	public DNode<N> next(){
		return next;
	}
	
	public void setElem( N elem ){
		this.elem = elem;
	}
	
	public void setPrev( DNode<N> prev ){
		this.prev = prev;
	}
	
	public void setNext( DNode<N> next ){
		this.next = next;
	}
	
	public String toString(){
		return elem.toString();
	}
}
