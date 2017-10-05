
public class IndexPos<E> implements Pos<E> {

	protected int index;
	protected E elem;
	
	public IndexPos( E elem, int index ){
		this.elem = elem;
		this.index = index;
	}
	
	public E elem() {
		return elem;
	}

	public int index(){
		return index;
	}
	
	public E setElem( E elem ){
		E temp = this.elem;
		this.elem = elem;
		return temp;
	}
	
	public String toString(){
		return elem.toString();
	}
}
