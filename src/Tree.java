public interface Tree<E> {
	public int size();
	public boolean isEmpty();
	public boolean isInternal( Pos<E> pos );
	public boolean isExternal( Pos<E> pos );
	public boolean isRoot( Pos<E> pos );
	public Pos<E> root();
	public Pos<E> parent( Pos<E> pos );
	public Iterable<Pos<E>> children( Pos<E> pos );
	public E replace( Pos<E> pos, E elem );
	public java.util.Iterator<E> iterator();
	public Iterable<Pos<E>> positions();
}
