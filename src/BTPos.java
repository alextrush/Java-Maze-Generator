
public interface BTPos<E> extends Pos<E> {
	public BTPos<E> parent();
	public BTPos<E> left();
	public BTPos<E> right();
	public void setElem( E elem );
	public void setParent( BTPos<E> parent );
	public void setLeft( BTPos<E> left );
	public void setRight( BTPos<E> right );
}
