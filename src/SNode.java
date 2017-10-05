public class SNode<N> implements Pos<N> {
	
	private N elem;
	private SNode<N> next;
	
	public SNode( N elem, SNode<N> next ){
		this.elem = elem;
		this.next = next;
	}
	
	public N elem(){
		return elem;
	}
	
	public SNode<N> next(){
		return next;
	}
	
	public void setElem( N elem ){
		this.elem = elem;
	}
	
	public void setNext( SNode<N> next ){
		this.next = next;
	}
	
	public static void main( String [] args ){
		
	}
}