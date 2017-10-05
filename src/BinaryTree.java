public interface BinaryTree<T> extends Tree<T> {
	public boolean hasLeft( Pos<T> pos);
	public boolean hasRight( Pos<T> pos);
	public Pos<T> left( Pos<T> pos );
	public Pos<T> right( Pos<T> pos );
}