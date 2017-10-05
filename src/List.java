
public interface List<L> extends Iterable<L>{
	public int size();
	public boolean isEmpty();
	public Pos<L> first();
	public Pos<L> last();
	public Pos<L> prev( Pos<L> pos );
	public Pos<L> next( Pos<L> pos );
	public Pos<L> addFirst( L elem );
	public Pos<L> addLast( L elem );
	public Pos<L> addBefore( Pos<L> pos, L elem );
	public Pos<L> addAfter( Pos<L> pos, L elem );
	public L replace( Pos<L> pos, L elem );
	public L remove( Pos<L> pos );
	public Iterable<Pos<L>> positions();
}
